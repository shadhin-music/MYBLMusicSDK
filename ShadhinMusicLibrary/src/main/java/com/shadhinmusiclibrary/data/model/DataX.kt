package com.shadhinmusiclibrary.data.model

import java.io.Serializable

data class DataX(
    val AlbumId: String,
    val AlbumImage: Any,
    val AlbumName: Any,
    val Artist: String,
    val ArtistId: String,
    val ArtistImage: Any,
    val Banner: String,
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
    val RootType: String,
    val Seekable: Boolean,
    val TeaserUrl: String,
    val TrackType: String,
    val Type: String,
    val fav: String,
    val image: String,
    val imageWeb: Any,
    val title: String
) : Serializable