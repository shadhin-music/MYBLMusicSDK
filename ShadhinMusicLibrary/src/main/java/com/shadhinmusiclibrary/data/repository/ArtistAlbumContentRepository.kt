package com.shadhinmusiclibrary.data.repository

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.utils.safeApiCall

class ArtistAlbumContentRepository(private val apiService: ApiService) {


    suspend fun fetchArtistAlbum(type:String,id:Int) = safeApiCall {
        apiService.fetchArtistAlbum(type,id)
    }

}