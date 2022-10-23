package com.shadhinmusiclibrary.fragments.artist


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.data.model.APIResponse
import com.shadhinmusiclibrary.data.model.FeaturedSongDetail
import com.shadhinmusiclibrary.data.repository.FeaturedTracklistRepository
import com.shadhinmusiclibrary.utils.ApiResponse
import kotlinx.coroutines.launch


internal class FeaturedTracklistViewModel(private val featuredTracklistRepository: FeaturedTracklistRepository) :
    ViewModel() {

    private val _featuredTracklistContent: MutableLiveData<ApiResponse<APIResponse<MutableList<FeaturedSongDetail>>>> =
        MutableLiveData()
    val featuredTracklistContent: LiveData<ApiResponse<APIResponse<MutableList<FeaturedSongDetail>>>> =
        _featuredTracklistContent

    fun fetchFeaturedTrackList() = viewModelScope.launch {
        val response = featuredTracklistRepository.fetchFeaturedTrackList()
        _featuredTracklistContent.postValue(response)
    }
}