package com.shadhinmusiclibrary.data.model

data class SongDetail(
    val ContentID: String,
    val image: String,
    val title: String,
    val ContentType: String,
    val PlayUrl: String,
    val artist: String,
    val duration: String,
    val copyright: String,
    val labelname: String,
    val releaseDate: String,
    val fav: String,
    val ArtistId: String?,
    val albumId: String?,
    val userPlayListId: String?,
    val rootType: String?,

    val rootContentID: String,
    val rootImage: String,
    val rootContentType: String
) {
    fun getImageUrl300Size(): String {
        return this.image.replace("<\$size\$>", "300")
    }

    fun getRootImageUrl300Size(): String {
        return this.rootImage.replace("<\$size\$>", "300")
    }
}