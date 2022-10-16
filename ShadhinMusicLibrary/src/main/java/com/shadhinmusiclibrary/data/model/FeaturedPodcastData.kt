package com.shadhinmusiclibrary.data.model

import androidx.annotation.Keep

@Keep
internal data class FeaturedPodcastData(
    val Data: List<FeaturedPodcastDetails>,
    val Design: Any,
    val PatchName: String,
    val PatchType: String
)