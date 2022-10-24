package com.shadhinmusiclibrary.player.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import com.shadhinmusiclibrary.download.room.DatabaseClient
import com.shadhinmusiclibrary.download.room.DownloadedContent

class CacheRepository(val context: Context) {
    val databaseClient = DatabaseClient(context)

    val downloadDb = databaseClient.getDownloadDatabase()
    fun insertDownload(downloadedContent: DownloadedContent) {
        downloadDb?.DownloadedContentDao()?.insert(downloadedContent)
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
//    fun deleteDownloadById(id: String) {
//        val path=downloadDb?.DownloadedContentDao()?.getTrackPathById(id)
//        downloadDb?.DownloadedContentDao()?.deleteDownloadById(id)
//
//        try {
//            FileUtil.deleteFileIfExists(Uri.parse(path))
//        }catch (e:NullPointerException){
//
//        }
//
//        DownloadOrDeleteMp3Observer.notifySubscriber()
//
//    }
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
    var path = downloadDb?.DownloadedContentDao()?.getTrackById(contentId)
    Log.e("TAG", "Track: " + path)
    if (path == null) {
        Log.e("TAG", "Track123: " + path)
        return false
    } else {
        return true
    }

}


}