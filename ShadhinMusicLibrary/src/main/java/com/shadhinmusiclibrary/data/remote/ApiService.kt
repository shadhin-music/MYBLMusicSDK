package com.shadhinmusiclibrary.data.remote

import com.shadhinmusiclibrary.data.model.Data
import com.shadhinmusiclibrary.data.model.HomeData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("HomeContent/GetHomeContent")
    suspend fun fetchHomeData(
        @Query("pageNumber") pageNumber: Int?,
        @Query("isPaid") isPaid: Boolean?
    ): HomeData

    //@Get()
}