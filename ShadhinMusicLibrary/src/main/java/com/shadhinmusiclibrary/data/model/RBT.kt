package com.shadhinmusiclibrary.data.model

import androidx.annotation.Keep

@Keep
internal data class RBT(
    val `data`: RBTDATA,
    val message: String,
    val status: String
)