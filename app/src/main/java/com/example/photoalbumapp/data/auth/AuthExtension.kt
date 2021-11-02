package com.example.photoalbumapp.data.auth

import okhttp3.Request


fun Request.Builder.addAuthHeader(accessToken: String): Request.Builder {
    return header("token", "$accessToken")
}