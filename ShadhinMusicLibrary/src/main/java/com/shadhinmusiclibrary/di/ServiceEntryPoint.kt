package com.shadhinmusiclibrary.di

import android.app.Service

interface ServiceEntryPoint {
    val injector: Module
        get() = ShadhinApp.module((this as Service).applicationContext)
}