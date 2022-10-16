package com.shadhinmusiclibrary.player.data.source

import android.util.Log
import com.shadhinmusiclibrary.player.Constants
import com.shadhinmusiclibrary.player.data.model.Music
import com.shadhinmusiclibrary.player.data.rest.MusicRepository
import com.shadhinmusiclibrary.player.singleton.DataSourceInfo
import okhttp3.Interceptor
import okhttp3.Response

internal class PlayerInterceptor(private val musicRepository: MusicRepository, private val music: Music) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        DataSourceInfo.isDataSourceError = false
        val newUrl = musicRepository.fetchURL(music)
     //   Log.i("music_payer", "intercept: $newUrl")
        val newRequest =
            chain.request().newBuilder()
                .url(newUrl.toString())
                .header("User-Agent", Constants.userAgent)
                .method("GET", null)
                .build()
        return chain.proceed(newRequest)

    }
}