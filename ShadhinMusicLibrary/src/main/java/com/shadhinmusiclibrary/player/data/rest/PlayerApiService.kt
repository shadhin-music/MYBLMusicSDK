package com.shadhinmusiclibrary.player.data.rest

import com.shadhinmusiclibrary.player.data.model.ContentUrlResponse
import com.shadhinmusiclibrary.player.data.model.SongTrackingModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

internal interface PlayerApiService {

    @GET("streaming/getpth")
    suspend fun fetchContentUrl(
        @Header("Authorization") token: String?,
        @Query("ptype") ptype:String?,
        @Query("type") type:String?,
        @Query("ttype") ttype:String?,
        @Query("name") name:String?

    ): ContentUrlResponse

    @GET("clientstreaming/getdwnpth")
    suspend fun fetchDownloadContentUrl(
        @Header("Authorization") token: String,
        @Query("name") name:String?
    ): ContentUrlResponse

    @POST("ClientPodcast/PodcastUserHistoryV3")
    suspend fun trackPodcastPlaying(
        @Body body: HashMap<String?, Any?>?
    ): SongTrackingModel?

    @POST("ClientActivity/UserHistory")
    suspend fun trackSongPlaying(
        @Body body: HashMap<String?, Any?>?
    ): SongTrackingModel

}