package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.FeaturedLatestTrackListData
import com.shadhinmusiclibrary.data.model.FeaturedSongDetail
import com.shadhinmusiclibrary.data.model.fav.FavData
import com.shadhinmusiclibrary.download.room.DownloadedContent

internal interface DownloadedSongOnCallBack {
    fun onClickItem(mSongDetails: MutableList<DownloadedContent>, clickItemPosition: Int)
    fun onClickFavItem(mSongDetails: MutableList<FavData>,clickItemPosition: Int)
}