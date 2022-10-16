package com.shadhinmusiclibrary.activities.video.audio_focus

import android.content.Context
import android.media.AudioManager

interface AudioFocusManager {
    fun initialize(context: Context,listener: AudioManager.OnAudioFocusChangeListener)
    fun requestAudioFocus():Int?
}