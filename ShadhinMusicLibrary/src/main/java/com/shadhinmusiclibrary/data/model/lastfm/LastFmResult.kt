package com.shadhinmusiclibrary.data.model.lastfm

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.shadhinmusiclibrary.data.model.lastfm.LastFmArtistData

@Keep
internal class LastFmResult {
    @SerializedName("artist")
    @Expose
    var artist: LastFmArtistData? = null
}