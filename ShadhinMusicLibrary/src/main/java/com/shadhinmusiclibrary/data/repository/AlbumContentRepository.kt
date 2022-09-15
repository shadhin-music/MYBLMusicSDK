package com.shadhinmusiclibrary.data.repository

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.utils.safeApiCall

class AlbumContentRepository(private val apiService: ApiService) {
    suspend fun fetchAlbumContent(contentId: Int) = safeApiCall {
        apiService.fetchAlbumContent(contentId)
    }

    suspend fun fetchPlaylistContent(contentId: Int) = safeApiCall {
        apiService.fetchGetPlaylistContentById(contentId)
    }
}