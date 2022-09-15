package com.shadhinmusiclibrary.di

import android.app.Service
import androidx.fragment.app.Fragment

interface ServiceEntryPoint {
    val injector: Module
        get() = ((this as Service).application as ShadhinApp).module
}