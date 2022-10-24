package com.shadhinmusiclibrary.player.data.rest

import com.shadhinmusiclibrary.player.data.model.Music

internal interface MusicRepository {
    fun fetchURL(music: Music):String
    fun refreshStreamingStatus()
    fun fetchDownloadedURL(name:String):String
}