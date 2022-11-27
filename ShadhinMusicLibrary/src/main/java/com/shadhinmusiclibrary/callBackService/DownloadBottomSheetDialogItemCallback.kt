package com.shadhinmusiclibrary.callBackService


import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.download.room.DownloadedContent


internal interface DownloadBottomSheetDialogItemCallback {
    fun onClickBottomItemPodcast(mSongDetails: IMusicModel)
    fun onClickBottomItemSongs(mSongDetails: IMusicModel)
    fun onClickBottomItemVideo(mSongDetails: IMusicModel)
}