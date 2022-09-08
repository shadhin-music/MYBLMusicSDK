package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModelData

interface HomeCallBack {
    fun onClickItemAndAllItem(itemPosition: Int, selectedHomePatchItem: HomePatchItem)
    fun onClickSeeAll(selectedHomePatchItem: HomePatchItem)
    fun onArtistAlbumClick(itemPosition: Int, artistAlbumModelData: List<ArtistAlbumModelData>){}
}