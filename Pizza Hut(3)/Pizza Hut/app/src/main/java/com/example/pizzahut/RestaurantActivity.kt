package com.example.pizzahut

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzahut.adapter.RestaurantAdapter
import com.example.pizzahut.databinding.ActivityAddressBinding
import com.example.pizzahut.info.RestaurantInfo

class RestaurantActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressBinding
    private val dataList = ArrayList<RestaurantInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = window
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = resources.getColor(R.color.white)

        initList()
        val layoutManager = LinearLayoutManager(this)
        binding.RecyclerView.layoutManager = layoutManager
        val adapter = RestaurantAdapter(dataList)

        // 设置点击事件监听器
        adapter.setOnItemClickListener(object : RestaurantAdapter.ListOnClickItemListener {
            override fun onItemClick(position: Int) {
                val name = dataList[position].name + " "+dataList[position].address
                val resultIntent = Intent().apply {
                    putExtra("RESTAURANT_NAME", name)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        })

        binding.RecyclerView.adapter = adapter

        binding.back.setOnClickListener {
            finish()
        }
    }

    private fun initList() {
        dataList.add(RestaurantInfo("浙江农林大（校外勿点）", "浙江农林大学集贤食堂一楼南侧外侧", "11:00-20:00"))
        dataList.add(RestaurantInfo("临安宝龙", "-1临安市锦北街道白湖", "10:00-22:00"))
        dataList.add(RestaurantInfo("临安万华餐厅", "锦城街道钱王街855号", "10:00-22:00"))
        dataList.add(RestaurantInfo("临安锦南宝龙店", "临安市双拥路1号杭州锦南宝龙广场L1层M1-L1-001-1/002-2商铺", "10:00-21:00"))
        dataList.add(RestaurantInfo("临安青山湖宝龙", "临安区青山湖科技城轻轨站周边之杭州青山湖宝龙广场一期M-L1-015-2必胜客", "10:00-22:00"))
        dataList.add(RestaurantInfo("西溪银泰", "西湖蒋村街道双龙街88号一层必胜客", "10:00-22:00"))
        dataList.add(RestaurantInfo("西溪印象城", "五常街道五常大道1号地下一层", "10:00-22:00"))
        dataList.add(RestaurantInfo("西城广场餐厅", "文二西路551号西城广场一层", "10:30-22:00"))
        dataList.add(RestaurantInfo("城西银泰店", "丰潭路380号城西银泰城B1层F016，B1F017", "10:00-22:00"))
        dataList.add(RestaurantInfo("杭州科技城宝龙", "余杭区浙江省杭州市余杭区余杭塘路2855号杭州科技城宝龙广场第1层M1-L1-0", "11:00-20:00"))

    }
}