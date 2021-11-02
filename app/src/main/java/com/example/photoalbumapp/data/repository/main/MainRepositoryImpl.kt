package com.example.photoalbumapp.data.repository.main

import com.example.photoalbumapp.data.model.Photo
import com.example.photoalbumapp.data.repository.DataSource
import com.example.photoalbumapp.utils.ApiResult

class MainRepositoryImpl(private val remoteDataSource: DataSource): MainRepository {


    override suspend fun getPhotos(Id: String): ApiResult<ArrayList<Photo>> {
        return remoteDataSource.getPhotos(Id)
    }


    companion object {
        @Volatile
        private var INSTANCE: MainRepositoryImpl? = null

        fun getInstance(remoteDataSource: DataSource): MainRepositoryImpl {
            return INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: MainRepositoryImpl(remoteDataSource
                    ).also { INSTANCE = it }
            }
        }
    }

}