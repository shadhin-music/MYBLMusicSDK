package com.shadhinmusiclibrary.di.single

import android.content.Context
import com.shadhinmusiclibrary.player.ShadhinMusicServiceConnection

class SingleMusicServiceConnection private constructor() {
    companion object {
        @Volatile
        private var INSTANCE: ShadhinMusicServiceConnection? = null
        fun getInstance(context: Context): ShadhinMusicServiceConnection =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: newInstance(context).also { INSTANCE = it }
            }

        private fun newInstance(context: Context):ShadhinMusicServiceConnection{
            return ShadhinMusicServiceConnection(context)
        }

    }
}
