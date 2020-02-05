package com.example.dataloadersample.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.contentloader.ImageDownloader
import com.example.dataloadersample.R
import com.example.dataloadersample.api.PinBoard
import kotlinx.android.synthetic.main.pin_item.view.*

class PinBoardAdapter(private val context: Context, private val items: List<PinBoard>) :
    RecyclerView.Adapter<PinBoardAdapter.ImageViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ImageViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.pin_item, parent, false
        )
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(items[position])
    }


    inner class ImageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(pinBoard: PinBoard) {
            with(view) {
                ImageDownloader
                    .Builder<ImageView>()
                    .load(pinBoard.urls.regular)
                    .into(pinItem)
                    .scale(200,200)
                    .build()
                    .fetchImage()
            }
        }
    }
}