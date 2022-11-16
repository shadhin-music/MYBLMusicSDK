package com.shadhinmusiclibrary.activities

import com.shadhinmusiclibrary.data.model.SongDetailModel


internal interface ItemClickListener {
    fun onClick(position: Int, mSongDetails: SongDetailModel, id: String?)
}
