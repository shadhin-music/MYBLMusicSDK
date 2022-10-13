package com.shadhinmusiclibrary.fragments.artist

import androidx.annotation.Keep
import java.io.Serializable
@Keep
internal data class ArtistAlbumModel(
    val `data`: MutableList<ArtistAlbumModelData>,
    val fav: String,
    val follow: String,
    val image: String,
    val isPaid: Boolean,
    val message: String,
    val status: String,
    val type: String
):Serializable