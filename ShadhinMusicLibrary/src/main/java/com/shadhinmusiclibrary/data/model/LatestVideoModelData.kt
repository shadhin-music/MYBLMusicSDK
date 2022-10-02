package com.shadhinmusiclibrary.data.model

import java.io.Serializable

data class LatestVideoModelData(
    val AlbumId: String,
    val AlbumImage: Any,
    val AlbumName: Any,
    val Artist: String,
    val ArtistId: String,
    val ArtistImage: Any,
    val Banner: String,
    val ClientValue: Int,
    val ContentID: String,
    val ContentType: String,
    val CreateDate: Any,
    val Duration: String,
    val Follower: Any,
    val IsPaid: Boolean,
    val NewBanner: String,
    val PlayCount: Int,
    val PlayListId: Any,
    val PlayListImage: Any,
    val PlayListName: Any,
    val PlayUrl: String,
    val RootId: Any,
    val RootType: Any,
    val Seekable: Boolean,
    val TeaserUrl: Any,
    val TrackType: String,
    val Type: String,
    val fav: String,
    val image: String,
    val imageWeb: Any,
    val title: String
): Serializable {
    fun getImageUrl300Size(): String {
        return this.image.replace("<\$size\$>", "300")
    }
}