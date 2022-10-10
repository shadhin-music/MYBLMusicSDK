package com.shadhinmusiclibrary.data.model.podcast

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
    val TrackList: MutableList<Track>,
    val fav: String
)