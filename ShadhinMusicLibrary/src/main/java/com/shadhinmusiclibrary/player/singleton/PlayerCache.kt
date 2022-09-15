package com.gm.shadhin.player.singleton

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider

import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File

object PlayerCache {
    @Volatile
    private var INSTANCE: SimpleCache? = null
    fun getInstance(context: Context): SimpleCache =
        INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildCache(context).also { INSTANCE = it }
        }

    private fun buildCache(context: Context): SimpleCache {
        val evictor =
            LeastRecentlyUsedCacheEvictor((100 * 1024 * 1024).toLong())
        com.google.android.exoplayer2.database.ExoDatabaseProvider(context)
       // val databaseProvider = StandaloneDatabaseProvider(context)
        val databaseProvider = ExoDatabaseProvider(context)
        return SimpleCache(File(context.cacheDir, "media"), evictor, databaseProvider)
    }
}