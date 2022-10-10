package com.shadhinmusiclibrary.data.model

data class FeaturedPodcast(
    val `data`: List<FeaturedPodcastData>,
    val message: String,
    val status: String
)