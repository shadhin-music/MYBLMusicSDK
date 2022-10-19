package com.shadhinmusiclibrary.player.data.source

import android.content.Context
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.offline.DownloadManager
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.Util
import java.io.File


class MyBLDownloadManager( private val context: Context) {
    var dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
        context, Util.getUserAgent(context, "uamp"), null)

    private  var dataBase: DatabaseProvider
    private var downloadContentDirectory: File
    var downloadCache: Cache
    var downloadManager: DownloadManager

    init {
        dataBase = ExoDatabaseProvider(context)
        downloadContentDirectory = File(context.getExternalFilesDir(null), "my app")
        downloadCache = SimpleCache(downloadContentDirectory, NoOpCacheEvictor(), dataBase)
        downloadManager = DownloadManager(context, dataBase, downloadCache,dataSourceFactory)
    }

}