package com.shadhinmusiclibrary

interface ShadhinSDKCallback {
    fun tokenStatus(isTokenValid: Boolean, error: String)
}