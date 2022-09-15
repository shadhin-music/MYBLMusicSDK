package com.shadhinmusiclibrary.activities

import android.graphics.PorterDuff
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

    fun setControlColor(ibtnControl: ImageButton) {
        ibtnControl.setColorFilter(
            ContextCompat.getColor(
                this,
                R.color.colorAccent
            ), PorterDuff.Mode.SRC_IN
        )
    }
}