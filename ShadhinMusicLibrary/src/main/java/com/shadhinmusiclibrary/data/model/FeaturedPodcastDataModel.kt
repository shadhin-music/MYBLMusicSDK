package com.shadhinmusiclibrary.data.model

import androidx.annotation.Keep

@Keep
internal data class FeaturedPodcastDataModel(
    val Data: List<FeaturedPodcastDetailsModel>,
    val Design: Any,
    val PatchName: String,
    val PatchType: String
)