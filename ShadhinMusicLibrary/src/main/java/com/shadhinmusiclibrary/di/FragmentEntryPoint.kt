package com.shadhinmusiclibrary.di
import androidx.fragment.app.Fragment

interface FragmentEntryPoint {
    val injector: Module
        get() = ((this as Fragment).requireActivity().application as ShadhinApp).module
}