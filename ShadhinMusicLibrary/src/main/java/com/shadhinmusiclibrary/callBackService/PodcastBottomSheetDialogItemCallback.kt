package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.model.podcast.Track
import com.shadhinmusiclibrary.fragments.artist.ArtistContentData

internal interface PodcastBottomSheetDialogItemCallback {
    fun onClickBottomItem(track: Track)
}