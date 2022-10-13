package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.model.podcast.Data
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.data.model.search.*
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModelData

internal interface SearchItemCallBack {
    fun onClickSearchItem(searchData: SearchData)
    fun onClickPlayItem(songItem: List<TopTrendingdata>, clickItemPosition: Int)
    fun onClickPlaySearchItem(songItem: List<SearchData>, clickItemPosition: Int)
//    fun onClickAlbumItem(albumModelData: SearchData)
//    fun onAlbumClick(itemPosition: Int, songDetail: MutableList<ArtistAlbumModelData>)
}