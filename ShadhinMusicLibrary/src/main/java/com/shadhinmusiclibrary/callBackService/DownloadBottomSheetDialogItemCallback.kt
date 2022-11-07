package com.shadhinmusiclibrary.callBackService


import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.download.room.DownloadedContent


internal interface DownloadBottomSheetDialogItemCallback {
    fun onClickBottomItemPodcast(mSongDetails: DownloadedContent)
    fun onClickBottomItemSongs(mSongDetails: DownloadedContent)
    fun onClickBottomItemVideo(mSongDetails: DownloadedContent)
}