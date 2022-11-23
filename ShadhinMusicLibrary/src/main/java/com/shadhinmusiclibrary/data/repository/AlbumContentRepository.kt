package com.shadhinmusiclibrary.data.repository

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.utils.safeApiCall
import retrofit2.http.Header

internal class AlbumContentRepository(private val apiService: ApiService) {
    suspend fun fetchAlbumContent(contentId: String) = safeApiCall {
        apiService.fetchAlbumContent(contentId)
    }

    suspend fun fetchPlaylistContent(contentId: String) = safeApiCall {
        apiService.fetchGetPlaylistContentById(contentId)
    }

    suspend fun fetchGetAllRadio(token: String) = safeApiCall {
        apiService.fetchGetAllRadio(token)
    }
}