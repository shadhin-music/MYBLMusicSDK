package com.shadhinmusiclibrary.data.repository

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.utils.safeApiCall

class ArtistContentRepository(private val apiService: ApiService) {

    suspend fun fetchArtistBiogrphyData(artist: String) = safeApiCall {
        apiService.fetchArtistBiography(artist)
    }
}