package com.shadhinmusiclibrary.callBackService

import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.model.podcast.Track
import com.shadhinmusiclibrary.fragments.artist.ArtistContentData

interface PodcustOnItemClickCallback {
    fun onRootClickItem(mSongDetails: MutableList<Track>, clickItemPosition: Int)
    fun onClickItem(mTracks: MutableList<Track>, clickItemPosition: Int)
    fun getCurrentVH(currentVH: RecyclerView.ViewHolder, mTracks: MutableList<Track>)
}