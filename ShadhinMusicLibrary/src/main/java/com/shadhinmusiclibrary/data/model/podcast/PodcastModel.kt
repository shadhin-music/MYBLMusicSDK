package com.shadhinmusiclibrary.data.model.podcast

import androidx.annotation.Keep

@Keep
internal data class PodcastModel(
    val `data`: Data,
    val message: String,
    val status: String
)