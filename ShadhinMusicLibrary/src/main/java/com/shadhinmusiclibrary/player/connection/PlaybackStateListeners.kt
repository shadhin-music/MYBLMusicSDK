package com.shadhinmusiclibrary.player.connection

import android.support.v4.media.session.PlaybackStateCompat

internal fun interface PlaybackStateListeners {
    fun stateChange(playbackState: PlaybackStateCompat?)

}