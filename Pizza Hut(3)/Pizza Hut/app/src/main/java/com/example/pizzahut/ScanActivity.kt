package com.example.pizzahut

import android.Manifest
import android.app.DatePickerDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView

class ScanActivity : AppCompatActivity() {

    private val context: Context = this
    private val TAG = ScanActivity::class.java.simpleName
    private lateinit var barcodeView: CompoundBarcodeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        initView()
        scan()
    }

    private fun initView() {
        barcodeView = findViewById(R.id.barcode_scanner)
    }

    private fun scan() {
        barcodeView.resume()
        barcodeView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                if (result.text != null) {
                    barcodeView.pause()
                    showScanResultDialog(context, result.text)
                }
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {
                // Do nothing
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            // 如果应用没有摄像头权限，请求权限
            Toast.makeText(this, "请设置摄像头权限", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 200)
        } else {
            scan()
        }
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    private fun showScanResultDialog(context: Context, scanResult: String) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle("扫码结果")
        builder.setCancelable(false)

        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_scan_result, null)
        builder.setView(dialogView)
        val textView = dialogView.findViewById<TextView>(R.id.text_scan_result)
        textView.text = scanResult

        builder.setPositiveButton("复制") { dialogInterface: DialogInterface, _: Int ->
            copyToClipboard(scanResult)
            scan()
        }

        builder.setNegativeButton("关闭") { dialogInterface: DialogInterface, _: Int ->
            scan()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun copyToClipboard(text: String) {
        // 获取剪贴板管理器
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        // 创建 ClipData 对象
        val clipData = ClipData.newPlainText("Scanned Result", text)

        // 将 ClipData 设置到剪贴板
        clipboardManager.setPrimaryClip(clipData)
    }
}
