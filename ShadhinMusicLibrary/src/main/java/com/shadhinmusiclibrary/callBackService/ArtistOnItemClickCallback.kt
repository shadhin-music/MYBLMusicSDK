package com.shadhinmusiclibrary.callBackService

import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.fragments.artist.ArtistContentData

internal interface ArtistOnItemClickCallback {
    fun onRootClickItem(mSongDetails: MutableList<ArtistContentData>, clickItemPosition: Int)
    fun onClickItem(mSongDetails: MutableList<ArtistContentData>, clickItemPosition: Int)
    fun getCurrentVH(
        currentVH: RecyclerView.ViewHolder,
        songDetails: MutableList<ArtistContentData>
    )
}