package com.shadhinmusiclibrary.data.repository

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.utils.Status
import com.shadhinmusiclibrary.utils.safeApiCall

internal class AuthRepository (private val apiService: ApiService) {

    suspend fun login(token:String):Pair<Boolean,String?>{
       val response =  safeApiCall { apiService.login("Bearer $token") }
        return if(response.status == Status.SUCCESS){
            appToken = response.data?.data?.token
            Pair(true,response.message)
        }else{
           Pair(false,response.message)
        }
    }
    companion object{
        var appToken:String?=null
    }
}