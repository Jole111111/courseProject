package com.example.pizzahut.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzahut.R
import com.example.pizzahut.info.HomeImgInfo

class HomeListAdapter(val imgList: ArrayList<HomeImgInfo>) :RecyclerView.Adapter<HomeListAdapter.MyHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_list_item, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val imgInfo = imgList[position]
        holder.image.setImageResource(imgInfo.image)
    }

    override fun getItemCount(): Int {
        return imgList.size
    }

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image)

    }


}