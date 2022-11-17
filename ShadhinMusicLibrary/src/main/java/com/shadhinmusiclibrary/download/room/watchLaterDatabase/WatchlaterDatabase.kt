package com.shadhinmusiclibrary.download.room.watchLaterDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shadhinmusiclibrary.download.room.WatchLaterContent

@Database(
    entities = [
        WatchLaterContent::class
    ],
    version = 2,
    exportSchema = false
)

internal abstract class WatchlaterDatabase : RoomDatabase() {
    abstract fun WatchlaterContentDao(): WatchlaterContentDao
}