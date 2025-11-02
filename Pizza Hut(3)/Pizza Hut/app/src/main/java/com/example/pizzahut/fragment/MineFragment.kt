package com.example.pizzahut.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import com.example.login.entity.UserInfo
import com.example.pizzahut.InformationActivity
import com.example.pizzahut.LoginActivity
import com.example.pizzahut.R
import com.example.pizzahut.ScanActivity
import com.example.pizzahut.SettingActivity
import com.example.pizzahut.databinding.FragmentMineBinding

class MineFragment : Fragment() {
    private lateinit var binding: FragmentMineBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMineBinding.inflate(layoutInflater)

        val window = requireActivity().window
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = resources.getColor(R.color.gray)

        /*        binding.username.setOnClickListener {

                    val intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent)
                }*/
        binding.setting.setOnClickListener {
            val intent = Intent(activity, SettingActivity::class.java)
            startActivity(intent)
        }
        binding.scan.setOnClickListener {
            startActivity(Intent(context, ScanActivity::class.java))
        }
        binding.myInformation.setOnClickListener {
            val intent = Intent(activity, InformationActivity::class.java)
            startActivityForResult(intent, 1000)

        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val userInfo = UserInfo.getUserInfo()
        if (userInfo != null) {
            binding.username.text = userInfo.username
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1000) {
            val username = data?.getStringExtra("username")
            username?.let {
                binding.username.text = it
            }

        }
    }

}