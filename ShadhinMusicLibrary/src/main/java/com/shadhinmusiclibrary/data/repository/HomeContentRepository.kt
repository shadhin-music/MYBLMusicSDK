package com.shadhinmusiclibrary.data.repository

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.utils.safeApiCall

class HomeContentRepository(private val apiService: ApiService) {

    suspend fun fetchHomeData(pageNumber: Int?, isPaid: Boolean?) = safeApiCall {
        apiService.fetchHomeData(pageNumber,isPaid)
    }

}