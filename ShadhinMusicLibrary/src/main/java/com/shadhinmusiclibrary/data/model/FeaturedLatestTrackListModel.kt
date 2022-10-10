package com.shadhinmusiclibrary.data.model

data class FeaturedLatestTrackListModel(
    val `data`: MutableList<FeaturedLatestTrackListData>,
    val message: String,
    val status: String
)