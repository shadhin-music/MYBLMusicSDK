package com.shadhinmusiclibrary.data.remote

import com.shadhinmusiclibrary.data.model.*
import com.shadhinmusiclibrary.data.model.auth.LoginResponse
import com.shadhinmusiclibrary.data.model.lastfm.LastFmResult
import com.shadhinmusiclibrary.data.model.podcast.PodcastModel
import com.shadhinmusiclibrary.data.model.search.SearchModel
import com.shadhinmusiclibrary.data.model.search.TopTrendingModel
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModel
import com.shadhinmusiclibrary.data.model.ArtistBannerModel
import com.shadhinmusiclibrary.fragments.artist.ArtistContentModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

internal interface ApiService {
    @GET("ClientHomeContent/GetHomeContent")
    suspend fun fetchHomeData(
        @Query("pageNumber") pageNumber: Int?,
        @Query("isPaid") isPaid: Boolean?,
    ): HomeDataModel

    @GET("Album/GetAlbumContent")
    suspend fun fetchAlbumContent(
        @Query("id") contentId: String,
    ): APIResponse<MutableList<SongDetailModel>>

    @GET("?method=artist.getinfo")
    suspend fun fetchArtistBiography(
        @Query("artist") artist: String?,
    ): LastFmResult

    @GET("Artist/ArtistPlayList")
    suspend fun fetchArtistBannerData(
        @Query("id") id: String?,

        ): ArtistBannerModel

    @GET("Artist/GetArtistContent")
    suspend fun fetchArtistSongs(
        @Query("id") id: String?,
    ): ArtistContentModel

    @GET("Artist/ArtistAlbumsbyidtype")
    suspend fun fetchArtistAlbum(
        @Query("type") type: String,
        @Query("id") id: String?,
    ): ArtistAlbumModel

    @GET("Playlist/GetPlaylistContentById")
    suspend fun fetchGetPlaylistContentById(
        @Query("id") id: String,
    ): APIResponse<MutableList<SongDetailModel>>

    @GET("Podcast/PodcastbyepisodeIdV3")
    suspend fun fetchPodcastByID(
        @Query("podType") podType: String,
        @Query("episodeId") episodeId: Int,
        @Query("contentTYpe") contentTYpe: String,
        @Query("isPaid") isPaid: Boolean,
    ): PodcastModel

    @GET("ClientPodcast/PodcastHomeDataV1")
    suspend fun fetchFeturedPodcast(
        @Query("isPaid") isPaid: Boolean,
    ): FeaturedPodcastModel

    @GET("Podcast/PodcastV3")
    suspend fun fetchPodcastShow(
        @Query("podType") podType: String,
        @Query("contentType") contentType: String,
        @Query("isPaid") isPaid: Boolean,
    ): PodcastModel

    @GET("artist/getpopularartist")
    suspend fun fetchPopularArtist(): PopularArtistModel

    @GET("video/getlatestvideo")
    suspend fun fetchLatestVideo(): LatestVideoModel

    @GET("track/GetLatestTrack")
    suspend fun fetchFeaturedTrackList(): APIResponse<MutableList<FeaturedSongDetailModel>>

    @GET("RBTPWA/GETPWATOKEN")
    suspend fun rbtURL(): RBTModel

    @GET("Search/SearchByKeyword")
    suspend fun getSearch(
        @Query("keyword") keyword: String
    ): SearchModel

    @GET("Track/TopTrending")
    suspend fun getTopTrendingItems(@Query("type") type: String): TopTrendingModel

    @GET("mybl/Login")
    suspend fun login(@Header("Authorization") token: String): LoginResponse
}