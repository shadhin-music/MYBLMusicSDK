package com.shadhinmusiclibrary.data.model

import androidx.annotation.Keep

@Keep
data class FeaturedLatestTrackListData(
    val albumId: String,
    val artistId: String,
    val artistname: String,
    val contentID: String,
    val contentType: String,
    val copyright: String,
    val duration: String,
    val fav: Any,
    val image: String,
    val labelname: String,
    val playUrl: String,
    val releaseDate: String,
    val title: String
)