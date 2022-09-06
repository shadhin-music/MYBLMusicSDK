package com.shadhinmusiclibrary

import androidx.fragment.app.Fragment
import com.shadhinmusiclibrary.fragments.home.HomeFragment

object ShadhinMusicSdkCore {
    private var backPressCount = 0

    fun getHomeFragment(): Fragment {
        return HomeFragment()
    }

    internal fun pressCountIncrement() {
        backPressCount += 1
    }

    internal fun pressCountDecrement(): Int {
        backPressCount -= 1
        return backPressCount
    }
}