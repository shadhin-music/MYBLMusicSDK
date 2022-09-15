package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.SongDetail

interface OnItemClickCallback {
    fun onClickItem(mSongDet: SongDetail)
}