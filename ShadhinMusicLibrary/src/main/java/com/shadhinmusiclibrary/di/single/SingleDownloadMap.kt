package com.shadhinmusiclibrary.di.single

import android.content.Context
import com.shadhinmusiclibrary.player.connection.MusicServiceController
import com.shadhinmusiclibrary.player.connection.ShadhinMusicServiceConnection

internal class SingleDownloadMap {
    companion object {
        @Volatile
        private var INSTANCE: MutableMap<String,String>? = null
        fun getInstance(): MutableMap<String,String> =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: newInstance().also { INSTANCE = it }
            }

        private fun newInstance(): MutableMap<String,String> {
            return HashMap()
        }
        fun destroy(){
            INSTANCE = null
        }


    }
}