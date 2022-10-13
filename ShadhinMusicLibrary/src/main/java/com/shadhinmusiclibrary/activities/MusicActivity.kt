package com.shadhinmusiclibrary.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore

internal class MusicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.fg_sdk_music_ac, ShadhinMusicSdkCore.getMusicFragment())
//        transaction.commit()
    }
}