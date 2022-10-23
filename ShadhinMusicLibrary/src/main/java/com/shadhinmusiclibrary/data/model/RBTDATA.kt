package com.shadhinmusiclibrary.data.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
internal data class RBTDATA(
    val pwaUrl: String,
    val pwatopchartURL: String,
    val redirectUrl: String
) : Serializable