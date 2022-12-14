package com.shadhinmusiclibrary.di.single


import com.shadhinmusiclibrary.player.data.rest.PlayerApiService
import retrofit2.Retrofit

class SinglePlayerApiService private constructor() {
   companion object {
        @Volatile
        private var INSTANCE: PlayerApiService? = null
        fun getInstance(retrofit: Retrofit): PlayerApiService =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: newInstance(retrofit).also { INSTANCE = it }
            }

        private fun newInstance(retrofit: Retrofit):PlayerApiService{
            return retrofit.create(PlayerApiService::class.java)
        }

    }
}