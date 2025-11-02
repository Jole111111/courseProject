package com.example.pizzahut.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzahut.R
import com.example.pizzahut.info.RightListInfo

class RightListAdapter(private var rightInfos: List<RightListInfo> = ArrayList()) : RecyclerView.Adapter<RightListAdapter.MyHolder>() {

    fun setListData(list: List<RightListInfo>) {
        rightInfos = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.right_list_item, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val info = rightInfos[position]
        holder.name.setText(info.name)
        holder.image.setImageResource(info.img)
        holder.price.setText(info.price.toString())
        holder.count.setText(info.count.toString())

        // 为 btn_plus 设置点击事件
        holder.btnPlus.setOnClickListener {
            mOnBtnPlusClickListener?.onBtnPlusClick(info, position)
        }

        // 为 btn_subtract 设置点击事件
        holder.btnSubtract.setOnClickListener {
            mOnBtnSubtractClickListener?.onBtnSubtractClick(info, position)
        }

        // 如果你还需要为整个列表项设置点击事件，可以保留下面的代码
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(info, position)
        }
    }

    override fun getItemCount(): Int {
        return rightInfos.size
    }

    // 添加的函数，用来返回列表中所有项目的详细信息
    fun getAllItemsInfo(): List<RightListInfo> {
        return rightInfos.toList() // 返回列表的一个副本
    }

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.product_img)
        val name: TextView = view.findViewById(R.id.product_name)
        val price: TextView = view.findViewById(R.id.product_price)
        val count: TextView = view.findViewById(R.id.product_count)
        val btnPlus: TextView = view.findViewById(R.id.btn_plus) // 假设 btn_plus 是一个 TextView
        val btnSubtract: TextView = view.findViewById(R.id.btn_subtract) // 假设 btn_subtract 是一个 TextView
    }

    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnBtnPlusClickListener: OnBtnPlusClickListener? = null
    private var mOnBtnSubtractClickListener: OnBtnSubtractClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    fun setOnBtnPlusClickListener(onBtnPlusClickListener: OnBtnPlusClickListener) {
        mOnBtnPlusClickListener = onBtnPlusClickListener
    }

    fun setOnBtnSubtractClickListener(onBtnSubtractClickListener: OnBtnSubtractClickListener) {
        mOnBtnSubtractClickListener = onBtnSubtractClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(info: RightListInfo, position: Int)
    }

    interface OnBtnPlusClickListener {
        fun onBtnPlusClick(info: RightListInfo, position: Int)
    }

    interface OnBtnSubtractClickListener {
        fun onBtnSubtractClick(info: RightListInfo, position: Int)
    }
}