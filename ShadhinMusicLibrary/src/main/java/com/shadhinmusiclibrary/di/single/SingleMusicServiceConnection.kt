package com.shadhinmusiclibrary.di.single

import android.content.Context
import com.shadhinmusiclibrary.player.connection.MusicServiceController
import com.shadhinmusiclibrary.player.connection.ShadhinMusicServiceConnection


class SingleMusicServiceConnection private constructor() {
    companion object {
        @Volatile
        private var INSTANCE: MusicServiceController? = null
        fun getInstance(context: Context): MusicServiceController =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: newInstance(context).also { INSTANCE = it }
            }

        private fun newInstance(context: Context):MusicServiceController{
            return ShadhinMusicServiceConnection(context)
        }

    }
}
