package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.fav.FavData

internal interface favItemClickCallback {
    fun onClickBottomItemPodcast(mSongDetails: FavData)
    fun onClickBottomItemSongs(mSongDetails: FavData)
    fun onClickBottomItemVideo(mSongDetails: FavData)
}
