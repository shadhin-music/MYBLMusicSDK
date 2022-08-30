//package com.gm.shadhin.player
//
//import com.gm.shadhin.data.rest.kotlin.RestRepositoryKt
//import com.shadhinmusiclibrary.player.data.local.MusicPositionRepository
//import com.google.android.exoplayer2.ExoPlayer
//import com.google.android.exoplayer2.upstream.cache.SimpleCache
//import kotlinx.coroutines.CoroutineScope
//
//interface ShadhinMusicPlayerContext:MediaContext {
//    val musicPositionRepository: MusicPositionRepository?
//    val musicPlaybackPreparer: ShadhinMusicPlaybackPreparer?
//    val playerCache: SimpleCache?
//}
//interface MediaContext{
//    val exoPlayer: ExoPlayer?
//    val repositoryKt: RestRepositoryKt?
//    val scope: CoroutineScope?
//}