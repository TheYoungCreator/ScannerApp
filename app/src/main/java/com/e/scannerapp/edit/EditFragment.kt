package com.e.scannerapp.edit

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewDebug
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.e.scannerapp.CaptureActivity
import com.e.scannerapp.MainActivity
import com.e.scannerapp.R
import com.e.scannerapp.databinding.ActivityMainBinding
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.edit_fragment.*
import kotlinx.android.synthetic.main.single_image_layout.*
import java.util.jar.Manifest
import kotlin.properties.Delegates


class EditFragment : Fragment() {
    val croppedImage = image_view
    val btn_crop=rl.findViewById<View>(R.id.crop_btn)

    //private  var editFragment: EditFragment= DataBindingUtil.setContentView(CaptureActivity,R.layout.activity_main)
    var   image_position by Delegates.notNull<Float>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.edit_fragment, container, false)
        val viewPager2 = root.findViewById<ViewPager2>(R.id.view_pager2)
        val springIndicator = root.findViewById<SpringDotsIndicator>(R.id.spring_viewpager_indicator)

        val LIST_OF_URI=getAllUris()
        Log.d("list of uri",LIST_OF_URI[image_position.toInt()].toString())
        viewPager2.apply {

            adapter = EditPageAdapter(getAllUris())

            springIndicator.setViewPager2(viewPager2)
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            //getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(5))
            compositePageTransformer.addTransformer{
                page,position ->
                image_position=position
                val r = 1 - kotlin.math.abs(position)
                page.scaleY = .85f + r*.15f
                page.scaleX = .85f + r*.15f
            }
            setPageTransformer(compositePageTransformer)

        }
        Log.d("imagePosition",image_position.toString())
        return root
        btn_crop.setOnClickListener(View.OnClickListener {
            cropIage(LIST_OF_URI)
        })
    }

    private fun getAllUris(): MutableList<Uri> {
//        val directory = captureFragment.outputDirectory
//        val ls = mutableListOf<I  mage>()
//        if (directory.isDirectory && directory.exists()){
//            directory.listFiles().forEach {
//                ls.add(Image(Uri.fromFile(it)))
//            }
//        }
//        return ls
        val args:EditFragmentArgs by navArgs()
        val listOfUri=args.imageParcelable.ls.reversed().toMutableList()
        return listOfUri
    }

    private fun rotateImage(){
        image_view.rotation = 90F
    }



    private fun cropIage( list_of_uri:MutableList<Uri>){
        cropImageView.setImageUriAsync(list_of_uri[image_position.toInt()]);
// or (prefer using uri for performance and better user experience)
        cropImageView.getCroppedImageAsync();
    }


}