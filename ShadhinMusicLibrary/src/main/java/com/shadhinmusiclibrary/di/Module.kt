package com.shadhinmusiclibrary.di

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.data.repository.HomeContentRepository
import com.shadhinmusiclibrary.fragments.home.HomeViewModelFactory
import com.shadhinmusiclibrary.utils.AppConstantUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Module {

    private val baseRetrofit:Retrofit = Retrofit.Builder()
        .baseUrl(AppConstantUtils.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiService:ApiService = baseRetrofit.create(ApiService::class.java)
    private val homeContentRepository: HomeContentRepository = HomeContentRepository(apiService)


    val homeViewModelFactory:HomeViewModelFactory
        get() = HomeViewModelFactory(homeContentRepository)
}
