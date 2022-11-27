package com.shadhinmusiclibrary.data.remote

import com.shadhinmusiclibrary.data.model.*
import com.shadhinmusiclibrary.data.model.auth.LoginResponse
import com.shadhinmusiclibrary.data.model.fav.FavDataResponseModel
import com.shadhinmusiclibrary.data.model.lastfm.LastFmResult
import com.shadhinmusiclibrary.data.model.podcast.PodcastModel
import com.shadhinmusiclibrary.data.model.search.SearchModel
import com.shadhinmusiclibrary.data.model.search.TopTrendingModel
import com.shadhinmusiclibrary.data.model.ArtistAlbumModel
import com.shadhinmusiclibrary.data.model.ArtistBannerModel
import com.shadhinmusiclibrary.data.model.ArtistContentModel
import com.shadhinmusiclibrary.fragments.create_playlist.CreatePlaylistResponseModel
import com.shadhinmusiclibrary.fragments.create_playlist.UserPlayListModel
import com.shadhinmusiclibrary.fragments.create_playlist.UserSongsPlaylistModel
import retrofit2.http.*
import retrofit2.Call
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

    @GET("mybl/getradiosdataall")
    suspend fun fetchGetAllRadio(): APIResponse<MutableList<HomePatchDetailModel>>

    @GET("Playlist/GetPlaylistContentById")
    fun fetchGetRadioListByContentById(
        @Query("id") id: String,
    ): Call<APIResponse<MutableList<SongDetailModel>>>

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


    @POST("Playlist/PostUserplayList")
    suspend fun createPlaylist(@Body body: PlaylistBody): CreatePlaylistResponseModel

    @GET("Playlist/Userplaylist")
    suspend fun getUserPlaylist(): UserPlayListModel

    @POST("Playlist/PostUserplayListContent")
    suspend fun songsAddedtoPlaylist(@Body body: SongsAddedtoPlaylistBody): CreatePlaylistResponseModel

    @GET("Playlist/GetUserPlaylist")
    suspend fun getUserSongsInPlaylist(@Query("id") id: String): UserSongsPlaylistModel

    /* @DELETE("Playlist/DeleteUserPlaylistContent")*/

    @HTTP(method = "DELETE", path = "Playlist/DeleteUserPlaylistContent", hasBody = true)
    suspend fun songDeletedfromPlaylist(@Body body: SongsAddedtoPlaylistBody): CreatePlaylistResponseModel

    @HTTP(method = "DELETE", path = "Playlist/DeleteUserplayList", hasBody = false)
    suspend fun deletePlaylist(@Query("id") id: String): CreatePlaylistResponseModel

    @GET("Favourite/GetFavourite")
    suspend fun fetchAllFavoriteByType(@Query("type") type: String): FavDataResponseModel

    @POST("Favourite")
    suspend fun addToFavorite(@Body body: AddtoFavBody): FavDataResponseModel

    @HTTP(method = "DELETE", path = "Favourite", hasBody = true)
    suspend fun deleteFavorite(@Body body: AddtoFavBody): FavDataResponseModel
}