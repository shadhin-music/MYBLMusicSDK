package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.IMusicModel

internal interface BottomSheetDialogItemCallback {
    fun onClickBottomItem(mSongDetails: IMusicModel)
}