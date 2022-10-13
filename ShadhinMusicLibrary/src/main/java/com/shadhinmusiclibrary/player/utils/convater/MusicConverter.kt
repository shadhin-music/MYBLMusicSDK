package com.shadhinmusiclibrary.player.utils.convater

import com.shadhinmusiclibrary.player.data.model.Music

internal interface MusicConverter {
    fun convert():Music
}