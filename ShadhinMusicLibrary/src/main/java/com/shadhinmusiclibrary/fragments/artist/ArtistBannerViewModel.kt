package com.shadhinmusiclibrary.fragments.artist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.data.repository.ArtistBannerContentRepository
import com.shadhinmusiclibrary.utils.Status
import kotlinx.coroutines.launch

class ArtistBannerViewModel (private val artistBannerContentRepository: ArtistBannerContentRepository): ViewModel() {
    private val  _artistBannerContent: MutableLiveData<ArtistBanner> = MutableLiveData()

     val artistBannerContent:MutableLiveData<ArtistBanner> = _artistBannerContent



    fun fetchArtistBannerData(id:Int) = viewModelScope.launch {
        val response = artistBannerContentRepository.fetchArtistBannerData(id)
        if (response.status == Status.SUCCESS) {
            _artistBannerContent.postValue(response.data)
            Log.d("dfsfdsdfsdf","DATA123 :"+ response.message)
        }
        Log.i("dfsfdsdfsdf", "fetchArtistBannerData: ${response.message}")
    }
}