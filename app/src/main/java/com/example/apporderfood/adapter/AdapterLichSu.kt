package com.example.apporderfood.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.apporderfood.R
import com.example.apporderfood.model.LichSu

class AdapterLichSu(val context1:Context, val ds:MutableList<LichSu>):ArrayAdapter<LichSu>(context1, R.layout.lich_su_layout) {
    override fun getCount(): Int {
        return ds.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lich_su_layout, parent, false)

        val txtMaDonHang = view.findViewById<TextView>(R.id.txtMaDonHang)
        val txtHoTen = view.findViewById<TextView>(R.id.txtHoVaTen)
        val txtPhone = view.findViewById<TextView>(R.id.txtSoDienThoai)
        val diaChi = view.findViewById<TextView>(R.id.txtDiaChiNhan)
        val txtThucDon = view.findViewById<TextView>(R.id.txtThucDon)
        val txtNgayDatHang = view.findViewById<TextView>(R.id.txtNgayDatHang)
        val txtTongTien = view.findViewById<TextView>(R.id.txtTongTienLS)
        val txtThanhToan = view.findViewById<TextView>(R.id.txtThanhToan)

        txtMaDonHang.setText(ds[position].getId())
        txtHoTen.setText(ds[position].getHoTen())
        txtPhone.setText(ds[position].getPhone())
        diaChi.setText(ds[position].getDiaChi())
        txtThucDon.setText(ds[position].getThucDon())
        txtNgayDatHang.setText(ds[position].getNgayDat())
        txtTongTien.setText(ds[position].getTongTien())
        txtThanhToan.setText(ds[position].getThanhToan())

        return view
    }
}