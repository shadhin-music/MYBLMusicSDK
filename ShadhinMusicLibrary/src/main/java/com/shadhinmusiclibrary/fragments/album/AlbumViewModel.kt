package com.shadhinmusiclibrary.fragments.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.repository.AlbumContentRepository
import com.shadhinmusiclibrary.utils.Status
import kotlinx.coroutines.launch

class AlbumViewModel(private val albumContentRepository: AlbumContentRepository) : ViewModel() {
    private val _albumContent: MutableLiveData<MutableList<SongDetail>> = MutableLiveData()
    val albumContent: LiveData<MutableList<SongDetail>> = _albumContent

    fun fetchAlbumContent(contentId: Int) = viewModelScope.launch {
        val response = albumContentRepository.fetchAlbumContent(contentId)
        if (response.status == Status.SUCCESS) {
            _albumContent.postValue(response.data!!.data)
        }
    }

    fun fetchPlaylistContent(contentId: Int) = viewModelScope.launch {
        val response = albumContentRepository.fetchPlaylistContent(contentId)
        if (response.status == Status.SUCCESS) {
            _albumContent.postValue(response.data!!.data)
        }
    }
}