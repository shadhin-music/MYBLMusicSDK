package com.shadhinmusiclibrary.fragments.artist

import androidx.annotation.Keep

@Keep
internal data class ArtistBanner(
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