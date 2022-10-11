package com.shadhinmusiclibrary.data.model.search

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class SearchData(
    val AlbumId: String,
    val AlbumImage: String,
    val AlbumName: String,
    val Artist: String,
    val ArtistId: String,
    val ArtistImage: String,
    val Banner: String,
    val ClientValue: Int,
    val ContentID: String,
    val ContentType: String,
    val CreateDate: String,
    val Duration: String,
    val Follower: String,
    val IsPaid: Boolean,
    val NewBanner: String,
    val PlayCount: Int,
    val PlayListId: String,
    val PlayListImage: String,
    val PlayListName: String,
    val PlayUrl: String?,
    val RootId: String,
    /*val RootType: String,*/
    val Seekable: Boolean,
    val TeaserUrl: String,
    val TrackType: String,
    val Type: String,
    val fav: String,
    val image: String,
    val imageWeb: String,
    val title: String,

/*    val rootType: String?,*/
    val rootContentID: String,
    val rootContentType: String,
    val rootImage: String
) : Serializable {
    fun getImageUrl300Size(): String {
        return this.image.replace("<\$size\$>", "300")
    }
}