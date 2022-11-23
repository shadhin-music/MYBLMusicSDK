package com.shadhinmusiclibrary.callBackService

import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.data.IMusicModel

internal interface RadioTrackCallBack {
    fun onClickOpenRadio(currentSong: IMusicModel)
    fun getCurrentVH(currentVH: RecyclerView.ViewHolder, songDetails: MutableList<IMusicModel>)
}