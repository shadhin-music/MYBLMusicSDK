package com.shadhinmusiclibrary.fragments.podcast

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.data.model.FeaturedPodcast
import com.shadhinmusiclibrary.data.model.FeaturedPodcastData
import com.shadhinmusiclibrary.data.repository.FeaturedPodcastRepository

import com.shadhinmusiclibrary.data.repository.PodcastRepository
import com.shadhinmusiclibrary.utils.ApiResponse
import kotlinx.coroutines.launch

class FeaturedPodcastViewModel (private val featuredPodcastRepository: FeaturedPodcastRepository): ViewModel() {
    private val  _featuredpodcastContent: MutableLiveData<ApiResponse<FeaturedPodcast>> = MutableLiveData()

     val featuredpodcastContent: LiveData<ApiResponse<FeaturedPodcast>> = _featuredpodcastContent

    private val  _featuredpodcastContentJC: MutableLiveData<ApiResponse<FeaturedPodcast>> = MutableLiveData()

    val featuredpodcastContentJC: LiveData<ApiResponse<FeaturedPodcast>> = _featuredpodcastContentJC
    fun fetchFeaturedPodcast(isPaid:Boolean) = viewModelScope.launch {
        val response = featuredPodcastRepository.fetchFeaturedPodcast(isPaid)
        Log.e("Podcast", "CALLED "+ response.data)
        _featuredpodcastContent.postValue(response)

    }
    fun fetchFeaturedPodcastJC(isPaid:Boolean) = viewModelScope.launch {
        val response = featuredPodcastRepository.fetchFeaturedPodcast(isPaid)
        Log.e("Podcast", "CALLED "+ response.data)
        _featuredpodcastContentJC.postValue(response)

    }
}