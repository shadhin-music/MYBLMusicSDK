package com.shadhinmusiclibrary.activities

import android.graphics.PorterDuff
import android.support.v4.media.session.PlaybackStateCompat
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.shadhinmusiclibrary.R

abstract class BaseActivity : AppCompatActivity() {

    var playerMode: PlayerMode? = null

    enum class PlayerMode {
        MINIMIZED, MAXIMIZED, CLOSED
    }

    abstract fun changePlayerView(playerMode: PlayerMode?)

    fun setControlColor(isTrue: Boolean, ibtnControl: ImageButton) {
        if (isTrue) {
            ibtnControl.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.colorAccent
                ), PorterDuff.Mode.SRC_IN
            )
        } else {
            ibtnControl.setColorFilter(0)
        }
    }

    fun setRepeatOneControlColor(it: Int, ibtnControl: ImageButton) {
        when (it) {
            PlaybackStateCompat.REPEAT_MODE_NONE -> {
                ibtnControl.setImageResource(R.drawable.ic_baseline_repeat_24)
//                setControlColor(false, ibtnControl)
            }
            PlaybackStateCompat.REPEAT_MODE_ONE -> {
                ibtnControl.setImageResource(R.drawable.ic_baseline_repeat_one_on_24)
//                setControlColor(true, ibtnControl)
            }
            PlaybackStateCompat.REPEAT_MODE_ALL -> {
                ibtnControl.setImageResource(R.drawable.ic_baseline_repeat_on_24)
//                setControlColor(true, ibtnControl)
            }
        }
    }
}