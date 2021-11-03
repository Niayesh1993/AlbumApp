package com.example.photoalbumapp.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.example.photoalbumapp.R

internal object ImageLoader {

    fun loadImageWithMinimumDimensionsMode(context: Context, url: String, imageView: ImageView) {
        val appendQueryParameter = Uri.Builder().appendQueryParameter("w", "100")
            .appendQueryParameter("h", "100")
            .appendQueryParameter("m", "md")
        Glide.with(context)
            .load(url+appendQueryParameter)
            .error(R.drawable.bg_no_image)
            .transition(withCrossFade())
            .into(imageView)
    }

    fun loadImageWithBoundingBoxMode(context: Context, url: String, imageView: ImageView) {
        val appendQueryParameter = Uri.Builder().appendQueryParameter("w", "350")
            .appendQueryParameter("h", "500")
            .appendQueryParameter("m", "bb")
        Glide.with(context)
            .load(url+appendQueryParameter)
            .error(R.drawable.bg_no_image)
            .transition(withCrossFade())
            .into(imageView)
    }

}