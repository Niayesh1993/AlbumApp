package com.example.photoalbumapp.data.api

import com.example.photoalbumapp.data.model.Photo
import retrofit2.http.*

interface ApiService {

    @GET("/shared/{sharedId}/media")
    suspend fun getPhotos(@Path("sharedId") Id: String): ArrayList<Photo>
}