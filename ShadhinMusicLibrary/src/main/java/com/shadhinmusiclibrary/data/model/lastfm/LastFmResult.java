package com.shadhinmusiclibrary.data.model.lastfm;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class LastFmResult {
    @SerializedName("artist")
    @Expose
    private LastFmArtistData artist;

    public LastFmArtistData getArtist() {
        return artist;
    }

    public void setArtist(LastFmArtistData artist) {
        this.artist = artist;
    }
}
