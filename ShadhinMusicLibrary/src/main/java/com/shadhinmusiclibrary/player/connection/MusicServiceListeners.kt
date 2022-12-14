package com.shadhinmusiclibrary.player.connection

import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.LiveData
import com.shadhinmusiclibrary.player.data.model.ErrorMessage
import com.shadhinmusiclibrary.player.data.model.Music
import com.shadhinmusiclibrary.player.data.model.MusicPlayList

interface MusicServiceListeners {
    val currentMusicLiveData: LiveData<Music?>
    val playerErrorLiveData:LiveData<ErrorMessage>
    val playListLiveData: LiveData<MusicPlayList>
    val playbackStateLiveData: LiveData<PlaybackStateCompat>
    val musicIndexLiveData:LiveData<Int>
    val repeatModeLiveData:LiveData<Int>
    val shuffleLiveData:LiveData<Int>
    fun playbackState(playbackStateListeners:PlaybackStateListeners)
}