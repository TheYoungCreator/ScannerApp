package com.e.scannerapp.main

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import com.e.scannerapp.CaptureActivity
import com.e.scannerapp.R
import com.e.scannerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED
            &&ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.CAMERA),
                11
            )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.fab.setOnClickListener { moveToCapture() }

        //settingUp RecyclerView of MainActivity
        setUpHome()
    }

    private fun setUpHome() {
        with(binding.recyclerView){
            val pdfAdapter = MainAdapter()
            pdfAdapter.submitList(fakeList())
            adapter = pdfAdapter
        }

    }

    private fun fakeList(): MutableList<PdfModel>?  = mutableListOf<PdfModel>()
        .apply {
            val pdfModel = PdfModel("pdf name","10/20/2020","name".toUri())
            for (i in 1..5)
                this.add(pdfModel)
        }

    private fun moveToCapture() {
        startActivity(Intent(this, CaptureActivity::class.java))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==11 && grantResults[0]!=PackageManager.PERMISSION_GRANTED &&grantResults[1]!=PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                11
            )

    }
}