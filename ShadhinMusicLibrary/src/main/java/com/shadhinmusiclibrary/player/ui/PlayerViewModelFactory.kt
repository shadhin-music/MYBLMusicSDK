package com.shadhinmusiclibrary.player.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shadhinmusiclibrary.player.ShadhinMusicServiceConnection

class PlayerViewModelFactory(private val shadhinMusicServiceConnection: ShadhinMusicServiceConnection) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return PlayerViewModel(shadhinMusicServiceConnection) as T
    }
}