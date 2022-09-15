package com.shadhinmusiclibrary.player.data.rest

import com.shadhinmusiclibrary.player.data.model.Music

interface MusicRepository {
    fun fetchURL(music: Music):String
}