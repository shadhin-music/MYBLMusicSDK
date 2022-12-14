package com.shadhinmusiclibrary.di.single

import com.shadhinmusiclibrary.fragments.artist.LastFmApiKeyInterceptor
import okhttp3.OkHttpClient

class SingleOkHttpClient private constructor() {
    companion object {
        @Volatile
        private var INSTANCE: OkHttpClient? = null
        fun getInstance(): OkHttpClient =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: newInstance().also { INSTANCE = it }
            }

        private fun newInstance(): OkHttpClient{
            return OkHttpClient.Builder()
                .addInterceptor(
                    LastFmApiKeyInterceptor()
                )
                .build()
        }

    }
}