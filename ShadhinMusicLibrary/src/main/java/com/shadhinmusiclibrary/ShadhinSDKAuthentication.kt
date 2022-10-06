package com.shadhinmusiclibrary

import androidx.annotation.Keep

@Keep
interface ShadhinSDKCallback {
    fun tokenStatus(): Boolean
}