package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.FeaturedLatestTrackListData
import com.shadhinmusiclibrary.data.model.FeaturedSongDetail
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.download.room.WatchLaterContent

internal interface WatchlaterOnCallBack {
    fun onClickItem(mSongDetails: MutableList<WatchLaterContent>, clickItemPosition: Int)
}