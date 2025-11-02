package com.example.pizzahut

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.login.db.UserDbHelper
import com.example.login.entity.UserInfo
import com.example.pizzahut.databinding.ActivityInformationBinding

class InformationActivity : AppCompatActivity() {
    private lateinit var binding:ActivityInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = window
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = resources.getColor(R.color.white)//设置状态栏与页面颜色一致

        val userInfo = UserInfo.getUserInfo()
        if (userInfo != null) {
            binding.username.setText(userInfo.username)
            binding.phone.text = userInfo.phone
        }

        binding.back.setOnClickListener {
            finish()
        }//返回我的界面

        binding.save.setOnClickListener {
            val username =binding.username.text.toString()
            val userInfo = UserInfo.getUserInfo()
            if(userInfo!=null){
                val row = UserDbHelper.getInstance(this).updateUsername(userInfo.phone,username)
                if(row>0){
                    binding.username.setText(username)
                    val resultIntent = Intent().apply {
                        putExtra("username", username)
                    }
                    setResult(1000,resultIntent)
                    finish()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000 && resultCode == 1000) {
            data?.getStringExtra("username")?.let {
                binding.username.setText(it)
            }
        }
    }

}