package com.shadhinmusiclibrary

import android.content.Context
import android.content.Intent
import androidx.annotation.Keep
import androidx.fragment.app.Fragment
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.di.ShadhinApp
import com.shadhinmusiclibrary.di.single.RetrofitClient
import com.shadhinmusiclibrary.di.single.SingleMusicServiceConnection
import com.shadhinmusiclibrary.di.single.SinglePlayerApiService
import com.shadhinmusiclibrary.fragments.home.HomeFragment
import com.shadhinmusiclibrary.utils.AppConstantUtils
import kotlinx.coroutines.*

@Keep
object ShadhinMusicSdkCore {
    private var backPressCount = 0
    private var  scope:CoroutineScope? = null

    //get Music frangment
    fun getMusicFragment(): Fragment {
        return HomeFragment()
    }


    fun initializeInternalSDK(context: Context) {
        ShadhinApp.module(context).musicServiceController
    }

    /**
    @param token required login auth code
    @param refSdkCall ShadhinSDKCallback
     */
    fun initializeSDK(context: Context,token: String, refSdkCall: ShadhinSDKCallback) {
        scope = CoroutineScope(Dispatchers.IO)
        scope?.launch {

            val res = ShadhinApp.module(context).authRepository().login(token)
            withContext(Dispatchers.Main) {
                refSdkCall.tokenStatus(res.first, res.second ?: "")
            }
        }

    }

    fun openPatch(reqContext: Context, requestId: String) {
        reqContext.startActivity(
            Intent(
                reqContext,
                SDKMainActivity::class.java
            ).apply {
                putExtra(AppConstantUtils.UI_Request_Type, AppConstantUtils.Requester_Name_API)
                putExtra(AppConstantUtils.DataContentRequestId, requestId)
            }
        )
    }

    fun destroySDK(context: Context) {
        scope?.cancel()
        ShadhinApp.module(context)
        ShadhinApp.onDestroy()
        SinglePlayerApiService.destroy()
        RetrofitClient.destroy()
        SingleMusicServiceConnection.destroy()
        ShadhinApp.onDestroy()
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