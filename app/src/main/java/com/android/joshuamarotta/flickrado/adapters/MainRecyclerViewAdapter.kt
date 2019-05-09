package com.android.joshuamarotta.flickrado.adapters

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.joshuamarotta.flickrado.R
import com.android.joshuamarotta.flickrado.api.response.FlickrItem
import com.android.joshuamarotta.flickrado.api.response.formattedName
import com.android.joshuamarotta.flickrado.viewholders.FlickrItemViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import javax.inject.Inject

const val AUTHOR = "author"
const val IMAGE_URL = "image_url"
const val TITLE = "title"

class MainRecyclerViewAdapter @Inject internal constructor(context: Context/*, private val onItemClick: (Bundle) -> Unit*/): RecyclerView.Adapter<FlickrItemViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private var items = emptyList<FlickrItem>()
    private lateinit var onItemClick: (Bundle) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FlickrItemViewHolder(inflater.inflate(R.layout.flickr_item_view, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: FlickrItemViewHolder, position: Int) {
        val item = items[position]
        Glide.with(holder.flickrImageView)
            .load(item.media.m)
            .placeholder(R.drawable.ic_mountains)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.flickrImageView)

        holder.flickrImageView.setOnClickListener {
            onItemClick(Bundle().apply {
                putString(AUTHOR, item./*author*/formattedName)
                putString(IMAGE_URL, item.media.m)
                putString(TITLE, item.title)
            })
        }
    }

    internal fun setItems(flickrItems: List<FlickrItem>) {
        items = flickrItems
        notifyDataSetChanged()
    }

    internal fun setListener(onItemClick: (Bundle) -> Unit) {
        this.onItemClick = onItemClick
    }
}

