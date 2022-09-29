package com.shadhinmusiclibrary

import android.content.Context
import androidx.fragment.app.Fragment
import com.shadhinmusiclibrary.fragments.home.HomeFragment

object ShadhinMusicSdkCore {
    private var backPressCount = 0


    fun getHomeFragment(): Fragment {
        return HomeFragment()
    }

    /**
    @param username user name
    @param msID required the mobile number
    @param imageUrl user profile image
    @param token required login auth code
     */
    fun initializeSDK(
        userName: String = "anonymous",
        msID: String,
        imageUrl: String = "https://cdn-icons-png.flaticon.com/512/634/634795.png",
        token: String
    ) {

    }

    fun openActivity(reqContext: Context, reqId: Int) {

    }

    internal fun pressCountIncrement() {
        backPressCount += 1
    }

    internal fun pressCountDecrement(): Int {
        backPressCount -= 1
        return backPressCount
    }

    const val API_LatestRelease = 1
    const val API_FeaturedPodcast = 2
    const val API_PopularArtists = 3
    const val API_Videos = 4
    const val API_Tunes = 5
}