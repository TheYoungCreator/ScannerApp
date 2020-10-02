package com.e.scannerapp.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.e.scannerapp.R

class MainAdapter() : ListAdapter<PdfModel, MainAdapter.MainHolder>(pdfDiffCallBack) {


    companion object {

        val pdfDiffCallBack = object : DiffUtil.ItemCallback<PdfModel>() {

            override fun areContentsTheSame(oldItem: PdfModel, newItem: PdfModel): Boolean
                    = oldItem.name == newItem.name

            override fun areItemsTheSame(oldItem: PdfModel, newItem: PdfModel): Boolean
                    = oldItem.name == newItem.name && oldItem.date == newItem.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.single_pdf_layout,parent,false)
        return MainHolder(root)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        with(holder){
            //image.setImageURI(getItem(position).image)
            pdfName.text = getItem(position).name
            dateModified.text = getItem(position).date
        }

    }

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image:ImageView = itemView.findViewById(R.id.pdf_image)
        val pdfName:TextView = itemView.findViewById(R.id.pdf_name)
        val dateModified:TextView = itemView.findViewById(R.id.date_modified)
    }

}