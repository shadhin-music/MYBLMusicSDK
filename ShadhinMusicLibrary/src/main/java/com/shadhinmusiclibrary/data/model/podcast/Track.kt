package com.shadhinmusiclibrary.data.model.podcast

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Track(
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
    val rootContentType: String
): Serializable {
    fun getImageUrl300Size(): String {
        return this.ImageUrl.replace("<\$size\$>", "300")
    }
}