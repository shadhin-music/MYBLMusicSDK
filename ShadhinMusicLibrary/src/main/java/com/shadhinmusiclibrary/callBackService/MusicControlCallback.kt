package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.SongDetail

interface MusicControlCallback {

    fun playSong(mSongDetail: SongDetail)
}