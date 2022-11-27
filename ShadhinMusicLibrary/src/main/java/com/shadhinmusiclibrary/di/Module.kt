package com.shadhinmusiclibrary.di

import android.content.Context
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.data.repository.*
import com.shadhinmusiclibrary.di.single.*
import com.shadhinmusiclibrary.fragments.album.AlbumViewModelFactory
import com.shadhinmusiclibrary.fragments.amar_tunes.AmarTunesViewModelFactory
import com.shadhinmusiclibrary.fragments.artist.*
import com.shadhinmusiclibrary.fragments.create_playlist.CreatePlaylistViewModelFactory
import com.shadhinmusiclibrary.fragments.fav.FavViewModelFactory
import com.shadhinmusiclibrary.fragments.home.HomeViewModelFactory
import com.shadhinmusiclibrary.fragments.podcast.FeaturedPodcastViewModelFactory
import com.shadhinmusiclibrary.fragments.podcast.PodcastViewModelFactory
import com.shadhinmusiclibrary.fragments.search.SearchViewModelFactory
import com.shadhinmusiclibrary.library.player.connection.MusicServiceController
import com.shadhinmusiclibrary.library.player.data.rest.MusicRepository
import com.shadhinmusiclibrary.library.player.data.rest.PlayerApiService
import com.shadhinmusiclibrary.library.player.data.rest.ShadhinMusicRepository
import com.shadhinmusiclibrary.library.player.data.rest.user_history.UserHistoryRepository
import com.shadhinmusiclibrary.library.player.data.rest.user_history.UserHistoryRepositoryImpl
import com.shadhinmusiclibrary.library.player.singleton.PlayerCache
import com.shadhinmusiclibrary.library.player.ui.PlayerViewModelFactory
import com.shadhinmusiclibrary.utils.AppConstantUtils
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class Module(private val applicationContext: Context) {

    fun getBaseOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HeaderInterceptor()
            ).build()
    }

    private fun getBaseClientWITHtOKEN(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                BearerTokenHeaderInterceptor()
            ).build()
    }

    private fun getBaseClientWithTokenAndClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                BearerTokenWithClientHeaderInterceptor()
            ).build()
    }

    private fun getOkHttpClientWithFMInterceptor(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                LastFmApiKeyInterceptor()
            ).build()
    }

    private fun getRetrofitFMAPIInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstantUtils.LAST_FM_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClientWithFMInterceptor())
            .build()
    }

    private fun getRetrofitAPIShadhinMusicInstanceV5(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstantUtils.BASE_URL_API_shadhinmusic)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getBaseOkHttpClient())
            .build()
    }

    private fun getRetrofitAPIShadhinMusicInstanceV5WithBearerToken(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstantUtils.BASE_URL_API_shadhinmusic)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getBaseClientWITHtOKEN())
            .build()
    }

    private fun getRetrofitAPIShadhinMusicInstanceV5WithBearerTokenAndClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstantUtils.BASE_URL_API_shadhinmusic)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getBaseClientWithTokenAndClient())
            .build()
    }

    private fun getFMService(): ApiService {
        return getRetrofitFMAPIInstance().create(ApiService::class.java)
    }

    private fun getApiShadhinMusicServiceV5(): ApiService {
        return getRetrofitAPIShadhinMusicInstanceV5().create(ApiService::class.java)
    }

    private fun getApiShadhinMusicServiceV5withToken(): ApiService {
        return getRetrofitAPIShadhinMusicInstanceV5WithBearerToken().create(ApiService::class.java)
    }

    private fun getApiShadhinMusicServiceV5withTokenAndClient(): ApiService {
        return getRetrofitAPIShadhinMusicInstanceV5WithBearerTokenAndClient().create(ApiService::class.java)
    }

    private val repositoryArtistContent: ArtistContentRepository =
        ArtistContentRepository(getFMService())

    fun authRepository() = AuthRepository(getApiShadhinMusicServiceV5())

    private val artistAlbumApiService: ApiService = getApiShadhinMusicServiceV5()
    private val podcastApiService: ApiService = getApiShadhinMusicServiceV5()
    private val repositoryHomeContent: HomeContentRepository =
        HomeContentRepository(getApiShadhinMusicServiceV5())
    private val repositoryArtistBannerContent: ArtistBannerContentRepository =
        ArtistBannerContentRepository(getApiShadhinMusicServiceV5())
    private val repositoryArtistSongContent: ArtistSongContentRepository =
        ArtistSongContentRepository(getApiShadhinMusicServiceV5())

    private val repositoryHomeContentRBT: AmartunesContentRepository =
        AmartunesContentRepository(getApiShadhinMusicServiceV5withToken())
    private val repositoryAlbumContent: AlbumContentRepository =
        AlbumContentRepository(getApiShadhinMusicServiceV5withToken())

    private val repositoryCreatePlaylist: CreatePlaylistRepository =
        CreatePlaylistRepository(getApiShadhinMusicServiceV5withTokenAndClient())
    private val repositoryFavContentRepository: FavContentRepository =
        FavContentRepository(getApiShadhinMusicServiceV5withTokenAndClient())

    val factoryHomeVM: HomeViewModelFactory
        get() = HomeViewModelFactory(repositoryHomeContent)

    val factoryAmarTuneVM: AmarTunesViewModelFactory
        get() = AmarTunesViewModelFactory(repositoryHomeContentRBT)

    val factoryArtistVM: ArtistViewModelFactory
        get() = ArtistViewModelFactory(repositoryArtistContent)

    val factoryAlbumVM: AlbumViewModelFactory
        get() = AlbumViewModelFactory(repositoryAlbumContent)

    val factoryArtistBannerVM: ArtistBannerViewModelFactory
        get() = ArtistBannerViewModelFactory(repositoryArtistBannerContent)

    val factoryCreatePlaylistVM: CreatePlaylistViewModelFactory
        get() = CreatePlaylistViewModelFactory(repositoryCreatePlaylist)

    val factoryFavContentVM: FavViewModelFactory
        get() = FavViewModelFactory(repositoryFavContentRepository)

    val factoryArtistSongVM: ArtistContentViewModelFactory
        get() = ArtistContentViewModelFactory(repositoryArtistSongContent)

    private val artistAlbumContentRepository: ArtistAlbumContentRepository
        get() = ArtistAlbumContentRepository(
            artistAlbumApiService
        )

    val artistAlbumViewModelFactory: ArtistAlbumViewModelFactory
        get() = ArtistAlbumViewModelFactory(
            artistAlbumContentRepository
        )

    private val featuredpodcastRepository: FeaturedPodcastRepository
        get() = FeaturedPodcastRepository(
            getApiShadhinMusicServiceV5()
        )
    val featuredpodcastViewModelFactory: FeaturedPodcastViewModelFactory
        get() = FeaturedPodcastViewModelFactory(featuredpodcastRepository)

    private val podcastRepository: PodcastRepository
        get() = PodcastRepository(
            podcastApiService
        )
    val podcastViewModelFactory: PodcastViewModelFactory
        get() = PodcastViewModelFactory(podcastRepository)

    val popularArtistRepository: PopularArtistRepository
        get() = PopularArtistRepository(
            artistAlbumApiService
        )
    val popularArtistViewModelFactory: PopularArtistViewModelFactory
        get() = PopularArtistViewModelFactory(
            popularArtistRepository
        )

    val featuredtrackListRepository: FeaturedTracklistRepository
        get() = FeaturedTracklistRepository(
            artistAlbumApiService
        )
    val featuredtrackListViewModelFactory: FeaturedTracklistViewModelFactory
        get() = FeaturedTracklistViewModelFactory(
            featuredtrackListRepository
        )

    val searchRepository: SearchRepository
        get() = SearchRepository(
            artistAlbumApiService
        )
    val searchViewModelFactory: SearchViewModelFactory
        get() = SearchViewModelFactory(
            searchRepository
        )

    val exoplayerCache: SimpleCache
        get() = PlayerCache.getInstance(applicationContext)

    private fun getRetrofitInstance(): Retrofit {
//        MehenazBranch
//        val client = getBaseClient()
        return RetrofitClient.getInstance(getBaseClientWITHtOKEN())
//        return RetrofitClient.getInstance()
    }

    private val playerApiService: PlayerApiService
        get() = SinglePlayerApiService.getInstance(getRetrofitInstance())

    val musicRepository: MusicRepository = ShadhinMusicRepository(playerApiService)

    val musicServiceController: MusicServiceController
        get() = SingleMusicServiceConnection.getInstance(applicationContext)//ShadhinMusicServiceConnection(applicationContext)

    val userHistoryRepository: UserHistoryRepository
        get() = UserHistoryRepositoryImpl(playerApiService)

    val downloadTitleMap: MutableMap<String, String>
        get() = SingleDownloadMap.getInstance()

    val playerViewModelFactory: PlayerViewModelFactory
        get() = PlayerViewModelFactory(musicServiceController)
}