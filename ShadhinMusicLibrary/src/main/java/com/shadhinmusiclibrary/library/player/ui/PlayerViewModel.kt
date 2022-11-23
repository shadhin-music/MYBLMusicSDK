package com.shadhinmusiclibrary.library.player.ui

import androidx.lifecycle.*
import com.shadhinmusiclibrary.library.player.connection.MusicServiceController
import com.shadhinmusiclibrary.library.player.data.model.PlayerProgress
import com.shadhinmusiclibrary.library.player.utils.isPlaying
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class PlayerViewModel(private val musicServiceController: MusicServiceController) :
    ViewModel(),
    MusicServiceController by musicServiceController {

    private val _playerProgress: MutableLiveData<PlayerProgress> = MutableLiveData()
    val playerProgress: LiveData<PlayerProgress> = _playerProgress

    init {
        connect()
    }

    //DO NOT Call this function multiple times
    fun startObservePlayerProgress(viewLifecycleOwner: LifecycleOwner) {
        fun update() = viewModelScope.launch {
            while (true) {
                delay(500)
                _playerProgress.postValue(musicServiceController.playerProgress())
            }
        }

        var updateJob: Job? = update()
        playbackState {
            if (it?.isPlaying == true) {
                updateJob?.cancel()
                updateJob = update()
            } else {
                updateJob?.cancel()
            }
        }
    }
}