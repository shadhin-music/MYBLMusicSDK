package com.shadhinmusiclibrary.player

import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.coroutines.CoroutineScope

interface ShadhinMusicPlayerContext: MediaContext {
    var musicPlaybackPreparer: ShadhinMusicPlaybackPreparer?
}
interface MediaContext{
    val exoPlayer: SimpleExoPlayer?
    val scope: CoroutineScope?
}