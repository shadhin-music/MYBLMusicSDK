package com.shadhinmusiclibrary.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.NavigationRes
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.blue
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.exoplayer2.offline.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.CreatePlaylistListAdapter
import com.shadhinmusiclibrary.adapter.MusicPlayAdapter
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.HomePatchItemModel
import com.shadhinmusiclibrary.data.model.fav.FavDataModel
import com.shadhinmusiclibrary.download.MyBLDownloadService
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.fragments.create_playlist.CreateplaylistViewModel
import com.shadhinmusiclibrary.fragments.fav.FavViewModel
import com.shadhinmusiclibrary.library.discretescrollview.DSVOrientation
import com.shadhinmusiclibrary.library.discretescrollview.DiscreteScrollView
import com.shadhinmusiclibrary.library.discretescrollview.transform.ScaleTransformer
import com.shadhinmusiclibrary.library.player.Constants
import com.shadhinmusiclibrary.library.player.data.model.MusicPlayList
import com.shadhinmusiclibrary.library.player.ui.PlayerViewModel
import com.shadhinmusiclibrary.library.player.utils.CacheRepository
import com.shadhinmusiclibrary.library.player.utils.isPlaying
import com.shadhinmusiclibrary.library.slidinguppanel.SlidingUpPanelLayout
import com.shadhinmusiclibrary.utils.*
import com.shadhinmusiclibrary.utils.AppConstantUtils.PatchItem
import com.shadhinmusiclibrary.utils.AppConstantUtils.PlaylistId
import com.shadhinmusiclibrary.utils.AppConstantUtils.PlaylistName
import java.io.Serializable
import java.util.*

internal class SDKMainActivity : BaseActivity(),
    ItemClickListener {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private var isPlayOrPause = false
    private lateinit var slCustomBottomSheet: SlidingUpPanelLayout

    //mini music player
    private lateinit var llMiniMusicPlayer: CardView
    private lateinit var ivSongThumbMini: ImageView
    private lateinit var tvSongNameMini: TextView
    private lateinit var tvSingerNameMini: TextView
    private lateinit var tvTotalDurationMini: TextView
    private lateinit var ibtnSkipPreviousMini: ImageButton
    private lateinit var ibtnPlayPauseMini: ImageButton
    private lateinit var ibtnSkipNextMini: ImageButton

    //Main or Big Music Player
    private lateinit var rlContentMain: RelativeLayout

    //Slid/Custom Bottom sheet
    private lateinit var clMainMusicPlayer: ConstraintLayout
    private lateinit var acivMinimizePlayerBtn: AppCompatImageView
    private lateinit var tvTitle: TextView
    private lateinit var acivMenu: AppCompatImageView
    private lateinit var dsvCurrentPlaySongsThumb: DiscreteScrollView
    private lateinit var tvSongName: TextView
    private lateinit var tvSingerName: TextView
    private lateinit var ivFavoriteBtn: ImageView
    private lateinit var sbCurrentPlaySongStatus: SeekBar
    private lateinit var tvCurrentPlayDuration: TextView
    private lateinit var tvTotalPlayDuration: TextView
    private lateinit var ibtnShuffle: ImageButton
    private lateinit var ibtnSkipPrevious: ImageButton
    private lateinit var ibtnPlayPause: ImageButton
    private lateinit var ibtnSkipNext: ImageButton
    private lateinit var ibtnRepeatSong: ImageButton
    private lateinit var ibtnVolume: ImageButton
    private lateinit var ibtnLibraryAdd: ImageButton
    private lateinit var ibtnQueueMusic: ImageButton
    private lateinit var ibtnDownload: ImageButton

    private lateinit var playerViewModel: PlayerViewModel
    private lateinit var viewModel: CreateplaylistViewModel
    private lateinit var favViewModel: FavViewModel
    private lateinit var mainMusicPlayerAdapter: MusicPlayAdapter
    private lateinit var listData: MutableList<HomePatchDetailModel>

    private fun uiInitMiniMusicPlayer() {
        llMiniMusicPlayer = findViewById(R.id.include_mini_music_player)
        llMiniMusicPlayer.isEnabled = true
        ivSongThumbMini = findViewById(R.id.iv_song_thumb_mini)
        tvSongNameMini = findViewById(R.id.tv_song_name_mini)
        tvSingerNameMini = findViewById(R.id.tv_singer_name_mini)
        tvTotalDurationMini = findViewById(R.id.tv_total_duration_mini)
        ibtnSkipPreviousMini = findViewById(R.id.ibtn_skip_previous_mini)
        ibtnPlayPauseMini = findViewById(R.id.ibtn_play_pause_mini)
        ibtnSkipNextMini = findViewById(R.id.ibtn_skip_next_mini)
    }

    private fun uiInitMainMusicPlayer() {
        clMainMusicPlayer = findViewById(R.id.include_main_music_player)
        acivMinimizePlayerBtn = findViewById(R.id.aciv_minimize_player_btn)
        tvTitle = findViewById(R.id.tv_title)
        acivMenu = findViewById(R.id.aciv_menu)
        dsvCurrentPlaySongsThumb = findViewById(R.id.dsv_current_play_songs_thumb)
        tvSongName = findViewById(R.id.tv_song_name)
        tvSingerName = findViewById(R.id.tv_singer_name)
        ivFavoriteBtn = findViewById(R.id.iv_favorite_btn)
        sbCurrentPlaySongStatus = findViewById(R.id.sb_current_play_song_status)
        tvCurrentPlayDuration = findViewById(R.id.tv_current_play_duration)
        tvTotalPlayDuration = findViewById(R.id.tv_total_play_duration)
        ibtnShuffle = findViewById(R.id.ibtn_shuffle)
        ibtnSkipPrevious = findViewById(R.id.ibtn_skip_previous)
        ibtnPlayPause = findViewById(R.id.ibtn_play_pause)
        ibtnSkipNext = findViewById(R.id.ibtn_skip_next)
        ibtnRepeatSong = findViewById(R.id.ibtn_repeat_song)
        ibtnVolume = findViewById(R.id.ibtn_volume)
        ibtnLibraryAdd = findViewById(R.id.ibtn_library_add)
        ibtnQueueMusic = findViewById(R.id.ibtn_queue_music)
        ibtnDownload = findViewById(R.id.ibtn_download)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_bl_sdk_activity_sdk_main)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_navigation_host) as NavHostFragment
        navController = navHostFragment.navController
        slCustomBottomSheet = findViewById(R.id.sl_custom_bottom_sheet)
        slCustomBottomSheet.isEnabled = true
        rlContentMain = findViewById(R.id.rl_content_main)
        //DownloadProgressObserver.setCacheRepository(cacheRepository)
        createPlayerVM()

        uiInitMiniMusicPlayer()
        uiInitMainMusicPlayer()
        mainMusicPlayerAdapter = MusicPlayAdapter(this)

        //Will received request from Any page from MYBLL app
        val uiRequest = intent.extras!!.get(AppConstantUtils.UI_Request_Type)
        if (uiRequest == AppConstantUtils.Requester_Name_Home) {
            homeFragmentAccess()
        }
        //  homeFragmentAccess()
        if (uiRequest == AppConstantUtils.Requester_Name_API) {
            patchFragmentAccess()
        }
        if (uiRequest == AppConstantUtils.Requester_Name_Search) {
            searchFragmentAccess()
        }
        if (uiRequest == AppConstantUtils.Requester_Name_Download) {
            downloadFragmentAccess()
        }
        if (uiRequest == AppConstantUtils.Requester_Name_Watchlater) {
            watchLaterFragmentAccess()
        }
        if (uiRequest == AppConstantUtils.Requester_Name_MyPlaylist) {
            myPlaylistFragmentAccess()
        }
        if (uiRequest == AppConstantUtils.Requester_Name_MyFavorite) {
            myFavoriteFragmentAccess()
        }
        if (uiRequest == AppConstantUtils.Requester_Name_CreatePlaylist) {
            createPlaylistFragmentAccess()
        }
        if (uiRequest == AppConstantUtils.Requester_Name_CreatedPlaylistDetails) {
            createdPlaylistDetailsFragmentAccess()
        }
        //routeDataArtistType()
        /* playerViewModel.currentMusicLiveData.observe(this) { itMus ->
             if (itMus != null) {
                 setupMiniMusicPlayerAndFunctionality(UtilHelper.getSongDetailToMusic(itMus))
                 isPlayOrPause = itMus.isPlaying!!
             }
             Log.e("SDKMA", "cMLD: ")
             *//*else {
                playerViewModel.reAssignCurrentMusic()
            }*//*
        }*/

//        playerViewModel.playListLiveData.observe(this) { itMusicList ->
//            playerViewModel.musicIndexLiveData.observe(this) { itCurrentPlayIndex ->
//                try {
//                    if (itMusicList.list[itCurrentPlayIndex].seekable!!) {
//                        setupMainMusicPlayerAdapter(
//                            UtilHelper.getSongDetailToMusicList(itMusicList.list.toMutableList()),
//                            itCurrentPlayIndex
//                        )
//                        slCustomBShOnMaximized(true)
//                    } else {
//                        miniMusicPlayerHideShow(playerViewModel.isPlaying)
//                        slCustomBShOnMaximized(false)
//                    }
//                } catch (exception: Exception) {
//                }
//            }
//        }

        //miniMusicPlayerHideShow(playerViewModel.isMediaDataAvailable())

        llMiniMusicPlayer.setOnClickListener {
            //Mini player show. when mini player click
            toggleMiniPlayerView(false)
        }

        //DO NOT Call this function multiple times
        playerViewModel.startObservePlayerProgress(this)
        //  routeDataArtistType()
    }


    val cacheRepository by lazy {
        CacheRepository(this)
    }

    private fun searchFragmentAccess() {
        /*      val patch = intent.extras!!.getBundle(PatchItem)!!
            .getSerializable(PatchItem) as HomePatchItem

*/
        setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_search,
            Bundle().apply {})

    }

    private fun downloadFragmentAccess() {
        val patch = intent.extras!!.getBundle(PatchItem)!!
            .getSerializable(PatchItem) as HomePatchItemModel

        setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_download,
            Bundle().apply {
                putSerializable(
                    PatchItem,
                    patch as Serializable
                )
            })
    }

    private fun watchLaterFragmentAccess() {
        val patch = intent.extras!!.getBundle(PatchItem)!!
            .getSerializable(PatchItem) as HomePatchItemModel

        setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_watch_later,
            Bundle().apply {
                putSerializable(
                    PatchItem,
                    patch as Serializable
                )
            })
    }

    private fun homeFragmentAccess() {
        //Will received data from Home Fragment from MYBLL App
        val patch = intent.extras!!.getBundle(PatchItem)!!
            .getSerializable(PatchItem) as HomePatchItemModel
        var selectedPatchIndex: Int? = null
        if (intent.hasExtra(AppConstantUtils.SelectedPatchIndex)) {
            selectedPatchIndex = intent.extras!!.getInt(AppConstantUtils.SelectedPatchIndex)
        }

        routeDataHomeFragment(patch, selectedPatchIndex)
    }

    private fun myPlaylistFragmentAccess() {
        val patch = intent.extras!!.getBundle(PatchItem)!!
            .getSerializable(PatchItem) as HomePatchItemModel

        setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_my_playlist,
            Bundle().apply {
                putSerializable(
                    PatchItem,
                    patch as Serializable
                )
            })
    }

    private fun myFavoriteFragmentAccess() {
        val patch = intent.extras!!.getBundle(PatchItem)!!
            .getSerializable(PatchItem) as HomePatchItemModel

        setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_my_favorite,
            Bundle().apply {
                putSerializable(
                    PatchItem,
                    patch as Serializable
                )
            })
    }

    private fun createPlaylistFragmentAccess() {
        val patch = intent.extras!!.getBundle(PatchItem)!!
            .getSerializable(PatchItem) as HomePatchItemModel

        setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_create_playlist,
            Bundle().apply {
                putSerializable(
                    PatchItem,
                    patch as Serializable
                )
            })
    }

    //    private fun FavArtistDetailsFragmentAccess() {
//        val patch = intent.extras!!.getBundle(PatchItem)!!
//            .getSerializable(PatchItem) as HomePatchItem
//        val id = intent.extras!!.getBundle(PlaylistId)!!.getSerializable(PlaylistId) as String
//        val name = intent.extras!!.getBundle(PlaylistName)!!.getSerializable(PlaylistName) as String
//        setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_user_playlist_details,
//            Bundle().apply {
//                putSerializable(
//                    PatchItem,
//                    patch as Serializable
//                )
//                putSerializable(PlaylistId,id)
//                putSerializable(PlaylistName,name)
//            })
//    }
    private fun createdPlaylistDetailsFragmentAccess() {
        val patch = intent.extras!!.getBundle(PatchItem)!!
            .getSerializable(PatchItem) as HomePatchItemModel
        val id = intent.extras!!.getBundle(PlaylistId)!!.getSerializable(PlaylistId) as String
        val name = intent.extras!!.getBundle(PlaylistName)!!.getSerializable(PlaylistName) as String
        setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_user_playlist_details,
            Bundle().apply {
                putSerializable(
                    PatchItem,
                    patch as Serializable
                )
                putSerializable(PlaylistId, id)
                putSerializable(PlaylistName, name)
                putSerializable(
                    AppConstantUtils.PlaylistGradientId,
                    intent.extras!!.getInt(AppConstantUtils.PlaylistGradientId)
                )
            })
    }

    private fun patchFragmentAccess() {
        val dataContentType =
            intent.extras?.getString(AppConstantUtils.DataContentRequestId) as String

        routeDataPatch(dataContentType)
    }

    private fun routeDataPatch(contentType: String) {
        when (contentType.toUpperCase(Locale.ENGLISH)) {
            DataContentType.CONTENT_TYPE_R_RC201 -> {
                setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_patch_type_r, Bundle().apply {
                    putString(DataContentType.TITLE, "Latest Release")
                })
            }
            DataContentType.CONTENT_TYPE_PD_RC202 -> {
                setupNavGraphAndArg(
                    R.navigation.my_bl_sdk_nav_graph_patch_type_featured_podcast,
                    Bundle().apply {
                        putString(DataContentType.TITLE, "Featured Podcast")
                    })
            }
            DataContentType.CONTENT_TYPE_A_RC203 -> {
                setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_patch_type_a, Bundle().apply {
                    putString(DataContentType.TITLE, "Popular Artists")
                })
            }
            DataContentType.AMR_TUNE_ALL -> {
                setupNavGraphAndArg(
                    R.navigation.my_bl_sdk_nav_graph_patch_type_amar_tune,
                    Bundle().apply {
                        putString(DataContentType.CONTENT_TYPE, contentType)
                    })
            }
            DataContentType.AMR_TUNE -> {
                setupNavGraphAndArg(
                    R.navigation.my_bl_sdk_nav_graph_patch_type_amar_tune,
                    Bundle().apply {
                        putString(DataContentType.CONTENT_TYPE, contentType)
                    })
            }
            DataContentType.CONTENT_TYPE_V_RC204 -> {
                setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_music_video,
                    Bundle().apply {
                        putString(DataContentType.TITLE, "Music Video")
                    })
            }
            DataContentType.CONTENT_TYPE_RADIO -> {
                setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_radio,
                    Bundle().apply {
                        putString(DataContentType.TITLE, "Radio")
                    })
            }
        }
    }

    private fun routeDataHomeFragment(homePatchItem: HomePatchItemModel, selectedIndex: Int?) {
        if (selectedIndex != null && homePatchItem.Data.size > selectedIndex) {
            //Single Item Click event
            val homePatchDetail = homePatchItem.Data[selectedIndex]

            val podcast: String = homePatchDetail.content_Type ?: ""
            val podcastType = podcast.take(2)
            val contentType = podcast.takeLast(2)
            if (homePatchDetail.content_Type?.contains("PD") == true) {
                //onPodcastClick(homePatchDetail,homePatchDetail)
                setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_podcast_details,
                    Bundle().apply {
                        putSerializable(
                            PatchItem,
                            HomePatchItemModel(
                                homePatchItem.Code,
                                "PDBC",
                                homePatchItem.Data,
                                homePatchItem.Design,
                                homePatchItem.Name,
                                homePatchItem.Sort,
                                homePatchItem.Total
                            ) as Serializable
                        )
                        putSerializable(
                            AppConstantUtils.PatchDetail,
                            homePatchDetail as Serializable
//                            HomePatchDetail(homePatchDetail.AlbumId,
//                                homePatchDetail.AlbumImage,
//                                homePatchDetail.AlbumName,
//                                homePatchDetail.Artist,
//                                homePatchDetail.ArtistId,
//                                homePatchDetail.ArtistImage,
//                                homePatchDetail.Banner,
//                                homePatchDetail.ContentID,
//                                homePatchDetail.ContentType,
//                                homePatchDetail.CreateDate,
//                                homePatchDetail.Duration,
//                                homePatchDetail.Follower,
//                                homePatchDetail.IsPaid,
//                                homePatchDetail.NewBanner,
//                                homePatchDetail.PlayCount,
//                                homePatchDetail.PlayListId,
//                                homePatchDetail.PlayListId,
//                                homePatchDetail.PlayListImage,
//                                homePatchDetail.PlayUrl,
//                                homePatchDetail.RootId,
//                                homePatchDetail.RootType,
//                                homePatchDetail.Seekable,
//                                homePatchDetail.TeaserUrl,
//                                homePatchDetail.TrackType,
//                                homePatchDetail.Type,
//                                homePatchDetail.fav,
//                                homePatchDetail.image,
//                                homePatchDetail.imageWeb,
//                                homePatchDetail.title) as Serializable
                            // homePatchDetail as Serializable
                        )
                    })
            }
            //  Log.e("TAG","CHECKING: "+ podcast)
//            if (homePatchDetail.content_Type?.toUpperCase()!!.contains("PD")) {
//                    Log.e("TAG","DATA: "+ homePatchDetail.content_Type)
//
//                      //onPodcastClick(homePatchDetail,homePatchDetail)
//                setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_podcast_details,
//                    Bundle().apply {
//                        putSerializable(
//                            PatchItem,
//                            HomePatchItemModel(
//                                homePatchItem.Code,
//                                homePatchItem.ContentType,
//                                homePatchItem.Data,
//                                homePatchItem.Design,
//                                homePatchItem.Name,
//                                homePatchItem.Sort,
//                                homePatchItem.Total
//                            ) as Serializable
//                        )
//                        putSerializable(
//                            AppConstantUtils.PatchDetail,
////                            HomePatchDetail(homePatchDetail.AlbumId,
////                                homePatchDetail.AlbumImage,
////                                homePatchDetail.AlbumName,
////                                homePatchDetail.Artist,
////                                homePatchDetail.ArtistId,
////                                homePatchDetail.ArtistImage,
////                                homePatchDetail.Banner,
////                                homePatchDetail.ContentID,
////                                homePatchDetail.ContentType,
////                                homePatchDetail.CreateDate,
////                                homePatchDetail.Duration,
////                                homePatchDetail.Follower,
////                                homePatchDetail.IsPaid,
////                                homePatchDetail.NewBanner,
////                                homePatchDetail.PlayCount,
////                                homePatchDetail.PlayListId,
////                                homePatchDetail.PlayListId,
////                                homePatchDetail.PlayListImage,
////                                homePatchDetail.PlayUrl,
////                                homePatchDetail.RootId,
////                                homePatchDetail.RootType,
////                                homePatchDetail.Seekable,
////                                homePatchDetail.TeaserUrl,
////                                homePatchDetail.TrackType,
////                                homePatchDetail.Type,
////                                homePatchDetail.fav,
////                                homePatchDetail.image,
////                                homePatchDetail.imageWeb,
////                                homePatchDetail.title) as Serializable
//                            homePatchDetail as Serializable
//                        )
//                    })
//            }

            when (homePatchDetail.content_Type?.toUpperCase()) {
                DataContentType.CONTENT_TYPE_A -> {
                    //open artist details
                    setupNavGraphAndArg(
                        R.navigation.my_bl_sdk_nav_graph_artist_details,
                        Bundle().apply {
                            putSerializable(
                                PatchItem,
                                homePatchItem
                            )
                            putSerializable(
                                AppConstantUtils.PatchDetail,
                                homePatchDetail as Serializable
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_R -> {
                    //open album details
                    setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_album_details,
                        Bundle().apply {
                            putSerializable(
                                PatchItem,
                                homePatchItem
                            )
                            putSerializable(
                                AppConstantUtils.PatchDetail,
                                homePatchDetail as Serializable
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_P -> {
                    //open playlist
                    setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_playlist_details,
                        Bundle().apply {
                            putSerializable(
                                PatchItem,
                                homePatchItem
                            )
                            putSerializable(
                                AppConstantUtils.PatchDetail,
                                homePatchDetail as Serializable
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_S -> {
                    //open songs
                    setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_s_type_details,
                        Bundle().apply {
                            putSerializable(
                                PatchItem,
                                homePatchItem
                            )
                            putSerializable(
                                AppConstantUtils.PatchDetail,
                                homePatchDetail as Serializable
                            )
                        })
                }
                homePatchDetail.content_Type?.contains("PD").toString() -> {
                    Log.e("TAG", "CHECKING: " + homePatchDetail.content_Type)
                    //open podcast
                    Log.e("TAG", "CHECKING: " + PatchItem)
                }
            }

        } else {
            if (homePatchItem.ContentType.toUpperCase().contains("PD")) {
                //open podcast
                //setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_podcast_list_and_details,
                setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_patch_type_featured_podcast,
                    Bundle().apply {
                        putSerializable(
                            PatchItem,
                            homePatchItem as Serializable
                        )
                    })
            }
            //See All Item Click event
            when (homePatchItem.ContentType.toUpperCase()) {
                DataContentType.CONTENT_TYPE_A -> {
                    //open artist details
                    setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_artist_list_details,
                        Bundle().apply {
                            putSerializable(
                                PatchItem,
                                homePatchItem as Serializable
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_R -> {
                    //open album details
                    setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_album_list,
                        Bundle().apply {
                            putSerializable(
                                PatchItem,
                                homePatchItem
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_P -> {
                    //open playlist
                    setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_playlist_list,
                        Bundle().apply {
                            putSerializable(
                                PatchItem,
                                homePatchItem as Serializable
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_S -> {
                    //open songs
                    setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_s_type_list_details,
                        Bundle().apply {
                            putSerializable(
                                PatchItem,
                                homePatchItem as Serializable
                            )
                        })
                }

                DataContentType.CONTENT_TYPE_V -> {
                    //open video
                    setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_video_list_and_details,
                        Bundle().apply {
                            putSerializable(
                                PatchItem,
                                homePatchItem as Serializable
                            )
                        })
                }
            }
        }
    }

    private fun setupNavGraphAndArg(@NavigationRes graphResId: Int, bundleData: Bundle) {
        val inflater = navHostFragment.navController.navInflater
        val navGraph = inflater.inflate(graphResId)
        navController.setGraph(navGraph, bundleData)
    }

    private fun createPlayerVM() {
        playerViewModel = ViewModelProvider(
            this, injector.playerViewModelFactory
        )[PlayerViewModel::class.java]

        viewModel = ViewModelProvider(
            this,
            injector.factoryCreatePlaylistVM
        )[CreateplaylistViewModel::class.java]

        favViewModel = ViewModelProvider(
            this,
            injector.factoryFavContentVM
        )[FavViewModel::class.java]
    }

    private fun miniMusicPlayerHideShow(playing: Boolean) {
        // at fast show mini player
        // getDPfromPX paramerer pass pixel. how many height layout show.
        // this mini player height 72dp thats why i set 73dp view show
        if (playing && slCustomBottomSheet.panelState == SlidingUpPanelLayout.PanelState.HIDDEN) {
            slCustomBottomSheet.panelHeight = ImageSizeParser.getDPfromPX(73, this)
            slCustomBottomSheet.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }
    }

    private fun slCustomBShOnMaximized() {
        //when music player up side then mini player hide and big player show.
        //and when big player hide mini player show
        slCustomBottomSheet.addPanelSlideListener(object :
            SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                llMiniMusicPlayer.alpha = (1 - slideOffset)
                clMainMusicPlayer.alpha = slideOffset
                if (slideOffset == 1f) {
                    playerMode = PlayerMode.MAXIMIZED
                    llMiniMusicPlayer.visibility = GONE
                } else {
                    playerMode = PlayerMode.MINIMIZED
                    llMiniMusicPlayer.visibility = VISIBLE
                }
            }

            override fun onPanelStateChanged(
                panel: View?,
                previousState: SlidingUpPanelLayout.PanelState?,
                newState: SlidingUpPanelLayout.PanelState?,
            ) {
                val params = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
                )
                rlContentMain.layoutParams = params
                hideKeyboard(this@SDKMainActivity)
            }
        })
    }

    fun setMusicPlayerInitData(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int) {
        playerViewModel.unSubscribe()
        playerViewModel.subscribe(
            MusicPlayList(
                UtilHelper.getMusicListToSongDetailList(mSongDetails),
                0
            ),
            false,
            clickItemPosition
        )
    }

    private fun miniPlayerPlayPauseState(playing: Boolean) {
        if (playing) {
            ibtnPlayPauseMini.setImageResource(R.drawable.my_bl_sdk_ic_baseline_pause_24)
        } else {
            ibtnPlayPauseMini.setImageResource(R.drawable.my_bl_sdk_ic_baseline_play_arrow_black_24)
        }
    }

    private fun mainPlayerPlayPauseState(playing: Boolean) {
        if (playing) {
            ibtnPlayPause.setImageResource(R.drawable.my_bl_sdk_ic_baseline_pause_circle_filled_60)
        } else {
            ibtnPlayPause.setImageResource(R.drawable.my_bl_sdk_ic_baseline_play_circle_filled_60)
        }
    }

    override fun changePlayerView(playerMode: PlayerMode?) {
        when (playerMode) {
            PlayerMode.MINIMIZED -> toggleMiniPlayerView(true)
            PlayerMode.MAXIMIZED -> toggleMiniPlayerView(false)
            PlayerMode.CLOSED -> {}
            else -> {}
        }
    }

    private fun toggleMiniPlayerView(isVisible: Boolean) {
        hideKeyboard(this)
        if (isVisible) {
            playerMode = PlayerMode.MINIMIZED
            llMiniMusicPlayer.visibility = VISIBLE
            slCustomBottomSheet.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        } else {
            playerMode = PlayerMode.MAXIMIZED
            slCustomBottomSheet.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            llMiniMusicPlayer.visibility = INVISIBLE
        }
    }

    private fun setupMainMusicPlayerAdapter(
        mSongDetails: MutableList<IMusicModel>,
        clickItemPosition: Int,
    ) {
        mainMusicPlayerAdapter.setMusicData(mSongDetails)
        dsvCurrentPlaySongsThumb.adapter = mainMusicPlayerAdapter
        dsvCurrentPlaySongsThumb.setOrientation(DSVOrientation.HORIZONTAL)
        dsvCurrentPlaySongsThumb.setSlideOnFling(true)
        dsvCurrentPlaySongsThumb.setOffscreenItems(2)
        dsvCurrentPlaySongsThumb.setItemTransitionTimeMillis(150)
        dsvCurrentPlaySongsThumb.itemAnimator = null
        dsvCurrentPlaySongsThumb.setItemTransformer(
            ScaleTransformer.Builder()
                .setMaxScale(1.095f)
                .setMinScale(0.95f)
                .build()
        )
        dsvCurrentPlaySongsThumb.scrollToPosition(clickItemPosition)
        dsvCurrentPlaySongsThumb.addScrollStateChangeListener(object :
            DiscreteScrollView.ScrollStateChangeListener<MusicPlayAdapter.MusicPlayVH> {

            override fun onScrollStart(
                currentItemHolder: MusicPlayAdapter.MusicPlayVH,
                adapterPosition: Int
            ) {
            }

            override fun onScrollEnd(
                currentItemHolder: MusicPlayAdapter.MusicPlayVH,
                adapterPosition: Int
            ) {
                currentItemHolder.sMusicData.album_Name
                playerViewModel.skipToQueueItem(adapterPosition)
                tvSongName.text = currentItemHolder.sMusicData.titleName
                tvSingerName.text = currentItemHolder.sMusicData.artistName
                setMainPlayerBackgroundColor(getBitmapFromVH(currentItemHolder))
            }

            override fun onScroll(
                scrollPosition: Float,
                currentPosition: Int,
                newPosition: Int,
                currentHolder: MusicPlayAdapter.MusicPlayVH?,
                newCurrent: MusicPlayAdapter.MusicPlayVH?
            ) {
            }
        })

        tvSongName.text = mSongDetails[clickItemPosition].titleName
        tvSingerName.text = mSongDetails[clickItemPosition].artistName

        dsvCurrentPlaySongsThumb.addOnItemChangedListener { viewHolder, _ ->
            if (viewHolder != null) {
                viewHolder as MusicPlayAdapter.MusicPlayVH?
                setMainPlayerBackgroundColor(getBitmapFromVH(viewHolder))
            }
        }

        playerViewModel.playerProgress.observe(this) { itpProgress ->
            sbCurrentPlaySongStatus.progress = itpProgress.currentPosition?.toInt() ?: 0
            sbCurrentPlaySongStatus.max = itpProgress.duration?.toInt() ?: 0
            tvCurrentPlayDuration.text = itpProgress.currentPositionTimeLabel()
            tvTotalPlayDuration.text =
                if (itpProgress.durationTimeLabel() != "-153722867280912:0-55") {
                    itpProgress.durationTimeLabel()
                } else {
                    "0:00"
                }
            sbCurrentPlaySongStatus.secondaryProgress = itpProgress.bufferPosition?.toInt()!!
        }

        sbCurrentPlaySongStatus.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) {
                    sbCurrentPlaySongStatus.progress = p0!!.progress
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                playerViewModel.seekTo(p0!!.progress.toLong())
            }
        })

        playerViewModel.playbackStateLiveData.observe(this) {
            mainPlayerPlayPauseState(it.isPlaying)
        }

        playerViewModel.repeatModeLiveData.observe(this) {
            when (it) {
                PlaybackStateCompat.REPEAT_MODE_NONE -> {
                    setResource(ibtnRepeatSong, R.drawable.my_bl_sdk_ic_baseline_repeat_24)
                    ibtnShuffle.isEnabled = true
                    ibtnShuffle.setColorFilter(0)
                }
                PlaybackStateCompat.REPEAT_MODE_ONE -> {
                    setResource(
                        ibtnRepeatSong,
                        R.drawable.my_bl_sdk_ic_baseline_repeat_one_on_24
                    )
                    ibtnShuffle.isEnabled = false
                    ibtnShuffle.setColorFilter(
                        ContextCompat.getColor(
                            this,
                            R.color.my_sdk_color_transparent
                        ), PorterDuff.Mode.SRC_IN
                    )
                }
                PlaybackStateCompat.REPEAT_MODE_ALL -> {
                    setResource(ibtnRepeatSong, R.drawable.my_bl_sdk_ic_baseline_repeat_on_24)
                    ibtnShuffle.isEnabled = true
                    ibtnShuffle.setColorFilter(0)
                }
            }
        }

        playerViewModel.shuffleLiveData.observe(this) {
            when (it) {
                PlaybackStateCompat.SHUFFLE_MODE_NONE -> {
                    ibtnShuffle.setImageResource(R.drawable.my_bl_sdk_ic_baseline_shuffle_24)
                }
                PlaybackStateCompat.SHUFFLE_MODE_ALL -> {
                    ibtnShuffle.setImageResource(R.drawable.my_bl_sdk_ic_baseline_shuffle_on_24)
                }
            }
        }
        // var isDownloaded = false
        val downloaded =
            cacheRepository.getDownloadById(mSongDetails[clickItemPosition].content_Id)
        if (downloaded?.getIsDownloaded() == 1) {
            // isDownloaded=true
            ibtnDownload.setColorFilter(
                applicationContext.resources.getColor(R.color.my_sdk_color_primary)
            )
        } else {
            // isDownloaded=false
            ibtnDownload.setColorFilter(
                applicationContext.resources.getColor(R.color.my_sdk_color_white)
            )
        }
        ibtnShuffle.setOnClickListener {
            playerViewModel.shuffleToggle()
        }

        ibtnSkipPrevious.setOnClickListener {
            playerViewModel.skipToPrevious()
        }

        ibtnPlayPause.setOnClickListener {
            playerViewModel.togglePlayPause()
        }

        ibtnSkipNext.setOnClickListener {
            playerViewModel.skipToNext()
        }

        ibtnRepeatSong.setOnClickListener {
            playerViewModel.repeatTrack()
        }

        ibtnVolume.setOnClickListener {

        }

        ibtnLibraryAdd.setOnClickListener {
            if (isNetworkAvailable(this).equals(true)) {
                gotoPlayList(this, mSongDetails[clickItemPosition])
            } else {
                Toast.makeText(this, "Please check network", Toast.LENGTH_LONG).show()
            }


        }

        ibtnQueueMusic.setOnClickListener {
        }

        ibtnDownload.setOnClickListener {
            songDownload(mSongDetails[clickItemPosition])
        }

        acivMinimizePlayerBtn.setOnClickListener {
            toggleMiniPlayerView(true)
        }
        var isDownloaded = false
        val downloadedSong =
            cacheRepository.getDownloadById(mSongDetails[clickItemPosition].content_Id ?: "")
        if (downloadedSong?.getIsDownloaded() == 1) {
            isDownloaded = true
            ibtnDownload.setColorFilter(
                applicationContext.resources.getColor(R.color.my_sdk_color_primary)
            )
        } else {
            isDownloaded = false
            ibtnDownload.setColorFilter(
                applicationContext.resources.getColor(R.color.my_sdk_color_white)
            )
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    private fun songDownload(mSongDetails: IMusicModel) {
        var isDownloaded = false
        val downloaded = cacheRepository.getDownloadById(mSongDetails.content_Id)
        if (downloaded?.getIsDownloaded() == 1) {
            isDownloaded = true
            ibtnDownload.setColorFilter(
                applicationContext.resources.getColor(R.color.my_sdk_color_primary)
            )
        } else {
            isDownloaded = false
            ibtnDownload.setColorFilter(
                applicationContext.resources.getColor(R.color.my_sdk_color_white)
            )
        }
        if (isDownloaded == true) {
            cacheRepository.deleteDownloadById(mSongDetails.content_Id)
            DownloadService.sendRemoveDownload(
                applicationContext,
                MyBLDownloadService::class.java,
                mSongDetails.content_Id ?: "",
                false
            )
            //  Log.e("TAG","DELETED: "+ isDownloaded)
            val localBroadcastManager = LocalBroadcastManager.getInstance(applicationContext)
            val localIntent = Intent("DELETED")
                .putExtra("contentID", mSongDetails.content_Id ?: "")
            localBroadcastManager.sendBroadcast(localIntent)
            ibtnDownload.setColorFilter(
                applicationContext.resources.getColor(R.color.my_sdk_color_white)
            )
        } else {
            val url = "${Constants.FILE_BASE_URL}${mSongDetails.playingUrl}"
            val downloadRequest: DownloadRequest =
                DownloadRequest.Builder(mSongDetails.content_Id ?: "", url.toUri())
                    .build()
            injector.downloadTitleMap[mSongDetails.content_Id ?: ""] = mSongDetails.titleName ?: ""
            DownloadService.sendAddDownload(
                applicationContext,
                MyBLDownloadService::class.java,
                downloadRequest,
                /* foreground= */ false
            )
            Log.e("DELETEDX", "openDialog123:" + downloaded?.getIsDownloaded())
            if (cacheRepository.isDownloadCompleted(mSongDetails.content_Id ?: "") == true) {
                cacheRepository.insertDownload(
                    DownloadedContent().apply {
                        content_Id = mSongDetails.content_Id
                        album_Id = mSongDetails.album_Id
                        rootContentId = mSongDetails.rootContentId
                        imageUrl = mSongDetails.imageUrl
                        titleName = mSongDetails.titleName
                        content_Type = mSongDetails.content_Type
                        playingUrl = mSongDetails.playingUrl
                        content_Type = mSongDetails.content_Type
                        titleName = mSongDetails.titleName
                        artist_Id = mSongDetails.artist_Id
                        total_duration = mSongDetails.total_duration
                    }
                )
                isDownloaded = true
                ibtnDownload.setColorFilter(
                    applicationContext.getResources().getColor(R.color.my_sdk_color_primary)
                )
            }
        }
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setupMiniMusicPlayerAndFunctionality(mSongDetails: IMusicModel) {
        if (mSongDetails.isSeekAble!!) {
            ibtnSkipPreviousMini.visibility = VISIBLE
            ibtnSkipPreviousMini.setOnClickListener {
                playerViewModel.skipToPrevious()
            }
            ibtnSkipNextMini.visibility = VISIBLE
            ibtnSkipNextMini.setOnClickListener {
                playerViewModel.skipToNext()
            }
        } else {
            ibtnSkipPreviousMini.visibility = INVISIBLE
            ibtnSkipNextMini.visibility = INVISIBLE
        }

        Glide.with(this)
            .load(UtilHelper.getImageUrlSize300(mSongDetails.imageUrl!!))
            .transition(DrawableTransitionOptions().crossFade(500))
            .fitCenter()
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA))
            .placeholder(R.drawable.my_bl_sdk_default_song)
            .error(R.drawable.my_bl_sdk_default_song)
            .into(ivSongThumbMini)
        setMainPlayerBackgroundColor(getBitmapFromIV(ivSongThumbMini))

        tvSongNameMini.text = mSongDetails.titleName
        tvSingerNameMini.text = mSongDetails.artistName
        tvTotalDurationMini.text = TimeParser.secToMin(mSongDetails.total_duration)

        llMiniMusicPlayer.visibility = VISIBLE

//        playerViewModel.startObservePlayerProgress(this)
        playerViewModel.playerProgress.observe(this) {
            tvTotalDurationMini.text = it.currentPositionTimeLabel()
        }

        playerViewModel.playbackStateLiveData.observe(this) {
            if (it != null)
                miniPlayerPlayPauseState(it.isPlaying)
        }

        ibtnPlayPauseMini.setOnClickListener {
            playerViewModel.togglePlayPause()
        }
    }

    private fun setMainPlayerBackgroundColor(imBitmapData: Bitmap) {
        val palette: Palette = Palette.from(imBitmapData).generate()
        val vibrantSwatch: Palette.Swatch? = palette.vibrantSwatch
        if (vibrantSwatch != null) {
            if (vibrantSwatch.rgb.red > 0.90 && vibrantSwatch.rgb.green > 0.90 && vibrantSwatch.rgb.blue > 0.90) {
                val gradientDrawable = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    intArrayOf(
                        ContextCompat.getColor(this, R.color.my_sdk_shadin_required_color),
                        vibrantSwatch.rgb
                    )
                )
                gradientDrawable.cornerRadius = 0f
                clMainMusicPlayer.background = gradientDrawable
            }
        }
    }

    private fun getBitmapFromVH(currentItemHolder: MusicPlayAdapter.MusicPlayVH): Bitmap {
        val imageV = currentItemHolder.ivCurrentPlayImage
        val traDaw = imageV.drawable
        return (traDaw.toBitmap())
    }

    private fun getBitmapFromIV(ivCurrentPlayImage: ImageView): Bitmap {
        val traDaw = ivCurrentPlayImage.drawable
        return (traDaw.toBitmap())
    }

    override fun onBackPressed() {
        if (playerMode == PlayerMode.MAXIMIZED) {
            changePlayerView(PlayerMode.MINIMIZED)

            if (!navController.navigateUp()) {
                super.onBackPressed()
            }
        } else {
            if (!navController.navigateUp()) {
                super.onBackPressed()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        playerViewModel.currentMusicLiveData.observe(this) { itMus ->
            if (itMus != null) {
                setupMiniMusicPlayerAndFunctionality(UtilHelper.getSongDetailToMusic(itMus))
                isPlayOrPause = itMus.isPlaying!!
                miniMusicPlayerHideShow(playerViewModel.isPlaying)
            }
        }

        playerViewModel.playListLiveData.observe(this) { itMusicList ->
            playerViewModel.musicIndexLiveData.observe(this) { itCurrentPlayIndex ->
                try {
                    if (itMusicList.list[itCurrentPlayIndex].seekable!!) {
                        setupMainMusicPlayerAdapter(
                            UtilHelper.getSongDetailToMusicList(itMusicList.list.toMutableList()),
                            itCurrentPlayIndex
                        )
                        slCustomBShOnMaximized()
                        slCustomBottomSheet.isTouchEnabled = true
                        llMiniMusicPlayer.isEnabled = true
                    } else {
                        miniMusicPlayerHideShow(playerViewModel.isPlaying)
                        slCustomBottomSheet.isTouchEnabled = false
                        llMiniMusicPlayer.isEnabled = false
                    }
                } catch (exception: Exception) {
                }
            }
        }
    }

    override fun onDestroy() {
        DownloadOrDeleteObserver.removeSubscriber()
        super.onDestroy()
        //   playerViewModel.disconnect()
    }

    fun showBottomSheetDialog(
        bsdNavController: NavController,
        context: Context,
        mSongDetails: IMusicModel,
        argHomePatchItem: HomePatchItemModel?,
        argHomePatchDetail: HomePatchDetailModel?,
    ) {
        val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)

        val contentView =
            inflate(
                context,
                R.layout.my_bl_sdk_bottomsheet_three_dot_menu_layout,
                null
            )
        bottomSheetDialog.setContentView(contentView)
        bottomSheetDialog.show()
        val closeButton: ImageView? = bottomSheetDialog.findViewById(R.id.closeButton)
        closeButton?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val imageArtist: ImageView? = bottomSheetDialog.findViewById(R.id.imgAlbum)
        val textAlbum: TextView? = bottomSheetDialog.findViewById(R.id.tvAlbums)
        textAlbum?.text = "Go to Artist"
        val image: ImageView? = bottomSheetDialog.findViewById(R.id.thumb)
//        val url = argHomePatchDetail?.image
        val title: TextView? = bottomSheetDialog.findViewById(R.id.name)
        title?.text = argHomePatchDetail?.titleName
        var artistname = bottomSheetDialog.findViewById<TextView>(R.id.desc)
        artistname?.text = mSongDetails.artistName
        if (image != null) {
            Glide.with(context)
                .load(UtilHelper.getImageUrlSize300(argHomePatchDetail?.imageUrl!!))
                .into(image)
        }
        val downloadImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgDownload)
        val textViewDownloadTitle: TextView? =
            bottomSheetDialog.findViewById(R.id.tv_download)
        var isDownloadComplete = false
        val downloaded = cacheRepository.getDownloadById(mSongDetails.content_Id)
        if (downloaded?.playingUrl != null) {
            isDownloadComplete = true
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_ic_delete)
        } else {
            isDownloadComplete = false
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_icon_dowload)
        }

        if (isDownloadComplete) {
            textViewDownloadTitle?.text = "Remove From Download"
        } else {
            textViewDownloadTitle?.text = "Download Offline"
        }
        val constraintDownload: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintDownload)
        constraintDownload?.setOnClickListener {
            if (isDownloadComplete.equals(true)) {
                cacheRepository.deleteDownloadById(mSongDetails.content_Id)
                DownloadService.sendRemoveDownload(
                    applicationContext,
                    MyBLDownloadService::class.java,
                    mSongDetails.content_Id,
                    false
                )
                val localBroadcastManager =
                    LocalBroadcastManager.getInstance(applicationContext)
                val localIntent = Intent("DELETED")
                    .putExtra("contentID", mSongDetails.content_Id)
                localBroadcastManager.sendBroadcast(localIntent)
                isDownloadComplete = false
            } else {
                val url = "${Constants.FILE_BASE_URL}${mSongDetails.playingUrl}"
                val downloadRequest: DownloadRequest =
                    DownloadRequest.Builder(mSongDetails.content_Id, url.toUri())
                        .build()
                injector.downloadTitleMap[mSongDetails.content_Id ?: ""] =
                    mSongDetails.titleName ?: ""

                DownloadService.sendAddDownload(
                    applicationContext,
                    MyBLDownloadService::class.java,
                    downloadRequest,
                    /* foreground= */ false
                )
                if (cacheRepository.isDownloadCompleted(mSongDetails.content_Id)
                        .equals(true)
                ) {
//                if (cacheRepository.isDownloadCompleted(mSongDetails.ContentID).equals(true)) {
                    cacheRepository.insertDownload(
                        DownloadedContent().apply {
                            content_Id = mSongDetails.content_Id.toString()
                            rootContentId = mSongDetails.rootContentId
                            imageUrl = mSongDetails.imageUrl
                            titleName = mSongDetails.titleName
                            content_Type = mSongDetails.content_Type
                            playingUrl = mSongDetails.playingUrl
                            rootContentType = mSongDetails.rootContentType
                            artistName = mSongDetails.artistName
                            artist_Id = mSongDetails.artist_Id.toString()
                            total_duration = mSongDetails.total_duration
                        }
                    )
                    isDownloadComplete = true
                }
            }
            bottomSheetDialog.dismiss()
        }
        val constraintAlbum: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintAlbum)
        constraintAlbum?.setOnClickListener {
            gotoArtist(
                bsdNavController,
                context,
                mSongDetails,
                argHomePatchItem,
                argHomePatchDetail
            )
            bottomSheetDialog.dismiss()
        }

        val constraintPlaylist: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintAddtoPlaylist)

        constraintPlaylist?.setOnClickListener {
            gotoPlayList(context, mSongDetails)
            bottomSheetDialog.dismiss()
        }

        val constraintFav: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintFav)
        val favImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgLike)
        val textFav: TextView? = bottomSheetDialog.findViewById(R.id.tvFav)
        var isFav = false
//        favViewModel.getFavContentSong("S")
//        favViewModel.getFavContentSong.observe(this){res->
//            res.data?.forEach{
//                if(it.contentID.equals(mSongDetails.ContentID)) {
//
//                    isFav = true
//                    favImage?.setImageResource(R.drawable.my_bl_sdk_ic_icon_fav)
//                }
//                else {
//                    isFav = false
//                 favImage?.setImageResource(R.drawable.my_bl_sdk_ic_like)
//                }
//
//            }
//        }
        val isAddedToFav = cacheRepository.getFavoriteById(mSongDetails.content_Id)
        if (isAddedToFav?.content_Id != null) {
            favImage?.setImageResource(R.drawable.my_bl_sdk_ic_icon_fav)
            isFav = true
            textFav?.text = "Remove From favorite"
        } else {
            favImage?.setImageResource(R.drawable.my_bl_sdk_ic_like)
            isFav = false
            textFav?.text = "Favorite"
        }

        constraintFav?.setOnClickListener {
            if (isFav.equals(true)) {
                favViewModel.deleteFavContent(
                    mSongDetails.content_Id,
                    mSongDetails.content_Type!!
                )
                cacheRepository.deleteFavoriteById(mSongDetails.content_Id)
                Toast.makeText(
                    applicationContext,
                    "Removed from favorite",
                    Toast.LENGTH_LONG
                )
                    .show()
                favImage?.setImageResource(R.drawable.my_bl_sdk_ic_like)
                isFav = false
            } else {
                favViewModel.addFavContent(
                    mSongDetails.content_Id,
                    mSongDetails.content_Type!!
                )
                favImage?.setImageResource(R.drawable.my_bl_sdk_ic_icon_fav)
                cacheRepository.insertFavSingleContent(
                    FavDataModel().apply {
                        content_Id = mSongDetails.content_Id
                        album_Id = mSongDetails.album_Id
                        bannerImage = mSongDetails.imageUrl
                        artistName = mSongDetails.artistName
                        artist_Id = mSongDetails.artist_Id
                        clientValue = 2
                        content_Type = mSongDetails.content_Type
                        fav = "1"
                        imageUrl = mSongDetails.imageUrl
                        playingUrl = mSongDetails.playingUrl
                        rootContentId = mSongDetails.rootContentId
                        rootContentType = mSongDetails.rootContentType
                        titleName = mSongDetails.titleName
                        total_duration = mSongDetails.total_duration
                    }
                )
                isFav = true
                Toast.makeText(applicationContext, "Added to favorite", Toast.LENGTH_LONG)
                    .show()
            }
            bottomSheetDialog.dismiss()
        }
    }

    private fun gotoPlayList(context: Context, mSongDetails: IMusicModel) {
        val bottomSheetDialogPlaylist =
            BottomSheetDialog(context, R.style.BottomSheetDialog)
        val contentView =
            inflate(
                context,
                R.layout.my_bl_sdk_bottomsheet_create_playlist_with_list,
                null
            )
        bottomSheetDialogPlaylist.setContentView(contentView)
        bottomSheetDialogPlaylist.show()
        val closeButton: ImageView? =
            bottomSheetDialogPlaylist.findViewById(R.id.closeButton)
        closeButton?.setOnClickListener {
            bottomSheetDialogPlaylist.dismiss()
        }
        val recyclerView: RecyclerView? =
            bottomSheetDialogPlaylist.findViewById(R.id.recyclerView)
        viewModel.getuserPlaylist()
        viewModel.getUserPlaylist.observe(this) { res ->
            recyclerView?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            if (res != null) {
                if (res.data != null) {
                    recyclerView?.adapter = res.data?.let {
                        CreatePlaylistListAdapter(it, this, mSongDetails)
                    }
                }
            }
        }
        val btnCreatePlaylist: AppCompatButton? =
            bottomSheetDialogPlaylist.findViewById(R.id.btnCreatePlaylist)
        btnCreatePlaylist?.setOnClickListener {
            openCreatePlaylist(context)
            bottomSheetDialogPlaylist.dismiss()
        }
        viewModel.createPlaylist.observe(this) { res ->
            Toast.makeText(context, res.status.toString(), Toast.LENGTH_LONG).show()
        }
    }

    fun openCreatePlaylist(context: Context) {
        val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)

        val contentView =
            inflate(context, R.layout.my_bl_sdk_bottomsheet_create_new_playlist, null)
        bottomSheetDialog.setContentView(contentView)
        bottomSheetDialog.show()
        val closeButton: ImageView? = bottomSheetDialog.findViewById(R.id.closeButton)
        closeButton?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val etCreatePlaylist: EditText? =
            bottomSheetDialog.findViewById(R.id.etCreatePlaylist)
        var savePlaylist: AppCompatButton? =
            bottomSheetDialog.findViewById(R.id.btnSavePlaylist)
        etCreatePlaylist?.setOnFocusChangeListener { view, focused ->
            val keyboard: InputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (focused) keyboard.showSoftInput(
                etCreatePlaylist,
                0
            ) else keyboard.hideSoftInputFromWindow(
                etCreatePlaylist.getWindowToken(),
                0
            )
        }
        etCreatePlaylist?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val name: String = etCreatePlaylist.getText().toString()
                Log.e("TAG", "NAME: " + name)
                savePlaylist?.setBackgroundResource(R.drawable.my_bl_sdk_rounded_button_red)
                savePlaylist?.isEnabled = true
                savePlaylist?.textColor(R.color.my_sdk_color_white)
                savePlaylist?.setOnClickListener {

                    viewModel.createPlaylist(name)
                    // requireActivity().onBackPressed()
                    bottomSheetDialog.dismiss()

                }
                if (etCreatePlaylist.text.isNullOrEmpty()) {
                    savePlaylist?.setBackgroundResource(R.drawable.my_bl_sdk_rounded_button_gray)
                    savePlaylist?.isEnabled = false
                }
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }
        })
        etCreatePlaylist?.requestFocus()
    }

    fun showBottomSheetDialogForPodcast(
        bsdNavController: NavController,
        context: Context,
        iSongTrack: IMusicModel,
        argHomePatchItem: HomePatchItemModel?,
        argHomePatchDetail: HomePatchDetailModel?,
    ) {
        val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
        val contentView =
            inflate(
                context,
                R.layout.my_bl_sdk_bottomsheet_three_dot_menu_layout,
                null
            )
        bottomSheetDialog.setContentView(contentView)
        bottomSheetDialog.show()
        val closeButton: ImageView? = bottomSheetDialog.findViewById(R.id.closeButton)
        closeButton?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val image: ImageView? = bottomSheetDialog.findViewById(R.id.thumb)
        val url = iSongTrack.imageUrl
        val title: TextView? = bottomSheetDialog.findViewById(R.id.name)
        title?.text = iSongTrack.titleName
        val artistname = bottomSheetDialog.findViewById<TextView>(R.id.desc)
        artistname?.text = iSongTrack.artistName
        if (image != null) {
            Glide.with(context)
                .load(UtilHelper.getImageUrlSize300(url!!))
                .into(image)
        }

        val constraintAlbum: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintAlbum)
        constraintAlbum?.setOnClickListener {
//            gotoArtistFromPlaylist(
//                bsdNavController,
//                context,
//                mSongDetails,
//                argHomePatchItem,
//                argHomePatchDetail
//            )
            bottomSheetDialog.dismiss()
        }
        constraintAlbum?.visibility = GONE
        val downloadImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgDownload)
        val textViewDownloadTitle: TextView? =
            bottomSheetDialog.findViewById(R.id.tv_download)
        var isDownloadComplete = false
        val downloaded = cacheRepository.getDownloadById(iSongTrack.content_Id)
        if (downloaded?.playingUrl != null) {
            isDownloadComplete = true
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_ic_delete)
        } else {
            isDownloadComplete = false
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_icon_dowload)
        }

        if (isDownloadComplete) {
            textViewDownloadTitle?.text = "Remove From Download"
        } else {
            textViewDownloadTitle?.text = "Download Offline"
        }
        val constraintDownload: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintDownload)
        constraintDownload?.setOnClickListener {
            if (isDownloadComplete) {
//                cacheRepository.deleteDownloadById(track.EpisodeId)
                cacheRepository.deleteDownloadById(iSongTrack.content_Id)
                DownloadService.sendRemoveDownload(
                    applicationContext,
                    MyBLDownloadService::class.java,
                    iSongTrack.content_Id,
                    false
                )
                val localBroadcastManager =
                    LocalBroadcastManager.getInstance(applicationContext)
                val localIntent = Intent("DELETED")
                    .putExtra("contentID", iSongTrack.content_Id)
//                    .putExtra("contentID", track.EpisodeId)
                localBroadcastManager.sendBroadcast(localIntent)

            } else {
                val mPlayingUrl = "${Constants.FILE_BASE_URL}${iSongTrack.playingUrl}"
                val downloadRequest: DownloadRequest =
                    DownloadRequest.Builder(iSongTrack.content_Id, mPlayingUrl.toUri())
                        .build()
                Log.e("TAG", "NAME: " + iSongTrack.titleName + " URL " + mPlayingUrl)
                injector.downloadTitleMap[iSongTrack.content_Id ?: ""] = iSongTrack.titleName ?: ""
                DownloadService.sendAddDownload(
                    applicationContext,
                    MyBLDownloadService::class.java,
                    downloadRequest,
                    /* foreground= */ false
                )

                //Todo iSongTrack.EpisodeId
                if (cacheRepository.isDownloadCompleted(iSongTrack.content_Id)) {
//                    val contentType = iSongTrack.content_Type
//                    val podcastType = contentType?.take(2)
//                    val  Type = contentType?.takeLast(2)
                    cacheRepository.insertDownload(
                        DownloadedContent().apply {

                            content_Id = iSongTrack.content_Id
                            album_Id = iSongTrack.album_Id
                            rootContentId = iSongTrack.rootContentId
                            imageUrl = iSongTrack.imageUrl
                            titleName = iSongTrack.titleName
                            content_Type = iSongTrack.content_Type
                            playingUrl = iSongTrack.playingUrl
                            rootContentType = iSongTrack.rootContentType
                            titleName = iSongTrack.titleName
                            artist_Id = iSongTrack.artist_Id
                            total_duration = iSongTrack.total_duration
                        }
                    )
                }
            }
            bottomSheetDialog.dismiss()
        }

        val constraintFav: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintFav)
        val favImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgLike)
        val textFav: TextView? = bottomSheetDialog.findViewById(R.id.tvFav)
        var isFav = false
        //todo iSongTrack.Id
        val isAddedToFav = cacheRepository.getFavoriteById(iSongTrack.content_Id)
        if (isAddedToFav?.content_Id != null) {

            favImage?.setImageResource(R.drawable.my_bl_sdk_ic_icon_fav)
            isFav = true
            textFav?.text = "Remove From favorite"
        } else {

            favImage?.setImageResource(R.drawable.my_bl_sdk_ic_like)
            isFav = false
            textFav?.text = "Favorite"
        }

        constraintFav?.setOnClickListener {
            if (isFav.equals(true)) {
                val contentType = iSongTrack.content_Type
                val podcastType = contentType?.take(2)
                val Type = contentType?.takeLast(2)
                //todo iSongTrack.Id
                favViewModel.deleteFavContent(
                    iSongTrack.content_Id,
                    iSongTrack.content_Type.toString()
                )
                cacheRepository.deleteFavoriteById(iSongTrack.content_Id)
                Toast.makeText(
                    applicationContext,
                    "Removed from favorite",
                    Toast.LENGTH_LONG
                )
                    .show()
                favImage?.setImageResource(R.drawable.my_bl_sdk_ic_like)
                isFav = false
                Log.e("TAG", "NAME: " + iSongTrack.content_Type)
            } else {
                val contentType = iSongTrack.content_Type
                val podcastType = contentType?.take(2)
                val Type = contentType?.takeLast(2)
                //todo iSongTrack.EpisodeId
                favViewModel.addFavContent(
                    iSongTrack.content_Id,
                    iSongTrack.content_Type.toString()
                )

                favImage?.setImageResource(R.drawable.my_bl_sdk_ic_icon_fav)

                // todo iSongTrack.Id.toString(),
                //      iSongTrack.Id.toString(),
                cacheRepository.insertFavSingleContent(
                    FavDataModel().apply {
                        content_Id = iSongTrack.content_Id
                        album_Id = iSongTrack.content_Id
                        albumImage = iSongTrack.imageUrl
                        clientValue = 2
                        content_Type = iSongTrack.content_Type.toString()
                        fav = "1"
                        imageUrl = iSongTrack.imageUrl
                        playingUrl = iSongTrack.playingUrl
                        rootContentId = iSongTrack.rootContentId
                        rootContentType = iSongTrack.rootContentType
                        titleName = iSongTrack.titleName
                        total_duration = iSongTrack.total_duration
                    }
                )
                isFav = true
                Toast.makeText(applicationContext, "Added to favorite", Toast.LENGTH_LONG)
                    .show()
            }
            bottomSheetDialog.dismiss()
        }
        val constraintPlaylist: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintAddtoPlaylist)
        constraintPlaylist?.visibility = GONE
    }


    fun showBottomSheetDialogForPlaylist(
        bsdNavController: NavController,
        context: Context,
        mSongDetails: IMusicModel,
        argHomePatchItem: HomePatchItemModel?,
        argHomePatchDetail: HomePatchDetailModel?,
    ) {
        val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)

        val contentView =
            inflate(
                context,
                R.layout.my_bl_sdk_bottomsheet_three_dot_menu_layout,
                null
            )
        bottomSheetDialog.setContentView(contentView)
        bottomSheetDialog.show()
        val closeButton: ImageView? = bottomSheetDialog.findViewById(R.id.closeButton)
        closeButton?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        val image: ImageView? = bottomSheetDialog.findViewById(R.id.thumb)
//        val url = mSongDetails.imageUrl
        val title: TextView? = bottomSheetDialog.findViewById(R.id.name)
        title?.text = mSongDetails.titleName
        val artistname = bottomSheetDialog.findViewById<TextView>(R.id.desc)
        artistname?.text = mSongDetails.artistName
        if (image != null) {
            Glide.with(context)
                .load(UtilHelper.getImageUrlSize300(mSongDetails.imageUrl ?: ""))
                .into(image)
        }
        val constraintAlbum: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintAlbum)
        constraintAlbum?.setOnClickListener {
            gotoArtistFromPlaylist(
                bsdNavController,
                context,
                mSongDetails,
                argHomePatchItem,
                argHomePatchDetail
            )
            bottomSheetDialog.dismiss()
        }
        val downloadImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgDownload)
        val textViewDownloadTitle: TextView? =
            bottomSheetDialog.findViewById(R.id.tv_download)
        var isDownloadComplete = false
        val downloaded = cacheRepository.getDownloadById(mSongDetails.content_Id)
        if (downloaded?.playingUrl != null) {
            isDownloadComplete = true
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_ic_delete)
        } else {
            isDownloadComplete = false
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_icon_dowload)
        }

        if (isDownloadComplete) {
            textViewDownloadTitle?.text = "Remove From Download"
        } else {
            textViewDownloadTitle?.text = "Download Offline"
        }
        val constraintDownload: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintDownload)
        constraintDownload?.setOnClickListener {

            if (isDownloadComplete.equals(true)) {
                cacheRepository.deleteDownloadById(mSongDetails.content_Id)
                DownloadService.sendRemoveDownload(
                    applicationContext,
                    MyBLDownloadService::class.java,
                    mSongDetails.content_Id,
                    false
                )
                val localBroadcastManager =
                    LocalBroadcastManager.getInstance(applicationContext)
                val localIntent = Intent("DELETED")
                    .putExtra("contentID", mSongDetails.content_Id)
                localBroadcastManager.sendBroadcast(localIntent)
            } else {
                val mPlayingUrl = "${Constants.FILE_BASE_URL}${mSongDetails.playingUrl!!}"
                val downloadRequest: DownloadRequest =
                    DownloadRequest.Builder(mSongDetails.content_Id, mPlayingUrl.toUri())
                        .build()
                injector.downloadTitleMap[mSongDetails.content_Id] =
                    mSongDetails.titleName ?: ""

                DownloadService.sendAddDownload(
                    applicationContext,
                    MyBLDownloadService::class.java,
                    downloadRequest,
                    /* foreground= */ false
                )

                if (cacheRepository.isDownloadCompleted(mSongDetails.content_Id)) {

                    cacheRepository.insertDownload(
                        DownloadedContent().apply {
                            content_Id = mSongDetails.content_Id
                            rootContentId = mSongDetails.rootContentId
                            imageUrl = mSongDetails.imageUrl
                            titleName = mSongDetails.titleName
                            content_Type = mSongDetails.content_Type
                            playingUrl = mSongDetails.playingUrl
                            rootContentType = mSongDetails.content_Type
                            artistName = mSongDetails.artistName
                            artist_Id = mSongDetails.artist_Id.toString()
                            total_duration = mSongDetails.total_duration
                            album_Id = mSongDetails.album_Id
                        }
                    )
                }
            }
            bottomSheetDialog.dismiss()
        }
        val constraintPlaylist: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintAddtoPlaylist)
        constraintPlaylist?.setOnClickListener {
            gotoPlayList(context, mSongDetails)

            bottomSheetDialog.dismiss()
        }
        val constraintFav: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintFav)
        val favImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgLike)
        val textFav: TextView? = bottomSheetDialog.findViewById(R.id.tvFav)
        var isFav = false
        val isAddedToFav = cacheRepository.getFavoriteById(mSongDetails.content_Id)
        if (isAddedToFav?.content_Id != null) {
            favImage?.setImageResource(R.drawable.my_bl_sdk_ic_icon_fav)
            isFav = true
            textFav?.text = "Remove From favorite"
        } else {
            favImage?.setImageResource(R.drawable.my_bl_sdk_ic_like)
            isFav = false
            textFav?.text = "Favorite"
        }


        constraintFav?.setOnClickListener {
            if (isFav == true) {
                favViewModel.deleteFavContent(
                    mSongDetails.content_Id,
                    mSongDetails.content_Type!!
                )
                cacheRepository.deleteFavoriteById(mSongDetails.content_Id)
                Toast.makeText(
                    applicationContext,
                    "Removed from favorite",
                    Toast.LENGTH_LONG
                )
                    .show()
                favImage?.setImageResource(R.drawable.my_bl_sdk_ic_like)
                isFav = false
            } else {
                favViewModel.addFavContent(
                    mSongDetails.content_Id.toString(),
                    mSongDetails.content_Type!!
                )
                favImage?.setImageResource(R.drawable.my_bl_sdk_ic_icon_fav)
                cacheRepository.insertFavSingleContent(
                    FavDataModel().apply {
                        content_Id = mSongDetails.content_Id
                        album_Id = mSongDetails.album_Id
                        albumImage = mSongDetails.imageUrl
                        artistName = mSongDetails.artistName
                        artist_Id = mSongDetails.artist_Id
                        clientValue = 2
                        content_Type = mSongDetails.content_Type
                        content_Id = "1"
                        imageUrl = mSongDetails.imageUrl
                        playingUrl = mSongDetails.playingUrl
                        rootContentId = mSongDetails.rootContentId
                        rootContentType = mSongDetails.rootContentType
                        titleName = mSongDetails.titleName
                        total_duration = mSongDetails.total_duration
                    }
                )
                isFav = true
                Toast.makeText(applicationContext, "Added to favorite", Toast.LENGTH_LONG)
                    .show()
            }
            bottomSheetDialog.dismiss()
        }
    }

    fun showBottomSheetDialogGoTOALBUM(
        bsdNavController: NavController,
        context: Context,
        mSongDetails: IMusicModel,
        argHomePatchItem: HomePatchItemModel?,
        argHomePatchDetail: HomePatchDetailModel?,
    ) {
        val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
        val contentView =
            inflate(
                context,
                R.layout.my_bl_sdk_bottomsheet_three_dot_menu_layout,
                null
            )
        bottomSheetDialog.setContentView(contentView)
        bottomSheetDialog.show()
        val closeButton: ImageView? = bottomSheetDialog.findViewById(R.id.closeButton)
        closeButton?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val artistname = bottomSheetDialog.findViewById<TextView>(R.id.desc)
        artistname?.text = mSongDetails.artistName ?: ""
        val image: ImageView? = bottomSheetDialog.findViewById(R.id.thumb)
        val url = mSongDetails.imageUrl
        val title: TextView? = bottomSheetDialog.findViewById(R.id.name)
        title?.text = mSongDetails.titleName ?: ""
        if (image != null) {
            Glide.with(context).load(UtilHelper.getImageUrlSize300(url!!)).into(image)
        }
        val imageArtist: ImageView? = bottomSheetDialog.findViewById(R.id.imgAlbum)
        val textAlbum: TextView? = bottomSheetDialog.findViewById(R.id.tvAlbums)
        textAlbum?.text = "Go to Album"
        imageArtist?.setImageResource(R.drawable.my_bl_sdk_goto_album)
        val constraintAlbum: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintAlbum)
        constraintAlbum?.setOnClickListener {
            gotoAlbum(
                bsdNavController,
                context,
                mSongDetails,
                argHomePatchItem,
                argHomePatchDetail
            )
            bottomSheetDialog.dismiss()
        }
        val downloadImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgDownload)
        val textViewDownloadTitle: TextView? =
            bottomSheetDialog.findViewById(R.id.tv_download)
        var isDownloadComplete = false
        val downloaded = cacheRepository.getDownloadById(mSongDetails.content_Id)
        if (downloaded?.playingUrl != null) {
            isDownloadComplete = true
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_ic_delete)
        } else {
            isDownloadComplete = false
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_icon_dowload)
        }

        if (isDownloadComplete) {
            textViewDownloadTitle?.text = "Remove From Download"
        } else {
            textViewDownloadTitle?.text = "Download Offline"
        }
        val constraintDownload: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintDownload)
        constraintDownload?.setOnClickListener {
            if (isDownloadComplete == true) {
                cacheRepository.deleteDownloadById(mSongDetails.content_Id)
                DownloadService.sendRemoveDownload(
                    applicationContext,
                    MyBLDownloadService::class.java,
                    mSongDetails.content_Id,
                    false
                )
                val localBroadcastManager =
                    LocalBroadcastManager.getInstance(applicationContext)
                val localIntent = Intent("DELETED")
                    .putExtra("contentID", mSongDetails.content_Id)
                localBroadcastManager.sendBroadcast(localIntent)

            } else {
                val mPlayUrl = "${Constants.FILE_BASE_URL}${mSongDetails.playingUrl}"
                val downloadRequest: DownloadRequest =
                    DownloadRequest.Builder(mSongDetails.content_Id, mPlayUrl.toUri())
                        .build()
                injector.downloadTitleMap[mSongDetails.content_Id ?: ""] =
                    mSongDetails.titleName ?: ""
                DownloadService.sendAddDownload(
                    applicationContext,
                    MyBLDownloadService::class.java,
                    downloadRequest,
                    /* foreground= */ false
                )

                if (cacheRepository.isDownloadCompleted(mSongDetails.content_Id)) {
                    cacheRepository.insertDownload(
                        DownloadedContent().apply {
                            content_Id = mSongDetails.content_Id.toString()
                            rootContentId = mSongDetails.rootContentId
                            imageUrl = mSongDetails.imageUrl
                            titleName = mSongDetails.titleName
                            content_Type = mSongDetails.content_Type
                            playingUrl = mSongDetails.playingUrl
                            rootContentType = mSongDetails.content_Type
                            artistName = mSongDetails.artistName
                            artist_Id = mSongDetails.artist_Id.toString()
                            total_duration = mSongDetails.total_duration
                        }
                    )
                }
            }
            bottomSheetDialog.dismiss()
        }

        val constraintPlaylist: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintAddtoPlaylist)
        constraintPlaylist?.setOnClickListener {
            gotoPlayList(context, mSongDetails)
            bottomSheetDialog.dismiss()
        }
        val constraintFav: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintFav)
        val favImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgLike)
        val textFav: TextView? = bottomSheetDialog.findViewById(R.id.tvFav)
        var isFav = false
        val isAddedToFav = cacheRepository.getFavoriteById(mSongDetails.content_Id)

        if (isAddedToFav?.content_Id != null) {
            favImage?.setImageResource(R.drawable.my_bl_sdk_ic_icon_fav)
            isFav = true
            textFav?.text = "Remove From favorite"
        } else {
            favImage?.setImageResource(R.drawable.my_bl_sdk_ic_like)
            isFav = false
            textFav?.text = "Favorite"
        }

        constraintFav?.setOnClickListener {
            if (isFav == true) {
                favViewModel.deleteFavContent(
                    mSongDetails.content_Id,
                    mSongDetails.content_Type!!
                )
                cacheRepository.deleteFavoriteById(mSongDetails.content_Id)
                Toast.makeText(
                    applicationContext,
                    "Removed from favorite",
                    Toast.LENGTH_LONG
                )
                    .show()
                favImage?.setImageResource(R.drawable.my_bl_sdk_ic_like)
                isFav = false
            } else {
                favViewModel.addFavContent(
                    mSongDetails.content_Id,
                    mSongDetails.content_Type!!
                )
                favImage?.setImageResource(R.drawable.my_bl_sdk_ic_icon_fav)
                cacheRepository.insertFavSingleContent(
                    FavDataModel().apply {
                        content_Id = mSongDetails.content_Id
                        album_Id = mSongDetails.album_Id
                        albumImage = mSongDetails.imageUrl
                        artistName = mSongDetails.artistName
                        artist_Id = mSongDetails.artist_Id
                        clientValue = 2
                        content_Type = mSongDetails.content_Type
                        fav = "1"
                        imageUrl = mSongDetails.imageUrl
                        playingUrl = mSongDetails.playingUrl
                        rootContentId = mSongDetails.rootContentId
                        rootContentType = mSongDetails.rootContentType
                        titleName = mSongDetails.titleName
                        total_duration = mSongDetails.total_duration
                    }
                )
                isFav = true
                Toast.makeText(applicationContext, "Added to favorite", Toast.LENGTH_LONG)
                    .show()
            }
            bottomSheetDialog.dismiss()
        }
    }

    private fun gotoArtist(
        bsdNavController: NavController,
        context: Context,
        mSongDetails: IMusicModel,
        argHomePatchItem: HomePatchItemModel?,
        argHomePatchDetail: HomePatchDetailModel?
    ) {
        bsdNavController.navigate(R.id.artist_details_fragment,
            Bundle().apply {
                putSerializable(
                    PatchItem,
                    argHomePatchItem
                )
                putSerializable(
                    AppConstantUtils.PatchDetail,
                    argHomePatchDetail as Serializable
                )
            })
    }

    private fun gotoArtistFromPlaylist(
        bsdNavController: NavController,
        context: Context,
        mSongDetails: IMusicModel,
        argHomePatchItem: HomePatchItemModel?,
        argHomePatchDetail: HomePatchDetailModel?
    ) {
        bsdNavController.navigate(R.id.to_artist_details,
            Bundle().apply {
                putSerializable(
                    PatchItem,
                    argHomePatchItem
                )
                putSerializable(
                    AppConstantUtils.PatchDetail,
                    HomePatchDetailModel().apply {
                        album_Id = mSongDetails.album_Id
                        artist_Id = mSongDetails.artist_Id
                        content_Id = mSongDetails.content_Id
                        content_Type = mSongDetails.content_Type
                        playingUrl = mSongDetails.playingUrl
                        imageUrl = mSongDetails.imageUrl
                        artistName = mSongDetails.artistName
                        rootContentType = "P"
                        isSeekAble = true
                        titleName = ""
                    } as Serializable
                )
            })
    }

    private fun gotoAlbum(
        bsdNavController: NavController,
        context: Context,
        mSongDetails: IMusicModel,
        argHomePatchItem: HomePatchItemModel?,
        argHomePatchDetail: HomePatchDetailModel?
    ) {
        bsdNavController.navigate(
            R.id.to_album_details,
            Bundle().apply {
                putSerializable(
                    PatchItem,
                    argHomePatchItem
                )
                putSerializable(
                    AppConstantUtils.PatchDetail,
                    UtilHelper.getHomePatchDetailToSongDetail(mSongDetails) as Serializable
                )
            })
    }

    override fun onClick(position: Int, mSongDetails: IMusicModel, id: String?) {
        addSongsToPlaylist(mSongDetails, id)
    }

    private fun addSongsToPlaylist(mSongDetails: IMusicModel, id: String?) {
        id?.let { viewModel.songsAddedToPlaylist(it, mSongDetails.content_Id) }
        viewModel.songsAddedToPlaylist.observe(this) { res ->
            Toast.makeText(applicationContext, res.status.toString(), Toast.LENGTH_LONG).show()
        }
    }
}