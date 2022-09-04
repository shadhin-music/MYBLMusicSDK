package com.shadhinmusiclibrary.data.remote

import com.shadhinmusiclibrary.data.model.*
import com.shadhinmusiclibrary.data.model.lastfm.LastFmResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("HomeContent/GetHomeContent")
    suspend fun fetchHomeData(
        @Query("pageNumber") pageNumber: Int?,
        @Query("isPaid") isPaid: Boolean?,
    ):HomeData

    @GET("v5/Album/GetAlbumContent")
    fun getAlbumContent(
        @Query("id") pageNumber: Int,
    ): Call<APIResponse<List<SongDetail>>>

    @GET("?method=artist.getinfo")
    suspend fun fetchArtistBiography(
        @Query("artist") artist: String?,
    ): LastFmResult
}