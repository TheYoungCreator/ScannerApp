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
import java.text.SimpleDateFormat

class CaptureActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCaptureBinding
    private lateinit var captureBtn: ImageButton
    lateinit var camerakitview: CameraKitView
    private lateinit var callback: CameraKitView.ImageCallback
    val dateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_capture)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        )
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
            )


        camerakitview = binding.camerakit
        captureBtn = binding.captureBtn


        callback = CameraKitView.ImageCallback { cameraKitView, capturedImage ->
            val file_path = Environment.getExternalStorageDirectory()
            val directory =
                File(file_path.toString() + "/scannerapp_pdf")
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


    /*fun createPDF() {


        //val dateFormat = SimpleDateFormat("ddMMyyyy_HHmm")
        val outputStream = FileOutputStream(
            File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS
                ),
                pdf_name + dateFormat.format(Calendar.getInstance().time) + ".pdf"
            )
        )
        val writer: PdfWriter = PdfWriter.getInstance(document, outputStream)

        //Open the document
        document.open()
        val img: Image = Image.getInstance("IMG_0239.jpg")
        document.add(img)
        document.close()
        document.setPageSize(PageSize.A4)
        document.addCreationDate()
        document.addAuthor("AndroPDF")
        document.addCreator("http://chonchol.me")
    }*/


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
        if (requestCode==1 && grantResults[0]!=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
            )

        }
    }

    override fun onClick(v: View?) {
        Log.d("mytag", "onclick called")
        camerakitview.captureImage(callback)
    }



}