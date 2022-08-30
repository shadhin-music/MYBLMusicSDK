//package com.gm.shadhin.player.listener
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.os.Looper
//import androidx.media3.common.MediaItem
//import androidx.media3.common.PlaybackException
//import androidx.media3.common.Player
//import androidx.media3.common.Player.DISCONTINUITY_REASON_SEEK
//import androidx.media3.exoplayer.ExoPlayer
//import androidx.media3.exoplayer.analytics.AnalyticsListener
//
//import com.gm.shadhin.player.ShadhinMusicPlaybackPreparer
//import com.shadhinmusiclibrary.player.data.local.MusicPositionRepository
//import com.gm.shadhin.player.singleton.DataSourceInfo.dataSourceErrorCode
//import com.gm.shadhin.player.singleton.DataSourceInfo.dataSourceErrorMessage
//import com.gm.shadhin.player.singleton.DataSourceInfo.isDataSourceError
//import com.gm.shadhin.player.utils.PlayerLogSender
//import com.gm.shadhin.player.utils.isLocalUrl
//
//import com.shadhinmusiclibrary.player.utils.exH
//import kotlinx.coroutines.*
//
//@SuppressLint("UnsafeOptInUsageError")
//class ShadhinPlayerListener(
//    private val serviceScope: CoroutineScope?,
//    private val context: Context,
//    private val musicPlaybackPreparer: ShadhinMusicPlaybackPreparer?,
//    private val musicPositionRepository: MusicPositionRepository?,
//  //  private var userApiService: RestRepositoryKt?
//) : Player.Listener, AnalyticsListener {
//
//   // private var localStorage: LocalStorage = PreferenceStorage(context)
//    private var liveErrorHandler:android.os.Handler? = null
//    private var previousJob:Job?= null
//   // private val playerLogSender:PlayerLogSender = PlayerLogSender(serviceScope,userApiService,localStorage)
//
//
//
//    init {
//        musicPlaybackPreparer?.unsubscribeListener(::unsubscribe)
//    }
//    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
//        super<Player.Listener>.onMediaItemTransition(mediaItem, reason)
//       // playerLogSender.mediaItemTransition(mediaItem,reason)
//        initPreviousSeekPosition(mediaItem,reason)
//
//    }
//
//
//
//
//
//
//    @SuppressLint("UnsafeOptInUsageError")
//    override fun onIsPlayingChanged(isPlaying: Boolean) {
//        super<Player.Listener>.onIsPlayingChanged(isPlaying)
//
//        if(!isPlaying){
//            refreshStreamingStatusAsync()
//        }
//        //playerLogSender.playingChanged(isPlaying)
//        saveSeekPosition(isPlaying)
//    }
//
//
//
//    @SuppressLint("UnsafeOptInUsageError")
//    override fun onPositionDiscontinuity(
//        eventTime: AnalyticsListener.EventTime,
//        oldPosition: Player.PositionInfo,
//        newPosition: Player.PositionInfo,
//        reason: Int
//    ) {
//        super<AnalyticsListener>.onPositionDiscontinuity(eventTime, oldPosition, newPosition, reason)
//        exH {
//            val music = musicPlaybackPreparer?.musicAt(oldPosition.mediaItemIndex)
//            val positionMs = oldPosition.positionMs
//            if(reason == DISCONTINUITY_REASON_SEEK && positionMs !=0L) {
//                serviceScope?.launch {
//                    musicPositionRepository?.savePosition(music = music, position = positionMs)
//                }
//            }
//        }
//
//    }
//    fun refreshStreamingStatus() = runBlocking{
////        if(localStorage.getToken().isValidToken()) {
////            userApiService?.refreshStreamingStatus(localStorage.getToken())
////        }
//    }
//    private fun refreshStreamingStatusAsync() = serviceScope?.launch {
////        if(localStorage.getToken().isValidToken()) {
////            userApiService?.refreshStreamingStatus(localStorage.getToken())
////        }
//    }
//
//    fun unsubscribe(){
//        exH {
//            val music = musicPlaybackPreparer?.currentMusic()
//            val position = musicPlaybackPreparer?.playerProgress()?.currentPosition
//            serviceScope?.launch {
//                musicPositionRepository?.savePosition(music = music, position = position)
//            }
//
//        }
//    }
//
//    private fun saveSeekPosition(playing: Boolean) {
//        if(playing) {
//            exH {
//                val music = musicPlaybackPreparer?.currentMusic()
//                val duration = musicPlaybackPreparer?.playerProgress()?.duration
//                serviceScope?.launch {
//                    musicPositionRepository?.savePosition(music = music, duration = duration)
//                }
//            }
//        }
//    }
//
//    @SuppressLint("UnsafeOptInUsageError")
//    private fun initPreviousSeekPosition(mediaItem: MediaItem?, reason: Int) {
//
//        val mediaId = mediaItem?.mediaId
//        previousJob?.cancel()
//        previousJob = serviceScope?.launch {
//            delay(500)
//           // val obj = musicPositionRepository?.getSavedPosition(mediaId)
//          //  val savedPosition = obj?.position
//
//            withContext(Dispatchers.Main){
////                savedPosition?.let {
////                    musicPlaybackPreparer?.seekTo(it)
////                }
//
//            }
//        }
//    }
//
//
//
//
//
//
//
//
//    @SuppressLint("UnsafeOptInUsageError")
//    override fun onPlaybackStateChanged(playbackState: Int) {
//        super<Player.Listener>.onPlaybackStateChanged(playbackState)
//
//        if (playbackState == ExoPlayer.STATE_ENDED){
//            if(musicPlaybackPreparer?.isLive() == true){
//                handleLive()
//            }
//            refreshStreamingStatusAsync()
//        }
//
//    }
//    @SuppressLint("UnsafeOptInUsageError")
//    override fun onPlayerError(error: PlaybackException) {
//        super<Player.Listener>.onPlayerError(error)
//
//        val currentMusic = musicPlaybackPreparer?.currentMusic()
//
//        if(error.errorCode == 2005 && currentMusic?.mediaUrl.isLocalUrl()){
//            musicPlaybackPreparer?.sendError(true,"File not found maybe you are cleared cache",2005,currentMusic)
//            return
//        }
//
//        val errorMessage = if(isDataSourceError) dataSourceErrorMessage else error.localizedMessage
//        musicPlaybackPreparer?.sendError(isDataSourceError,errorMessage,dataSourceErrorCode,currentMusic)
//
//        if(musicPlaybackPreparer?.isLive() == true){
//            handleLive()
//        }
//    }
//
//    @SuppressLint("UnsafeOptInUsageError")
//    private fun handleLive() {
//
//            if (liveErrorHandler != null) {
//                liveErrorHandler?.removeCallbacksAndMessages(null)
//            }
//            if (liveErrorHandler == null) {
//                liveErrorHandler = android.os.Handler(Looper.getMainLooper())
//            }
//            liveErrorHandler?.postDelayed({
//                musicPlaybackPreparer?.restartPlayer()
//            }, 3000)
//
//    }
//}