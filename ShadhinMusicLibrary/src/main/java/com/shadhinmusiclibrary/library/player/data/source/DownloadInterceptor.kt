package com.shadhinmusiclibrary.player.data.source

import android.util.Log
import com.shadhinmusiclibrary.player.Constants
import com.shadhinmusiclibrary.player.data.rest.MusicRepository
import com.shadhinmusiclibrary.player.singleton.DataSourceInfo
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

internal class DownloadInterceptor(private val musicRepository: MusicRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        var tryCount = 0
        val url = request.url.toString().replace(Constants.FILE_BASE_URL, "")
        var newUrl = musicRepository.fetchDownloadedURL(url)
        DataSourceInfo.isDataSourceError = false
        while (newUrl.isNullOrBlank() && tryCount < 3) {
            tryCount++
            try {
                newUrl = musicRepository.fetchDownloadedURL(url)
            } catch (e: Exception) {
                Log.d("intercept", "Request is not successful - $tryCount")
                Log.e("TAG", "ERROR: " + chain.request().url)
            }
        }
        val newRequest =
            chain.request().newBuilder()
                .url(newUrl)
                .header("User-Agent", Constants.userAgent)
                .method("GET", null)
                .build()
        return chain.proceed(newRequest)
    }
}