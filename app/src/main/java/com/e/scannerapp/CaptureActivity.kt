package com.e.scannerapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class CaptureActivity : AppCompatActivity(){


    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_capture)


        /*
        Request camera permissions
        private fun createImg(capturedImage: ByteArray) {
        directory.mkdir()
        val savedPhoto = File(directory, "${dateFormat.format(System.currentTimeMillis())}.jpg")
        Log.d("myTag", "savePhoto " + savedPhoto.path)
        try {
        val outputStream = FileOutputStream(savedPhoto.path)
        outputStream.write(capturedImage)
        outputStream.close()
        Log.d("myTag", "output streamer")
        } catch (e: IOException) {
        Log.d("myTag", "output streamer error")
        e.printStackTrace()
        }*/




    }
    
}



