package com.shadhinmusiclibrary.data.repository

import com.shadhinmusiclibrary.data.model.*
import com.shadhinmusiclibrary.data.model.APIResponse
import com.shadhinmusiclibrary.data.model.FeaturedPodcast
import com.shadhinmusiclibrary.data.model.FeaturedSongDetail
import com.shadhinmusiclibrary.data.model.HomeData
import com.shadhinmusiclibrary.data.model.LatestVideoModel
import com.shadhinmusiclibrary.data.model.PopularArtistModel
import com.shadhinmusiclibrary.data.model.RBT
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.model.auth.LoginResponse
import com.shadhinmusiclibrary.data.model.lastfm.LastFmResult
import com.shadhinmusiclibrary.data.model.podcast.PodcastModel
import com.shadhinmusiclibrary.data.model.search.SearchModel
import com.shadhinmusiclibrary.data.model.search.TopTrendingModel
import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModel
import com.shadhinmusiclibrary.fragments.artist.ArtistBanner
import com.shadhinmusiclibrary.fragments.artist.ArtistContent
import com.shadhinmusiclibrary.utils.safeApiCall
import okhttp3.RequestBody
import org.json.JSONObject

internal class CreatePlaylistRepository(private val apiService: ApiService) {


    suspend fun createPlaylist(name: String) = safeApiCall {
        apiService.createPlaylist(PlaylistBody(name))
    }

    suspend fun getUserPlaylist() = safeApiCall {
        apiService.getUserPlaylist()
    }

    suspend fun songsAddedtoPlaylist(playlistId: String, contentId: String) = safeApiCall {
        apiService.songsAddedtoPlaylist(SongsAddedtoPlaylistBody(playlistId, contentId))
    }

    suspend fun getUserSongsInPlaylist(id: String) = safeApiCall {
        apiService.getUserSongsInPlaylist(id)
    }

    suspend fun songDeletedfromPlaylist(playlistId: String, contentId: String) = safeApiCall {
        apiService.songDeletedfromPlaylist(SongsAddedtoPlaylistBody(playlistId, contentId))
    }

    suspend fun deletePlaylist(playlistId: String) = safeApiCall {
        apiService.deletePlaylist(playlistId)
    }
}
