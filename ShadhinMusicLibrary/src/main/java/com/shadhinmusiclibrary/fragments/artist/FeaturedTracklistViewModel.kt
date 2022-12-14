package com.shadhinmusiclibrary.fragments.artist


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.data.model.FeaturedLatestTrackListModel
import com.shadhinmusiclibrary.data.model.PopularArtistModel

import com.shadhinmusiclibrary.data.repository.ArtistAlbumContentRepository
import com.shadhinmusiclibrary.data.repository.FeaturedTracklistRepository
import com.shadhinmusiclibrary.data.repository.PopularArtistRepository
import com.shadhinmusiclibrary.utils.ApiResponse
import com.shadhinmusiclibrary.utils.Status
import kotlinx.coroutines.launch


class FeaturedTracklistViewModel (private val featuredTracklistRepository: FeaturedTracklistRepository): ViewModel() {

    private val _featuredTracklistContent: MutableLiveData<ApiResponse<FeaturedLatestTrackListModel>> = MutableLiveData()
    val featuredTracklistContent: LiveData<ApiResponse<FeaturedLatestTrackListModel>> = _featuredTracklistContent



    fun fetchFeaturedTrackList() = viewModelScope.launch {
        val response = featuredTracklistRepository.fetchFeaturedTrackList()
        _featuredTracklistContent.postValue(response)
    }



}