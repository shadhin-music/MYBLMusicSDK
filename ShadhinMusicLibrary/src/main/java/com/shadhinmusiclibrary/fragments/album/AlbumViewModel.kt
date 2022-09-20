package com.shadhinmusiclibrary.fragments.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.data.model.APIResponse
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.repository.AlbumContentRepository
import com.shadhinmusiclibrary.utils.ApiResponse
import com.shadhinmusiclibrary.utils.Status
import kotlinx.coroutines.launch

class AlbumViewModel(private val albumContentRepository: AlbumContentRepository) : ViewModel() {
    private val _albumContent: MutableLiveData<ApiResponse<APIResponse<MutableList<SongDetail>>>> =
        MutableLiveData()
    val albumContent: LiveData<ApiResponse<APIResponse<MutableList<SongDetail>>>> = _albumContent

    fun fetchAlbumContent(contentId: Int) = viewModelScope.launch {
        val response = albumContentRepository.fetchAlbumContent(contentId)

        _albumContent.postValue(response)

    }

    fun fetchPlaylistContent(contentId: Int) = viewModelScope.launch {
        val response = albumContentRepository.fetchPlaylistContent(contentId)

        _albumContent.postValue(response)
    }

}