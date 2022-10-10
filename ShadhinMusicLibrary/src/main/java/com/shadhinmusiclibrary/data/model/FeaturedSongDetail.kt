package com.shadhinmusiclibrary.data.model

import java.io.Serializable

data class FeaturedSongDetail(
    val contentID: String,
    val image: String,
    val title: String,
    val contentType: String,
    val playUrl: String,
    val albumId: String,
    val artistname: String,
    val duration: String,
    val copyright: String,
    val labelname: String,
    val releaseDate: String,
    val fav: Any,
    val artistId: String,
    val rootType: String?,

    val rootContentID: String,
    val rootImage: String,
    val rootContentType: String
) : Serializable {
    fun getImageUrl300Size(): String {
        return this.image.replace("<\$size\$>", "300")
    }
}