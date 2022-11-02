package com.shadhinmusiclibrary.player.data.source

import android.content.Context
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider
import com.google.android.exoplayer2.offline.DownloadManager
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.Util
import com.shadhinmusiclibrary.di.ShadhinApp
import com.shadhinmusiclibrary.player.data.model.Music
import com.shadhinmusiclibrary.player.data.rest.MusicRepository
import java.io.File

internal class MyBLDownloadManager(private val context: Context, musicRepository: MusicRepository) {
    var dataSourceFactory: DataSource.Factory =
        DownloadDataSourceFactory.build(context, musicRepository)
    private var dataBase: DatabaseProvider

    //  var downloadCache: Cache
    var downloadManager: DownloadManager

    init {
        dataBase = StandaloneDatabaseProvider(context)
        downloadManager = DownloadManager(
            context,
            dataBase,
            ShadhinApp.module(context).exoplayerCache,
            dataSourceFactory,
            Runnable::run
        )
    }
}