package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.IMusicModel

internal interface CommonSinglePlayControlCallback {
    fun onClickBottomItem(mSongDetails: IMusicModel)
}