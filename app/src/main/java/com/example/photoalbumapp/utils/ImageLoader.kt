package com.example.photoalbumapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.example.photoalbumapp.R

internal object ImageLoader {

    fun loadImageWithCircularCrop(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .error(R.drawable.bg_no_image)
            .transition(withCrossFade())
            .into(imageView)
    }

}