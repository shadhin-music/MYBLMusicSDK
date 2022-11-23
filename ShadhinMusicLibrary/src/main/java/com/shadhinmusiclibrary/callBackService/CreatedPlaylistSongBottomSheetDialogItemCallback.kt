package com.shadhinmusiclibrary.callBackService


import com.shadhinmusiclibrary.data.IMusicModel


internal interface CreatedPlaylistSongBottomSheetDialogItemCallback {
    fun onClickBottomItem(mSongDetails: IMusicModel)
}