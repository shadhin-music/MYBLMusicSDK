package com.shadhinmusiclibrary.fragments.artist

data class ArtistContent(
    val MonthlyListener: String,
    val `data`: List<ArtistContentData>,
    val fav: String,
    val follow: String,
    val image: String,
    val message: String,
    val status: String,
    val total: Int,
    val type: String
)