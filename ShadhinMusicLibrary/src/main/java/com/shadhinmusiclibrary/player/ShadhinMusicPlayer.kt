package com.shadhinmusiclibrary.player

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.media.MediaBrowserServiceCompat
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.di.ServiceEntryPoint
import com.shadhinmusiclibrary.player.Constants.MEDIA_SESSION_TAG
import com.shadhinmusiclibrary.player.Constants.PENDING_INTENT_KEY
import com.shadhinmusiclibrary.player.Constants.PENDING_INTENT_REQUEST_CODE
import com.shadhinmusiclibrary.player.Constants.ROOT_ID_EMPTY
import com.shadhinmusiclibrary.player.Constants.ROOT_ID_PLAYLIST
import com.shadhinmusiclibrary.player.data.model.MusicPlayList
import com.shadhinmusiclibrary.player.listener.ShadhinMusicPlayerNotificationListener
import com.shadhinmusiclibrary.player.utils.toServiceMediaItemMutableList
import com.shadhinmusiclibrary.utils.exH


/**
 * Shadhin Music Service
 * <img src="https://developer.android.com/guide/topics/media-apps/images/audio-activity-and-service.png"/>
 */
private const val TAG = "ShadhinMusicPlayer"

class ShadhinMusicPlayer : MediaBrowserServiceCompat(), ServiceEntryPoint {

    private  var mediaSession: MediaSessionCompat?=null
    private  var mediaSessionConnector: MediaSessionConnector?=null
    private  var shadhinMusicNotificationManager:ShadhinMusicNotificationManager?=null
    private  var shadhinMusicQueueNavigator:ShadhinMusicQueueNavigator?=null
    private  var musicPlayerNotificationListener: ShadhinMusicPlayerNotificationListener?=null
    private var exoPlayer: SimpleExoPlayer?=null
    private var musicPlaybackPreparer: ShadhinMusicPlaybackPreparer?=null
    var isForegroundService = false

    override fun onCreate() {
        super.onCreate()
        initialization()
    }


    private  fun initialization() {
        exoPlayer = exoPlayer()
        mediaSession = MediaSessionCompat(this, MEDIA_SESSION_TAG).apply {
            setSessionActivity(pendingIntent())
            isActive = true
        }
        sessionToken = mediaSession?.sessionToken
        musicPlayerNotificationListener =
            ShadhinMusicPlayerNotificationListener(this@ShadhinMusicPlayer)
        mediaSessionConnector = mediaSession?.let { MediaSessionConnector(it) }
        shadhinMusicNotificationManager = ShadhinMusicNotificationManager(
            this,
            mediaSession?.sessionToken,
            musicPlayerNotificationListener
        )
        musicPlaybackPreparer = ShadhinMusicPlaybackPreparer(this@ShadhinMusicPlayer, exoPlayer,injector.exoplayerCache,injector.musicRepository)
        shadhinMusicQueueNavigator = mediaSession?.let { ShadhinMusicQueueNavigator(it,musicPlaybackPreparer?.playlist) }
        mediaSessionConnector?.setQueueNavigator(shadhinMusicQueueNavigator)
        mediaSessionConnector?.setPlayer(exoPlayer)
        mediaSessionConnector?.setPlaybackPreparer(musicPlaybackPreparer)
        shadhinMusicNotificationManager?.showNotification(exoPlayer)

    }
    private fun exoPlayer(): SimpleExoPlayer {
        return SimpleExoPlayer.Builder(this)
            .build()
    }


    private  fun pendingIntent(): PendingIntent? {
        val intent = Intent(this, SDKMainActivity::class.java)
        intent.putExtra(PENDING_INTENT_KEY, true)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        return PendingIntent.getActivity(
            this,
            PENDING_INTENT_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }


    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot {
        return if (clientPackageName == applicationContext.packageName)
          return  BrowserRoot(ROOT_ID_PLAYLIST, null)
        else BrowserRoot(ROOT_ID_EMPTY, null)
    }
    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>,
        options: Bundle
    ) {


        val musicPlayList = MusicPlayList.fromBundle(
            options,
            ShadhinMusicServiceConnection.Command.SUBSCRIBE
        )
        val isPlayWhenReady = options.getBoolean(Constants.PLAY_WHEN_READY_KEY)
        val isUpdatePlaylist = options.getBoolean(Constants.PLAYLIST_UPDATE)
        val defaultPosition = options.getInt(Constants.DEFAULT_POSITION_KEY)

        exH { sendData(musicPlayList, result, isPlayWhenReady, defaultPosition) }


    }
    private fun sendData(
        musicPlayList: MusicPlayList,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>,
        isPlayWhenReady: Boolean = false,
        defaultPosition: Int
    ) {
        Log.i("music_payer", "sendData: showNotification")

        if(musicPlayList.isValidPlayList()){
            //musicPlayList.loadAllImage(this)
            musicPlaybackPreparer?.defaultPosition = defaultPosition
            musicPlaybackPreparer?.initPlayList(musicPlayList)
            musicPlaybackPreparer?.onPrepare(isPlayWhenReady)
            result.sendResult(musicPlayList.list.toServiceMediaItemMutableList()?: mutableListOf())

        }else{
            result.sendResult(mutableListOf())
        }
    }

    override fun onLoadChildren(
        parentId: String, result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ){
        result.sendResult(mutableListOf())
    }

    override fun onTaskRemoved(rootIntent: Intent?) {


        musicPlaybackPreparer?.removeTimerHandler()
        exoPlayer?.stop(true)
        shadhinMusicNotificationManager?.hideNotification()

        super.onTaskRemoved(rootIntent)
    }
    override fun onDestroy() {
        super.onDestroy()
        musicPlaybackPreparer?.removeTimerHandler()
        mediaSession?.release()
        exoPlayer?.release()
        exoPlayer = null

    }
}