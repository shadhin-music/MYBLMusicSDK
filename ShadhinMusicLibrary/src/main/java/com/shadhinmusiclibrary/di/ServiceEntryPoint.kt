package com.shadhinmusiclibrary.di

import android.app.Service

interface ServiceEntryPoint {
    val injector: Module
        get() = ((this as Service).application as ShadhinApp).module
}