package com.shadhinmusiclibrary.download.room.watchLaterDatabase

import androidx.room.*
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.download.room.WatchLaterContent


@Dao
interface WatchlaterContentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(watchLaterContent: WatchLaterContent)



    @Query("SELECT * FROM WatchLaterContent WHERE type IS NOT NULL ORDER By timeStamp DESC ")
    fun getAllWatchLater():List<WatchLaterContent>

    @Query("SELECT track FROM WatchLaterContent where contentId = :contentId")
    fun getWatchlaterTrackById(contentId:String):String

    @Query("SELECT * FROM WatchLaterContent where contentId = :id")
    fun getWatchLaterById(id:String):List<WatchLaterContent>

    @Query("DELETE FROM WatchLaterContent WHERE contentId = :contentId")
    fun deleteWatchlaterById(contentId:String):Unit

    @Query("DELETE FROM WatchLaterContent")
    fun deleteAllWatchlater():Unit

}