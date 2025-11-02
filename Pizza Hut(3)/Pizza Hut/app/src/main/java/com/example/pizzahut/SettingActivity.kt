package com.example.pizzahut

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pizzahut.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = window
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = resources.getColor(R.color.white)//设置状态栏与页面颜色一致

        binding.back.setOnClickListener {
            finish()
        }//返回我的界面

        binding.information.setOnClickListener {
            val intent = Intent(this, InformationActivity::class.java)
            startActivity(intent)
        }
        binding.update.setOnClickListener {
            val intent = Intent(this, UpdatePwdActivity::class.java)
            startActivity(intent)
        }


        binding.exit.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("提示")
            builder.setMessage("确定要退出吗？")

            val cancelText = SpannableString("取消").apply {
                setSpan(ForegroundColorSpan(Color.BLACK), 0, this.length, 0)
            }

            builder.setNegativeButton(cancelText) { dialog, which ->
                }

            val okText = SpannableString("确定").apply {
                setSpan(ForegroundColorSpan(Color.RED), 0, this.length, 0)

            }

            builder.setPositiveButton(okText) { dialog, which ->
                    finish()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }

            builder.show()
        }

    }
}