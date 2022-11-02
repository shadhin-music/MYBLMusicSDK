package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.download.room.DownloadedContent

internal interface DownloadedSongOnCallBack {
    fun onClickItem(mSongDetails: MutableList<DownloadedContent>, clickItemPosition: Int)
}