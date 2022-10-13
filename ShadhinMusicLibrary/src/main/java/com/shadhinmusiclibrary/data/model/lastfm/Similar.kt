package com.shadhinmusiclibrary.data.model.lastfm

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.shadhinmusiclibrary.data.model.lastfm.LastFmArtist

@Keep
internal class Similar {
    @SerializedName("artist")
    @Expose
    var artist: List<LastFmArtist>? = null
}