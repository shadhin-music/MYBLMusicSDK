package com.shadhinmusiclibrary.player.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MusicPosition {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String mediaId;
    private long position;
    private long duration;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMediaId() {

        return mediaId;
    }

    public void setMediaId(String mediaId) {
        setId(mediaId.hashCode());
        this.mediaId = mediaId;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "MusicPosition{" +
                "id=" + id +
                ", mediaId='" + mediaId + '\'' +
                ", position=" + position +
                ", duration=" + duration +
                '}';
    }
}
