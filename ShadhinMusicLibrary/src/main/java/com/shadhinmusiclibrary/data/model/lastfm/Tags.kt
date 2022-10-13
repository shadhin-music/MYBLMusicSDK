package com.shadhinmusiclibrary.data.model.lastfm

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

@Keep
internal class Tags {
    @SerializedName("tag")
    @Expose
    var tag: List<Tag>? = null
}