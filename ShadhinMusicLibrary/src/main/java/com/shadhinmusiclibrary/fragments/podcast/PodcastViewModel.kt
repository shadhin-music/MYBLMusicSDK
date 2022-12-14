package com.shadhinmusiclibrary.fragments.podcast

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadhinmusiclibrary.data.model.podcast.PodcastModel
import com.shadhinmusiclibrary.data.repository.PodcastRepository
import com.shadhinmusiclibrary.utils.ApiResponse
import kotlinx.coroutines.launch

class PodcastViewModel (private val podcastRepository: PodcastRepository): ViewModel() {
    private val  _podcastContent: MutableLiveData<ApiResponse<PodcastModel>> = MutableLiveData()

     val podcastDetailsContent: LiveData<ApiResponse<PodcastModel>> = _podcastContent



    fun fetchPodcastContent(podType:String,episodeId: Int, contentType:String,isPaid:Boolean) = viewModelScope.launch {
        val response = podcastRepository.fetchPodcastByID(podType,episodeId,contentType,isPaid)
        Log.e("Podcast", "CALLED "+ response)
        _podcastContent.postValue(response)

    }
}