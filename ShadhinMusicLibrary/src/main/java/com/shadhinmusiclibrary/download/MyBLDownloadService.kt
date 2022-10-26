package com.shadhinmusiclibrary.download

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.exoplayer2.offline.Download
import com.google.android.exoplayer2.offline.Download.STATE_COMPLETED
import com.google.android.exoplayer2.offline.DownloadManager
import com.google.android.exoplayer2.offline.DownloadService
import com.google.android.exoplayer2.scheduler.Requirements
import com.google.android.exoplayer2.scheduler.Scheduler
import com.google.android.exoplayer2.ui.DownloadNotificationHelper
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.data.model.DownloadingItem
import com.shadhinmusiclibrary.di.ServiceEntryPoint
import com.shadhinmusiclibrary.download.room.DownloadedContent

import com.shadhinmusiclibrary.player.Constants
import com.shadhinmusiclibrary.player.Constants.DOWNLOAD_CHANNEL_ID
import com.shadhinmusiclibrary.player.Constants.SHOULD_OPEN_DOWNLOADS_ACTIVITY
import com.shadhinmusiclibrary.player.data.source.MyBLDownloadManager
import com.shadhinmusiclibrary.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.DownloadProgressObserver
import kotlinx.coroutines.*
import java.util.ArrayList

private val NOTIFICATION_ID=2

class MyBLDownloadService :  DownloadService(1, DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL, "my app", R.string.dummy_content, R.string.dummy_content),ServiceEntryPoint{

    private lateinit var notificationHelper: DownloadNotificationHelper

    private var currentItem:DownloadedContent?=null
    private lateinit var cacheRepository: CacheRepository
    private var downloadServiceScope: CoroutineScope? = null

    companion object {

        var isRunning: Boolean = true
        var currentId:String?=null
        var currentProgress:Int?= null


    }

    override fun onCreate() {
        super.onCreate()
       // context = this
        isRunning = true
        notificationHelper= DownloadNotificationHelper(this, "my app")
        cacheRepository = CacheRepository(applicationContext)

    }
   override fun getDownloadManager(): DownloadManager {

        val myBlDownloadmanager = MyBLDownloadManager(applicationContext, injector.musicRepository)


        val manager = myBlDownloadmanager.downloadManager
        //Set the maximum number of parallel downloads
        manager.maxParallelDownloads = 5


        manager.addListener(object : DownloadManager.Listener {
            override fun onDownloadRemoved(downloadManager: DownloadManager, download: Download) {
                Toast.makeText(applicationContext, "Deleted", Toast.LENGTH_SHORT).show()
//                val localBroadcastManager = LocalBroadcastManager.getInstance(applicationContext)
//                val localIntent = Intent("DOWNLOADREMOVE")
//                    .putExtra("Deleted",0)
//                localBroadcastManager.sendBroadcast(localIntent)
            }

            override fun onDownloadsPausedChanged(downloadManager: DownloadManager, downloadsPaused: Boolean) {
                if (downloadsPaused){
                    Toast.makeText(applicationContext, "paused", Toast.LENGTH_SHORT).show()

                  } else{

                    Toast.makeText(applicationContext, "Download Started", Toast.LENGTH_SHORT).show()


                   // startForeground(NOTIFICATION_ID,getDownloadingNotification("Song Downloading",100))
                }
                Log.i("getDownloadManager", "showBottomSheetDialog: ${downloadsPaused}}")

            }

            override fun onDownloadChanged(
                downloadManager: DownloadManager,
                download: Download,
                finalException: java.lang.Exception?
            ) {
                super.onDownloadChanged(downloadManager, download, finalException)
                if(download.state == STATE_COMPLETED){
                    currentProgress= 100
                    val localBroadcastManager = LocalBroadcastManager.getInstance(applicationContext)
                    val localIntent = Intent("PROGRESS")
                        .putExtra("progress",100)
                    localBroadcastManager.sendBroadcast(localIntent)
                }

                //getDownloadingNotification("Song Downloading",100)
               val currentItemID =download.request.id
                Log.e("getDownloadManagerx", "getForegroundNotification:" + currentItemID)
                cacheRepository.setDownloadedContentPath(download.request.id,
                    download.request.uri.toString())
                 //saveDownloadedContent(DownloadedContent(currentItemID,"","","","",download.request.uri.toString(),
                // "",0,0,"",""))

            }

            override fun onInitialized(downloadManager: DownloadManager) {
                super.onInitialized(downloadManager)
                Log.i("getDownloadManager", "onInitialized")
            }

            override fun onIdle(downloadManager: DownloadManager) {
                super.onIdle(downloadManager)
                Log.i("getDownloadManager", "onIdle")
            }

            override fun onRequirementsStateChanged(
                downloadManager: DownloadManager,
                requirements: Requirements,
                notMetRequirements: Int
            ) {
                super.onRequirementsStateChanged(downloadManager, requirements, notMetRequirements)
                Log.i("getDownloadManager", "onRequirementsStateChanged")
            }

            override fun onWaitingForRequirementsChanged(
                downloadManager: DownloadManager,
                waitingForRequirements: Boolean
            ) {
                super.onWaitingForRequirementsChanged(downloadManager, waitingForRequirements)
                Log.i("getDownloadManager", "onWaitingForRequirementsChanged")
            }


        })

        return manager
    }

    override fun getScheduler(): Scheduler? {

        return null
    }


    override fun getForegroundNotification(
        downloads: MutableList<Download>,
        notMetRequirements: Int,
    ): Notification {
       // Log.e("getDownloadManagerx", "getForegroundNotification: ${downloads.map { it.request.id }}")

        val downloadingItems = downloads.map {
//            if(it.percentDownloaded in 0f..100f) {
                DownloadingItem(contentId = it.request.id, progress = it.percentDownloaded)
//            }else{
//                DownloadingItem(contentId = it.request.id, progress = 0f)
//            }
        }

        val localBroadcastManager = LocalBroadcastManager.getInstance(applicationContext)
        val localIntent = Intent("ACTION")
            .putParcelableArrayListExtra("downloading_items",downloadingItems as ArrayList<out Parcelable>)
        localBroadcastManager.sendBroadcast(localIntent)


        return notificationHelper.buildProgressNotification(applicationContext, R.drawable.my_bl_sdk_shadhin_logo_with_text_for_light, null,"Song Downloading!!!",downloads, notMetRequirements)
    }

    fun getAllDownloadCompleteNotification():Notification{
        return NotificationCompat.Builder(applicationContext, DOWNLOAD_CHANNEL_ID)
            .setContentTitle("All Download Complete")
            .setShowWhen(false)
            .setOngoing(false)
            .setSmallIcon(R.drawable.my_bl_sdk_shadhin_logo_with_text_for_light)
            .setContentIntent(getPendingIntent())
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
    }
    override fun onDestroy() {
        isRunning = false
        currentId=null
        currentProgress=0
        downloadServiceScope?.coroutineContext?.cancelChildren()
        downloadServiceScope?.cancel()
        downloadServiceScope = null

        super.onDestroy()
    }

    fun saveDownloadedContent(downloadedContent: DownloadedContent) {
        downloadServiceScope?.launch {
            if (isRunning) {
                cacheRepository.setDownloadedContentPath(downloadedContent.contentId,
                    downloadedContent.track!!)
                delay(Constants.DELAY_DURATION)
               // downloadUpdateListener?.loadData()
                withContext(Dispatchers.Main) {
                    currentItem = null
                    //DownloadProgressObserver.updateProgressForAllHolder()
                    Log.i("getDownloadManager", "onDownloadChanged:"+   cacheRepository.setDownloadedContentPath(downloadedContent.contentId,
                        downloadedContent.track!!))
                    // DownloadOrDeleteMp3Observer.notifySubscriber()
                }
            }
        }
    }

    fun getDownloadingNotification(title:String,progress:Int):Notification{
        return NotificationCompat.Builder(applicationContext, DOWNLOAD_CHANNEL_ID)
            .setContentTitle("Downloading $title")
            .setContentText("$progress%")
            .setShowWhen(true)
            .setOngoing(true)
            .setSmallIcon(R.drawable.my_bl_sdk_shadhin_logo_with_text_for_light)
            .setContentIntent(getPendingIntent())
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.my_bl_sdk_shadhin_logo_with_text_for_light))
            .setProgress(100, progress ?: 0, false)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
    }
    fun getPendingIntent(): PendingIntent {
        var notificationIntent = Intent(this, SDKMainActivity::class.java)
        val arguments = Bundle()
        arguments.putBoolean(SHOULD_OPEN_DOWNLOADS_ACTIVITY, true)
        notificationIntent.putExtras(arguments)
        var uniqueReqCode :Int  =(System.currentTimeMillis()).toInt()
        var pendingIntent = PendingIntent.getActivity(this, uniqueReqCode, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        return pendingIntent
    }


}
