package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.SongDetail

internal interface MusicControlCallback {

    fun playSong(mSongDetail: SongDetail)
}