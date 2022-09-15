package com.shadhinmusiclibrary.utils

import android.app.Activity
import android.content.Context
import android.graphics.Point

object UtilHelper {
    fun getScreenHeightWidth(context: Context, type: Int): Int {
        val display = (context as Activity).windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        val height = size.y
        return if (type == 0) {
            width
        } else {
            height
        }
    }
}