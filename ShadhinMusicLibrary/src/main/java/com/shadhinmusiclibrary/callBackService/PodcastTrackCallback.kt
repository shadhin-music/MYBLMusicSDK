package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.HomePatchItemModel
import com.shadhinmusiclibrary.data.model.SongDetailModel

internal interface PodcastTrackCallback {
    fun onClickItem(mSongDetails: List<IMusicModel>, clickItemPosition: Int)
}