package com.shadhinmusiclibrary.data.model.search

import androidx.annotation.Keep

@Keep
internal data class SearchModel(
    val data: SearchModelData,
    val message: String,
    val status: String
)