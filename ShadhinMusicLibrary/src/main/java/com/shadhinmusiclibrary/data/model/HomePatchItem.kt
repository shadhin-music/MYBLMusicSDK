package com.shadhinmusiclibrary.data.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
internal data class HomePatchItem(
    val Code: String,
    var ContentType: String,
    var Data: List<HomePatchDetailModel>,
    val Design: String,
    val Name: String,
    val Sort: Int,
    val Total: Int
) : Serializable