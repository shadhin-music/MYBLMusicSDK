package com.shadhinmusiclibrary.fragments.artist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.data.repository.ArtistSongContentRepository
import com.shadhinmusiclibrary.utils.ApiResponse
import kotlinx.coroutines.launch

internal class ArtistContentViewModel(private val artistContentRepository: ArtistSongContentRepository) :
    ViewModel() {

    private val _artistSongContent: MutableLiveData<ApiResponse<ArtistContent>> = MutableLiveData()
    val artistSongContent: LiveData<ApiResponse<ArtistContent>> = _artistSongContent
    fun fetchArtistSongData(artist: String) = viewModelScope.launch {
        val response = artistContentRepository.fetchArtistSongData(artist)
        _artistSongContent.postValue(response)
    }
}