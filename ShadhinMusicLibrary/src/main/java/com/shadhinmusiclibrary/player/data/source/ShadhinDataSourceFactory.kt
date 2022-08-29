package com.shadhinmusiclibrary.player.data.source

import android.content.Context
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import com.gm.shadhin.data.rest.kotlin.RestRepositoryKt
import com.gm.shadhin.data.rest.kotlin.Status
import com.gm.shadhin.data.storage.LocalStorage
import com.gm.shadhin.data.storage.PreferenceStorage
import com.shadhinmusiclibrary.player.Constants.userAgent
import com.gm.shadhin.player.MediaContext
import com.gm.shadhin.player.ShadhinMusicPlayerContext
import com.shadhinmusiclibrary.player.data.model.Music
import com.gm.shadhin.player.singleton.DataSourceInfo.dataSourceErrorCode
import com.gm.shadhin.player.singleton.DataSourceInfo.dataSourceErrorMessage
import com.gm.shadhin.player.singleton.DataSourceInfo.isDataSourceError
import com.gm.shadhin.player.utils.isLocalUrl
import com.gm.shadhin.util.getToken
import com.gm.shadhin.util.monitorPerformance
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response


private const val TAG = "DataSourceFactory"
open class ShadhinDataSourceFactory private constructor(private val context: Context, private val music: Music) :DataSource.Factory {

    private lateinit var factory: DataSource.Factory
    private var localStorage: LocalStorage = PreferenceStorage(context)
    private val musicMusicPlayer: MediaContext = context as MediaContext
    private val restRepository: RestRepositoryKt? = musicMusicPlayer.repositoryKt


    init {
        initialization()
    }

    private fun initialization() {

        isDataSourceError = false
        val client = OkHttpClient()
            .newBuilder()
            .addInterceptor(PlayerInterceptor())
            .build()
        val networkFactory = OkHttpDataSource.Factory(client)
        factory =  DefaultDataSource.Factory(context,networkFactory)
    }

    private fun fetchContentUrl() = runBlocking  {

        val urlResponse = restRepository?.fetchContentUrl(
            token = localStorage.getToken(),
            pType = if (music.isPodCast()) "PD" else null,
            type = music.podcastSubType(),
            tType = music.trackType,
            name = if(!music.filePath().isNullOrEmpty()) music.filePath() else null
        )
        val url = if (urlResponse?.status == Status.SUCCESS && urlResponse.data?.data != null) {
            urlResponse.data.data
        } else {
            isDataSourceError = true
            dataSourceErrorCode = urlResponse?.errorCode
            dataSourceErrorMessage =
                urlResponse?.data?.message ?: urlResponse?.message ?: "Something wrong"
            null
        }
        return@runBlocking url
    }

    override fun createDataSource(): DataSource {
        return factory.createDataSource()
    }

    inner class PlayerInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            return monitorPerformance("PlayerPreprocessing", "${music.title}") {
                isDataSourceError = false
                //refreshStreamingStatus()
                val newUrl = fetchContentUrl()
                val newRequest =
                    chain.request().newBuilder()
                        .url(newUrl.toString())
                        .header("User-Agent", userAgent)
                        .method("GET", null)
                        .build()
                 chain.proceed(newRequest)
            }
        }
    }
    companion object{
        @JvmStatic
        fun buildWithCache(context: Context, music: Music, isInternetConnected: Boolean= true): DataSource.Factory {
            val cache = (context as ShadhinMusicPlayerContext).playerCache
            return if(
                cache !=null
                && !music.isLive()
                && !music.mediaUrl.isLocalUrl()
                && isInternetConnected){

                CacheDataSource.Factory()
                    .setCache(cache)
                    .setUpstreamDataSourceFactory(
                        ShadhinDataSourceFactory(context,music)
                    )
                    .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)

            }else{
                return ShadhinDataSourceFactory(context,music)
            }

        }
        @JvmStatic
        fun build(context: Context,music: Music): DataSource.Factory {
            return ShadhinDataSourceFactory(context,music)
        }

    }
}

