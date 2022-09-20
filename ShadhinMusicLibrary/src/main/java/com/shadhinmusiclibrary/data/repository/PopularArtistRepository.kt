package com.shadhinmusiclibrary.data.repository

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.utils.safeApiCall

class PopularArtistRepository(private val apiService: ApiService) {


    suspend fun fetchPopularArtist() = safeApiCall {
        apiService.fetchPopularArtist()
    }

}