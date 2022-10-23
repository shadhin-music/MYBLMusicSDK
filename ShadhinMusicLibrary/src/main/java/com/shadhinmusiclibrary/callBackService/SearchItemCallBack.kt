package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.search.SearchData
import com.shadhinmusiclibrary.data.model.search.TopTrendingdata

internal interface SearchItemCallBack {
    fun onClickSearchItem(searchData: SearchData)
    fun onClickPlayItem(songItem: List<TopTrendingdata>, clickItemPosition: Int)
    fun onClickPlaySearchItem(songItem: List<SearchData>, clickItemPosition: Int)
    fun onClickPlayVideoItem(songItem: List<SearchData>, clickItemPosition: Int)
//    fun onClickAlbumItem(albumModelData: SearchData)
//    fun onAlbumClick(itemPosition: Int, songDetail: MutableList<ArtistAlbumModelData>)
}