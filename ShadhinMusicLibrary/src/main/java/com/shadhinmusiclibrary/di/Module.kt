package com.shadhinmusiclibrary.di

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.data.repository.*
import com.shadhinmusiclibrary.fragments.album.AlbumViewModelFactory
import com.shadhinmusiclibrary.fragments.artist.*
import com.shadhinmusiclibrary.fragments.home.HomeViewModelFactory
import com.shadhinmusiclibrary.fragments.podcast.PodcastViewModelFactory
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

//    private fun getHomeApiService(): ApiService {
//        return getRetrofitInstance().create(ApiService::class.java)
//    }

    private fun getRetrofitAPIShadhinMusicInstanceV5(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstantUtils.BASE_URL_API_shadhinmusic)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getBaseClient())
            .build()
    }
    private fun getBaseClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
               HeaderInterceptor()
            )
            .build()
    }
    private fun getApiShadhinMusicServiceV5(): ApiService {
        return getRetrofitAPIShadhinMusicInstanceV5().create(ApiService::class.java)
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
    private val artistAlbumApiService:ApiService = getApiShadhinMusicServiceV5()
    private val podcastApiService:ApiService = getApiShadhinMusicServiceV5()
    private val repositoryArtistContent: ArtistContentRepository =
        ArtistContentRepository(getFMService())
    private val repositoryHomeContent: HomeContentRepository =
        HomeContentRepository(getApiShadhinMusicServiceV5())
    private val repositoryArtistBannerContent: ArtistBannerContentRepository =
        ArtistBannerContentRepository(getApiShadhinMusicServiceV5())
    private val repositoryArtistSongContent: ArtistSongContentRepository =
        ArtistSongContentRepository(
            getApiShadhinMusicServiceV5()
        )
    private val repositoryAlbumContent: AlbumContentRepository =
        AlbumContentRepository(getApiShadhinMusicServiceV5())

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

    private val podcastRepository: PodcastRepository get() = PodcastRepository(
        podcastApiService)
    val podcastViewModelFactory:PodcastViewModelFactory
        get() = PodcastViewModelFactory(podcastRepository)
}



