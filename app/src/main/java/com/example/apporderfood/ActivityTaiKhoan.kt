package com.example.apporderfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.apporderfood.model.TaiKhoan
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ActivityTaiKhoan : AppCompatActivity() {
    lateinit var taiKhoan: TaiKhoan
    lateinit var txtTenTaiKhoan:TextView
    lateinit var txtSoDienThoai:TextView
    lateinit var txtMatKhau:TextView
    lateinit var btnChange:Button
    lateinit var edtPassNew:EditText
    lateinit var edtCfPass:EditText
    lateinit var btnBack:ImageView

    lateinit var dbrf:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tai_khoan)

        taiKhoan = intent.getSerializableExtra("taikhoan") as TaiKhoan

        anhXa()
        initView()
    }

    private fun initView() {
        txtTenTaiKhoan.setText(taiKhoan.getUserName())
        txtMatKhau.setText(taiKhoan.getPassword())
        txtSoDienThoai.setText(taiKhoan.getPhone())

        btnChange.setOnClickListener {
            val pass = edtPassNew.text.toString()
            val cfPass = edtCfPass.text.toString()
            if(pass.equals("") || cfPass.equals("")){
                Toast.makeText(this, "Nhập đầy đủ mật khẩu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (txtMatKhau.text.toString().equals(pass)){
                Toast.makeText(this, "Mật khẩu mới phải khác mật khẩu cũ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!pass.equals(cfPass)){
                Toast.makeText(this, "Mật khẩu không khớp nhau", Toast.LENGTH_SHORT).show()
            }else{
                dbrf = FirebaseDatabase.getInstance().getReference("account")
                taiKhoan.setPassword(pass)
                dbrf.child(taiKhoan.getId()).setValue(taiKhoan)
                txtMatKhau.setText(pass)
                edtPassNew.setText("")
                edtCfPass.setText("")
                Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun anhXa() {
        txtTenTaiKhoan = findViewById(R.id.txtTenDangNhap)
        txtSoDienThoai = findViewById(R.id.txtSoDienThoaiTT)
        txtMatKhau = findViewById(R.id.txtMatKhauTT)
        btnChange = findViewById(R.id.btnChange)
        edtPassNew = findViewById(R.id.edtPassNew)
        edtCfPass = findViewById(R.id.edtConfirmPassNew)
        btnBack = findViewById(R.id.imageBackTK)
    }
}