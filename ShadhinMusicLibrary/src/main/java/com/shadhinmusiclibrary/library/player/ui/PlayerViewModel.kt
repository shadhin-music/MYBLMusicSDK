package com.shadhinmusiclibrary.library.player.ui

import androidx.lifecycle.*
import com.shadhinmusiclibrary.library.player.connection.MusicServiceController
import com.shadhinmusiclibrary.library.player.data.model.PlayerProgress
import com.shadhinmusiclibrary.library.player.utils.isPlaying
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class PlayerViewModel(private val musicServiceController: MusicServiceController): ViewModel(),
    MusicServiceController by  musicServiceController{

    private val _playerProgress: MutableLiveData<PlayerProgress> = MutableLiveData()
    public val playerProgress: LiveData<PlayerProgress> = _playerProgress

    init { connect() }

    //DO NOT Call this function multiple times
    fun startObservePlayerProgress(viewLifecycleOwner: LifecycleOwner) {
      //  Log.i("Homexy${System.identityHashCode(this)}","")
        fun update() = viewModelScope.launch {
            while (true) {
            //    Log.i("Homexy${System.identityHashCode(this)}","dfzsfz")
                delay(500)
                _playerProgress.postValue(musicServiceController.playerProgress())
            }
        }

        var updateJob: Job?=update()

        playbackState {
         //   Log.i("Homexy${System.identityHashCode(this)}", "playbackState: isPlaying : ${it?.isPlaying} , currentState:${viewLifecycleOwner.lifecycle.currentState.name} , check : ${viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)}")
            if (it?.isPlaying == true) {
               // if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    updateJob?.cancel()
                    updateJob = update()
                  //  Log.i("Homexy${System.identityHashCode(this)}","playbackState update")
             //   }

            } else {
                updateJob?.cancel()
               // Log.i("Homexy${System.identityHashCode(this)}","playbackState cancel")
            }
        }

       /* viewLifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                Log.i("Homexy${System.identityHashCode(this)}","onStateChanged All"+event.name)
                when (event) {
                    Lifecycle.Event.ON_START -> {
                        if (isPlaying) {
                            updateJob?.cancel()
                            updateJob = update()
                            Log.i("Homexy${System.identityHashCode(this)}","onStateChanged ON_START ")
                        }
                    }

                    Lifecycle.Event.ON_PAUSE -> {
                        updateJob?.cancel()
                        Log.i("Homexy${System.identityHashCode(this)}","onStateChanged cancel")
                    }
                    else -> {}
                }
            }
        })*/
    }
}