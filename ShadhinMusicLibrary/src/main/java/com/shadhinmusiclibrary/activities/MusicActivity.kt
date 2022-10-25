package com.shadhinmusiclibrary.activities

import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import com.shadhinmusiclibrary.R

internal class MusicActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        val imageBackBtn: AppCompatImageView = findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            onBackPressed()
        }
    }

    override fun changePlayerView(playerMode: PlayerMode?) {

    }
}