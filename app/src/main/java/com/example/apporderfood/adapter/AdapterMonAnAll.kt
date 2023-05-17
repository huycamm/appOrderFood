package com.example.appdoan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.appdoan.inteface.InterfaceRvFood
import com.example.apporderfood.R
import com.example.apporderfood.model.MonAn
import com.squareup.picasso.Picasso

class AdapterMonAnAll(var ds:MutableList<MonAn>, var interfaceRvFood:InterfaceRvFood):RecyclerView.Adapter<AdapterMonAnAll.ViewHoderMonAnAll>() {
    class ViewHoderMonAnAll(itemView:View):RecyclerView.ViewHolder(itemView){
        var title = itemView.findViewById<TextView>(R.id.txtTitle)
        val gia = itemView.findViewById<TextView>(R.id.txtGia)
        var image = itemView.findViewById<ImageView>(R.id.imgFood)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoderMonAnAll {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_mon_an_layout,parent,false)
        return ViewHoderMonAnAll(view)
    }

    override fun getItemCount(): Int {
        return ds.size
    }

    override fun onBindViewHolder(holder: ViewHoderMonAnAll, position: Int) {
        holder.title.setText(ds[position].getTitle())
        holder.gia.setText(ds[position].getPrice().toString()+" đ")
        Picasso.get().load(ds[position].getPicture()).into(holder.image)

        holder.itemView.setOnClickListener {
            interfaceRvFood.onClick(ds[position])
        }
    }
}

