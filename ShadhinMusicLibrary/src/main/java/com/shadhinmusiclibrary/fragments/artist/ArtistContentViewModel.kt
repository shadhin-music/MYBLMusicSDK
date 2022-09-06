package com.shadhinmusiclibrary.fragments.artist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.data.model.lastfm.LastFmResult
import com.shadhinmusiclibrary.data.repository.ArtistContentRepository
import com.shadhinmusiclibrary.data.repository.ArtistSongContentRepository
import com.shadhinmusiclibrary.utils.Status
import kotlinx.coroutines.launch

class ArtistContentViewModel (private val artistContentRepository: ArtistSongContentRepository): ViewModel() {

    private val _artistSongContent: MutableLiveData<ArtistContent> = MutableLiveData()
    val artistSongContent: MutableLiveData<ArtistContent> = _artistSongContent


    fun fetchArtistSongData(artist: Int) = viewModelScope.launch {
        val response = artistContentRepository.fetchArtistSongData(artist)
        if (response.status == Status.SUCCESS) {
            _artistSongContent.postValue(response.data)
            Log.d("dfsfdsdfsdf","DATA :"+ response.data.toString())
        }
        Log.i("dfsfdsdfsdf", "fetchArtistBioData: ${response.message}")
    }
//

}