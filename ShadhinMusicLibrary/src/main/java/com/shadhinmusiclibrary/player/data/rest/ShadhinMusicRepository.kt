package com.shadhinmusiclibrary.player.data.rest

import com.shadhinmusiclibrary.player.data.model.Music
import com.shadhinmusiclibrary.player.singleton.DataSourceInfo
import com.shadhinmusiclibrary.utils.Status
import com.shadhinmusiclibrary.utils.safeApiCall
import kotlinx.coroutines.runBlocking

class ShadhinMusicRepository(private val playerApiService:PlayerApiService):MusicRepository {

    override fun fetchURL(music: Music): String  = runBlocking {
        val response = safeApiCall {
            playerApiService.fetchContentUrl(
                token = null, //TODO will apply in future
                ptype = if (music.isPodCast()) "PD" else null,
                type = music.podcastSubType(),
                ttype = music.trackType,
                name = if (!music.filePath().isNullOrEmpty()) music.filePath() else null
            )
        }

        val url = if (response.status == Status.SUCCESS && response.data?.data != null) {
            response.data.data
        } else {
            DataSourceInfo.isDataSourceError = true
            DataSourceInfo.dataSourceErrorCode = response.errorCode
            DataSourceInfo.dataSourceErrorMessage =
                response.data?.message ?: response.message ?: "Something wrong"
            null
        }
        return@runBlocking url.toString()
    }

    override fun refreshStreamingStatus() {

    }
}