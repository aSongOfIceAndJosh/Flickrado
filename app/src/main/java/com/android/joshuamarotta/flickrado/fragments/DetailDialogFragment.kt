package com.android.joshuamarotta.flickrado.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.joshuamarotta.flickrado.R
import com.android.joshuamarotta.flickrado.adapters.AUTHOR
import com.android.joshuamarotta.flickrado.adapters.IMAGE_URL
import com.android.joshuamarotta.flickrado.adapters.TITLE
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment
import kotlinx.android.synthetic.main.blur_fragment_dialog.view.*

class DetailDialogFragment: SupportBlurDialogFragment() {

    private lateinit var flickrItemView: ImageView
    private lateinit var photographerTextView: TextView
    private lateinit var titleTextView: TextView

    private  var author: String? = null
    private  var imageUrl: String? = null
    private  var title: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.blur_fragment_dialog, container)
        flickrItemView = rootView.blur_dialog_fragment_flickr_item_view_image_view
        photographerTextView = rootView.blur_fragment_dialog_photographer_text_view
        titleTextView = rootView.blur_fragment_dialog_title_text_view

        arguments?.apply {
            author = getString(AUTHOR)
            imageUrl = getString(IMAGE_URL)
            title = getString(TITLE)
        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        photographerTextView.text = author
        titleTextView.text = title

        activity?.let {
            Glide.with(it)
                .load(imageUrl)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(flickrItemView)
        }
    }

    override fun getDownScaleFactor() = 8.0f
}