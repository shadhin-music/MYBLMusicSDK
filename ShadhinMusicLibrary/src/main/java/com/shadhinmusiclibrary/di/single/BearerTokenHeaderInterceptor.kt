package com.shadhinmusiclibrary.di.single

import com.shadhinmusiclibrary.data.repository.AuthRepository
import com.shadhinmusiclibrary.utils.AppConstantUtils
import com.shadhinmusiclibrary.utils.AppConstantUtils.token
import okhttp3.Interceptor
import okhttp3.Response

class BearerTokenHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response  {
        val finalToken = ("Bearer ${AuthRepository.appToken?:AppConstantUtils.token}")
        return chain.run {
            proceed(
                request()
                    .newBuilder()
                    .addHeader("Authorization", finalToken)
                    .build()
            )
        }
    }
}