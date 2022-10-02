package com.shadhinmusiclibrary.data.model.search

data class Album(
    val data: List<SearchAlbumdata>,
    val message: String,
    val status: String,
    val type: String
)