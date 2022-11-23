package com.shadhinmusiclibrary.download.room.favDataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shadhinmusiclibrary.data.model.fav.FavData


@Database(
    entities = [
        FavData::class
    ],
    version = 1,
    exportSchema = false
)

internal abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun FavoriteContentDao(): FavoriteContentDao
}