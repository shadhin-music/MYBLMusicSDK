package com.shadhinmusiclibrary.data.model.search

data class Track(
    val data: List<SearchTrackdata>,
    val message: String,
    val status: String,
    val type: String
)