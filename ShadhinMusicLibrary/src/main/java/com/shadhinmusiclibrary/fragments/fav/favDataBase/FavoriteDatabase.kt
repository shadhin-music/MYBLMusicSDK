package com.shadhinmusiclibrary.fragments.fav.favDataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shadhinmusiclibrary.data.model.fav.FavData



@Database(
    entities = [
        FavData::class
    ],
    version = 1
)

abstract class FavoriteDatabase :RoomDatabase(){
    abstract fun FavoriteContentDao(): FavoriteContentDao

}