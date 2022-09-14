package com.shadhinmusiclibrary.player.data.model

import android.os.Bundle
import com.google.android.exoplayer2.SimpleExoPlayer
import java.io.Serializable

data class PlayerProgress(
    var currentPosition:Long?=0,
    val bufferPosition:Long?=0,
    val duration:Long?=0,
    val isPlaying:Boolean = false,
    val mediaId:String?=null
):Serializable {
    val isEmpty:Boolean
        get() = currentPosition == 0L && duration?:0L <= 0L && bufferPosition == 0L


    fun durationTimeLabel() = createTimeLabel(duration ?: 0)
    fun currentPositionTimeLabel() = createTimeLabel(currentPosition ?: 0)
    fun createTimeLabel(duration: Long): String? {
        var timeLabel: String? = ""
        val min = duration / 1000 / 60
        val sec = duration / 1000 % 60
        timeLabel += "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec
        return timeLabel
    }
    fun toBundle(key:String) = Bundle().apply {
        putSerializable(key,this@PlayerProgress)
    }

    override fun toString(): String {
        return "$mediaId (currentPosition=$currentPosition, bufferPosition=$bufferPosition, duration=$duration, isPlaying=$isPlaying, isEmpty=$isEmpty)"
    }

    companion object{
        fun fromPlayer(exoPlayer: SimpleExoPlayer): PlayerProgress {
            return PlayerProgress(
                currentPosition = exoPlayer.currentPosition,
                bufferPosition = exoPlayer.bufferedPosition,
                duration = exoPlayer.duration,
                isPlaying = exoPlayer.isPlaying,
               // mediaId = exoPlayer.currentMediaItem?.mediaId
            )
        }
    }


}



