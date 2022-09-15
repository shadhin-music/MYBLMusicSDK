package com.shadhinmusiclibrary.player.data.source

import com.google.android.exoplayer2.source.MediaSource

interface MediaSources {
    fun createSources(): List<MediaSource>
}