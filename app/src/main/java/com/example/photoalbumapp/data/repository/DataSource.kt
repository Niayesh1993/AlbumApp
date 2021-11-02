package com.example.photoalbumapp.data.repository

import com.example.photoalbumapp.data.api.ApiService
import com.example.photoalbumapp.data.api.safeApiCall
import com.example.photoalbumapp.data.model.Photo
import com.example.photoalbumapp.utils.ApiResult
import javax.inject.Inject

class DataSource @Inject constructor(private val apiService: ApiService) {


    suspend fun getPhotos(Id: String): ApiResult<ArrayList<Photo>> {
        return safeApiCall {
            apiService.getPhotos(Id)
        }
    }

}