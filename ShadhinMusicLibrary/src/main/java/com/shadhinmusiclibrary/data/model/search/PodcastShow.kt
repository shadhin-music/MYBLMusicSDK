package com.shadhinmusiclibrary.data.model.search

data class PodcastShow(
    val data: List<SearchPodcastShowdata>,
    val message: String,
    val status: String,
    val type: String
)