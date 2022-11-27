package com.co.shadhinmusicsdk.data

import com.co.shadhinmusicsdk.data.model.LoginResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface AppApiService {
    @GET("mybl/Login")
    fun fetchPhoneAuth(@Header("Authorization") token: String): Call<LoginResponse>
}