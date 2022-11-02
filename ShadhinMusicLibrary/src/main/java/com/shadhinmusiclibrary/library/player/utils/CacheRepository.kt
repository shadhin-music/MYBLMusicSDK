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

    fun insertWatchLater(watchLaterContent: WatchLaterContent) {
        watchLaterDb?.WatchlaterContentDao()?.insert(watchLaterContent)
    }

    fun getAllWatchlater() = watchLaterDb?.WatchlaterContentDao()?.getAllWatchLater()

    fun getWatchedVideoById(id: String): WatchLaterContent? {
        var contents = watchLaterDb?.WatchlaterContentDao()?.getWatchLaterById(id)
        if (contents?.size ?: 0 > 0) {
            return contents!![0]
        }
        return null
    }

    fun getAllPendingDownloads() = downloadDb?.DownloadedContentDao()?.getAllPendingDownloads()
    fun getAllDownloads() = downloadDb?.DownloadedContentDao()?.getAllDownloads()
    fun getAllVideosDownloads() = downloadDb?.DownloadedContentDao()?.getAllVideosDownloads()
    fun getAllSongsDownloads() = downloadDb?.DownloadedContentDao()?.getAllSongsDownloads()
    fun getAllPodcastDownloads() = downloadDb?.DownloadedContentDao()?.getAllPodcastDownloads()

    //
    fun getDownloadById(id: String): DownloadedContent? {
        var contents = downloadDb?.DownloadedContentDao()?.getDownloadById(id)
        if (contents?.size ?: 0 > 0) {
            return contents!![0]
        }
        return null
    }

    fun setDownloadedContentPath(id: String, path: String) =
        downloadDb?.DownloadedContentDao()?.setPath(id, path)

    fun deleteDownloadById(id: String) {
        val path = downloadDb?.DownloadedContentDao()?.getTrackById(id)
        downloadDb?.DownloadedContentDao()?.deleteDownloadById(id)
        try {
            UtilHelper.deleteFileIfExists(Uri.parse(path))
        } catch (e: NullPointerException) {
        }
    }

    fun deleteWatchlaterById(id: String) {
        val path = watchLaterDb?.WatchlaterContentDao()?.getWatchlaterTrackById(id)
        watchLaterDb?.WatchlaterContentDao()?.deleteWatchlaterById(id)
        try {
            UtilHelper.deleteFileIfExists(Uri.parse(path))
        } catch (e: NullPointerException) {
        }
    }

    fun isTrackDownloaded(contentId: String): Boolean {
        var path = downloadDb?.DownloadedContentDao()?.getTrackById(contentId)
        Log.e("TAG", "Track: " + path)
        if (path == null) {
            Log.e("TAG", "Track123: " + path)
            return false
        } else {
            return true
        }
    }

    fun getDownloadedContent() = downloadDb?.DownloadedContentDao()?.getAllDownloadedTrackById()

    fun isDownloadCompleted(): Boolean {
        val progress = sh.getInt("progress", 0)
        Log.e("TAG", "TrackDownload: " + progress)
        if (progress.equals(3)) {
            Log.e("TAG", "TrackDownload: " + progress)
            return true
        } else {
            return false
        }
    }
}