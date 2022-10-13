package com.shadhinmusiclibrary.data.model.search

internal data class TopTrendingModel(
    val data: List<TopTrendingdata>,
    val fav: Any,
    val follow: Any,
    val image: Any,
    val isPaid: Boolean,
    val message: String,
    val status: String,
    val type: String
)