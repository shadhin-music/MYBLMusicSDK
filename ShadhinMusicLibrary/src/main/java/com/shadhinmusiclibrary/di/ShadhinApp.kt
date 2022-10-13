package com.shadhinmusiclibrary.di

import android.app.Application
import android.content.Context
import com.shadhinmusiclibrary.player.utils.createMusicNotificationChannel
import com.shadhinmusiclibrary.utils.AppConstantUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


internal object ShadhinApp {
    private var _module:Module?=null
   /* val module:Module
        get() = _module!!*/
    fun module(context: Context): Module{
        createMusicNotificationChannel(context)
        return _module ?: synchronized(this) { _module ?: Module(context).also { _module = it } }
    }
    fun onDestroy(){
        _module = null
    }
}
