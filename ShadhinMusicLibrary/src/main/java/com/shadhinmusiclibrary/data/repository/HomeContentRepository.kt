package com.shadhinmusiclibrary.data.repository

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.utils.safeApiCall

class HomeContentRepository(private val apiService: ApiService) {

    suspend fun fetchHomeData(client:Int,pageNumber: Int?, isPaid: Boolean?) = safeApiCall {
        apiService.fetchHomeData(client,pageNumber, isPaid)
    }

}