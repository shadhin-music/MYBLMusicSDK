package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.download.room.DownloadedContent

internal interface DownloadedSongOnCallBack {
    fun onClickItem(mSongDetails: MutableList<DownloadedContent>, clickItemPosition: Int)
    fun onClickFavItem(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int)
}