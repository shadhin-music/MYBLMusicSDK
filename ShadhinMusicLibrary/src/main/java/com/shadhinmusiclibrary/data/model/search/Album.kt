package com.shadhinmusiclibrary.data.model.search

data class Album(
    val data: MutableList<SearchData>,
    val message: String,
    val status: String,
    val type: String
)