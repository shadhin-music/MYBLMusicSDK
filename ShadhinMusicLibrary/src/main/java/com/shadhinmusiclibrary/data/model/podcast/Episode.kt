package com.shadhinmusiclibrary.data.model.podcast

import androidx.annotation.Keep

@Keep
data class Episode(
    val Code: String,
    val ContentType: String,
    val Details: String,
    var Id: Int,
    val ImageUrl: String,
    val IsCommentPaid: Boolean,
    val IsPaid: Boolean,
    val Name: String,
    val ShowId: String,
    val Sort: Int,
    val TrackList: List<Track>,
    val fav: String
)