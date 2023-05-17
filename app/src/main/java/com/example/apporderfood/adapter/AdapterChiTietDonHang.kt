package com.example.apporderfood.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.apporderfood.R
import com.example.apporderfood.model.MonAn

class AdapterChiTietDonHang(val activity: Activity, val ds:MutableList<MonAn>):ArrayAdapter<MonAn>(activity, R.layout.chi_tiet_don_hang_layout) {
    override fun getCount(): Int {
        return ds.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = activity.layoutInflater.inflate(R.layout.chi_tiet_don_hang_layout, parent, false)

        val txtTen = view.findViewById<TextView>(R.id.txtTenMonAnCT)
        val txtGia = view.findViewById<TextView>(R.id.txtGiaTienCT)
        val txtSoLuong = view.findViewById<TextView>(R.id.txtSoLuongCT)

        txtTen.setText("- "+ds[position].getTitle())
        txtGia.setText("("+(ds[position].getPrice()*ds[position].getNumInCart()).toString()+")")
        txtSoLuong.setText(ds[position].getNumInCart().toString())

        return view
    }
}