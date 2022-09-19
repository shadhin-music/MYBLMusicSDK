package com.shadhinmusiclibrary.callBackService

import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.data.model.SongDetail

interface OnItemClickCallback {
    fun onClickItem(mSongDet: SongDetail)
    fun getCurrentVH(currentVH: RecyclerView.ViewHolder,mSongDet: SongDetail)
}