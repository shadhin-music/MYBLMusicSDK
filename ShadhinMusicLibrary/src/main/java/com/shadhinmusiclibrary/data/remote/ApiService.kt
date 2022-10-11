package com.shadhinmusiclibrary.data.remote

import com.shadhinmusiclibrary.data.model.*
import com.shadhinmusiclibrary.data.model.lastfm.LastFmResult
import com.shadhinmusiclibrary.data.model.podcast.PodcastModel
import com.shadhinmusiclibrary.data.model.search.SearchModel
import com.shadhinmusiclibrary.data.model.search.TopTrendingModel
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModel
import com.shadhinmusiclibrary.fragments.artist.ArtistBanner
import com.shadhinmusiclibrary.fragments.artist.ArtistContent
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("ClientHomeContent/GetHomeContent")
    suspend fun fetchHomeData(
        @Query("pageNumber") pageNumber: Int?,
        @Query("isPaid") isPaid: Boolean?,
    ): HomeData

    @GET("Album/GetAlbumContent")
    suspend fun fetchAlbumContent(
        @Query("id") contentId: String,
    ): APIResponse<MutableList<SongDetail>>

    @GET("?method=artist.getinfo")
    suspend fun fetchArtistBiography(
        @Query("artist") artist: String?,
    ): LastFmResult

    @GET("Artist/ArtistPlayList")
    suspend fun fetchArtistBannerData(
        @Query("id") id: String?,

        ): ArtistBanner

    @GET("Artist/GetArtistContent")
    suspend fun fetchArtistSongs(
        @Query("id") id: String?,
        ): ArtistContent

    @GET("Artist/ArtistAlbumsbyidtype")
    suspend fun fetchArtistAlbum(
        @Query("type") type: String,
        @Query("id") id: String?,
    ): ArtistAlbumModel

    @GET("Playlist/GetPlaylistContentById")
    suspend fun fetchGetPlaylistContentById(
        @Query("id") id: String,
    ): APIResponse<MutableList<SongDetail>>

    @GET("Podcast/PodcastbyepisodeIdV3")
    suspend fun fetchPodcastByID(
        @Query("podType") podType: String,
        @Query("episodeId") episodeId: Int,
        @Query("contentTYpe") contentTYpe: String,
        @Query("isPaid") isPaid: Boolean,
    ): PodcastModel

    @GET("artist/getpopularartist")
    suspend fun fetchPopularArtist(): PopularArtistModel

    @GET("video/getlatestvideo")
    suspend fun fetchLatestVideo(): LatestVideoModel

    @GET("track/GetLatestTrack")
    suspend fun fetchFeaturedTrackList(): APIResponse<MutableList<FeaturedSongDetail>>

    @GET("RBTPWA/GETPWATOKEN")
    suspend fun rbtURL(): RBT

    @GET("Search/SearchByKeyword")
    suspend fun getSearch(
        @Query("keyword") keyword: String
    ): SearchModel

    @GET("Track/TopTrending")
    suspend fun getTopTrendingItems(@Query("type") type: String): TopTrendingModel
}