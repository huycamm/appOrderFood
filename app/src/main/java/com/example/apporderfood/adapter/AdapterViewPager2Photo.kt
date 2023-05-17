package com.example.apporderfood.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.apporderfood.R
import com.squareup.picasso.Picasso

class AdapterViewPager2Photo(val ds:MutableList<String>):RecyclerView.Adapter<AdapterViewPager2Photo.PhotoViewHolder>() {
    class PhotoViewHolder(view:View):RecyclerView.ViewHolder(view){
        val image = view.findViewById<ImageView>(R.id.photoItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        Picasso.get().load(ds[position]).into(holder.image)
    }

    override fun getItemCount(): Int {
        return ds.size
    }
}