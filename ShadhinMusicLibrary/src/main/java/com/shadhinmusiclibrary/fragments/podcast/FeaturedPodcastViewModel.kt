package com.shadhinmusiclibrary.fragments.podcast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.data.model.FeaturedPodcastModel
import com.shadhinmusiclibrary.data.repository.FeaturedPodcastRepository

import com.shadhinmusiclibrary.utils.ApiResponse
import kotlinx.coroutines.launch

internal class FeaturedPodcastViewModel(private val featuredPodcastRepository: FeaturedPodcastRepository) :
    ViewModel() {
    private val _featuredpodcastContent: MutableLiveData<ApiResponse<FeaturedPodcastModel>> =
        MutableLiveData()
    val featuredpodcastContent: LiveData<ApiResponse<FeaturedPodcastModel>> =
        _featuredpodcastContent

    private val _featuredpodcastContentJC: MutableLiveData<ApiResponse<FeaturedPodcastModel>> =
        MutableLiveData()
    val featuredpodcastContentJC: LiveData<ApiResponse<FeaturedPodcastModel>> =
        _featuredpodcastContentJC

    fun fetchFeaturedPodcast(isPaid: Boolean) = viewModelScope.launch {
        val response = featuredPodcastRepository.fetchFeaturedPodcast(isPaid)
        _featuredpodcastContent.postValue(response)
    }

    fun fetchFeaturedPodcastJC(isPaid: Boolean) = viewModelScope.launch {
        val response = featuredPodcastRepository.fetchFeaturedPodcast(isPaid)
        _featuredpodcastContentJC.postValue(response)
    }
}