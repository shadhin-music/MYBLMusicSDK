package com.shadhinmusiclibrary.utils

import android.content.Context
import android.content.Intent
import com.shadhinmusiclibrary.activities.SDKMainActivity

internal object ShadhinSdkCore {
    //    private lateinit var funfff = FirebaseException
    fun openFeature(context: Context, requestType: Int) {
        context.startActivity(
            Intent(
                context,
                SDKMainActivity::class.java
            ).apply {
                putExtra(AppConstantUtils.requestType, requestType)
            }
        )
    }


}