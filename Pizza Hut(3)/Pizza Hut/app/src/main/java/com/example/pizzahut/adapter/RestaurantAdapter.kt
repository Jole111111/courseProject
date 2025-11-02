package com.example.pizzahut.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzahut.R
import com.example.pizzahut.info.RestaurantInfo

class RestaurantAdapter(private val dataList: ArrayList<RestaurantInfo>) : RecyclerView.Adapter<RestaurantAdapter.MyHolder>(){
    private var mListOnClickItemListener: ListOnClickItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_list_item, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val Info = dataList[position]
        holder.name.setText(Info.name)
        holder.address.setText(Info.address)
        holder.time.setText(Info.time)

        holder.itemView.setOnClickListener {
            mListOnClickItemListener?.onItemClick(position)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = itemView.findViewById(R.id.name)
        val address: TextView = itemView.findViewById(R.id.address)
        val time: TextView = itemView.findViewById(R.id.time)

    }


    interface ListOnClickItemListener {
        fun onItemClick(position: Int)
    }


    fun setOnItemClickListener(listener: ListOnClickItemListener) {
        mListOnClickItemListener = listener
    }

}