package com.shadhinmusiclibrary.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import com.shadhinmusiclibrary.R

internal class MusicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_bl_sdk_activity_music)

        val imageBackBtn: AppCompatImageView = findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            onBackPressed()
        }
    }
}