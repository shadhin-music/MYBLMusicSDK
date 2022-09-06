package com.shadhinmusiclibrary.data.repository

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.utils.safeApiCall

class ArtistSongContentRepository(private val apiService: ApiService) {

    suspend fun fetchArtistSongData(artist:Int) = safeApiCall {
        apiService.fetchArtistSongs(artist)
    }


}