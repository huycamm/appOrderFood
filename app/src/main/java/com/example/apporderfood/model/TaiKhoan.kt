package com.example.apporderfood.model

import android.text.TextUtils
import android.util.Patterns
import java.io.Serializable

class TaiKhoan:Serializable {
    private lateinit var id:String
    private lateinit var userName:String
    private lateinit var phone:String
    private lateinit var password:String

    constructor(){

    }

    constructor(id: String, userName: String, phone: String, password: String) {
        this.id = id
        this.userName = userName
        this.phone = phone
        this.password = password
    }

    fun getId():String{
        return this.id
    }

    fun getUserName():String{
        return this.userName
    }
    fun getPhone():String{
        return this.phone
    }
    fun getPassword():String{
        return this.password
    }
    fun setPassword(newPass:String){
        this.password = newPass
    }

    fun checkPhone():Boolean{
        return !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches()
    }
    fun checkPass():Boolean{
        return !TextUtils.isEmpty(password) && password.length>=6
    }

}