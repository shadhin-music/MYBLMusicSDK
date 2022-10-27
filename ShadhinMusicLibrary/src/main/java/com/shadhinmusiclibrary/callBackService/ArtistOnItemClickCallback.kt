package com.shadhinmusiclibrary.callBackService

import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.fragments.artist.ArtistContentData

internal interface ArtistOnItemClickCallback {
    fun onRootClickItem(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int)
    fun onClickItem(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int)
    fun getCurrentVH(
        currentVH: RecyclerView.ViewHolder,
        songDetails: MutableList<IMusicModel>
    )
}