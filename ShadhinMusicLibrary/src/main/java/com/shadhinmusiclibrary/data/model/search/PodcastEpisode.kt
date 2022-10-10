package com.shadhinmusiclibrary.data.model.search

data class PodcastEpisode(
    val data: List<SearchPodcastEpisodedata>,
    val message: String,
    val status: String,
    val type: String
)