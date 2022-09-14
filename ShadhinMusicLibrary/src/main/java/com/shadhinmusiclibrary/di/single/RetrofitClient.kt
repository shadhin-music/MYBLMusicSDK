package com.shadhinmusiclibrary.di.single

import com.shadhinmusiclibrary.utils.AppConstantUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {
    companion object {
        @Volatile
        private var INSTANCE: Retrofit? = null
        fun getInstance(): Retrofit =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: newInstance().also { INSTANCE = it }
            }

        private fun newInstance():Retrofit{
            return Retrofit.Builder()
                .baseUrl(AppConstantUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    }
}