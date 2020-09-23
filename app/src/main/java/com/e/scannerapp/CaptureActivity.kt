package com.e.scannerapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.camerakit.CameraKitView
import com.e.scannerapp.databinding.ActivityCaptureBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CaptureActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCaptureBinding
    private lateinit var captureBtn: ImageButton
    lateinit var camerakitview: CameraKitView
    private lateinit var callback: CameraKitView.ImageCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
        camerakitview = binding.camerakit
        captureBtn = binding.captureBtn
        callback = CameraKitView.ImageCallback { cameraKitView, capturedImage ->

            val directory =
                File(Environment.getExternalStorageDirectory().path + File.separator + "zeeshankiphoto")
            directory.mkdir()
            val savedPhoto = File(directory, "myimage.jpg")
            Log.d("mytag", "savePhoto " + savedPhoto.path)
            try {
                val outputStream = FileOutputStream(savedPhoto.path)
                outputStream.write(capturedImage)
                outputStream.close()
                Log.d("mytag", "output streamer")
            } catch (e: IOException) {
                Log.d("mytag", "output streamer error")
                e.printStackTrace()
            }
        }

        captureBtn.setOnClickListener(this)


    }

    override fun onStart() {
        super.onStart()
        camerakitview.onStart()
    }

    override fun onPause() {
        super.onPause()
        camerakitview.onPause()
    }

    override fun onResume() {
        super.onResume()
        camerakitview.onResume()
    }

    override fun onStop() {
        super.onStop()
        camerakitview.onStop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        camerakitview.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==1 && grantResults[0]!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1)

        }
    }

    override fun onClick(v: View?) {
        Log.d("mytag","onclick called")
        camerakitview.captureImage(callback)
    }



}