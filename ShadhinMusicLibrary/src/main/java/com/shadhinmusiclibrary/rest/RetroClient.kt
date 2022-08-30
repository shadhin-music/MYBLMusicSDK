package com.shadhinmusiclibrary.rest

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.utils.AppConstantUtils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroClient
{
    companion object{
        private fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getApiService(): ApiService? {
            return getRetrofitInstance().create(ApiService::class.java)
        }
    }
}
