package com.example.apporderfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.apporderfood.databinding.ActivityDangKyBinding
import com.example.apporderfood.model.TaiKhoan
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private lateinit var bindingDK:ActivityDangKyBinding
class Activity_DangKy : AppCompatActivity() {
    lateinit var dbrf:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingDK = ActivityDangKyBinding.inflate(layoutInflater)
        setContentView(bindingDK.root)

        bindingDK.txtDK.setOnClickListener {
            val intent = Intent(this, Activity_DangNhap::class.java)
            startActivity(intent)
        }

        actionDangKy()
    }

    private fun actionDangKy() {
        bindingDK.btnDangKy.setOnClickListener {
            dbrf = FirebaseDatabase.getInstance().getReference("account")
            val confirmPass = bindingDK.edtConfirmDK.text.toString()
            val id = dbrf.push().key!!
            val userName = bindingDK.edtUserDK.text.toString()
            val phone = bindingDK.edtPhoneDK.text.toString()
            val password = bindingDK.edtPassDK.text.toString()
            val account = TaiKhoan(id,userName,phone, password)

            if(!password.equals(confirmPass)){
                Toast.makeText(this@Activity_DangKy, "Mật khẩu phải trùng khớp!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(account.checkPass() && account.checkPhone()){
                dbrf.addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()){
                            for(account in snapshot.children){
                                //Kiểm tra xem sđt đã được dùng trước đó hay chưa
                                if(account.getValue(TaiKhoan::class.java)?.getPhone().equals(phone)){
                                    Toast.makeText(this@Activity_DangKy, "Số điện thoại đã được dùng!", Toast.LENGTH_SHORT).show()
                                    return
                                }
                            }
                        }
                        dbrf.child(id).setValue(account).addOnSuccessListener {
                            Toast.makeText(this@Activity_DangKy, "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show()
                            bindingDK.edtUserDK.setText("")
                            bindingDK.edtPhoneDK.setText("")
                            bindingDK.edtConfirmDK.setText("")
                            bindingDK.edtPassDK.setText("")
                            bindingDK.edtUserDK.requestFocus()
                        }.addOnFailureListener { err ->
                            Toast.makeText(this@Activity_DangKy, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }else{
                Toast.makeText(this@Activity_DangKy, "Số điện thoại phải đúng đinh dạng và mật khẩu phải có ít nhất 6 kí tự", Toast.LENGTH_SHORT).show()
            }
        }
    }
}