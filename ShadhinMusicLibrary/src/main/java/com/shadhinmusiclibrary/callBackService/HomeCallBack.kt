package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.model.podcast.Data
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModelData

interface HomeCallBack {
    fun onClickItemAndAllItem(itemPosition: Int, selectedHomePatchItem: HomePatchItem)
    fun onClickSeeAll(selectedHomePatchItem: HomePatchItem)
    fun onArtistAlbumClick(itemPosition: Int, artistAlbumModelData: List<ArtistAlbumModelData>){}
    fun onClickItemPodcastEpisode(itemPosition: Int, selectedEpisode: List<Episode>)
//    fun onAlbumClick(itemPosition: Int, songDetail: MutableList<ArtistAlbumModelData>)
}