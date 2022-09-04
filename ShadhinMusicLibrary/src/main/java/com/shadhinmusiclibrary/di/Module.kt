package com.shadhinmusiclibrary.di

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.data.repository.ArtistContentRepository
import com.shadhinmusiclibrary.data.repository.HomeContentRepository
import com.shadhinmusiclibrary.fragments.artist.ArtistViewModel
import com.shadhinmusiclibrary.fragments.artist.ArtistViewModelFactory
import com.shadhinmusiclibrary.fragments.artist.LastFmApiKeyInterceptor
import com.shadhinmusiclibrary.fragments.home.HomeViewModelFactory
import com.shadhinmusiclibrary.utils.AppConstantUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Module {
    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                LastFmApiKeyInterceptor()
            )
            .build()
    }
    private val baseRetrofit:Retrofit = Retrofit.Builder()
        .baseUrl(AppConstantUtils.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val artistBioRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(AppConstantUtils.LAST_FM_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClient())
        .build()
    private val apiService:ApiService = baseRetrofit.create(ApiService::class.java)
    private val apiServiceArtistBio:ApiService = artistBioRetrofit.create(ApiService::class.java)

    private val homeContentRepository: HomeContentRepository = HomeContentRepository(apiService)
    private val artistContentRepository:ArtistContentRepository = ArtistContentRepository(apiServiceArtistBio)

    val homeViewModelFactory:HomeViewModelFactory
        get() = HomeViewModelFactory(homeContentRepository)
    val artistViewModelFactory:ArtistViewModelFactory
       get()= ArtistViewModelFactory(artistContentRepository)
}
