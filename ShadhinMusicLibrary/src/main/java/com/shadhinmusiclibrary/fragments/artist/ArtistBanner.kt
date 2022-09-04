package com.shadhinmusiclibrary.fragments.artist

data class ArtistBanner(
    val PlayListId: String,
    val PlayListImage: String,
    val `data`: List<Any>,
    val fav: String,
    val follow: String,
    val image: String,
    val message: String,
    val status: String,
    val type: String
)