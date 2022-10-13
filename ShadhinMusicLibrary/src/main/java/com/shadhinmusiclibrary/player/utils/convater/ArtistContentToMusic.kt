package com.shadhinmusiclibrary.player.utils.convater

import com.shadhinmusiclibrary.fragments.artist.ArtistContentData
import com.shadhinmusiclibrary.player.Constants.FILE_BASE_URL
import com.shadhinmusiclibrary.player.data.model.Music
import com.shadhinmusiclibrary.player.utils.CharParser

internal class ArtistContentToMusic(private val data: ArtistContentData):MusicConverter {
    override fun convert(): Music {
       return Music(
            mediaId = data.ContentID,
            title = data.title,
            displayDescription = null,
            displayIconUrl = CharParser.getImageFromTypeUrl(data.image,"A"),
            mediaUrl = "${FILE_BASE_URL}${data.PlayUrl}",
            artistName = data.artistname,
            date = null,
            contentType = null,
            userPlayListId = null,
            episodeId = null,
            starring = null,
            seekable = null,
            details = null,
            totalStream = null,
            fav = null,
            trackType = null,
            isPaid = null,
            rootId = null,
            rootType = null,
            rootTitle = null,
            rootImage = null,
            isPrepare = null,
            isPlaying = null
        )
    }
}