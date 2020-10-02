package com.e.scannerapp.edit

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.e.scannerapp.R
import com.e.scannerapp.databinding.EditFragmentBinding
import kotlinx.android.synthetic.main.single_image_layout.*


class EditFragment : Fragment(), View.OnClickListener {

    private var imgPosition: Int = 0
    private lateinit var binding: EditFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.edit_fragment, container, false)
        with(binding) {
            viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    imgPosition = position
                    Log.d("position", imgPosition.toString())
                }
            })

            viewPager2.apply {

                adapter = EditPageAdapter(getAllUris())

                binding.springViewpagerIndicator.setViewPager2(viewPager2)
                clipChildren = false
                clipToPadding = false
                offscreenPageLimit = 3
                //getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                val compositePageTransformer = CompositePageTransformer()
                compositePageTransformer.addTransformer(MarginPageTransformer(5))
                compositePageTransformer.addTransformer { page, position ->
                    val r = 1 - kotlin.math.abs(position)
                    page.scaleY = .85f + r * .15f
                    page.scaleX = .85f + r * .15f
                }
                setPageTransformer(compositePageTransformer)

            }
        }


        settingListeners()
        return binding.root
    }

    private fun getAllUris(): MutableList<Uri> {
        val args: EditFragmentArgs by navArgs()
        return args.imageParcelable.ls.reversed().toMutableList()
    }

    fun settingListeners() {
        binding.btnRotate.setOnClickListener(this)
    }

    fun rotateImage() {

        image_view.rotation = image_view.rotation + 90F
        Log.d("rotate", "rotated by 90")
    }


    private fun cropIage(list_of_uri: MutableList<Uri>) {
        val urls = getAllUris()//list o
        cropImageView.setImageUriAsync(urls[imgPosition]);
// or (prefer using uri for performance and better user experience)
        cropImageView.getCroppedImageAsync();
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.btnRotate.id -> rotateImage()

        }
    }


}