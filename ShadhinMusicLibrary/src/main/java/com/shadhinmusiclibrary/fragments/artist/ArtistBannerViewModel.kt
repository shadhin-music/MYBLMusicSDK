package com.shadhinmusiclibrary.fragments.artist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.data.repository.ArtistBannerContentRepository
import com.shadhinmusiclibrary.utils.ApiResponse
import com.shadhinmusiclibrary.utils.Status
import kotlinx.coroutines.launch

class ArtistBannerViewModel (private val artistBannerContentRepository: ArtistBannerContentRepository): ViewModel() {
    private val  _artistBannerContent: MutableLiveData<ApiResponse<ArtistBanner>> = MutableLiveData()

     val artistBannerContent: LiveData<ApiResponse<ArtistBanner>> = _artistBannerContent



    fun fetchArtistBannerData(id:Int) = viewModelScope.launch {
        val response = artistBannerContentRepository.fetchArtistBannerData(id)

            _artistBannerContent.postValue(response)

    }
}