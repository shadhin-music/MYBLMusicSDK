package com.shadhinmusiclibrary.data.model

import androidx.annotation.Keep

@Keep
data class Data(
    val ArtistName: String,
    val Client: Int,
    val Follower: String,
    val Id: String,
    val Image: String,
    val PlayUrl: String
)