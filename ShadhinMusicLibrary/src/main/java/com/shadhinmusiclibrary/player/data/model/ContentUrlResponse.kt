package com.shadhinmusiclibrary.player.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ContentUrlResponse(
    @SerializedName("Data")
    var `data`: String? = null,
    @SerializedName("Message")
    var message: String? = null
)