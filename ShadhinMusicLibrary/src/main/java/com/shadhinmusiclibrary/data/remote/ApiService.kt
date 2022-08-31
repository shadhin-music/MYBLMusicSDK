package com.shadhinmusiclibrary.rest

import com.shadhinmusiclibrary.data.model.APIResponse
import com.shadhinmusiclibrary.data.model.Content
import com.shadhinmusiclibrary.data.model.SortDescription
import com.shadhinmusiclibrary.data.model.DataDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("HomeContent/GetHomeContent")
    suspend fun getHomeData(
        @Query("pageNumber") pageNumber: Int?,
        @Query("isPaid") isPaid: Boolean?
    ): Call<APIResponse<List<SortDescription>>>

    @GET("v5/Album/GetAlbumContent")
    fun getAlbumContent(
        @Query("id") pageNumber: Int
    ): Call<APIResponse<List<Content>>>
}