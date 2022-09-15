package com.shadhinmusiclibrary.cviewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shadhinmusiclibrary.data.model.SongDetail

class MusicPlayerVM : ViewModel() {
    private val _songDetails: MutableLiveData<SongDetail> = MutableLiveData()
    val homeContent: LiveData<SongDetail> = _songDetails
    fun setPlayMusic(mSongDetail: SongDetail) {
        _songDetails.value = mSongDetail
    }
}