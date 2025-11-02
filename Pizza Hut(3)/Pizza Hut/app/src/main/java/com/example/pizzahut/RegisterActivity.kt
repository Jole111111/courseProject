package com.example.pizzahut

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.login.db.UserDbHelper
import com.example.pizzahut.databinding.ActivityLoginBinding
import com.example.pizzahut.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var is_agree = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val window = window
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = resources.getColor(R.color.white)//将上面的状态栏颜色改成与页面颜色一致

        binding.back.setOnClickListener {
            finish()
        }

        binding.agree.setOnCheckedChangeListener { buttonView, isChecked ->
            is_agree = true
        }//隐私政策同意

        binding.register.setOnClickListener {
            val phone = binding.etPhone.text.toString()
            val password = binding.etPassword.text.toString()
            if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "请输入手机号和密码", Toast.LENGTH_SHORT).show()
            } else {
                if (!is_agree) {
                    Toast.makeText(this, "请同意隐私协议", Toast.LENGTH_SHORT).show()
                } else {
                    val dbHelper = UserDbHelper.getInstance(this)
                    val row = dbHelper.register(phone, password)
                    if (row > 0) {
                        Toast.makeText(this, "注册成功,请登录", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }
}