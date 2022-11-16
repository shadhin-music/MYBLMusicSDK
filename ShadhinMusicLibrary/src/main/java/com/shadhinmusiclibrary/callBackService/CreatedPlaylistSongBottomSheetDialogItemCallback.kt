package com.shadhinmusiclibrary.callBackService


import com.shadhinmusiclibrary.data.model.SongDetailModel


internal interface CreatedPlaylistSongBottomSheetDialogItemCallback {
    fun onClickBottomItem(mSongDetails: SongDetailModel)
}