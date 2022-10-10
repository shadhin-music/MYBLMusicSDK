package com.shadhinmusiclibrary.data.repository

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.utils.safeApiCall
import okhttp3.RequestBody
import org.json.JSONObject

class AmartunesContentRepository(private val apiService: ApiService) {

     suspend fun rbtURL() = safeApiCall {
         apiService.rbtURL()
     }
}