package com.shadhinmusiclibrary.activities

import com.shadhinmusiclibrary.data.model.SongDetail

interface ItemClickListener {
    fun onClick(position: Int, mSongDetails: SongDetail, id: String?)
}
