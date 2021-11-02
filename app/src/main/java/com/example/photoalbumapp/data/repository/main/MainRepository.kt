package com.example.photoalbumapp.data.repository.main

import com.example.photoalbumapp.data.model.Photo
import com.example.photoalbumapp.utils.ApiResult


interface MainRepository {

    suspend fun getPhotos(Id: String): ApiResult<ArrayList<Photo>>
}