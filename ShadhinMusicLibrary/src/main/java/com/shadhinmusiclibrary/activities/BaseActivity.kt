package com.shadhinmusiclibrary.activities

import android.graphics.PorterDuff
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
                    R.color.my_sdk_color_accent
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