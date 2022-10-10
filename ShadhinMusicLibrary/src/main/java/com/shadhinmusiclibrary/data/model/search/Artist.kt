package com.shadhinmusiclibrary.data.model.search

data class Artist(
    val data: List<SearchArtistdata>,
    val message: String,
    val status: String,
    val type: String
)