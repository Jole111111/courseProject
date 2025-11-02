package com.example.pizzahut

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzahut.adapter.HomeListAdapter
import com.example.pizzahut.databinding.ActivityMainBinding
import com.example.pizzahut.fragment.GiftFragment
import com.example.pizzahut.fragment.HomeFragment
import com.example.pizzahut.fragment.MineFragment
import com.example.pizzahut.fragment.VipFragment
import com.example.pizzahut.info.HomeImgInfo

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mHomeFragment: HomeFragment? = null
    private var mGiftFragment: GiftFragment? = null
    private var mVipFragment: VipFragment? = null
    private var mMineFragment: MineFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttomnavigation.setOnNavigationItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.home -> {
                    selectedFragment(0)
                    true
                }
                R.id.gift -> {
                    selectedFragment(1)
                    true
                }
                R.id.vip -> {
                    selectedFragment(2)
                    true
                }
                else -> {
                    selectedFragment(3)
                    true
                }
            }
        }
        selectedFragment(0)

    }

    private fun selectedFragment(position: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        hideFragment(fragmentTransaction)
        if (position == 0) {
            if (mHomeFragment == null) {
                mHomeFragment = HomeFragment()
                fragmentTransaction.add(R.id.content, mHomeFragment!!)
            } else {
                fragmentTransaction.show(mHomeFragment!!)
            }
        } else if (position == 1) {
            if (mGiftFragment == null) {
                mGiftFragment = GiftFragment()
                fragmentTransaction.add(R.id.content, mGiftFragment!!)
            } else {
                fragmentTransaction.show(mGiftFragment!!)

            }
        }
        else if (position == 2) {
            if (mVipFragment == null) {
                mVipFragment = VipFragment()
                fragmentTransaction.add(R.id.content, mVipFragment!!)
            } else {
                fragmentTransaction.show(mVipFragment!!)

            }
        }
        else {
            if (mMineFragment == null) {
                mMineFragment = MineFragment()
                fragmentTransaction.add(R.id.content, mMineFragment!!)
            } else {
                fragmentTransaction.show(mMineFragment!!)
            }
        }
        fragmentTransaction.commit()
    }

    private fun hideFragment(fragmentTransaction: FragmentTransaction) {
        if (mHomeFragment != null) {
            fragmentTransaction.hide(mHomeFragment!!)
        }
        if (mGiftFragment != null) {
            fragmentTransaction.hide(mGiftFragment!!)
        }
        if (mVipFragment != null) {
            fragmentTransaction.hide(mVipFragment!!)
        }
        if (mMineFragment != null) {
            fragmentTransaction.hide(mMineFragment!!)
        }
    }
}
