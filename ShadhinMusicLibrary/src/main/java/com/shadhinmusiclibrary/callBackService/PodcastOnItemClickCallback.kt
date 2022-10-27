package com.shadhinmusiclibrary.callBackService

import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.data.IMusicModel

internal interface PodcastOnItemClickCallback {
    fun onRootClickItem(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int)
    fun onClickItem(mTracks: MutableList<IMusicModel>, clickItemPosition: Int)
    fun getCurrentVH(currentVH: RecyclerView.ViewHolder, mTracks: MutableList<IMusicModel>)
}