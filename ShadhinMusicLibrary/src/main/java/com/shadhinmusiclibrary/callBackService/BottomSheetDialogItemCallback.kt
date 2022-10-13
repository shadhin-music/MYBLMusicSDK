package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.fragments.artist.ArtistContentData

internal interface BottomSheetDialogItemCallback {
    fun onClickBottomItem(mSongDetails: SongDetail)
}