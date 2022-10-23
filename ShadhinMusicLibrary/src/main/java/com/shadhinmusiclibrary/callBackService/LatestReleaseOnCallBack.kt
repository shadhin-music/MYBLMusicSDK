package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.FeaturedSongDetail

internal interface LatestReleaseOnCallBack {
    fun onClickItem(mSongDetails: MutableList<FeaturedSongDetail>, clickItemPosition: Int)
}