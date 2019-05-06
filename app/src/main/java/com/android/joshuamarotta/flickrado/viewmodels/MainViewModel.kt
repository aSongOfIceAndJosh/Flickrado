package com.android.joshuamarotta.flickrado.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.android.joshuamarotta.flickrado.api.response.OfflineFlickrResponse
import com.android.joshuamarotta.flickrado.repositories.MainRepository
import javax.inject.Inject

/*class MainViewModel(application: Application, val mainRepository: MainRepository): AndroidViewModel(application) {
    val flickrResponse: LiveData<OfflineFlickrResponse> = mainRepository.flickrItems

    override fun onCleared() {
        super.onCleared()
        mainRepository.onCleared()
    }
}*/

class MainViewModel @Inject constructor(val mainRepository: MainRepository): ViewModel() {
    val flickrResponse: LiveData<OfflineFlickrResponse> = mainRepository.flickrItems

    override fun onCleared() {
        super.onCleared()
        mainRepository.onCleared()
    }
}
