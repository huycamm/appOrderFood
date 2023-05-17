package com.example.apporderfood.model

import java.io.Serializable

class LichSu:Serializable {
    private lateinit var id:String
    private lateinit var hoTen:String
    private lateinit var phone:String
    private lateinit var diaChi:String
    private lateinit var thucDon:String
    private lateinit var ngayDat:String
    private lateinit var tongTien:String
    private lateinit var thanhToan:String
    private lateinit var idTaiKhoan:String

    constructor(){

    }

    constructor(id: String, hoTen: String, phone: String, diaChi: String, thucDon: String, ngayDat: String, tongTien: String, thanhToan: String,idTK:String) {
        this.id = id
        this.hoTen = hoTen
        this.phone = phone
        this.diaChi = diaChi
        this.thucDon = thucDon
        this.ngayDat = ngayDat
        this.tongTien = tongTien
        this.thanhToan = thanhToan
        this.idTaiKhoan = idTK
    }

    fun getId():String{
        return this.id
    }
    fun getHoTen():String{
        return this.hoTen
    }
    fun getPhone():String{
        return this.phone
    }
    fun getDiaChi():String{
        return this.diaChi
    }
    fun getThucDon():String{
        return this.thucDon
    }
    fun getNgayDat():String{
        return this.ngayDat
    }
    fun getTongTien():String{
        return this.tongTien
    }
    fun getThanhToan():String{
        return this.thanhToan
    }
    fun getIdTaiKhoan():String{
        return this.idTaiKhoan
    }

}