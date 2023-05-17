package com.example.apporderfood.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.appdoan.inteface.InterfaceRvFood
import com.example.apporderfood.R
import com.example.apporderfood.inteface.InterfaceGioHang
import com.example.apporderfood.model.MonAn
import com.squareup.picasso.Picasso

class AdapterGioHang(val dsMonAn:List<MonAn>, val interfaceGioHang: InterfaceGioHang):RecyclerView.Adapter<AdapterGioHang.ViewHolderGioHang>(){
    class ViewHolderGioHang(view: View):RecyclerView.ViewHolder(view){
        val image = view.findViewById<ImageView>(R.id.imgGioHang)
        val txtTitle = view.findViewById<TextView>(R.id.txtTitleGioHang)
        val txtGia = view.findViewById<TextView>(R.id.txtGiaTienGioHang)
        val txtSoLuong = view.findViewById<TextView>(R.id.txtSoLuongGioHang)

        val txtCong = view.findViewById<TextView>(R.id.txtCongGH)
        val txtTru = view.findViewById<TextView>(R.id.txtTruGH)
        val btnXoa = view.findViewById<Button>(R.id.btnXoa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGioHang {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gio_hang_layout,parent,false)
        return ViewHolderGioHang(view)
    }

    override fun onBindViewHolder(holder: ViewHolderGioHang, position: Int) {
        holder.txtTitle.setText(dsMonAn[position].getTitle())
        holder.txtGia.setText((dsMonAn[position].getPrice()*dsMonAn[position].getNumInCart()).toString())
        holder.txtSoLuong.setText(dsMonAn[position].getNumInCart().toString())
        Picasso.get().load(dsMonAn[position].getPicture()).into(holder.image)

        holder.txtCong.setOnClickListener {
            holder.txtSoLuong.setText((holder.txtSoLuong.text.toString().toInt()+1).toString())
            interfaceGioHang.updateClick(dsMonAn[position],holder.txtSoLuong.text.toString().toInt())
        }
        holder.txtTru.setOnClickListener {
            if(holder.txtSoLuong.text.toString().toInt()>1){
                holder.txtSoLuong.setText((holder.txtSoLuong.text.toString().toInt()-1).toString())
                interfaceGioHang.updateClick(dsMonAn[position],holder.txtSoLuong.text.toString().toInt())
            }
        }
        holder.btnXoa.setOnClickListener {
            val diolog = AlertDialog.Builder(holder.itemView.context)
            diolog.setTitle("Xác nhận xóa khỏi giỏ hàng")
            diolog.setTitle("Bạn có chắc chắn muốn xóa món ăn này không")
            diolog.setNegativeButton("HỦY"){listener,i ->
                listener.dismiss()
            }
            diolog.setPositiveButton("XÓA"){listener,i ->
                interfaceGioHang.xoaClick(dsMonAn[position])
                listener.dismiss()
            }
            diolog.show()
        }
    }

    override fun getItemCount(): Int {
        return dsMonAn.size
    }
}