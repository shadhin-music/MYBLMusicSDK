package com.shadhinmusiclibrary.data.model.lastfm;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Keep
public class Similar {
    @SerializedName("artist")
    @Expose
    private List<LastFmArtist> artist = null;

    public List<LastFmArtist> getArtist() {
        return artist;
    }

    public void setArtist(List<LastFmArtist> artist) {
        this.artist = artist;
    }
}
