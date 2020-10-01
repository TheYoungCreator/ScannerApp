package com.e.scannerapp.edit

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.e.scannerapp.R
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator

class EditFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.edit_fragment, container, false)
        val viewPager2 = root.findViewById<ViewPager2>(R.id.view_pager2)
        val springIndicator = root.findViewById<SpringDotsIndicator>(R.id.spring_viewpager_indicator)

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
                val r = 1 - kotlin.math.abs(position)
                page.scaleY = .85f + r*.15f
                page.scaleX = .85f + r*.15f
            }
            setPageTransformer(compositePageTransformer)
        }
        return root
    }

    private fun getAllUris(): MutableList<Uri> {
//        val directory = captureFragment.outputDirectory
//        val ls = mutableListOf<Image>()
//        if (directory.isDirectory && directory.exists()){
//            directory.listFiles().forEach {
//                ls.add(Image(Uri.fromFile(it)))
//            }
//        }
//        return ls
        val args:EditFragmentArgs by navArgs()
        return args.imageParcelable.ls.reversed().toMutableList()
    }


}