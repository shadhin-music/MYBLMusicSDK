package com.shadhinmusiclibrary.fragments.artist

import java.io.Serializable

data class ArtistAlbumModel(
    val `data`: List<ArtistAlbumModelData>,
    val fav: String,
    val follow: String,
    val image: String,
    val isPaid: Boolean,
    val message: String,
    val status: String,
    val type: String
):Serializable