package com.example.pizzahut.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pizzahut.BuyActivity
import com.example.pizzahut.Item2Activity
import com.example.pizzahut.Item3Activity
import com.example.pizzahut.Item6Activity
import com.example.pizzahut.Item7Activity
import com.example.pizzahut.MainActivity2
import com.example.pizzahut.R
import com.example.pizzahut.ScanActivity
import com.example.pizzahut.adapter.HomeListAdapter
import com.example.pizzahut.databinding.FragmentHomeBinding
import com.example.pizzahut.info.BannerDataInfo
import com.example.pizzahut.info.HomeImgInfo
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


private const val REQUEST_IMAGE_CAPTURE = 1

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val imgList = ArrayList<HomeImgInfo>()
    private var mBannerDataInfos: MutableList<BannerDataInfo> = ArrayList()


    private var imageUri: Uri? = null
    private lateinit var currentPhotoPath: String

    private val takePicture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // 处理拍照结果
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val window = requireActivity().window
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = resources.getColor(R.color.home)

        initImageList()
        initBannerList()

        val layoutManager = StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL)
        binding.RecyclerView.layoutManager = layoutManager
        val adapter = HomeListAdapter(imgList)
        binding.RecyclerView.adapter = adapter
        binding.quan.setOnClickListener{
            startActivity(Intent(context, MainActivity2::class.java))
        }
        binding.buy.setOnClickListener {
            val intent = Intent (context,BuyActivity::class.java)
            startActivity(intent)
        }

        binding.scan.setOnClickListener {
            // 调用手机相机
//            if (Permission.checkPermission(requireActivity())) {
//                dispatchTakePictureIntent()
//            } else {
//                requestPermissions()
//            }
            startActivity(Intent(context, ScanActivity::class.java))
        }

        binding.item2.setOnClickListener {
            val intent = Intent (context, Item2Activity::class.java)
            startActivity(intent)
        }
        binding.item3.setOnClickListener {
            val intent = Intent (context,Item3Activity::class.java)
            startActivity(intent)
        }
        binding.item6.setOnClickListener {
            val intent = Intent (context, Item6Activity::class.java)
            startActivity(intent)
        }
        binding.item7.setOnClickListener {
            val intent = Intent (context, Item7Activity::class.java)
            startActivity(intent)
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
            .setIndicator(CircleIndicator(context))




        return binding.root
    }


    private fun initImageList(){
        imgList.add(HomeImgInfo(R.mipmap.img_home1))
        imgList.add(HomeImgInfo(R.mipmap.img_home2))
        imgList.add(HomeImgInfo(R.mipmap.img_home3))
        imgList.add(HomeImgInfo(R.mipmap.img_home4))
        imgList.add(HomeImgInfo(R.mipmap.img_home5))
        imgList.add(HomeImgInfo(R.mipmap.img_home6))
    }
    private fun initBannerList(){
        mBannerDataInfos.add(BannerDataInfo(R.mipmap.img_banner1))
        mBannerDataInfos.add(BannerDataInfo(R.mipmap.img_banner2))
        mBannerDataInfos.add(BannerDataInfo(R.mipmap.img_banner3))
    }
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            // 确保有解决此 Intent 的 Activity
            intent.resolveActivity(requireActivity().packageManager)?.also {
                // 创建用于存储照片的文件
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // 错误处理：无法创建文件
                    Toast.makeText(requireContext(), "无法创建文件", Toast.LENGTH_SHORT).show()
                    return
                }
                photoFile?.also {
                    val photoURI: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        FileProvider.getUriForFile(
                            requireActivity(),
                            "com.example.pizzahut.fileprovider",
                            it
                        )
                    } else {
                        Uri.fromFile(it)
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    takePicture.launch(intent)
                }
            } ?: run {
                // 错误处理：没有可用的相机应用
                Toast.makeText(requireContext(), "没有可用的相机应用", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // 创建一个以时间为命名的照片文件
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireActivity().externalCacheDir
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (permissions.all { it.value }) {
            dispatchTakePictureIntent()
        } else {
            Toast.makeText(requireContext(), "请授予相机权限", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(arrayOf(Manifest.permission.CAMERA))
    }
}