package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.SongDetail

interface BottomSheetDialogItemCallback {
    fun onClickBottomItem(mSongDetails: SongDetail)
}