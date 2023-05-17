package com.example.apporderfood.inteface

import com.example.apporderfood.model.MonAn

interface InterfaceGioHang {
    fun updateClick(monAn: MonAn, numInCart:Int)
    fun xoaClick(monAn: MonAn)
}