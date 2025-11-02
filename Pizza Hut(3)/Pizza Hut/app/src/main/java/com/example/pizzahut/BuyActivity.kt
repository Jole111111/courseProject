package com.example.pizzahut

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.login.entity.DataService
import com.example.pizzahut.adapter.LeftListAdapter
import com.example.pizzahut.adapter.RightListAdapter
import com.example.pizzahut.databinding.ActivityBuyBinding
import com.example.pizzahut.info.BannerDataInfo
import com.example.pizzahut.info.RightListInfo
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator

class BuyActivity : AppCompatActivity(), RightListAdapter.OnBtnPlusClickListener, RightListAdapter.OnBtnSubtractClickListener {
    val server_ip = "192.168.43.216"
    val test_jsp = "http://${server_ip}:8888/aaa/"
    private lateinit var binding: ActivityBuyBinding
    private lateinit var leftList: ArrayList<String>
    private var rightList: ArrayList<RightListInfo> = ArrayList()
    private var mBannerDataInfos: MutableList<BannerDataInfo> = java.util.ArrayList()

    private lateinit var leftAdapter: LeftListAdapter
    private lateinit var rightAdapter: RightListAdapter
    private var to = 0.0 // 将 to 声明为 Double 类型

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initList()
        initBannerList()

        val window = window
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = resources.getColor(R.color.white)

        val leftLayoutManager = LinearLayoutManager(this)
        binding.leftRecyclerView.layoutManager = leftLayoutManager
        val rightLayoutManager = LinearLayoutManager(this)
        binding.rightRecyclerView.layoutManager = rightLayoutManager

        leftAdapter = LeftListAdapter(leftList)
        binding.leftRecyclerView.adapter = leftAdapter

        rightAdapter = RightListAdapter()
        binding.rightRecyclerView.adapter = rightAdapter

        rightAdapter.setListData(DataService.getListData(0)) // 设置初始数据
        rightAdapter.setOnBtnPlusClickListener(this) // 设置 btn_plus 的点击事件监听器
        rightAdapter.setOnBtnSubtractClickListener(this) // 设置 btn_subtract 的点击事件监听器

        // 获取所有项目的详细信息
        rightList = rightAdapter.getAllItemsInfo() as ArrayList<RightListInfo>

        binding.youhui.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        binding.back.setOnClickListener {
            binding.back.setOnClickListener {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("提示")
                builder.setMessage("您确定要退出外带点餐吗？")

                val cancelText = SpannableString("手滑了").apply {
                    setSpan(ForegroundColorSpan(Color.BLACK), 0, this.length, 0)
                }

                builder.setNegativeButton(cancelText) { dialog, which ->
                }

                val okText = SpannableString("退出").apply {
                    setSpan(ForegroundColorSpan(Color.RED), 0, this.length, 0)

                }

                builder.setPositiveButton(okText) { dialog, which ->
                    finish()
                }

                builder.show()
            }
        }
        binding.address.setOnClickListener {
            val intent = Intent(this, RestaurantActivity::class.java)
            startActivityForResult(intent, 1)
        }
        binding.banner.setAdapter(object : BannerImageAdapter<BannerDataInfo?>(mBannerDataInfos as List<BannerDataInfo?>?) {
            override fun onBindView(
                holder: BannerImageHolder?,
                data: BannerDataInfo?,
                position: Int,
                size: Int
            ) {
                if (holder != null) {
                    if (data != null) {
                        holder.imageView.setImageResource(data.image.toInt())
                    }
                }
            }
        })
            .addBannerLifecycleObserver(this) //添加生命周期观察者
            .setIndicator(CircleIndicator(this))

        // 设置点击事件监听器跳转到 zhangdan 活动
        val zhangdZ: ImageView = findViewById(R.id.zhangd_z)
        zhangdZ.setOnClickListener {
            val intent = Intent(this, zhangdan::class.java)
            startActivity(intent)
        }

        leftAdapter.setLeftListOnClickItemListener(object : LeftListAdapter.LeftListOnClickItemListener {
            override fun onItemClick(position: Int) {
                leftAdapter.setCurrentIndex(position)
                rightAdapter.setListData(DataService.getListData(position))
                // 更新 rightList 为当前分类的详细信息
                rightList = rightAdapter.getAllItemsInfo() as ArrayList<RightListInfo>
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data!= null) {
            val Name = data.getStringExtra("RESTAURANT_NAME")
            Name?.let {
                binding.restaurant.text = it
            }
        }
    }

    private fun initList() {
        leftList = ArrayList()
        leftList.add("一人食")
        leftList.add("IP联名")
        leftList.add("优惠套餐")
        leftList.add("超值推荐")
        leftList.add("汉堡上新")
        leftList.add("披萨")
        leftList.add("面/饭")
        leftList.add("小食")
        leftList.add("牛排烤肉")
        leftList.add("甜品/沙拉/汤")
        leftList.add("饮料/咖啡")
    }
    private fun initBannerList(){
        mBannerDataInfos.add(BannerDataInfo(R.mipmap.img_banner1))
        mBannerDataInfos.add(BannerDataInfo(R.mipmap.img_banner2))
        mBannerDataInfos.add(BannerDataInfo(R.mipmap.img_banner3))
    }

    override fun onBtnPlusClick(info: RightListInfo, position: Int) {
        // 增加商品数量
        info.count += 1
        rightAdapter.notifyItemChanged(position)
        // 将商品的价格加到 to 变量中
        to += info.price
        // 更新 total TextView 的内容，保留两位小数
        binding.total.text = if (to == -0.00) "0.00" else String.format("%.2f", to)

        // 存储数据到 SharedPreferences
        val sharedPreferences = getSharedPreferences("cart_data", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val productList = sharedPreferences.getStringSet("product_list", mutableSetOf())?.toMutableList()?.toMutableSet()?: mutableSetOf()
        productList.add("${info.name},${info.price},${info.count}")
        editor.putStringSet("product_list", productList)
        editor.apply()

        Log.d("BuyActivity", "Button plus clicked at position $position for item: ${info.name}, to: $to")
    }

    override fun onBtnSubtractClick(info: RightListInfo, position: Int) {
        // 减少商品数量，但不能小于0
        if (info.count > 0) {
            info.count -= 1
            rightAdapter.notifyItemChanged(position)
            // 从 to 变量中减去商品的价格
            to -= info.price
            // 更新 total TextView 的内容，保留两位小数
            binding.total.text = if (to == -0.00) "0.00" else String.format("%.2f", to)
        }
        Log.d("BuyActivity", "Button subtract clicked at position $position for item: ${info.name}, to: $to")
    }
}