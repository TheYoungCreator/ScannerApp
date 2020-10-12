package com.e.scannerapp

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.scannerapp.edit.ImageModel

class SharedViewModel : ViewModel() {

    val imageLiveData =MutableLiveData<MutableList<ImageModel>>()

    val imageList:MutableList<ImageModel> = mutableListOf()

    fun setLiveData(){
        imageLiveData.value = imageList
    }
}