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


        // Request camera permissions
//    private fun createImg(capturedImage: ByteArray) {
//        directory.mkdir()
//        val savedPhoto = File(directory, "${dateFormat.format(System.currentTimeMillis())}.jpg")
//        Log.d("myTag", "savePhoto " + savedPhoto.path)
//        try {
//            val outputStream = FileOutputStream(savedPhoto.path)
//            outputStream.write(capturedImage)
//            outputStream.close()
//            Log.d("myTag", "output streamer")
//        } catch (e: IOException) {
//            Log.d("myTag", "output streamer error")
//            e.printStackTrace()
//        }
//        createPdf(arrayListOf(capturedImage))
//    }
//
//    private fun createPdf(capturedImgList: ArrayList<ByteArray>) {
//        val savePdf = File(directory, "myPdf.pdf")
//        val img: Image = Image.getInstance(capturedImgList.firstOrNull())
//        img.alignment = Image.ALIGN_MIDDLE
//        img.originalType = Image.ORIGINAL_JPEG
//        img.scaleToFit(Rectangle(1000f,750f))
//        val outputStream = FileOutputStream(savePdf.path)
//        val document = Document()
//        val pdfWriter = PdfWriter.getInstance(document, outputStream)
//
//        document.open()
//        document.pageSize = PageSize.A4
//        document.addCreationDate()
//        document.addAuthor("androidPDF")
//        document.addCreator("http://chonchol.me")
//        //document.setMargins(10f,10f,10f,10f)
//        document.addTitle("first page")
//        document.add(img)
//        document.close()
//        pdfWriter.close()
//
//    }
    }
    
}



