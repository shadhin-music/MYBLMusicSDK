package com.shadhinmusiclibrary.di

import android.content.Context

import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.data.repository.*
import com.shadhinmusiclibrary.di.single.*
import com.shadhinmusiclibrary.fragments.album.AlbumViewModelFactory
import com.shadhinmusiclibrary.fragments.artist.*
import com.shadhinmusiclibrary.fragments.home.HomeViewModelFactory

import com.shadhinmusiclibrary.fragments.podcast.PodcastViewModelFactory
import com.shadhinmusiclibrary.rest.RetroClient
import com.shadhinmusiclibrary.utils.AppConstantUtils
import okhttp3.OkHttpClient

import retrofit2.Retrofit

class Module(private val applicationContext: Context) {

    private fun getRetrofitInstance(): Retrofit {
        return  RetrofitClient.getInstance()
    }

    private fun getHomeApiService(): ApiService {
        return SingleApiService.getInstance(getRetrofitInstance())
    }

    private fun getRetrofitAPIShadhinMusicInstance(): Retrofit {
        return ShadhinRetrofitClient.getInstance()
    }

    private fun getApiShadhinMusicService(): ApiService {
        return  ShadhinMusicAPIService.getInstance(getRetrofitAPIShadhinMusicInstance())
    }

    private fun getClient(): OkHttpClient {
        return SingleOkHttpClient.getInstance()
    }

    private fun getFMClient(): Retrofit {
        return RetrofitFMClient.getInstance(getClient())
    }

    private fun getFMService(): ApiService {
        return SingleFMService.getInstance(getFMClient())
    }

    private val artistAlbumApiService:ApiService = getApiShadhinMusicService()
    
    private val repositoryArtistContent: ArtistContentRepository =
        ArtistContentRepository(getFMService())

    private val repositoryHomeContent: HomeContentRepository =
        HomeContentRepository(getHomeApiService())

    private val repositoryArtistBannerContent: ArtistBannerContentRepository =
        ArtistBannerContentRepository(getApiShadhinMusicService())

    private val repositoryArtistSongContent: ArtistSongContentRepository =
        ArtistSongContentRepository(getApiShadhinMusicService())

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

    private val podcastRepository: PodcastRepository get() = PodcastRepository(
        getApiShadhinMusicService())
    val podcastViewModelFactory:PodcastViewModelFactory
        get() = PodcastViewModelFactory(podcastRepository)



    /* val exoplayerCache: SimpleCache
         get() = PlayerCache.getInstance(applicationContext)

     private val playerApiService:PlayerApiService
         get() = SinglePlayerApiService.getInstance(getRetrofitInstance())

     val musicRepository:MusicRepository = ShadhinMusicRepository(playerApiService)

     private val musicServiceConnection: ShadhinMusicServiceConnection
         get() = ShadhinMusicServiceConnection(applicationContext)

     val playerViewModelFactory:PlayerViewModelFactory
         get() = PlayerViewModelFactory(musicServiceConnection)*/


}




