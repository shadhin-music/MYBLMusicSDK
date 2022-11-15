package com.shadhinmusiclibrary.data.remote

import com.shadhinmusiclibrary.data.model.*
import com.shadhinmusiclibrary.data.model.auth.LoginResponse
import com.shadhinmusiclibrary.data.model.fav.FavData
import com.shadhinmusiclibrary.data.model.fav.FavDataModel
import com.shadhinmusiclibrary.data.model.lastfm.LastFmResult
import com.shadhinmusiclibrary.data.model.podcast.PodcastModel
import com.shadhinmusiclibrary.data.model.search.SearchModel
import com.shadhinmusiclibrary.data.model.search.TopTrendingModel
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModel
import com.shadhinmusiclibrary.fragments.artist.ArtistBanner
import com.shadhinmusiclibrary.fragments.artist.ArtistContent
import com.shadhinmusiclibrary.fragments.create_playlist.CreatePlaylistResponseModel
import com.shadhinmusiclibrary.fragments.create_playlist.UserPlayListModel
import com.shadhinmusiclibrary.fragments.create_playlist.UserSongsPlaylistModel
import org.json.JSONObject
import retrofit2.http.*
import java.util.HashMap

internal interface ApiService {
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

    @GET("ClientPodcast/PodcastHomeDataV1")
    suspend fun fetchFeturedPodcast(
        @Query("isPaid") isPaid: Boolean,
    ): FeaturedPodcast

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

    @GET("mybl/Login")
    suspend fun login(@Header("Authorization") token: String): LoginResponse


    @POST("Playlist/PostUserplayList")
    suspend fun createPlaylist(@Body body:PlaylistBody): CreatePlaylistResponseModel

    @GET("Playlist/Userplaylist")
    suspend fun getUserPlaylist():UserPlayListModel

    @POST("Playlist/PostUserplayListContent")
    suspend fun songsAddedtoPlaylist(@Body body:SongsAddedtoPlaylistBody): CreatePlaylistResponseModel

    @GET("Playlist/GetUserPlaylist")
    suspend fun getUserSongsInPlaylist(@Query("id") id: String): UserSongsPlaylistModel

   /* @DELETE("Playlist/DeleteUserPlaylistContent")*/

    @HTTP(method = "DELETE", path = "Playlist/DeleteUserPlaylistContent", hasBody = true)
    suspend fun songDeletedfromPlaylist(@Body body:SongsAddedtoPlaylistBody): CreatePlaylistResponseModel

    @HTTP(method = "DELETE", path = "Playlist/DeleteUserplayList", hasBody = false)
    suspend fun deletePlaylist(@Query("id") id: String): CreatePlaylistResponseModel

    @GET("Favourite/GetFavourite")
    suspend fun fetchAllFavoriteByType(@Query("type")type: String):FavDataModel

    @POST("Favourite")
    suspend fun addToFavorite(@Body body:AddtoFavBody): FavDataModel

    @HTTP(method ="DELETE", path ="Favourite", hasBody = true)
    suspend fun deleteFavorite(@Body body:AddtoFavBody): FavDataModel

}