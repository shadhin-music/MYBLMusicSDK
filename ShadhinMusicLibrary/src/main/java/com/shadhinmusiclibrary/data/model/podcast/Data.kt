package com.shadhinmusiclibrary.data.model.podcast

data class Data(
    val About: String,
    val Code: String,
    val ContentType: String,
    val Duration: String,
    val EpisodeList: List<Episode>,
    val Id: Int,
    val ImageUrl: String,
    val IsComingSoon: Boolean,
    val Name: String,
    val Presenter: String,
    val ProductBy: String,
    val Sort: Int
)