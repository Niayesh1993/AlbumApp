package com.example.photoalbumapp.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

object BindingHelper {


    @JvmStatic
    fun string(context: Context, stringResource: Int): String {
        return if (stringResource == 0) "" else context.resources.getString(stringResource)
    }
}