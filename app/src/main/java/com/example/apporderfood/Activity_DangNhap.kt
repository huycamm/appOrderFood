package com.example.apporderfood

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.apporderfood.databinding.ActivityDangNhapBinding
import com.example.apporderfood.model.TaiKhoan
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private lateinit var bindingDN: ActivityDangNhapBinding
class Activity_DangNhap : AppCompatActivity() {
    lateinit var dbrf:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingDN = ActivityDangNhapBinding.inflate(layoutInflater)
        setContentView(bindingDN.root)

        bindingDN.txtDN.setOnClickListener {
            val intent = Intent(this, Activity_DangKy::class.java)
            startActivity(intent)
        }

        actionDangNhap()
    }

    private fun actionDangNhap() {
        bindingDN.btnDangNhap.setOnClickListener {
            val phone = bindingDN.edtPhoneDN.text.toString()
            val pass = bindingDN.edtPassDN.text.toString()
            if(phone.equals("") || pass.equals("")){
                Toast.makeText(this, "Nhập đầy đủ thông tin đăng nhập!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            dbrf = FirebaseDatabase.getInstance().getReference("account")
            dbrf.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        var check = false
                        for (account in snapshot.children){
                            val taiKhoan = account.getValue(TaiKhoan::class.java)
                            if(taiKhoan?.getPhone().equals(phone) && taiKhoan?.getPassword().equals(pass)){
                                check = true
                                val intent = Intent(this@Activity_DangNhap,MainActivity::class.java)
                                intent.putExtra("taikhoan",taiKhoan)
                                //finish()
                                startActivity(intent)
                            }
                        }
                        if (!check){
                            val diolog = AlertDialog.Builder(this@Activity_DangNhap)
                            diolog.setTitle("Đăng nhập")
                            diolog.setMessage("Sai tên đăng nhập hoặc mật khẩu")
                            diolog.setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->
                                bindingDN.edtPhoneDN.setText("")
                                bindingDN.edtPassDN.setText("")
                                bindingDN.edtPhoneDN.requestFocus()
                            })
                            diolog.show()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}