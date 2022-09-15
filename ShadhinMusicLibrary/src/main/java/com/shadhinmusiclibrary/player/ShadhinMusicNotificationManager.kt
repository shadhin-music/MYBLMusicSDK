package com.shadhinmusiclibrary.player

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import com.shadhinmusiclibrary.player.Constants.MUSIC_NOTIFICATION_CHANNEL_ID
import com.shadhinmusiclibrary.player.Constants.MUSIC_NOTIFICATION_ID
import com.gm.shadhin.player.utils.bitmapFromUri
import com.gm.shadhin.player.utils.getPreloadBitmap
import com.gm.shadhin.player.utils.nullFix
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.shadhinmusiclibrary.R

class  ShadhinMusicNotificationManager(
    private val context: Context,
    private val token: MediaSessionCompat.Token?,
    private val notificationListener:PlayerNotificationManager.NotificationListener?
) {

    private lateinit var notificationManager: PlayerNotificationManager
    private var shadhinCustomActionReceiver: ShadhinCustomActionReceiver =
        ShadhinCustomActionReceiver(context)
    private var placeholderArtwork:Bitmap? = defaultArtwork()// this placeholder only for fix samsung one UI blink issue
    private var mediaControllerCompat: MediaControllerCompat? = null
    init {
        mediaControllerCompat = token?.let { MediaControllerCompat(context, it) }
        notificationManager =  PlayerNotificationManager.Builder(
            context,
            MUSIC_NOTIFICATION_ID,
            MUSIC_NOTIFICATION_CHANNEL_ID
        ).apply {
            setMediaDescriptionAdapter(MusicMediaDescriptionAdapter())
            notificationListener?.let { setNotificationListener(it) }

        }
            .build().apply {
            setSmallIcon(R.drawable.ic_shadhin_icon_gray_vector)
            token?.let { setMediaSessionToken(it) }
        }
    }
    fun showNotification(player: Player?) = notificationManager.setPlayer(player)
    fun hideNotification(){
        notificationManager.setPlayer(null)
    }
    inner  class  MusicMediaDescriptionAdapter : PlayerNotificationManager.MediaDescriptionAdapter {
        override fun getCurrentContentTitle(player: Player): CharSequence {
            return mediaControllerCompat?.metadata?.description?.title.nullFix()
        }

        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            return  mediaControllerCompat?.sessionActivity
        }

        override fun getCurrentContentText(player: Player): CharSequence {
            return mediaControllerCompat?.metadata?.description?.subtitle.nullFix()
        }

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {
            val uri:Uri? = mediaControllerCompat?.metadata?.description?.iconUri
            val mediaId =  mediaControllerCompat?.metadata?.description?.mediaId

            bitmapFromUri(context,uri){ bitmap->
                if(bitmap !=null){
                    callback.onBitmap(bitmap)
                }
            }
            return mediaId?.let { getPreloadBitmap(it) }
        }

    }

    private fun defaultArtwork(): Bitmap? {
      return  BitmapFactory.decodeResource(context.resources, R.drawable.default_song)
    }



}