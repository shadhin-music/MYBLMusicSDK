package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.IMusicModel

internal interface DownloadedSongOnCallBack {
    fun onClickItem(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int)
    fun onClickFavItem(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int)
}