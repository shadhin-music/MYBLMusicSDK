package com.shadhinmusiclibrary.callBackService

import android.graphics.Bitmap
import com.shadhinmusiclibrary.data.model.HomePatchItem

interface ChildCallback {
    fun onClickItemAndAllItem(currentBitmap: Bitmap)
}