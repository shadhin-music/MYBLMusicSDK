package com.shadhinmusiclibrary.callBackService


import com.shadhinmusiclibrary.data.model.SongDetail


internal interface BottomSheetDialogItemCallback {
    fun onClickBottomItem(mSongDetails: SongDetail)
}