package com.shadhinmusiclibrary.player.ui

import androidx.lifecycle.ViewModel
import com.shadhinmusiclibrary.player.MusicServiceController
import com.shadhinmusiclibrary.player.ShadhinMusicServiceConnection

class PlayerViewModel(private val musicServiceConnection: ShadhinMusicServiceConnection): ViewModel(),
    MusicServiceController by  musicServiceConnection{

}