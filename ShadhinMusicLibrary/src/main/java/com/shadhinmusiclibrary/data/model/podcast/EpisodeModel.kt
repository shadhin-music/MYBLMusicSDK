package com.shadhinmusiclibrary.data.model.podcast

import androidx.annotation.Keep

@Keep
internal data class EpisodeModel(
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
    val TrackList: MutableList<SongTrackModel>,
    val fav: String
)