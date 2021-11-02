package com.example.choco.di

import com.example.photoalbumapp.data.repository.DataSource
import com.example.photoalbumapp.data.repository.main.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideMainRepository(dataSource: DataSource): MainRepositoryImpl {
        return MainRepositoryImpl.getInstance(dataSource)
    }


    @Provides
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }


}