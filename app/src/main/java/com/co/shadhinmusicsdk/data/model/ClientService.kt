package com.co.shadhinmusicsdk.data.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientService {

    fun getRetrofitBaseService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.shadhinmusic.com/api/v5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}