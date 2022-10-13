package com.shadhinmusiclibrary.player.data.source

import android.content.Context
import android.os.Bundle
import androidx.core.net.toUri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.shadhinmusiclibrary.data.model.Video
import com.shadhinmusiclibrary.player.Constants
import com.shadhinmusiclibrary.player.data.model.Music
import com.shadhinmusiclibrary.player.data.rest.MusicRepository
import com.shadhinmusiclibrary.player.utils.CharParser
import com.shadhinmusiclibrary.utils.exH
import com.shadhinmusiclibrary.utils.randomString

internal class ShadhinVideoMediaSource(
    private val context: Context,
    private val videoList: List<Video>,
    private val cache: SimpleCache,
    private val musicRepository: MusicRepository
) :MediaSources{
    override fun createSources(): List<MediaSource> {
        return videoList.map { createSource(it) }
    }

    private fun createSource(video: Video): ProgressiveMediaSource {
        val dataSource: DataSource.Factory = ShadhinDataSourceFactory.build(context,toMusic(video),cache,musicRepository)
        val pla = toVideoMediaItem(video)
        return ProgressiveMediaSource.Factory(dataSource)
            .createMediaSource(pla)
    }
    private fun toMusic(video: Video): Music {
        return Music(
            mediaId = video.contentID,
            title = video.title,
            displayDescription = "",
            date = "",
            displayIconUrl = CharParser.getImageFromTypeUrl(video.image, video.contentType),
            mediaUrl = video.playUrl,
            artistName = video.artist,
            contentType = video.contentType,
            userPlayListId = video.albumId,
            fav = video.fav,
            trackType = video.trackType,
            isPaid = video.isPaid,
        ).applyRootInfo(
            rootId = video.rootId,
            rootType = video.rootType,
           // rootTitle = video.rootTitle,
           // rootImage = video.rootImage
        )
    }
   private fun toVideoMediaItem(video: Video): MediaItem {
        val videoUrl = "${Constants.FILE_BASE_URL}${video.playUrl}"

        return MediaItem.Builder()
            .setMediaId(video.contentID?: randomString(5))
            .setUri(videoUrl)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(video.title)
                    .setMediaUri(videoUrl.toUri())
                    .setExtras(Bundle().apply {
                        putString(Music.CONTENT_TYPE,video.contentType)
                    })
                    .setArtworkUri(exH { video.image?.toUri() })
                    .setArtist(video.artist)
                    .build()
            )
            .build()
    }
}