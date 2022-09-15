package com.shadhinmusiclibrary.player.ui

import androidx.lifecycle.ViewModel
import com.gm.shadhin.player.MusicServiceController

import com.shadhinmusiclibrary.player.ShadhinMusicServiceConnection

class PlayerViewModel(private val musicServiceConnection: ShadhinMusicServiceConnection): ViewModel(),
    MusicServiceController by  musicServiceConnection{

}