package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.search.SearchData
import com.shadhinmusiclibrary.data.model.search.TopTrendingData

internal interface SearchItemCallBack {
    fun onClickSearchItem(searchData: SearchData)
    fun onClickPlayItem(songItem: MutableList<IMusicModel>, clickItemPosition: Int)
    fun onClickPlaySearchItem(songItem: MutableList<IMusicModel>, clickItemPosition: Int)
    fun onClickPlayVideoItem(songItem: MutableList<IMusicModel>, clickItemPosition: Int)
//    fun onClickAlbumItem(albumModelData: SearchData)
//    fun onAlbumClick(itemPosition: Int, songDetail: MutableList<ArtistAlbumModelData>)
}