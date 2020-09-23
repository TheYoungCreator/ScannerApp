package com.e.scannerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.e.scannerapp.databinding.ActivityCaptureBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCaptureBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

    }
}