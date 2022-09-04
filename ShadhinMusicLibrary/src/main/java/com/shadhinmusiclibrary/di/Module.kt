package com.shadhinmusiclibrary.di

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.data.repository.AlbumContentRepository
import com.shadhinmusiclibrary.data.repository.HomeContentRepository
import com.shadhinmusiclibrary.fragments.home.AlbumViewModelFactory
import com.shadhinmusiclibrary.fragments.home.HomeViewModelFactory
import com.shadhinmusiclibrary.rest.RetroClient
import com.shadhinmusiclibrary.utils.AppConstantUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Module {

    private val baseRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(AppConstantUtils.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiServiceHome: ApiService = RetroClient.getApiService()

    //    private val apiService: ApiService = baseRetrofit.create(ApiService::class.java)
    private val homeContentRepository: HomeContentRepository = HomeContentRepository(apiServiceHome)
    val homeViewModelFactory: HomeViewModelFactory
        get() = HomeViewModelFactory(homeContentRepository)

    private val apiServiceAlbum: ApiService = RetroClient.getApiShadhinMusicService()
    private val albumContentRepository: AlbumContentRepository =
        AlbumContentRepository(apiServiceAlbum)
    val albumViewModelFactory: AlbumViewModelFactory
        get() = AlbumViewModelFactory(albumContentRepository)
}
