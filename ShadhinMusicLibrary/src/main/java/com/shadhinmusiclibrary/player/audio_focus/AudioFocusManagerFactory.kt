package com.shadhinmusiclibrary.player.audio_focus

import android.os.Build

class AudioFocusManagerFactory {
    companion object{
        @JvmStatic
        fun createAudioFocusManager(): AudioFocusManager {
           /* return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                PostOreoAudioFocusManager()
            }else{*/
              return  PreOreoAudioFocusManager()
          //  }
        }
    }
}