package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.model.podcast.Data
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.data.model.search.SearchAlbumdata
import com.shadhinmusiclibrary.data.model.search.SearchArtistdata
import com.shadhinmusiclibrary.data.model.search.SearchModelData
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModelData

interface SearchItemCallBack {
    fun onClickArtistItem( searchArtistdata: SearchArtistdata)
    fun onClickAlbumItem(albumModelData: SearchAlbumdata)

//    fun onAlbumClick(itemPosition: Int, songDetail: MutableList<ArtistAlbumModelData>)
}