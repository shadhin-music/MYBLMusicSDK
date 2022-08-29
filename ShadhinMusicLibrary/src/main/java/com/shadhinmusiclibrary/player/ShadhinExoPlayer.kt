package com.shadhinmusiclibrary.player

import android.annotation.SuppressLint
import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory



class ShadhinExoPlayer private constructor(private val exoPlayer: ExoPlayer):ExoPlayer by exoPlayer{
    override fun seekToPrevious() {
        if(!isUserLimitOver) {
            exoPlayer.seekToPrevious()
        }
        isUserLimitOver = false
    }

    override fun seekToNext() {
        if(!isUserLimitOver) {
            exoPlayer.seekToNext()
        }
        isUserLimitOver = false
    }


    companion object{
        @JvmStatic
        public var isUserLimitOver:Boolean = false

        @SuppressLint("UnsafeOptInUsageError")
        public  fun build(context: Context): ShadhinExoPlayer {
            val exoPlayer = ExoPlayer.Builder(context)
                .setAudioAttributes(audioAttributes(),true)
                .setHandleAudioBecomingNoisy(true)
                .setMediaSourceFactory(
                    DefaultMediaSourceFactory(context)
                        .setLiveMinOffsetMs(2000)
                )
                .build()
            return ShadhinExoPlayer(exoPlayer)
        }
        @SuppressLint("UnsafeOptInUsageError")
        private  fun audioAttributes(): AudioAttributes = AudioAttributes.Builder()
            .setContentType(C.CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()
    }
}
