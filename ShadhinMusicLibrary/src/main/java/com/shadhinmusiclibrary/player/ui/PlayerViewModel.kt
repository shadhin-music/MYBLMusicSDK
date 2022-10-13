package com.shadhinmusiclibrary.player.ui

import androidx.lifecycle.*
import com.shadhinmusiclibrary.player.connection.MusicServiceController
import com.shadhinmusiclibrary.player.data.model.PlayerProgress
import com.shadhinmusiclibrary.player.utils.isPlaying
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class PlayerViewModel(private val musicServiceController: MusicServiceController): ViewModel(),
    MusicServiceController by  musicServiceController{

    private val _playerProgress: MutableLiveData<PlayerProgress> = MutableLiveData()
    public val playerProgress: LiveData<PlayerProgress> = _playerProgress


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
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    updateJob?.cancel()
                    updateJob = update()
                }

            } else {
                updateJob?.cancel()
            }
        }

        viewLifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_START -> {
                        if (isPlaying) {
                            updateJob?.cancel()
                            updateJob = update()
                        }
                    }
                    Lifecycle.Event.ON_PAUSE -> {
                        updateJob?.cancel()
                    }
                    else -> {}
                }
            }
        })
    }

}