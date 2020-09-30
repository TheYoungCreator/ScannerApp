package com.e.scannerapp.edit

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.e.scannerapp.R
import kotlinx.android.synthetic.main.single_image_layout.view.*
import java.io.File

class EditPageAdapter(private val listOfImages:MutableList<Image>): RecyclerView.Adapter<EditPageAdapter.EditHolder>() {

    class EditHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.single_image_layout,parent,false)
        return EditHolder(root)
    }

    override fun onBindViewHolder(holder: EditHolder, position: Int) {
        with(holder.itemView){
            Glide.with(this.context).load(listOfImages[position].imageUrl).into(image_view)
        }
    }

    override fun getItemCount(): Int {
        return listOfImages.size
    }
}