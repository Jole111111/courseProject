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
import com.example.pizzahut.databinding.ActivityUpdatePwdBinding

class UpdatePwdActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUpdatePwdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdatePwdBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val window = window
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = resources.getColor(R.color.white)


        binding.btnUpdatePwd.setOnClickListener {
            val new_pwd =binding.newPassword.text.toString()
            val confirm_pwd = binding.confirmPassword.text.toString()

            if(TextUtils.isEmpty(new_pwd)|| TextUtils.isEmpty(confirm_pwd)){
                Toast.makeText(this,"信息不能为空", Toast.LENGTH_SHORT).show()
            }else if(new_pwd!=confirm_pwd){
                Toast.makeText(this,"新密码和确认密码不一致", Toast.LENGTH_SHORT).show()
            }else{
                val userInfo = UserInfo.getUserInfo()
                if(userInfo!=null){
                    val row = UserDbHelper.getInstance(this).updatePwd(userInfo.phone,new_pwd)
                    if(row>0){
                        Toast.makeText(this,"密码修改成功，请重新登录", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this,"修改失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.back.setOnClickListener {
            finish()
        }

    }
}