package com.shadhinmusiclibrary.callBackService

import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.data.IMusicModel

internal interface OnItemClickCallback {
    fun onRootClickItem(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int)
    fun onClickItem(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int)
    fun getCurrentVH(currentVH: RecyclerView.ViewHolder, songDetails: MutableList<IMusicModel>)
}