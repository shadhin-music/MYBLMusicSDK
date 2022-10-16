package com.shadhinmusiclibrary.data.model

import androidx.annotation.Keep

@Keep
internal data class FeaturedPodcast(
    val `data`: List<FeaturedPodcastData>,
    val message: String,
    val status: String
)