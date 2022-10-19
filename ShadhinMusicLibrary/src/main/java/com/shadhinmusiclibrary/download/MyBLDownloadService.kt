package com.shadhinmusiclibrary.download

import android.app.Application
import android.app.Notification
import android.content.Context
import android.media.AudioManager
import android.widget.Toast
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.offline.Download
import com.google.android.exoplayer2.offline.DownloadManager
import com.google.android.exoplayer2.offline.DownloadService
import com.google.android.exoplayer2.scheduler.Scheduler
import com.google.android.exoplayer2.ui.DownloadNotificationHelper
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.util.Util
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.player.data.source.MyBLDownloadManager
import java.io.File


class MyBLDownloadService :  DownloadService(1, DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL, "my app", R.string.dummy_content, R.string.dummy_content){

    private lateinit var notificationHelper: DownloadNotificationHelper
    private lateinit var context: Context
    private lateinit var  myBlDownloadmanager:MyBLDownloadManager

    override fun onCreate() {
        super.onCreate()
        context = this
        notificationHelper= DownloadNotificationHelper(this, "my app")
       myBlDownloadmanager = MyBLDownloadManager(context)




    }
    override fun getDownloadManager(): DownloadManager {
        val manager = myBlDownloadmanager.downloadManager
        //Set the maximum number of parallel downloads
        manager.maxParallelDownloads = 5
        manager.addListener(object : DownloadManager.Listener {
            override fun onDownloadRemoved(downloadManager: DownloadManager, download: Download) {
                // Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
            }

            override fun onDownloadsPausedChanged(downloadManager: DownloadManager, downloadsPaused: Boolean) {
                if (downloadsPaused){
                    Toast.makeText(context, "paused", Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(context, "resumed", Toast.LENGTH_SHORT).show()
                }

            }

            fun onDownloadChanged(downloadManager: DownloadManager, download: Download) {

            }

        })
        // return (application as App).appContainer.downloadManager
        return manager
    }


    //If you want to restart the download when it failed, you the can override this method, it uses Jobscheduler.
    override fun getScheduler(): Scheduler? {
        return null
    }

    override fun getForegroundNotification(
        downloads: MutableList<Download>,
        notMetRequirements: Int,
    ): Notification {
        return notificationHelper.buildProgressNotification(context, R.drawable.my_bl_sdk_ic_icon_download, null,getString(R.string.dummy_content),downloads, notMetRequirements)
    }


}
