package com.shadhinmusiclibrary.fragments.artist


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.data.model.PopularArtistModel

import com.shadhinmusiclibrary.data.repository.ArtistAlbumContentRepository
import com.shadhinmusiclibrary.data.repository.PopularArtistRepository
import com.shadhinmusiclibrary.utils.ApiResponse
import com.shadhinmusiclibrary.utils.Status
import kotlinx.coroutines.launch


class PopularArtistViewModel (private val popularArtistRepository: PopularArtistRepository): ViewModel() {

    private val _popularArtistContent: MutableLiveData<ApiResponse<PopularArtistModel>> = MutableLiveData()
    val popularArtistContent: LiveData<ApiResponse<PopularArtistModel>> = _popularArtistContent



    fun fetchPouplarArtist() = viewModelScope.launch {
        val response = popularArtistRepository.fetchPopularArtist()
        _popularArtistContent.postValue(response)
    }



}