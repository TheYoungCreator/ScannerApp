package com.e.scannerapp.edit

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.e.scannerapp.R
import com.e.scannerapp.SharedViewModel
import com.e.scannerapp.databinding.EditFragmentBinding
import com.phelat.navigationresult.BundleFragment
import kotlinx.android.synthetic.main.single_image_layout.*


class EditFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: EditFragmentBinding
    private lateinit var editPageAdapter: EditPageAdapter
    val model: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.edit_fragment, container, false)
        with(binding) {
            viewPager2.apply {
                editPageAdapter = EditPageAdapter(model.imageList)
                adapter = editPageAdapter
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


    private fun settingListeners() {
        binding.btnRotate.setOnClickListener(this)
        binding.btnDelete.setOnClickListener(this)

    }


    private fun deleteImage() {
        if (editPageAdapter.list.size>0){

            Log.d("mytag","delete called size = ${binding.viewPager2.currentItem}")
            editPageAdapter.list.removeAt(binding.viewPager2.currentItem)
            Log.d("mytag","delete called size = ${binding.viewPager2.currentItem}")
            //setLiveData()
            //editPageAdapter.notifyItemRemoved(binding.viewPager2.currentItem)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            //  binding.cropBtn.id -> cropImage()
            //       binding.btnRotate.id -> rotateImage()
            binding.btnDelete.id -> deleteImage()
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("mytag","edit last count ${model.imageList.size}")
    }
}