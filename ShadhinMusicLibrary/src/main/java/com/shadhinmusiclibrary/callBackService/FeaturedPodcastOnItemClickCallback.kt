package com.shadhinmusiclibrary.callBackService

import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.data.model.FeaturedPodcastDetails

internal interface FeaturedPodcastOnItemClickCallback {
    fun onRootClickItem(episode: MutableList<FeaturedPodcastDetails>, clickItemPosition: Int)
    fun onClickItem(episode: MutableList<FeaturedPodcastDetails>, clickItemPosition: Int)
    fun getCurrentVH(currentVH: RecyclerView.ViewHolder, episode: MutableList<FeaturedPodcastDetails>)
}