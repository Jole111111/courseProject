package com.example.pizzahut.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pizzahut.R
import com.example.pizzahut.adapter.HomeListAdapter
import com.example.pizzahut.databinding.FragmentGiftBinding
import com.example.pizzahut.info.HomeImgInfo

class GiftFragment : Fragment() {
    private lateinit var binding: FragmentGiftBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGiftBinding.inflate(layoutInflater)

        val window = requireActivity().window
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = resources.getColor(R.color.gift)


        return binding.root
    }

}