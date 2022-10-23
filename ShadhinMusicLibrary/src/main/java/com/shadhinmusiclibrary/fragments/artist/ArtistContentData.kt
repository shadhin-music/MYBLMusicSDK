package com.shadhinmusiclibrary.fragments.artist

import androidx.annotation.Keep

@Keep
internal data class ArtistContentData(
    val AlbumId: String,
    val ArtistId: String,
    val ContentID: String,
    val ContentType: String,
    val PlayUrl: String,
    val TotalPlay: Int,
    val artistname: String,
    val copyright: String,
    val duration: String,
    val fav: String,
    val image: String,
    val labelname: String,
    val releaseDate: String,
    val title: String,

    val rootContentID: String,
    val rootImage: String,
    val rootContentType: String,
    var isPlaying: Boolean = false
) {
    fun getImageUrl300Size(): String {
        return this.image.replace("<\$size\$>", "300")
    }

    fun getRootImageUrl300Size(): String {
        return this.rootImage.replace("<\$size\$>", "300")
    }
}