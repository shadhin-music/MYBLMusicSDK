package com.shadhinmusiclibrary.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore

internal class MusicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        val imageBackBtn: AppCompatImageView = findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            onBackPressed()
        }
    }
}