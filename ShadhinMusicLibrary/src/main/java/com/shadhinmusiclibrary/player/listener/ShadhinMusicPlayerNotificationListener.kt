package com.shadhinmusiclibrary.player.listener

import android.annotation.SuppressLint
import android.app.Notification
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.media3.ui.PlayerNotificationManager
import com.shadhinmusiclibrary.player.Constants.MUSIC_NOTIFICATION_ID
import com.gm.shadhin.player.ShadhinMusicPlayer
import com.shadhinmusiclibrary.player.utils.exH

@SuppressLint("UnsafeOptInUsageError")
class ShadhinMusicPlayerNotificationListener(
    private val musicService: ShadhinMusicPlayer
) : PlayerNotificationManager.NotificationListener {


    override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
        musicService.apply {
            exH {
                stopForeground(true)
                isForegroundService = false
                stopSelf()
            }

        }
    }

    override fun onNotificationPosted(
        notificationId: Int,
        notification: Notification,
        ongoing: Boolean
    ) {

        musicService.apply {

            if (ongoing && !isForegroundService) {
               exH {
                   ContextCompat.startForegroundService(
                       this,
                       Intent(applicationContext, this::class.java)
                   )
                   startForeground(MUSIC_NOTIFICATION_ID, notification)
                   isForegroundService = true
               }
            }
        }
    }

}