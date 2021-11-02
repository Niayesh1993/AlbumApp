package com.example.photoalbumapp.utils

import android.os.Handler
import android.os.Looper
import com.example.photoalbumapp.BuildConfig

object Helper {
    var handler = Handler(Looper.getMainLooper())
    val isDebug: Boolean
        get() = !isRelease
    val isRelease: Boolean
        get() = BuildConfig.ENABLE_RELEASE_FEATURES


}
