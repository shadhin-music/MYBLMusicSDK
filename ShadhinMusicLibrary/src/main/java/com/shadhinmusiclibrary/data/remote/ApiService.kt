package com.shadhinmusiclibrary.data.remote

import com.shadhinmusiclibrary.data.model.*
import com.shadhinmusiclibrary.data.model.lastfm.LastFmResult
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModel
import com.shadhinmusiclibrary.fragments.artist.ArtistBanner
import com.shadhinmusiclibrary.fragments.artist.ArtistContent
import com.shadhinmusiclibrary.rest.RetroClient
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("ClientHomeContent/GetHomeContent")
    suspend fun fetchHomeData(
        @Header("Client") client: Int,
        @Query("pageNumber") pageNumber: Int?,
        @Query("isPaid") isPaid: Boolean?
    ): HomeData

    @GET("Album/GetAlbumContent")
    suspend fun fetchAlbumContent(
        @Query("id") contentId: Int
    ): APIResponse<MutableList<SongDetail>>

    @GET("?method=artist.getinfo")
    suspend fun fetchArtistBiography(
        @Query("artist") artist: String?,
    ): LastFmResult

    @GET("Artist/ArtistPlayList")
    suspend fun fetchArtistBannerData(
        @Query("id") id: Int?,

        ): ArtistBanner

    @GET("Artist/GetArtistContent")
    suspend fun fetchArtistSongs(
        @Query("id") id: Int?,

        ): ArtistContent

    @GET("Artist/ArtistAlbumsbyidtype")
    suspend fun fetchArtistAlbum(
        @Query("type") type: String,
        @Query("id") id: Int?,
    ): ArtistAlbumModel

    @GET("Playlist/GetPlaylistContentById")
    suspend fun fetchGetPlaylistContentById(
        @Query("id") id: Int
    ): APIResponse<MutableList<SongDetail>>
}