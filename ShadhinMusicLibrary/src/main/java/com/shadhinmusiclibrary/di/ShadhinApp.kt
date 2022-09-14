package com.shadhinmusiclibrary.di

import android.app.Application
import com.shadhinmusiclibrary.player.utils.createMusicNotificationChannel

class ShadhinApp: Application() {
    private var  _module:Module?=null
    val module:Module
        get() = _module!!

    override fun onCreate() {
        super.onCreate()
        _module = Module(applicationContext)
        createMusicNotificationChannel(applicationContext)
    }
}