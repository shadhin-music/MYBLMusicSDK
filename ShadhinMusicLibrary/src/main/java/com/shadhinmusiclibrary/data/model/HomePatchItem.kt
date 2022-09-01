package com.shadhinmusiclibrary.data.model

import java.io.Serializable

data class HomePatchItem(
    val Code: String,
    val ContentType: String,
    val Data: List<HomePatchDetail>,
    val Design: String,
    val Name: String,
    val Sort: Int,
    val Total: Int
) : Serializable