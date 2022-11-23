package com.shadhinmusiclibrary.fragments.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.data.model.APIResponse
import com.shadhinmusiclibrary.data.model.SongDetailModel
import com.shadhinmusiclibrary.data.repository.AlbumContentRepository
import com.shadhinmusiclibrary.utils.ApiResponse
import kotlinx.coroutines.launch

internal class AlbumViewModel(private val albumContentRepository: AlbumContentRepository) :
    ViewModel() {
    private val _albumContent: MutableLiveData<ApiResponse<APIResponse<MutableList<SongDetailModel>>>> =
        MutableLiveData()
    val albumContent: LiveData<ApiResponse<APIResponse<MutableList<SongDetailModel>>>> =
        _albumContent

    fun fetchAlbumContent(contentId: String) = viewModelScope.launch {
        val response = albumContentRepository.fetchAlbumContent(contentId)
        _albumContent.postValue(response)
    }

    fun fetchPlaylistContent(contentId: String) = viewModelScope.launch {
        val response = albumContentRepository.fetchPlaylistContent(contentId)
        _albumContent.postValue(response)
    }
}