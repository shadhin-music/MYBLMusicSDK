package com.shadhinmusiclibrary.player.connection

import android.support.v4.media.session.PlaybackStateCompat

fun interface PlaybackStateListeners {
    fun stateChange(playbackState: PlaybackStateCompat?)

}