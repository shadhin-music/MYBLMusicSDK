package com.shadhinmusiclibrary.data.model

import androidx.annotation.Keep

@Keep
internal data class PopularArtistModel(
    val Total: Int,
    val `data`: List<Data>,
    val message: String,
    val status: String
)