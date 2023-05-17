package com.example.appdoan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appdoan.inteface.InterfaceRvFood
import com.example.apporderfood.R
import com.example.apporderfood.model.MonAn
import com.squareup.picasso.Picasso

class AdapterMonAnMain(var ds:MutableList<MonAn>, var interfaceRvFood:InterfaceRvFood):RecyclerView.Adapter<AdapterMonAnMain.ViewHoderMonAn>() {
    class ViewHoderMonAn(itemView:View):RecyclerView.ViewHolder(itemView){
        var title = itemView.findViewById<TextView>(R.id.txtTitle)
        var txtScore = itemView.findViewById<TextView>(R.id.txtScore)
        var time = itemView.findViewById<TextView>(R.id.txtTime)
        var image = itemView.findViewById<ImageView>(R.id.imgFood)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoderMonAn {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mon_an_layout,parent,false)
        return ViewHoderMonAn(view)
    }

    override fun getItemCount(): Int {
        return ds.size
    }

    override fun onBindViewHolder(holder: ViewHoderMonAn, position: Int) {
        holder.txtScore.setText(ds[position].getScore().toString())
        holder.time.setText((ds[position].getTime().toString())+" phút")
        holder.title.setText(ds[position].getTitle())
        Picasso.get().load(ds[position].getPicture()).into(holder.image)

        holder.itemView.setOnClickListener {
            interfaceRvFood.onClick(ds[position])
        }
    }
}

