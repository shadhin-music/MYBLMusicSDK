package com.shadhinmusiclibrary.di

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.data.repository.*
import com.shadhinmusiclibrary.fragments.artist.ArtistBannerViewModelFactory
import com.shadhinmusiclibrary.fragments.artist.ArtistContentViewModelFactory
import com.shadhinmusiclibrary.fragments.artist.ArtistViewModelFactory
import com.shadhinmusiclibrary.fragments.artist.LastFmApiKeyInterceptor
import com.shadhinmusiclibrary.fragments.home.AlbumViewModelFactory
import com.shadhinmusiclibrary.fragments.home.HomeViewModelFactory
import com.shadhinmusiclibrary.rest.RetroClient
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

    private val baseRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(AppConstantUtils.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val artistBannerRetrofit:Retrofit = Retrofit.Builder()
        .baseUrl(AppConstantUtils.BASE_URL_API_shadhinmusic)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val artistBioRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(AppConstantUtils.LAST_FM_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClient())
        .build()
    private val apiServiceHome: ApiService = RetroClient.getApiService()

    private val homeContentRepository: HomeContentRepository = HomeContentRepository(apiServiceHome)
    private val apiServiceArtistBio: ApiService = artistBioRetrofit.create(ApiService::class.java)
    private val artistContentRepository: ArtistContentRepository =
        ArtistContentRepository(apiServiceArtistBio)

    val homeViewModelFactory: HomeViewModelFactory
        get() = HomeViewModelFactory(homeContentRepository)

    val artistViewModelFactory: ArtistViewModelFactory
        get() = ArtistViewModelFactory(artistContentRepository)

    private val artistBannerApiService:ApiService = artistBannerRetrofit.create(ApiService::class.java)
    private val artistSongApiService:ApiService = artistBannerRetrofit.create(ApiService::class.java)
    private val apiServiceAlbum: ApiService = RetroClient.getApiShadhinMusicService()
    private val artistBannerContentRepository:ArtistBannerContentRepository= ArtistBannerContentRepository(artistBannerApiService)
    private val artistSongContentRepository:ArtistSongContentRepository = ArtistSongContentRepository(
        artistSongApiService)
    private val albumContentRepository: AlbumContentRepository =
        AlbumContentRepository(apiServiceAlbum)
    val albumViewModelFactory: AlbumViewModelFactory
        get() = AlbumViewModelFactory(albumContentRepository)

     val artistBannerViewModelFactory:ArtistBannerViewModelFactory
     get() = ArtistBannerViewModelFactory(artistBannerContentRepository)

    val artistSongViewModelFactory:ArtistContentViewModelFactory
        get() = ArtistContentViewModelFactory(artistSongContentRepository)


    private val artistBannerApiService: ApiService = RetroClient.getAPIArtistBannerService()
    private val apiServiceAlbum: ApiService = RetroClient.getApiShadhinMusicService()
    private val artistBannerContentRepository: ArtistContentRepository = ArtistContentRepository(
        artistBannerApiService
    )

    private val albumContentRepository: AlbumContentRepository =
        AlbumContentRepository(apiServiceAlbum)
    val albumViewModelFactory: AlbumViewModelFactory
        get() = AlbumViewModelFactory(albumContentRepository)

}
