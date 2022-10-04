package com.shadhinmusiclibrary.data.model

import androidx.annotation.Keep

@Keep
data class FeaturedLatestTrackListData(
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
    val artistId: String
)