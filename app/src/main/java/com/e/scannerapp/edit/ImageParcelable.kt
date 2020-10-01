package com.e.scannerapp.edit

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ImageParcelable(val ls:MutableList<Uri>) : Parcelable