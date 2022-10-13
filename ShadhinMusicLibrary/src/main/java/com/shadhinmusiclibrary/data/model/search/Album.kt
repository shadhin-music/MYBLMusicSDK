package com.shadhinmusiclibrary.data.model.search

internal data class Album(
    val data: MutableList<SearchData>,
    val message: String,
    val status: String,
    val type: String
)