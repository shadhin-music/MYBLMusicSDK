package com.shadhinmusiclibrary.player.data.local;

import static com.shadhinmusiclibrary.player.Constants.*;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.gm.shadhin.player.data.local.MusicPositionDatabaseDao;
import com.gm.shadhin.player.data.model.MusicPosition;

/**
This code is Java because , this time (16-11-2021) Shadhin app doesn't supported kapt annotation processor
*/
@Database(entities = MusicPosition.class,version = 1,exportSchema = false)
public abstract class MusicPositionDatabase extends RoomDatabase{
    abstract MusicPositionDatabaseDao dao();
    public static MusicPositionDatabase newInstance(Context context){
        return Room.databaseBuilder(context,
                MusicPositionDatabase.class, PLAYER_DATABASE)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }
}
