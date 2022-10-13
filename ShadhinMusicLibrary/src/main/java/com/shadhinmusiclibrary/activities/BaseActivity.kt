package com.shadhinmusiclibrary.activities

import android.graphics.PorterDuff
import android.support.v4.media.session.PlaybackStateCompat
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.shadhinmusiclibrary.R

internal abstract class BaseActivity : AppCompatActivity() {

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

    fun setResource(ibtnControl: ImageButton, resId: Int) {
        ibtnControl.setImageResource(resId)
    }
}