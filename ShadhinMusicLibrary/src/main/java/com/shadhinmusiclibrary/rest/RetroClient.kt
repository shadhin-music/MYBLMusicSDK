package com.shadhinmusiclibrary.rest

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.fragments.artist.LastFmApiKeyInterceptor
import com.shadhinmusiclibrary.utils.AppConstantUtils.BASE_URL
import com.shadhinmusiclibrary.utils.AppConstantUtils.BASE_URL_API_shadhinmusic
import com.shadhinmusiclibrary.utils.AppConstantUtils.BASE_URL_V5
import okhttp3.OkHttpClient
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
//     private  fun getRetrofitAPIV5Instance(): Retrofit{
//         return Retrofit.Builder()
//             .baseUrl(BASE_URL_V5)
//             .addConverterFactory(GsonConverterFactory.create())
//             .build()
//     }
//        fun getAPIArtistBannerService():ApiService{
//            return getRetrofitAPIV5Instance().create(ApiService::class.java)
//        }
    }
}
