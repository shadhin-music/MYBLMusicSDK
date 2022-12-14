package com.shadhinmusiclibrary.data.repository

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.utils.safeApiCall

class ArtistBannerContentRepository(private val apiService: ApiService) {

    suspend fun fetchArtistBannerData(id: Int) = safeApiCall {
        apiService.fetchArtistBannerData(id)
    }
}