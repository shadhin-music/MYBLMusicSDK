package com.shadhinmusiclibrary.di

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.data.repository.*
import com.shadhinmusiclibrary.fragments.album.AlbumViewModelFactory
import com.shadhinmusiclibrary.fragments.artist.*
import com.shadhinmusiclibrary.fragments.home.HomeViewModelFactory
import com.shadhinmusiclibrary.rest.RetroClient
import com.shadhinmusiclibrary.utils.AppConstantUtils
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Module {
    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstantUtils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getHomeApiService(): ApiService {
        return getRetrofitInstance().create(ApiService::class.java)
    }

    private fun getRetrofitAPIShadhinMusicInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstantUtils.BASE_URL_API_shadhinmusic)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getApiShadhinMusicService(): ApiService {
        return getRetrofitAPIShadhinMusicInstance().create(ApiService::class.java)
    }

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                LastFmApiKeyInterceptor()
            )
            .build()
    }

    private fun getFMClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstantUtils.LAST_FM_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
    }

    private fun getFMService(): ApiService {
        return getFMClient().create(ApiService::class.java)
    }
    private val artistAlbumApiService:ApiService = getApiShadhinMusicService()

    private val repositoryArtistContent: ArtistContentRepository =
        ArtistContentRepository(getFMService())
    private val repositoryHomeContent: HomeContentRepository =
        HomeContentRepository(getHomeApiService())
    private val repositoryArtistBannerContent: ArtistBannerContentRepository =
        ArtistBannerContentRepository(getApiShadhinMusicService())
    private val repositoryArtistSongContent: ArtistSongContentRepository =
        ArtistSongContentRepository(
            getApiShadhinMusicService()
        )
    private val repositoryAlbumContent: AlbumContentRepository =
        AlbumContentRepository(getApiShadhinMusicService())

    val factoryHomeVM: HomeViewModelFactory
        get() = HomeViewModelFactory(repositoryHomeContent)

    val factoryArtistVM: ArtistViewModelFactory
        get() = ArtistViewModelFactory(repositoryArtistContent)

    val factoryAlbumVM: AlbumViewModelFactory
        get() = AlbumViewModelFactory(repositoryAlbumContent)

    val factoryArtistBannerVM: ArtistBannerViewModelFactory
        get() = ArtistBannerViewModelFactory(repositoryArtistBannerContent)

    val factoryArtistSongVM: ArtistContentViewModelFactory
        get() = ArtistContentViewModelFactory(repositoryArtistSongContent)
    private val artistAlbumContentRepository: ArtistAlbumContentRepository get() = ArtistAlbumContentRepository(
        artistAlbumApiService)

    val artistAlbumViewModelFactory: ArtistAlbumViewModelFactory
        get() = ArtistAlbumViewModelFactory(
        artistAlbumContentRepository)
}



