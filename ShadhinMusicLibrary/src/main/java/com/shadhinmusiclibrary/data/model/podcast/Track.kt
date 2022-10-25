package com.shadhinmusiclibrary.data.model.podcast

import androidx.annotation.Keep
import java.io.Serializable

@Keep
internal data class Track(
    val CeateDate: String,
    val ContentType: String,
    val Details: String,
    val Duration: String,
    val EpisodeId: String,
    val Id: Int,
    val ImageUrl: String,
    val IsPaid: Boolean,
    val Name: String,
    val PlayUrl: String,
    val Seekable: Boolean,
    val ShowId: String,
    val Sort: Int,
    val Starring: String,
    val TrackType: String,
    val fav: String,
    val totalStream: Int,

    val rootContentID: String,
    val rootImage: String,
    val rootContentType: String,
    var isPlaying: Boolean = false
) : Serializable {
    fun getImageUrl300Size(): String {
        return this.ImageUrl.replace("<\$size\$>", "300")
    }
}


//SongDetail(
//ContentID = ShowId,
//image = ImageUrl,
//title = Name,
//ContentType = ContentType,
//PlayUrl = PlayUrl,
//artist = Starring,
//duration = Duration,
//copyright = "",
//labelname = Name,
//releaseDate = CeateDate,
//fav = fav,
//ArtistId = "",
//albumId = "",
//userPlayListId = "",
///*rootType = ContentType,*/
//
//rootContentID = rootContentID,
//rootContentType = rootContentType,
//rootImage = rootImage
//)