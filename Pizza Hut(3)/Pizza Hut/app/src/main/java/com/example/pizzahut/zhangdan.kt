package com.example.pizzahut

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// 假设你的数据模型类名为 YourDataType
data class YourDataType(val name: String, val price: String, val count: String)

class zhangdan : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_zhangdan)

        // 获取 main 视图并设置窗口插入监听器
        val mainView = findViewById<View>(R.id.main)
        if (mainView!= null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        // 从 SharedPreferences 获取数据
        val sharedPreferences = getSharedPreferences("cart_data", MODE_PRIVATE)
        val productList = sharedPreferences.getStringSet("product_list", mutableSetOf())?.toList()?.map {
            val parts = it.split(",")
            YourDataType(parts[0], parts[1], parts[2])
        }?.toMutableList()?: mutableListOf()

        // 使用获取的数据更新 UI 或进行其他操作
        // 例如，更新 RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = YourAdapter(productList)
        recyclerView.adapter = adapter
    }
}


// 适配器类定义在 Activity 外部
class YourAdapter(private val dataList: List<YourDataType>) : RecyclerView.Adapter<YourAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        val countTextView: TextView = itemView.findViewById(R.id.countTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.zhang_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dataList[position]
        holder.nameTextView.text = "Name: ${item.name}"
        holder.priceTextView.text = "Price: ${item.price}"
        holder.countTextView.text = "Count: ${item.count}"
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}