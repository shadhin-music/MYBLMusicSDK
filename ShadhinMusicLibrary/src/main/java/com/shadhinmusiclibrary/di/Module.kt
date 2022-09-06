package com.shadhinmusiclibrary.di

import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.data.repository.*
import com.shadhinmusiclibrary.fragments.album.AlbumViewModelFactory
import com.shadhinmusiclibrary.fragments.artist.ArtistBannerViewModelFactory
import com.shadhinmusiclibrary.fragments.artist.ArtistContentViewModelFactory
import com.shadhinmusiclibrary.fragments.artist.ArtistViewModelFactory
import com.shadhinmusiclibrary.fragments.artist.LastFmApiKeyInterceptor
import com.shadhinmusiclibrary.fragments.home.HomeViewModelFactory
import com.shadhinmusiclibrary.utils.AppConstantUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    //    private val apiServiceHome: ApiService = RetroClient.getHomeApiService()
//    private val apiServiceArtistBio: ApiService = artistBioRetrofit.create(ApiService::class.java)
//    private val artistBannerApiService: ApiService =
//        artistBannerRetrofit.create(ApiService::class.java)
//    private val artistSongApiService: ApiService =
//        artistBannerRetrofit.create(ApiService::class.java)
//    private val apiServiceAlbum: ApiService = getApiShadhinMusicService()

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

//    private val artistBannerApiService: ApiService = RetroClient.getAPIArtistBannerService()
//    private val apiServiceAlbum: ApiService = RetroClient.getApiShadhinMusicService()
//    private val artistBannerContentRepository: ArtistContentRepository = ArtistContentRepository(
//        artistBannerApiService
//    )
//    private val albumContentRepository: AlbumContentRepository =
//        AlbumContentRepository(apiServiceAlbum)
//    val albumViewModelFactory: AlbumViewModelFactory
//        get() = AlbumViewModelFactory(albumContentRepository)

}
