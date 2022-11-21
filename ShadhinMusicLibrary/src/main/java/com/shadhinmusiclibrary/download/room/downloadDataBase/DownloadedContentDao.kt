package com.shadhinmusiclibrary.download.room.downloadDataBase

import androidx.room.*
import com.shadhinmusiclibrary.download.room.DownloadedContent


@Dao
internal interface DownloadedContentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(download: DownloadedContent)

    @Query("SELECT * FROM DownloadedContent WHERE content_Type  IS  NULL ORDER By total_duration ASC ")
    fun getAllPendingDownloads(): List<DownloadedContent>

    @Query("SELECT * FROM DownloadedContent WHERE content_Type IS NOT NULL AND isDownloaded_dc = 1 ORDER By total_duration DESC ")
    fun getAllDownloads(): List<DownloadedContent>

    @Query("SELECT * FROM DownloadedContent WHERE rootContentType='V' AND isDownloaded_dc = 1 ORDER By total_duration DESC ")
    fun getAllVideosDownloads(): List<DownloadedContent>

    @Query("SELECT * FROM DownloadedContent WHERE rootContentType='S' AND isDownloaded_dc = 1 ORDER By total_duration DESC ")
    fun getAllSongsDownloads(): List<DownloadedContent>

    @Query("SELECT * FROM DownloadedContent WHERE rootContentType='PDJG' AND isDownloaded_dc = 1 ORDER By total_duration DESC ")
    fun getAllPodcastDownloads(): List<DownloadedContent>

    @Query("SELECT * FROM DownloadedContent where content_Id = :id")
    fun getDownloadById(id: String): List<DownloadedContent>

    @Query("SELECT * FROM DownloadedContent where rootContentId = :albumId")
    fun getDownloadsByAlbumId(albumId: String): List<DownloadedContent>

    @Query("DELETE FROM DownloadedContent WHERE content_Id = :id")
    fun deleteDownloadById(id: String): Unit

    @Query("DELETE FROM DownloadedContent WHERE content_Id = :albumId")
    fun deleteDownloadByAlbumIdId(albumId: String): Unit

    @Query("SELECT COUNT(*) FROM DownloadedContent WHERE content_Type IS NULL")
    fun getPendingDownloadCount(): Int

    @Query("UPDATE DownloadedContent SET playingUrl = :path  WHERE content_Id =:id")
    fun setPath(id: String, path: String)

//    @Query("SELECT * from DownloadedContent WHERE path IS NOT NULL GROUP BY albumId ORDER By createTime DESC ")
//    fun getAllAlbums():List<DownloadedContent>

    @Query("SELECT playingUrl FROM DownloadedContent where content_Id = :contentId  AND isDownloaded_dc = 1")
    fun getTrackById(contentId: String): String

    @Query("SELECT * FROM DownloadedContent where  isDownloaded_dc = 1")
    fun getAllDownloadedTrackById(): List<DownloadedContent>

    @Query("SELECT isDownloaded_dc FROM DownloadedContent WHERE  content_Id = :contentId AND isDownloaded_dc =1 LIMIT 1")
    fun downloadedContent(contentId: String): Boolean?

    @Query("SELECT isDownloaded_dc FROM DownloadedContent WHERE content_Type = 'V' AND content_Id = :contentId AND isDownloaded_dc =1 LIMIT 1")
    fun downloadedVideoContent(contentId: String): Boolean?

    @Query("UPDATE DownloadedContent SET isDownloaded_dc = 1 WHERE content_Id = :contentId")
    fun downloadCompleted(contentId: String)

//    @Query("SELECT track FROM DownloadedContent where contentId = :contentId & isDownloaded = 1")
//    fun getDownloadedTrackById(contentId:String):String

//    @Query("DELETE FROM DownloadedContent")
//    fun deleteAllDownloads():Unit
}