package com.shadhinmusiclibrary.download.room.downloadDataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shadhinmusiclibrary.download.room.DownloadedContent


@Database(
    entities = [
        DownloadedContent::class
    ],
    version = 4
)

abstract class DownloadDatabase :RoomDatabase(){
    abstract fun DownloadedContentDao():DownloadedContentDao
}