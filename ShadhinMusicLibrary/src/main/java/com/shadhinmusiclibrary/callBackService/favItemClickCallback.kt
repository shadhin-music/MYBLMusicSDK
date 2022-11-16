package com.shadhinmusiclibrary.callBackService

import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.model.fav.FavData
import com.shadhinmusiclibrary.download.room.DownloadedContent

interface favItemClickCallback {
  fun onClickBottomItemPodcast(mSongDetails: FavData)
  fun onClickBottomItemSongs(mSongDetails: FavData)
  fun onClickBottomItemVideo(mSongDetails: FavData)
}
