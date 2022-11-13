package com.shadhinmusiclibrary.player.data.rest.user_history

import com.shadhinmusiclibrary.data.repository.AuthRepository.Companion.appToken
import com.shadhinmusiclibrary.player.data.rest.PlayerApiService
import com.shadhinmusiclibrary.utils.AppConstantUtils.token
import com.shadhinmusiclibrary.utils.isValidToken
import com.shadhinmusiclibrary.utils.postContentType
import com.shadhinmusiclibrary.utils.preContentType
@OptIn(ExperimentalStdlibApi::class)
internal class UserHistoryRepositoryImpl(private val playerApiService: PlayerApiService) :UserHistoryRepository {

    override suspend fun postHistory(
        isPD: Boolean,
        isVideo:Boolean,
        conId: String,
        type: String,
        playCount: String,
        time: Int,
        sTime: String,
        eTime: String,
        userPlayListId: String?,
    ) {

        val jsonParams = HashMap<String?, Any?>()
        jsonParams["ContentId"] = conId
        jsonParams["PlayCount"] = playCount
        jsonParams["TimeCountSecond"] = time
        jsonParams["PlayIn"] = sTime
        jsonParams["PlayOut"] = eTime
        if (userPlayListId != null) {
            jsonParams["UserPlayListId"] = userPlayListId
        }

            if (isPD || isVideo) {
                jsonParams["Type"] = type.trim().uppercase().preContentType()
                jsonParams["ContentType"] = type.trim().uppercase().postContentType()
                try {
                    playerApiService.trackPodcastPlaying(jsonParams)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            } else {
                jsonParams["Type"] = type
                try {
                    playerApiService.trackSongPlaying(jsonParams)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
    }
}