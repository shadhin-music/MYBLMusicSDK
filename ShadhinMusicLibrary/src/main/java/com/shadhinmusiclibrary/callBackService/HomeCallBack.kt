package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.podcast.EpisodeModel
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModelData

internal interface HomeCallBack {
    fun onClickItemAndAllItem(itemPosition: Int, selectedHomePatchItem: HomePatchItem)
    fun onClickSeeAll(selectedHomePatchItem: HomePatchItem)
    fun onArtistAlbumClick(itemPosition: Int, artistAlbumModelData: List<ArtistAlbumModelData>) {}
    fun onClickItemPodcastEpisode(itemPosition: Int, selectedEpisode: List<EpisodeModel>)
//    fun onAlbumClick(itemPosition: Int, songDetail: MutableList<ArtistAlbumModelData>)
}