package com.example.apporderfood.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.apporderfood.model.MonAn
import com.example.apporderfood.model.TaiKhoan

lateinit var db:SQLiteDatabase
lateinit var rs:Cursor
class DatabaseGioHang(val context: Context):SQLiteOpenHelper(context, "giohang", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table giohang(id integer primary key autoincrement, " +
                "title text , " +
                "picture text, " +
                "price integer, " +
                "numincart integer, " +
                "idtaikhoan text)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun getMonAn(taiKhoan: TaiKhoan):Cursor{
        db = this.readableDatabase
        rs = db.rawQuery("select * from giohang where idtaikhoan = '${taiKhoan.getId()}'", null)

        return rs
    }

    fun insertMonAn(monAn: MonAn){
        db = this.writableDatabase
        val cv = ContentValues()
        cv.put("title", monAn.getTitle())
        cv.put("picture", monAn.getPicture())
        cv.put("price", monAn.getPrice())
        cv.put("numincart", monAn.getNumInCart())
        cv.put("idtaikhoan", monAn.getIdTaiKhoan())
        db.insert("giohang",null, cv)

        db.close()
    }

    fun updateNumInCart(monAn: MonAn, soLuong:Int){
        db = this.writableDatabase
        val cv = ContentValues()
        cv.put("numincart", soLuong)
        db.update("giohang", cv, "id=?", arrayOf(monAn.getId()))
        db.close()
    }

    fun deleteMonAn(monAn: MonAn){
        db = this.writableDatabase
        db.delete("giohang","id=?", arrayOf(monAn.getId()))
    }

}