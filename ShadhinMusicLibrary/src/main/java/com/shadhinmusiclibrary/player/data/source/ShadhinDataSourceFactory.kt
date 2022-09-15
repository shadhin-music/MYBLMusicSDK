package com.shadhinmusiclibrary.player.data.source

import android.content.Context
import android.util.Log
import com.shadhinmusiclibrary.player.singleton.DataSourceInfo.dataSourceErrorCode
import com.shadhinmusiclibrary.player.singleton.DataSourceInfo.dataSourceErrorMessage
import com.shadhinmusiclibrary.player.singleton.DataSourceInfo.isDataSourceError
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.shadhinmusiclibrary.player.Constants
import com.shadhinmusiclibrary.player.data.model.Music
import com.shadhinmusiclibrary.player.data.rest.MusicRepository
import com.shadhinmusiclibrary.utils.Status
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response


private const val TAG = "DataSourceFactory"
open class ShadhinDataSourceFactory private constructor(
    private val context: Context,
    private val music: Music,
    private val cache: SimpleCache,
    private val musicRepository: MusicRepository
) :DataSource.Factory {
    private lateinit var factory: DataSource.Factory
    init {
        initialization()
    }

    private fun initialization() {
        Log.i(TAG, "initialization: ${music.toString()}")
        isDataSourceError = false
        val client = OkHttpClient()
            .newBuilder()
            .addInterceptor(PlayerInterceptor(musicRepository,music))
            .build()
        val networkFactory =  OkHttpDataSourceFactory(client,Constants.userAgent)
        factory =  DefaultDataSourceFactory(context,networkFactory)
    }
    override fun createDataSource(): DataSource {

        return  factory.createDataSource()
      /*  return CacheDataSourceFactory(
            cache,
            factory,
            CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR
        )
            .createDataSource()*/
    }
    companion object{
        @JvmStatic
        fun build(
            context: Context,
            music: Music,
            cache: SimpleCache,
            musicRepository: MusicRepository
        ): DataSource.Factory {
            return ShadhinDataSourceFactory(context,music,cache,musicRepository)
        }

    }
}

