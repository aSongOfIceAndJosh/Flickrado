package com.android.joshuamarotta.flickrado.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.android.joshuamarotta.flickrado.api.response.OfflineFlickrResponse
import com.android.joshuamarotta.flickrado.repositories.MainRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(val mainRepository: MainRepository): ViewModel() {
    val flickrResponse: LiveData<OfflineFlickrResponse> = mainRepository.flickrItems

    override fun onCleared() {
        super.onCleared()
        mainRepository.onCleared()
    }
}
