package com.shadhinmusiclibrary.player.utils.convater

import com.shadhinmusiclibrary.fragments.artist.ArtistContentData
import com.shadhinmusiclibrary.player.data.model.Music

class ArtistContentToMusic(private val data: ArtistContentData):MusicConverter {
    override fun convert(): Music {
       return Music(
            mediaId = data.ContentID,
            title = data.title,
            displayDescription = null,
            displayIconUrl = "https://images.unsplash.com/photo-1611915387288-fd8d2f5f928b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8MXx8fGVufDB8fHx8&w=1000&q=80",
            mediaUrl = "https://file-examples.com/storage/fe6d784fb46320d949c245e/2017/11/file_example_MP3_1MG.mp3",
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