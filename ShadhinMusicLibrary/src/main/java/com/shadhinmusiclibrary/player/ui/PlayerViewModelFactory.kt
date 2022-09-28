package com.shadhinmusiclibrary.player.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shadhinmusiclibrary.player.connection.ShadhinMusicServiceConnection.*
import com.shadhinmusiclibrary.player.connection.ShadhinMusicServiceConnection.*
import com.shadhinmusiclibrary.player.connection.MusicServiceController

class PlayerViewModelFactory(private val shadhinMusicServiceConnection: MusicServiceController) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlayerViewModel(shadhinMusicServiceConnection) as T
    }
}