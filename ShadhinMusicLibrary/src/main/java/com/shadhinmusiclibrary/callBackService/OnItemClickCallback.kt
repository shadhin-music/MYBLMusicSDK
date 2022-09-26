package com.shadhinmusiclibrary.callBackService

import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.data.model.SongDetail

interface OnItemClickCallback {
    fun onRootClickItem(mSongDetails: MutableList<SongDetail>, clickItemPosition: Int)
    fun onClickItem(mSongDetails: MutableList<SongDetail>, clickItemPosition: Int)
    fun getCurrentVH(currentVH: RecyclerView.ViewHolder, songDetails: MutableList<SongDetail>)
}