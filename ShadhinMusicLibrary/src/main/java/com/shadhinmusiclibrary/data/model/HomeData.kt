package com.shadhinmusiclibrary.data.model

import androidx.annotation.Keep

@Keep
internal data class HomeData(
    val data: List<HomePatchItem>,
    val fav: Any,
    val follow: Any,
    val image: Any,
    val message: String,
    val status: String,
    val total: Int,
    val type: Any
)