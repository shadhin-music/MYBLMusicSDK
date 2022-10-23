package com.shadhinmusiclibrary.callBackService

import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.data.model.podcast.Track

internal interface PodcastOnItemClickCallback {
    fun onRootClickItem(mSongDetails: MutableList<Track>, clickItemPosition: Int)
    fun onClickItem(mTracks: MutableList<Track>, clickItemPosition: Int)
    fun getCurrentVH(currentVH: RecyclerView.ViewHolder, mTracks: MutableList<Track>)
}