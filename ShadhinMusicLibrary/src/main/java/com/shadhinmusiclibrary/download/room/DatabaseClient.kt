package com.shadhinmusiclibrary.download.room

import android.content.Context
import androidx.room.Room
import com.shadhinmusiclibrary.download.room.downloadDataBase.DownloadDatabase

class DatabaseClient {
    private var mCtx: Context? = null
    private var mInstance: DatabaseClient? = null
    private var downloadDataBase: DownloadDatabase? = null

    constructor(mCtx: Context) {
        this.mCtx = mCtx


        downloadDataBase = Room.databaseBuilder(mCtx, DownloadDatabase::class.java, "DownloadDb")
            .allowMainThreadQueries().build()
    }
        @Synchronized
        fun getInstance(mCtx: Context): DatabaseClient? {
            if (mInstance == null) {
                mInstance = DatabaseClient(mCtx)
            }
            return mInstance
        }



        fun getDownloadDatabase()= downloadDataBase




}