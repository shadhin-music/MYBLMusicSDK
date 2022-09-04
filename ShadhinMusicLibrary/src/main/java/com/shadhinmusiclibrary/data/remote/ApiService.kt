package com.shadhinmusiclibrary.data.remote

import com.shadhinmusiclibrary.data.model.*
import com.shadhinmusiclibrary.data.model.lastfm.LastFmResult
import com.shadhinmusiclibrary.fragments.artist.ArtistBanner
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

    @GET("?method=artist.getinfo")
    suspend fun fetchArtistBiography(
        @Query("artist") artist: String?,
    ): LastFmResult

    @GET("Artist/ArtistPlayList?id")
    suspend fun fetchArtistBannerData(
        @Query("id") id: Int?,

    ): ArtistBanner
}