package com.shadhinmusiclibrary.player.data.source

import android.content.Context
import androidx.core.net.toUri
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.shadhinmusiclibrary.player.data.model.Music
import com.shadhinmusiclibrary.player.data.rest.MusicRepository


class ShadhinMediaSources(
    private val context: Context,
    private val musicList: List<Music>,
    private val cache: SimpleCache,
    private val musicRepository: MusicRepository
): MediaSources {
    override fun createSources(): List<ProgressiveMediaSource> {
        return musicList.map {createSource(it)}.toList()
    }
    private fun createSource(music: Music): ProgressiveMediaSource {
        val dataSource: DataSource.Factory = ShadhinDataSourceFactory.build(context,music,cache,musicRepository)
        val pla = music.toPlayerMediaItem()
        return ProgressiveMediaSource.Factory(dataSource)
            .createMediaSource(pla)
    }
}