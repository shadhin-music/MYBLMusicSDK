package com.shadhinmusiclibrary.rest

import com.shadhinmusiclibrary.data.model.Data
import com.shadhinmusiclibrary.data.model.HomeData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("HomeContent/GetHomeContent?")
    fun getHomeData(
        @Query("pageNumber") pageNumber: Int?,
        @Query("isPaid") isPaid: Boolean?
    ): Call<HomeData>
}