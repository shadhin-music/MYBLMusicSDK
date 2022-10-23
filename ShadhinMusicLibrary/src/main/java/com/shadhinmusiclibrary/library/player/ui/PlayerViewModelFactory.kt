package com.shadhinmusiclibrary.library.player.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shadhinmusiclibrary.library.player.connection.MusicServiceController

internal class PlayerViewModelFactory(private val shadhinMusicServiceConnection: MusicServiceController) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlayerViewModel(shadhinMusicServiceConnection) as T
    }
}