package com.shadhinmusiclibrary.di.single

import com.shadhinmusiclibrary.utils.AppConstantUtils.token
import okhttp3.Interceptor
import okhttp3.Response

class BearerTokenHeaderInterceptor : Interceptor {
    val finalToken = "Bearer "+token
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Authorization",finalToken)
                .build()
        )
    }
}