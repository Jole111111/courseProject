package com.example.pizzahut

import android.content.Context
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
import com.example.pizzahut.databinding.ActivityLoginBinding
import com.example.pizzahut.fragment.MineFragment

class LoginActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityLoginBinding
    private var is_agree = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = window
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = resources.getColor(R.color.white)

        val prefs = getSharedPreferences("user", Context.MODE_PRIVATE)
        var is_login = prefs.getBoolean("is_login",false)

        if(is_login){
            val phone = prefs.getString("phone","")
            val password = prefs.getString("password","")
            binding.phone.setText(phone)
            binding.password.setText(password)
            binding.checkbox.isChecked = true
        }//如果选中记住密码，则下次登入时手机号和密码自动填写



        binding.agree.setOnCheckedChangeListener{ buttonView, isChecked ->
            is_agree = true
        }//隐私政策同意

/*        binding.back.setOnClickListener {
            finish()
        }//点击×返回上一个界面*/


        binding.register.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }//跳转到注册界面


        binding.login.setOnClickListener {
            val phone = binding.phone.text.toString()
            val password = binding.password.text.toString()
            if(TextUtils.isEmpty(phone)){
                Toast.makeText(this,"请输入正确手机号", Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(password)) {
                Toast.makeText(this,"请输入密码", Toast.LENGTH_SHORT).show()
            }else{
                val dbHelper = UserDbHelper.getInstance(this)
                val login = dbHelper.login(phone)
                if(!is_agree){
                    Toast.makeText(this,"请同意隐私协议", Toast.LENGTH_SHORT).show()
                }else{
                    if(login!=null) {
                        if(phone == login?.phone && password == login?.password){
                            val editor = getSharedPreferences("user", Context.MODE_PRIVATE).edit()
                            editor.putString("phone", phone)
                            editor.putString("password", password)
                            editor.putBoolean("is_login", is_login)
                            editor.apply()
                            UserInfo.setUserInfo(login)

                            val intent = Intent(this,MainActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this,"密码错误", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this,"请输入正确手机号", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.checkbox.setOnCheckedChangeListener{ buttonView, isChecked ->
            is_login = isChecked
        }
    }
}