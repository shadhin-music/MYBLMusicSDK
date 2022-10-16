package com.shadhinmusiclibrary.data.model.search

import androidx.annotation.Keep

@Keep
internal data class Artist(
    val data: MutableList<SearchData>,
    val message: String,
    val status: String,
    val type: String
)