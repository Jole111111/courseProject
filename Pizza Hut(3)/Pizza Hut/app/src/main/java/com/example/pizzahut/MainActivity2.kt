package com.example.pizzahut

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.view.View
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<TextView>(R.id.back).setOnClickListener {
            finish() // 结束当前的Activity
        }

        // 设置RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 假设你有一个照片资源ID列表
        val photoList = listOf(
            R.drawable.you_ph_1,
            R.drawable.you_ph_2,
            R.drawable.you_ph_3,
            R.drawable.you_ph_4,
            R.drawable.you_ph_5,
            // 更多照片资源ID...
        )

        // 设置适配器
        recyclerView.adapter = PhotoAdapter(photoList)
    }
}

// PhotoAdapter类定义
class PhotoAdapter(private val photoList: List<Int>) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoImageView: ImageView = itemView.findViewById(R.id.photoImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoResId = photoList[position]
        holder.photoImageView.setImageResource(photoResId)
    }

    override fun getItemCount() = photoList.size
}