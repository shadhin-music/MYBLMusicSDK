package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.IMusicModel

internal interface favItemClickCallback {
    fun onClickBottomItemPodcast(mSongDetails: IMusicModel)
    fun onClickBottomItemSongs(mSongDetails: IMusicModel)
    fun onClickBottomItemVideo(mSongDetails: IMusicModel)
}
