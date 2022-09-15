package com.shadhinmusiclibrary.player.data.source

import android.content.Context
import android.util.Log
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSource

import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource

import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.shadhinmusiclibrary.player.Constants
import com.shadhinmusiclibrary.player.data.model.Music
import com.shadhinmusiclibrary.player.data.rest.MusicRepository
import com.shadhinmusiclibrary.player.singleton.DataSourceInfo.isDataSourceError
import okhttp3.OkHttpClient


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


        val networkFactory =  OkHttpDataSource.Factory(client)
        factory =  DefaultDataSource.Factory(context,networkFactory)
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

