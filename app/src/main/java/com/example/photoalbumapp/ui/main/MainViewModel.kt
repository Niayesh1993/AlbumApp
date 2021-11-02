package com.example.photoalbumapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoalbumapp.data.api.StatusCode.NOT_FOUND
import com.example.photoalbumapp.data.model.Photo
import com.example.photoalbumapp.data.repository.main.MainRepositoryImpl
import com.example.photoalbumapp.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepositoryImpl: MainRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableLiveData<UiStateModel>()
    val uiState: LiveData<UiStateModel> get() = _uiState

    private val _products = MutableLiveData<ArrayList<Photo>?>()
    val products: LiveData<ArrayList<Photo>?> get() = _products

    fun loadPhotos() {
        _uiState.update(loading = true)
        viewModelScope.launch {
            val productList = withContext(Dispatchers.Default) {
                mainRepositoryImpl.getPhotos(SettingManager.getString(Constants().SHAREDID).toString())
            }

            _uiState.update(loading = false)

            updatePhotoList(productList)
        }
    }

    private fun updatePhotoList(response: ApiResult<ArrayList<Photo>>) {
        if (response is ApiResult.Success) {
            _products.value = response.value
            _uiState.update(loading = false)
        } else {
            if (response is ApiResult.Error &&
                response.error?.code == NOT_FOUND) {
                _products.value = null
                _uiState.update(loading = false)
            } else {
                _uiState.handleApiError(response)
            }
        }
    }

}