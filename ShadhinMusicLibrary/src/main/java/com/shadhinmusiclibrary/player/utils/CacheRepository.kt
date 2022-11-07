package com.shadhinmusiclibrary.player.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import com.shadhinmusiclibrary.download.room.DatabaseClient
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.download.room.WatchLaterContent
import com.shadhinmusiclibrary.utils.DownloadOrDeleteObserver
import com.shadhinmusiclibrary.utils.UtilHelper


class CacheRepository(val context: Context) {
    var sh: SharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE)


    val databaseClient = DatabaseClient(context)

    val watchLaterDb = databaseClient.getWatchlaterDatabase()

    val downloadDb = databaseClient.getDownloadDatabase()
    fun insertDownload(downloadedContent: DownloadedContent) {
        downloadDb?.DownloadedContentDao()?.insert(downloadedContent)
    }
    fun insertWatchLater(watchLaterContent: WatchLaterContent){
        watchLaterDb?.WatchlaterContentDao()?.insert(watchLaterContent)
    }
    fun getAllWatchlater() = watchLaterDb?.WatchlaterContentDao()?.getAllWatchLater()

    fun getWatchedVideoById(id: String): WatchLaterContent? {
        var contents = watchLaterDb?.WatchlaterContentDao()?.getWatchLaterById(id)
        if ((contents?.size ?: 0) > 0) {
            return contents!![0]
        }
        return null
    }
//    fun deleteAllDownloads() {
//        val list=downloadDb?.DownloadedContentDao()?.getAllDownloads()
//        downloadDb?.DownloadedContentDao()?.deleteAllDownloads()
//        list?.forEach {
//            it.path?.let { uriStr->
//                FileUtil.deleteFileIfExists(Uri.parse(uriStr))
//                DownloadOrDeleteMp3Observer.notifySubscriber()
//            }
//        }
//    }
    fun getAllPendingDownloads() = downloadDb?.DownloadedContentDao()?.getAllPendingDownloads()
    fun getAllDownloads() = downloadDb?.DownloadedContentDao()?.getAllDownloads()
    fun getAllVideosDownloads() = downloadDb?.DownloadedContentDao()?.getAllVideosDownloads()
    fun getAllSongsDownloads() = downloadDb?.DownloadedContentDao()?.getAllSongsDownloads()
    fun getAllPodcastDownloads() = downloadDb?.DownloadedContentDao()?.getAllPodcastDownloads()

    fun getWatchTrackByID(contentId: String)= watchLaterDb?.WatchlaterContentDao()?.getWatchlaterTrackById(contentId)
//
    fun getDownloadById(id: String): DownloadedContent? {
        var contents = downloadDb?.DownloadedContentDao()?.getDownloadById(id)
        if (contents?.size ?: 0 > 0) {
            return contents!![0]
        }
        return null
    }
//
//    fun getPendingDownloadCount() = downloadDb?.DownloadedContentDao()?.getPendingDownloadCount()
    fun setDownloadedContentPath(id:String,path:String)=downloadDb?.DownloadedContentDao()?.setPath(id,path)
    //fun setDownloadedContentPath(id:String,path:String)=downloadDb?.DownloadedContentDao()?.setPath(id,path)
    fun deleteDownloadById(id: String) {
        val path = downloadDb?.DownloadedContentDao()?.getTrackById(id)
        downloadDb?.DownloadedContentDao()?.deleteDownloadById(id)
        try {
            UtilHelper.deleteFileIfExists(Uri.parse(path))
        } catch (e: NullPointerException) {

        }
    }
        fun deleteWatchlaterById(id: String) {
            val path=watchLaterDb?.WatchlaterContentDao()?.getWatchlaterTrackById(id)
            watchLaterDb?.WatchlaterContentDao()?.deleteWatchlaterById(id)
            try {
                UtilHelper.deleteFileIfExists(Uri.parse(path))
            }catch (e:NullPointerException){

            }
       // DownloadOrDeleteObserver.notifySubscriber()

    }
//
//    fun deleteDownloadByAlbumId(albumId: String) {
//        val list=downloadDb?.DownloadedContentDao()?.getDownloadsByAlbumId(albumId)
//        downloadDb?.DownloadedContentDao()?.deleteDownloadByAlbumIdId(albumId)
//        list?.forEach {
//            it.path?.let {  uriStr->
//                FileUtil.deleteFileIfExists(Uri.parse(uriStr))
//                DownloadOrDeleteMp3Observer.notifySubscriber()
//            }
//        }
//    }

fun isTrackDownloaded(contentId:String):Boolean {
    val path = downloadDb?.DownloadedContentDao()?.getTrackById(contentId)
    Log.e("TAG", "Track: " + path)
    return if (path == null) {
        Log.e("TAG", "Track123: " + path)
        false
    } else {
        true
    }

}
   fun getDownloadedContent()=downloadDb?.DownloadedContentDao()?.getAllDownloadedTrackById()

//fun getDownlodById(contentId:String):Boolean{
//    var path = downloadDb?.DownloadedContentDao()?.getDownloadedTrackById(contentId)
//    Log.e("TAG", "TrackDownloadTrue: " + path)
//    if (path == null) {
//        Log.e("TAG", "TrackDownload: " + path)
//        return false
//    } else {
//        return true
//    }
//}
  fun isVideoDownloaded(content:String?): Boolean {
    return databaseClient.getDownloadDatabase()?.DownloadedContentDao()?.downloadedVideoContent(content?:"")?:false
}
    fun downloadCompleted(content:String?) {
        databaseClient.getDownloadDatabase()?.DownloadedContentDao()
            ?.downloadCompleted(content ?: "")
    }

fun isDownloadCompleted(contentId:String):Boolean{
    val completed = databaseClient.getDownloadDatabase()?.DownloadedContentDao()?.downloadedContent(contentId)
    if (completed?.equals(null) == true) {
        Log.e("TAG", "TrackDownload: " + completed)
        return false
    } else {
        Log.e("TAG", "TrackDownload: " + completed)
        return true
    }

}


}