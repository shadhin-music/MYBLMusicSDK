package com.co.shadhinmusicsdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shadhinmusiclibrary.ShadhinMusicSdkCore

class AppActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.app_home_fragment, ShadhinMusicSdkCore.getHomeFragment())
        transaction.commit()
    }
}