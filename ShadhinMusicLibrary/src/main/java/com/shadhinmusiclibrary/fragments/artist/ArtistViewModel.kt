package com.shadhinmusiclibrary.fragments.artist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.data.model.lastfm.LastFmResult
import com.shadhinmusiclibrary.data.repository.ArtistContentRepository
import com.shadhinmusiclibrary.utils.Status
import kotlinx.coroutines.launch

class ArtistViewModel (private val artistContentRepository: ArtistContentRepository): ViewModel() {

    private val _artistBioContent: MutableLiveData<LastFmResult> = MutableLiveData()
    val artistBioContent: MutableLiveData<LastFmResult> = _artistBioContent
    private val  _artistBannerContent: MutableLiveData<ArtistBanner> = MutableLiveData()
     val artistBannerContent:MutableLiveData<ArtistBanner> = _artistBannerContent

    fun fetchArtistBioData(artist: String) = viewModelScope.launch {
        val response = artistContentRepository.fetchArtistBiogrphyData(artist)
        if (response.status == Status.SUCCESS) {
            _artistBioContent.postValue(response.data)
            Log.d("dfsfdsdfsdf","DATA :"+ response.data.toString())
        }
        Log.i("dfsfdsdfsdf", "fetchArtistBioData: ${response.message}")
    }

    fun fetchArtistBannerData(artist: String) = viewModelScope.launch {
        val response = artistContentRepository.fetchArtistBiogrphyData(artist)
        if (response.status == Status.SUCCESS) {
            _artistBioContent.postValue(response.data)
            Log.d("dfsfdsdfsdf","DATA :"+ response.data.toString())
        }
        Log.i("dfsfdsdfsdf", "fetchArtistBioData: ${response.message}")
    }
}