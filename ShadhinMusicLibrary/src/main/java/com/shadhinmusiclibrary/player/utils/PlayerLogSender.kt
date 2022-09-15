/*
package com.shadhinmusiclibrary.player.utils

import android.os.Bundle
import com.google.android.exoplayer2.MediaItem
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


private const val TAG = "PlayerLogSender"
class PlayerLogSender(
    private val serviceScope: CoroutineScope?,
    private val userApiService: RestRepositoryKt?,
    private val localStorage:LocalStorage
) {
    private val firebaseAnalytics:FirebaseAnalytics = Firebase.analytics
    private var timeDuration:Long = 0L
    private var startTime:Long = 0L
    private var endTime:Long = 0L
    private var oldMediaId:String = "old"
    private var currentMediaId:String = "new"
    private var isMusicChange = false
    private var currentMusic: Music?=null
    private var oldMusic: Music?=null


    fun mediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        currentMediaId = mediaItem?.mediaId.toString()
        currentMusic = mediaItem?.toMusic()
        if(oldMediaId != currentMediaId){
            isMusicChange = true
        }
    }

     fun playingChanged(isPlaying: Boolean) {

        if(isPlaying){
            startTime = System.currentTimeMillis()
        }else{
            addTimeDuration()
        }

        finalCalculation()

    }
    private fun addTimeDuration(){
        val currentTime = System.currentTimeMillis()
        val duration = currentTime - startTime
        timeDuration += duration
    }
    public fun closePlayer(){

        addTimeDuration()
        endTime = System.currentTimeMillis()

        if(timeDuration.toSecond()> DELAY){
            sendContentPlayedEvent(timeDuration,currentMusic)
            if(currentMusic?.trackType?.equals("LM",true) != true) {
                eventLogger(
                    timeDuration.toSecond().toInt(),
                    startTime.toDateTimeString(),
                    endTime.toDateTimeString(),
                    currentMusic?.isPodCast(),
                    currentMusic?.isVideo(),
                    currentMusic?.mediaId,
                    currentMusic?.contentType,
                    currentMusic?.userPlayListId
                )
            }
        }


    }
    private fun finalCalculation(){

        if(isMusicChange){
            endTime = System.currentTimeMillis()
            sendData()
            oldMediaId = currentMediaId
            oldMusic = currentMusic
            timeDuration = 0
            isMusicChange = false
        }
    }
    private fun sendContentPlayedEvent(duration: Long, music: Music?) {


        val bundle = Bundle().apply {
            putString("content_type",music?.contentType?.lowercase())
            putString("content_id",music?.mediaId?.lowercase())
            putString("user_type", subStatus?.iosName)
            putString("content_name",music?.title?.lowercase())
            putString("duration_sec",duration.toSecond().toString())
            putString("platform","android")
        }
        firebaseAnalytics.logEvent("sm_content_played",bundle)

    }

    private fun sendData() {

        if(oldMusic !=null && timeDuration.toSecond()> DELAY){
            sendContentPlayedEvent(timeDuration,oldMusic)
            if(oldMusic?.trackType?.equals("LM",true) != true) {
                eventLogger(
                    timeDuration.toSecond().toInt(),
                    startTime.toDateTimeString(),
                    endTime.toDateTimeString(),
                    oldMusic?.isPodCast(),
                    oldMusic?.isVideo(),
                    oldMusic?.mediaId,
                    oldMusic?.contentType,
                    oldMusic?.userPlayListId
                )
            }
        }

    }
    private fun eventLogger(
        timeCountSecond: Int,
        sTime: String?,
        eTime: String?,
        isPodcast:Boolean?= false,
        isVideo:Boolean?= false,
        id: String?,
        type: String?,
        userPlayListId: String?
    ) {


        if (sTime == null || eTime == null || id == null || type == null) {
            return
        }


        serviceScope?.launch {

            userApiService?.postSongPlayCount(
                isPD = isPodcast?:false,
                isVideo = isVideo?:false,
                conId = id,
                type = type,
                playCount = "1",
                time = timeCountSecond,
                sTime = sTime,
                eTime = eTime,
                userPlayListId = userPlayListId,
                localStorage.getToken()
            )
            this.cancel()
        }

    }
    companion object{
        const val DELAY:Int = 1 //1 Sec
        @JvmStatic
        public var subStatus:SubStatus?=null
    }

}*/
