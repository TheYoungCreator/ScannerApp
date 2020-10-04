package com.e.scannerapp

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    val mutableLiveData:MutableLiveData<MutableList<Uri>> = MutableLiveData(mutableListOf())


}