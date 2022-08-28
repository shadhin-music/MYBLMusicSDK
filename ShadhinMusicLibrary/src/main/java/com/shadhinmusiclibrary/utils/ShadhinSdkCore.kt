package com.shadhinmusiclibrary.utils

import android.content.Context
import android.content.Intent
import com.shadhinmusiclibrary.activities.MainActivity

object ShadhinSdkCore {
    //    private lateinit var funfff = FirebaseException
    fun openFeature(context: Context, requestType: Int) {
        context.startActivity(
            Intent(
                context,
                MainActivity::class.java
            ).apply {
                putExtra(AppConstantUtils.requestType, requestType)
            }
        )
    }

    const val HOME = 1
    const val HADITH = 2
    const val SURAH = 3
    const val TASBIH = 4
    //const val IFTARSEHRITIME =5
    const val ROJA = 5
    const val ISLAMICCALANDER = 6
    const val COMPASS = 7
    const val SALATLEARNING = 8
    const val ZAKAT = 9
    const val LIVEVIDEO = 10
    const val NEAREST_MOSQUE = 11
    const val WALLPAPER = 12
    const val PAYER_TIME = 13
}