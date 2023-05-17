package com.example.apporderfood.model

import java.io.Serializable

class MonAn :Serializable{
    private lateinit var id:String
    private lateinit var title:String
    private lateinit var picture:String
    private  var price:Int = 0
    private  var score:Double = 0.0
    private  var energy:Int = 0
    private var time:Int = 0
    private lateinit var desc:String
    private var numInCart:Int = 0
    private lateinit var idTaiKhoan:String

    constructor() {

    }

    constructor(id: String, title: String, picture: String, price: Int, numInCart: Int) {
        this.id = id
        this.title = title
        this.picture = picture
        this.price = price
        this.numInCart = numInCart
    }

    fun getTitle():String{
        return this.title
    }
    fun getScore():Double{
        return this.score
    }
    fun getTime():Int{
        return this.time
    }
    fun getPicture():String{
        return this.picture
    }
    fun getDesc():String{
        return this.desc
    }
    fun getEnergy():Int{
        return this.energy
    }
    fun getPrice():Int{
        return this.price
    }
    fun getNumInCart():Int{
        return this.numInCart
    }
    fun getId():String{
        return this.id
    }
    fun setNumInCart(i:Int){
        this.numInCart = i
    }
    fun getIdTaiKhoan():String{
        return this.idTaiKhoan
    }
    fun setIdTaiKhoan(id:String){
       this.idTaiKhoan = id
    }
}