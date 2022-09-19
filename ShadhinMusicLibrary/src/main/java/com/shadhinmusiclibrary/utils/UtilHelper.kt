package com.shadhinmusiclibrary.utils

import android.app.Activity
import android.content.Context
import android.graphics.Point
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.player.Constants
import com.shadhinmusiclibrary.player.data.model.Music

object UtilHelper {
    fun getScreenHeightWidth(context: Context, type: Int): Int {
        val display = (context as Activity).windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        val height = size.y
        return if (type == 0) {
            width
        } else {
            height
        }
    }

    fun getImageUrlSize300(imageUrl: String): String {
        return imageUrl.replace("<\$size\$>", "300")
    }

    fun getMusicToSongDetail(mSongDet: SongDetail): Music {
        mSongDet.apply {
            return Music(
                mediaId = ContentID,
                title = title,
                displayDescription = "",
                displayIconUrl = getImageUrl300Size(),
                mediaUrl = Constants.FILE_BASE_URL + PlayUrl,
                artistName = artist,
                date = releaseDate,
                contentType = ContentType,
                userPlayListId = userPlayListId,
                episodeId = "",
                starring = "",
                seekable = false,
                details = "",
                totalStream = 0L
            )
        }
    }

    fun getSongDetailToMusic(mMusic: Music): SongDetail {
        mMusic.apply {
            return SongDetail(
                ContentID = mediaId!!,
                image = displayIconUrl!!,
                title = title!!,
                ContentType = contentType!!,
                PlayUrl = mediaUrl!!,
                artist = artistName!!,
                duration = date!!,
                copyright = "",
                labelname = "",
                releaseDate = "",
                fav = "",
                ArtistId = "",
                albumId = "",
                userPlayListId = if (userPlayListId != null) {
                    userPlayListId!!
                } else {
                    ""
                }
            )
        }
    }
}