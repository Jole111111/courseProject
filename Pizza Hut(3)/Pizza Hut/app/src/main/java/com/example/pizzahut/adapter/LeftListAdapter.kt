package com.example.pizzahut.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzahut.R
import com.example.pizzahut.info.HomeImgInfo

class LeftListAdapter(private val dataList: List<String>) : RecyclerView.Adapter<LeftListAdapter.MyHolder>(){
    private var mLeftListOnClickItemListener: LeftListOnClickItemListener? = null
    private var currentIndex = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.left_list_item, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val name = dataList.get(position)
        holder.name.setText(name)

        holder.itemView.setOnClickListener {
            mLeftListOnClickItemListener?.onItemClick(position)
        }
        if(currentIndex == position){
            holder.itemView.setBackgroundResource(R.color.white)
        }else{
            holder.itemView.setBackgroundResource(R.color.gray)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = itemView.findViewById(R.id.name)

    }

    public fun setLeftListOnClickItemListener(leftListOnClickItemListener: LeftListOnClickItemListener) {
        mLeftListOnClickItemListener = leftListOnClickItemListener
    }

    interface LeftListOnClickItemListener {
        fun onItemClick(position: Int)
    }

    fun setCurrentIndex(position: Int){
        currentIndex=position
        notifyDataSetChanged()
    }


}