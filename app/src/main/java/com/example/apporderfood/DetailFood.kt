package com.example.apporderfood

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.apporderfood.database.DatabaseGioHang
import com.example.apporderfood.model.MonAn
import com.example.apporderfood.model.TaiKhoan
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso

class DetailFood : AppCompatActivity() {
    lateinit var imageFood:ImageView
    lateinit var txtTime:TextView
    lateinit var txtScore:TextView
    lateinit var txtEnergy:TextView
    lateinit var txtDesc:TextView
    lateinit var txtTru:TextView
    lateinit var txtCong:TextView
    lateinit var txtSoLuong:TextView
    lateinit var txtTitle:TextView
    lateinit var txtPrice:TextView
    lateinit var imageBack:ImageView
    lateinit var btnAddToCart:Button

    lateinit var db: DatabaseGioHang
    lateinit var rs:Cursor

    lateinit var monAn: MonAn
    lateinit var taiKhoan:TaiKhoan

    var soLuong = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_food)

        db = DatabaseGioHang(this)

        monAn = intent.getSerializableExtra("monAn")!! as MonAn
        taiKhoan = intent.getSerializableExtra("taikhoan")!! as TaiKhoan

        anhXa()
        setView()
        setClick()

    }

    private fun setClick() {
        imageBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("taikhoan", taiKhoan)
            finish()
            startActivity(intent)
        }
        txtSoLuong.setText(soLuong.toString())
        txtTru.setOnClickListener {
            if(soLuong>=2){
                soLuong--
            }
            txtSoLuong.setText(soLuong.toString())
        }
        txtCong.setOnClickListener {
            soLuong++
            txtSoLuong.setText(soLuong.toString())
        }
        btnAddToCart.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.custom_bottom_sheet_diolog,null,false)
            bottomSheetDialog.setContentView(view)
            val image = view.findViewById<ImageView>(R.id.imgFoodBottomDiolog)
            val title = view.findViewById<TextView>(R.id.txtTenMonAn)
            val price = view.findViewById<TextView>(R.id.txtGiaTien)
            val soLuongAddToCart = view.findViewById<TextView>(R.id.txtSoLuongBT)
            val btnHuy = view.findViewById<Button>(R.id.btnHuyThem)
            val btnThem = view.findViewById<Button>(R.id.btnThem)

            Picasso.get().load(monAn.getPicture()).into(image)
            price.setText((soLuong*monAn.getPrice()).toString()+" đ")
            soLuongAddToCart.setText("Số lượng: "+soLuong)
            title.setText(monAn.getTitle())

            btnHuy.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            btnThem.setOnClickListener {
                rs = db.getMonAn(taiKhoan)
                while (rs.moveToNext()){
                    if(rs.getString(1).equals(monAn.getTitle())){
                        Toast.makeText(this, "Món ăn đã có trong giỏ hàng", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }
                monAn.setNumInCart(soLuong)
                monAn.setIdTaiKhoan(taiKhoan.getId())
                db.insertMonAn(monAn)
                bottomSheetDialog.dismiss()
                btnAddToCart.setText("Đã thêm vào giỏ hàng")
                btnAddToCart.setBackgroundResource(R.drawable.custom_btn_huy)
                btnAddToCart.isEnabled = false
            }

            bottomSheetDialog.setCancelable(false)
            bottomSheetDialog.show()
        }
    }

    private fun setView() {
        Picasso.get().load(monAn.getPicture()).into(imageFood)
        txtTime.setText(monAn.getTime().toString())
        txtTitle.setText(monAn.getTitle())
        txtPrice.setText(monAn.getPrice().toString()+" đ")
        txtDesc.setText(monAn.getDesc())
        txtEnergy.setText(monAn.getEnergy().toString())
        txtScore.setText(monAn.getScore().toString())
    }

    private fun anhXa() {
        imageFood = findViewById(R.id.imageFood)
        txtTime = findViewById(R.id.textTime)
        txtScore = findViewById(R.id.textScore)
        txtEnergy = findViewById(R.id.textCal)
        txtDesc = findViewById(R.id.textDesc)
        txtTru = findViewById(R.id.txtTru)
        txtCong = findViewById(R.id.txtCong)
        txtSoLuong = findViewById(R.id.txtSoLuong)
        txtTitle = findViewById(R.id.textTitle)
        txtPrice = findViewById(R.id.textPrice)
        imageBack = findViewById(R.id.imageBack)
        btnAddToCart = findViewById(R.id.btnAdd)
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("taikhoan", taiKhoan)
        finish()
        startActivity(intent)
    }
}