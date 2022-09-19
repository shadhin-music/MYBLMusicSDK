package com.shadhinmusiclibrary.di

import android.app.Activity

interface ActivityEntryPoint {
    val injector: Module
        get() = ((this as Activity).application as ShadhinApp).module
}