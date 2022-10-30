package com.shadhinmusiclibrary.data.model.podcast

import androidx.annotation.Keep

@Keep
internal data class DataModel(
    val About: String,
    val Code: String,
    val ContentType: String,
    val Duration: String,
    val EpisodeList: MutableList<EpisodeModel>,
    val Id: Int,
    val ImageUrl: String,
    val IsComingSoon: Boolean,
    val Name: String,
    val Presenter: String,
    val ProductBy: String,
    val Sort: Int
)