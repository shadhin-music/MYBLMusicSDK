package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.SongDetail

internal interface BottomSheetDialogItemCallback {
    fun onClickBottomItem(mSongDetails: IMusicModel)
}