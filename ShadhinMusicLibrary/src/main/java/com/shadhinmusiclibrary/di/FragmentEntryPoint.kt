package com.shadhinmusiclibrary.di

import androidx.fragment.app.Fragment

interface FragmentEntryPoint {
    val injector: Module
        get() =  ShadhinApp.module((this as Fragment).requireContext().applicationContext)
}