package com.shadhinmusiclibrary.data.repository

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.utils.safeApiCall
import okhttp3.RequestBody
import org.json.JSONObject

class HomeContentRepository(private val apiService: ApiService) {

    suspend fun fetchHomeData(pageNumber: Int?, isPaid: Boolean?) = safeApiCall {
        apiService.fetchHomeData(pageNumber, isPaid)
    }


     suspend fun rbtURL(requestBody:RequestBody) = safeApiCall {
         apiService.rbtURL(requestBody)
     }
}