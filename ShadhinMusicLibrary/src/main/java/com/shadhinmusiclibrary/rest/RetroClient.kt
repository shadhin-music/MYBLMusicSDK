package com.shadhinmusiclibrary.rest

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.utils.AppConstantUtils.BASE_URL
import com.shadhinmusiclibrary.utils.AppConstantUtils.BASE_URL_API_shadhinmusic
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroClient {
    companion object {
        private fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getApiService(): ApiService {
            return getRetrofitInstance().create(ApiService::class.java)
        }

        private fun getRetrofitAPIShadhinMusicInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL_API_shadhinmusic)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getApiShadhinMusicService(): ApiService {
            return getRetrofitAPIShadhinMusicInstance().create(ApiService::class.java)
        }
    }
}
