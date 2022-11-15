package com.shadhinmusiclibrary.download.room.downloadDataBase

import androidx.room.*
import com.shadhinmusiclibrary.download.room.DownloadedContent


@Dao
interface DownloadedContentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(download: DownloadedContent)

    @Query("SELECT * FROM DownloadedContent WHERE type  IS  NULL ORDER By timeStamp ASC ")
    fun getAllPendingDownloads(): List<DownloadedContent>

    @Query("SELECT * FROM DownloadedContent WHERE type IS NOT NULL AND isDownloaded = 1 ORDER By timeStamp DESC ")
    fun getAllDownloads(): List<DownloadedContent>

    @Query("SELECT * FROM DownloadedContent WHERE rootType='V' AND isDownloaded = 1 ORDER By timeStamp DESC ")
    fun getAllVideosDownloads(): List<DownloadedContent>

    @Query("SELECT * FROM DownloadedContent WHERE rootType='S' AND isDownloaded = 1 ORDER By timeStamp DESC ")
    fun getAllSongsDownloads(): List<DownloadedContent>

    @Query("SELECT * FROM DownloadedContent WHERE rootType='PDJG' AND isDownloaded = 1 ORDER By timeStamp DESC ")
    fun getAllPodcastDownloads(): List<DownloadedContent>

    @Query("SELECT * FROM DownloadedContent where contentId = :id")
    fun getDownloadById(id: String): List<DownloadedContent>

    @Query("SELECT * FROM DownloadedContent where rootId = :albumId")
    fun getDownloadsByAlbumId(albumId: String): List<DownloadedContent>

    @Query("DELETE FROM DownloadedContent WHERE contentId = :id")
    fun deleteDownloadById(id: String): Unit

    @Query("DELETE FROM DownloadedContent WHERE contentId = :albumId")
    fun deleteDownloadByAlbumIdId(albumId: String): Unit

    @Query("SELECT COUNT(*) FROM DownloadedContent WHERE type IS NULL")
    fun getPendingDownloadCount(): Int

    @Query("UPDATE DownloadedContent SET track = :path  WHERE contentId =:id")
    fun setPath(id: String, path: String)

//    @Query("SELECT * from DownloadedContent WHERE path IS NOT NULL GROUP BY albumId ORDER By createTime DESC ")
//    fun getAllAlbums():List<DownloadedContent>

    @Query("SELECT track FROM DownloadedContent where contentId = :contentId  AND isDownloaded = 1")
    fun getTrackById(contentId: String): String

    @Query("SELECT * FROM DownloadedContent where  isDownloaded = 1")
    fun getAllDownloadedTrackById(): List<DownloadedContent>

    @Query("SELECT isDownloaded FROM downloadedcontent WHERE  contentId = :contentId AND isDownloaded =1 LIMIT 1")
    fun downloadedContent(contentId: String): Boolean?

    @Query("SELECT isDownloaded FROM downloadedcontent WHERE type = 'V' AND contentId = :contentId AND isDownloaded =1 LIMIT 1")
    fun downloadedVideoContent(contentId: String): Boolean?

    @Query("UPDATE downloadedcontent SET isDownloaded = 1 WHERE contentId = :contentId")
    fun downloadCompleted(contentId: String)

//
//    @Query("SELECT track FROM DownloadedContent where contentId = :contentId & isDownloaded = 1")
//    fun getDownloadedTrackById(contentId:String):String

//    @Query("DELETE FROM DownloadedContent")
//    fun deleteAllDownloads():Unit
}