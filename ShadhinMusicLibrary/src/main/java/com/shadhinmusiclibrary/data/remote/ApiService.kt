package com.shadhinmusiclibrary.data.remote

import com.shadhinmusiclibrary.data.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("HomeContent/GetHomeContent")
    suspend fun fetchHomeData(
        @Query("pageNumber") pageNumber: Int?,
        @Query("isPaid") isPaid: Boolean?
    ): HomeData

    @GET("v5/Album/GetAlbumContent")
    suspend fun fetchAlbumContent(
        @Query("id") contentId: Int
    ): APIResponse<MutableList<SongDetail>>
}