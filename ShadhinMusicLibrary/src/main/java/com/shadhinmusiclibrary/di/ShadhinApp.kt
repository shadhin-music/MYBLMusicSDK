package com.shadhinmusiclibrary.di

import android.app.Application

class ShadhinApp: Application() {
    private var  _module:Module?=null
    val module:Module
        get() = _module!!

    override fun onCreate() {
        super.onCreate()
        _module = Module(applicationContext)
    }
}