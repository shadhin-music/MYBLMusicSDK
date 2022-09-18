package com.shadhinmusiclibrary.player.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shadhinmusiclibrary.player.MusicServiceController

import com.shadhinmusiclibrary.player.ShadhinMusicServiceConnection
import com.shadhinmusiclibrary.player.data.model.Music

class PlayerViewModel(private val musicServiceConnection: ShadhinMusicServiceConnection): ViewModel(),
    MusicServiceController by  musicServiceConnection{

        public val currentPlayingMusic: LiveData<Music?> = musicServiceConnection.currentPlayingMusicLiveData

}