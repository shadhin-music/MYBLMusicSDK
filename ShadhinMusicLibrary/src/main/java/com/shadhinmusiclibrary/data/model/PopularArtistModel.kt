package com.shadhinmusiclibrary.data.model

data class PopularArtistModel(
    val Total: Int,
    val `data`: List<Data>,
    val message: String,
    val status: String
)