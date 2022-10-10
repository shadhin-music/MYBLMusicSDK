package com.shadhinmusiclibrary.data.model.search

data class PodcastTrack(
    val data: List<SearchData>,
    val message: String,
    val status: String,
    val type: String
)