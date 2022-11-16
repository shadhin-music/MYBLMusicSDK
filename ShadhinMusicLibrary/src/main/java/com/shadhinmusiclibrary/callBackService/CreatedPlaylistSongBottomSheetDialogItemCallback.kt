package com.shadhinmusiclibrary.callBackService


import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.fragments.create_playlist.UserSongsPlaylistDataModel


internal interface CreatedPlaylistSongBottomSheetDialogItemCallback {
    fun onClickBottomItem(mSongDetails: SongDetail)

}