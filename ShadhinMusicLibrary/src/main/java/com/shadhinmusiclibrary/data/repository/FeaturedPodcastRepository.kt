package com.shadhinmusiclibrary.data.repository

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.utils.safeApiCall

class FeaturedPodcastRepository(private val apiService: ApiService) {


    suspend fun fetchFeaturedPodcast(isPaid:Boolean) = safeApiCall {
        apiService.fetchFeturedPodcast(isPaid)
    }
}