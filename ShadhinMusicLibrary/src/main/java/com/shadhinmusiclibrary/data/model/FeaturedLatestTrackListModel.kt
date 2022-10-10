package com.shadhinmusiclibrary.data.model

import androidx.annotation.Keep

@Keep
data class FeaturedLatestTrackListModel(
    val `data`: MutableList<FeaturedLatestTrackListData>,
    val message: String,
    val status: String
)