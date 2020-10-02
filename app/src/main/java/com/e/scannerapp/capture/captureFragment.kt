package com.e.scannerapp.capture

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.e.scannerapp.CaptureActivity
import com.e.scannerapp.R
import com.e.scannerapp.databinding.CaptureFragmentBinding
import com.e.scannerapp.edit.ImageParcelable
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class captureFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: CaptureFragmentBinding

    private val uriList = mutableListOf<Uri>()

    //camerax
    private var imageCapture: ImageCapture? = null

    private lateinit var cameraExecutor: ExecutorService

    companion object {
        private const val TAG = "myTag"
        private const val PHOTO_REQUEST_CODE = 12
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        val outputDirectory: File = File(
            Environment.getExternalStorageDirectory().path + File.separator + "ScannerApp"
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.capture_fragment, container, false)

        //listeners
        setListeners()
        cameraExecutor = Executors.newSingleThreadExecutor()
        startCamera()
        return binding.root
    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this.requireContext())

        cameraProviderFuture.addListener(Runnable{
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.createSurfaceProvider())
                }

            imageCapture = ImageCapture.Builder()
                .build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this.requireContext()))
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return
        outputDirectory.mkdir()
        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    uriList.add(savedUri)
//                    Log.d("list", uriList[1].toString())
                   // Log.d("list size", uriList?.size.toString())
                    activity?.let { Glide.with(it).load(uriList.last()).into(binding.previewBtn) }
                    val msg = "Photo capture succeeded: $savedUri"
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }
            })
    }


    private fun setListeners() {

        binding.captureBtn.setOnClickListener(this)
        binding.previewBtn.setOnClickListener(this)
        binding.importBtn.setOnClickListener(this)

    }


    private fun importImage() {
        Intent().apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            action = Intent.ACTION_GET_CONTENT
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        }.also {
            startActivityForResult(
                Intent.createChooser(it, "Choose Multiple Images"),
                PHOTO_REQUEST_CODE
            )
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.captureBtn.id -> takePhoto()
            binding.previewBtn.id -> moveToPreview()
            binding.importBtn.id -> importImage()
        }
    }

    private fun moveToPreview() {
        val parcelable = ImageParcelable(uriList)
        val action = captureFragmentDirections.actionCaptureFragmentToEditFragment(parcelable)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        if (uriList.isNotEmpty())
            Glide.with(this).load(uriList.last()).into(binding.previewBtn)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("mytag", "img ${data?.data.toString()}")
        Log.d("mytag", "loop ${data?.clipData.toString()}")
        if (requestCode == PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {

            data?.apply {
                if (this.data == null) {
                    clipData?.apply {
                        for (i in 0 until itemCount) {

                            uriList.add(
                                getItemAt(i).uri
                            )
                        }
                    }
                } else {
                    this.data?.let {
                        uriList.add(it)
                    }
                }
                binding.previewBtn.setImageURI(uriList.last())
            }

        }
    }

}