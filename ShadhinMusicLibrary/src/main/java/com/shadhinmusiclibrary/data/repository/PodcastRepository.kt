package com.shadhinmusiclibrary.data.repository

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.utils.safeApiCall

class PodcastRepository(private val apiService: ApiService) {

    suspend fun fetchPodcastByID(podType:String,episodeId: Int, contentType:String,isPaid:Boolean) = safeApiCall {
        apiService.fetchPodcastByID(podType,episodeId,contentType,isPaid)
    }
}