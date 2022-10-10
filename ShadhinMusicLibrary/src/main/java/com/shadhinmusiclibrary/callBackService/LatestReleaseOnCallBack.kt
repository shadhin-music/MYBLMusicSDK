package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.FeaturedLatestTrackListData
import com.shadhinmusiclibrary.data.model.FeaturedSongDetail

interface LatestReleaseOnCallBack {
    fun onClickItem(mSongDetails: MutableList<FeaturedSongDetail>, clickItemPosition: Int)
}