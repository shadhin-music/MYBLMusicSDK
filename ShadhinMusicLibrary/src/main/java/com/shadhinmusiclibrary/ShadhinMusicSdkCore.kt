package com.shadhinmusiclibrary

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.shadhinmusiclibrary.fragments.home.HomeFragment

object ShadhinMusicSdkCore {
    fun getHomeFragment(context: Context): Fragment {
        return HomeFragment()
    }
}