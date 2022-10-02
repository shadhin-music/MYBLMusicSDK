package com.shadhinmusiclibrary.data.remote

import com.shadhinmusiclibrary.data.model.*
import com.shadhinmusiclibrary.data.model.lastfm.LastFmResult
import com.shadhinmusiclibrary.data.model.podcast.PodcastModel
import com.shadhinmusiclibrary.data.model.search.SearchModel
import com.shadhinmusiclibrary.data.model.search.SearchModelData
import com.shadhinmusiclibrary.data.model.search.TopTrendingModel
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModel
import com.shadhinmusiclibrary.fragments.artist.ArtistBanner
import com.shadhinmusiclibrary.fragments.artist.ArtistContent
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {
    @GET("ClientHomeContent/GetHomeContent")
    suspend fun fetchHomeData(
        @Query("pageNumber") pageNumber: Int?,
        @Query("isPaid") isPaid: Boolean?,
    ): HomeData

    @GET("Album/GetAlbumContent")
    suspend fun fetchAlbumContent(
        @Query("id") contentId: Int,
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
        @Query("id") id: Int,
    ): APIResponse<MutableList<SongDetail>>

    @GET("Podcast/PodcastbyepisodeIdV3")
    suspend fun fetchPodcastByID(
        @Query("podType") podType: String,
        @Query("episodeId") episodeId: Int,
        @Query("contentTYpe") contentTYpe: String,
        @Query("isPaid") isPaid: Boolean,
    ): PodcastModel

    @GET("artist/getpopularartist")
     suspend fun fetchPopularArtist():PopularArtistModel
    @GET("video/getlatestvideo")
    suspend fun fetchLatestVideo():LatestVideoModel

     @GET("track/GetLatestTrack")
    suspend fun fetchFeaturedTrackList() :FeaturedLatestTrackListModel

    @POST("RBTPWA/GETPWATOKEN")
    suspend fun rbtURL(@Body requestBody: RequestBody):RBT

    @GET("Search/SearchByKeyword")
      suspend fun getSearch(
        @Query("keyword") keyword:String
      ):SearchModel


   @GET("Track/TopTrending")
      suspend fun getTopTrendingItems(@Query("type") type:String):TopTrendingModel

}