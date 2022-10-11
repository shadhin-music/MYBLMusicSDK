package com.shadhinmusiclibrary.di

import android.app.Activity

interface ActivityEntryPoint {
    val injector: Module
        get() = ShadhinApp.module((this as Activity).applicationContext)
}