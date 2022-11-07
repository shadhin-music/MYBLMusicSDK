package com.shadhinmusiclibrary.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import android.view.View
import android.view.View.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.blue
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.exoplayer2.offline.DownloadRequest
import com.google.android.exoplayer2.offline.DownloadService
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.MusicPlayAdapter
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.HomePatchItemModel
import com.shadhinmusiclibrary.di.ActivityEntryPoint
import com.shadhinmusiclibrary.download.MyBLDownloadService
import com.shadhinmusiclibrary.download.room.DownloadedContent
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
import java.io.Serializable
import java.util.*
import androidx.annotation.NavigationRes as NavigationRes1


internal class SDKMainActivity : BaseActivity(), ActivityEntryPoint {
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

    private lateinit var mainMusicPlayerAdapter: MusicPlayAdapter

    private lateinit var listData: MutableList<HomePatchDetailModel>

    private fun uiInitMiniMusicPlayer() {
        llMiniMusicPlayer = findViewById(R.id.include_mini_music_player)
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
        if (uiRequest == AppConstantUtils.Requester_Name_CreatePlaylist) {
            createPlaylistFragmentAccess()
        }
        //routeDataArtistType()
        playerViewModel.currentMusicLiveData.observe(this) { itMus ->
            if (itMus != null) {
                setupMiniMusicPlayerAndFunctionality(UtilHelper.getSongDetailToMusic(itMus))
                isPlayOrPause = itMus.isPlaying!!
            }
        }

        playerViewModel.playListLiveData.observe(this) { itMusicList ->
            playerViewModel.musicIndexLiveData.observe(this) { itCurrentPlayIndex ->
                try {
                    if (itMusicList.list[itCurrentPlayIndex].seekable!!) {
                        slCustomBottomSheet.isEnabled = true
                        setupMainMusicPlayerAdapter(
                            UtilHelper.getSongDetailToMusicList(itMusicList.list.toMutableList()),
                            itCurrentPlayIndex
                        )
                        llMiniMusicPlayer.isEnabled = true
                    } else {
                        slCustomBottomSheet.isEnabled = false
                        llMiniMusicPlayer.isEnabled = false
                    }
                } catch (exception: Exception) {
                }
            }
        }

        miniMusicPlayerHideShow(playerViewModel.isMediaDataAvailable())
        slCustomBShOnMaximized()

        llMiniMusicPlayer.setOnClickListener {
            //Mini player show. when mini player click
            toggleMiniPlayerView(false)
        }
        //DO NOT Call this function multiple times
        //  playerViewModel.startObservePlayerProgress(this)
        //  routeDataArtistType()
    }

    val cacheRepository by lazy {
        CacheRepository(this)
    }

    private fun searchFragmentAccess() {
        val patch = intent.extras!!.getBundle(PatchItem)!!
            .getSerializable(PatchItem) as HomePatchItemModel

        setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_search,
            Bundle().apply {
                putSerializable(
                    PatchItem,
                    patch as Serializable
                )
            })
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
                setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_music_video, Bundle().apply {
                    putString(DataContentType.TITLE, "Music Video")
                })

            }
        }
    }

    private fun routeDataHomeFragment(homePatchItem: HomePatchItemModel, selectedIndex: Int?) {
        if (selectedIndex != null) {
            //Single Item Click event
            val homePatchDetail = homePatchItem.Data[selectedIndex]
            when (homePatchDetail.ContentType.toUpperCase()) {
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
                DataContentType.CONTENT_TYPE_PD -> {
                    //open podcast
                    setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_podcast_details,
                        Bundle().apply {
                            putSerializable(
                                PatchItem,
                                homePatchItem as Serializable
                            )
                            putSerializable(
                                AppConstantUtils.PatchDetail,
                                homePatchDetail as Serializable
                            )
                        })
                }
            }
        } else {
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
                DataContentType.CONTENT_TYPE_PD -> {
                    //open podcast
                    setupNavGraphAndArg(R.navigation.my_bl_sdk_nav_graph_podcast_list_and_details,
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

    private fun setupNavGraphAndArg(@NavigationRes1 graphResId: Int, bundleData: Bundle) {
        val inflater = navHostFragment.navController.navInflater
        val navGraph = inflater.inflate(graphResId)
        navController.setGraph(navGraph, bundleData)
    }

    private fun createPlayerVM() {
        playerViewModel = ViewModelProvider(
            this, injector.playerViewModelFactory
        )[PlayerViewModel::class.java]
    }

    private fun miniMusicPlayerHideShow(playing: Boolean) {
        // at fast show mini player
        // getDPfromPX paramerer pass pixel. how many height layout show.
        // this mini player height 72dp thats why i set 73dp view show
        if (playing) {
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
                    llMiniMusicPlayer.visibility = View.VISIBLE
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
        Log.e("SDKMA", "setMusic: " + mSongDetails[clickItemPosition].isSeekAble)
        /* if(BuildConfig.DEBUG){
       mSongDetails.forEach {
           it.PlayUrl = "https://cdn.pixabay.com/download/audio/2022/01/14/audio_88400099c4.mp3?filename=madirfan-demo-20-11-2021-14154.mp3"
       }
   }*/
        playerViewModel.unSubscribe()
        playerViewModel.subscribe(
            MusicPlayList(
                UtilHelper.getMusicListToSongDetailList(mSongDetails),
                0
            ),
            false,
            clickItemPosition
        )

        miniMusicPlayerHideShow(true)
        if (mSongDetails[clickItemPosition].isSeekAble!!) {
            setupMainMusicPlayerAdapter(mSongDetails, clickItemPosition)
        }
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

        playerViewModel.playerProgress.observe(this, Observer {
            sbCurrentPlaySongStatus.progress = it.currentPosition?.toInt() ?: 0
            sbCurrentPlaySongStatus.max = it.duration?.toInt() ?: 0
            tvCurrentPlayDuration.text = it.currentPositionTimeLabel()
            tvTotalPlayDuration.text = if (it.durationTimeLabel() != "-153722867280912:0-55") {
                it.durationTimeLabel()
            } else {
                "0:00"
            }
            sbCurrentPlaySongStatus.secondaryProgress = it.bufferPosition?.toInt()!!
        })

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
//                setControlColor(false, ibtnControl)
                }
                PlaybackStateCompat.REPEAT_MODE_ONE -> {
                    setResource(ibtnRepeatSong, R.drawable.my_bl_sdk_ic_baseline_repeat_one_on_24)
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
        }

        ibtnQueueMusic.setOnClickListener {
        }

        ibtnDownload.setOnClickListener {
        }

        acivMinimizePlayerBtn.setOnClickListener {
            toggleMiniPlayerView(true)
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

        playerViewModel.startObservePlayerProgress(this)
        playerViewModel.playerProgress.observe(this) {
            tvTotalDurationMini.text = it.currentPositionTimeLabel()
        }

        playerViewModel.playbackStateLiveData.observe(this) {
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
            View.inflate(context, R.layout.my_bl_sdk_bottomsheet_three_dot_menu_layout, null)
        bottomSheetDialog.setContentView(contentView)
        bottomSheetDialog.show()
        val closeButton: ImageView? = bottomSheetDialog.findViewById(R.id.closeButton)
        closeButton?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
//        val imageArtist: ImageView? = bottomSheetDialog.findViewById(R.id.imgAlbum)
        val textAlbum: TextView? = bottomSheetDialog.findViewById(R.id.tvAlbums)
        textAlbum?.text = "Go to Artist"
        val image: ImageView? = bottomSheetDialog.findViewById(R.id.thumb)
//        val url = argHomePatchDetail?.image
        val title: TextView? = bottomSheetDialog.findViewById(R.id.name)
        title?.text = argHomePatchDetail?.title
        val artistname = bottomSheetDialog.findViewById<TextView>(R.id.desc)
        artistname?.text = mSongDetails.artistName
        if (image != null) {
            Glide.with(context)
                ?.load(UtilHelper.getImageUrlSize300(argHomePatchDetail?.image!!))
                ?.into(image)
        }
        val downloadImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgDownload)
        val textViewDownloadTitle: TextView? = bottomSheetDialog.findViewById(R.id.tv_download)
        var isDownloaded = false
        val downloaded = cacheRepository.getDownloadById(mSongDetails.content_Id!!)
        if (downloaded?.track != null) {
            isDownloaded = true
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_ic_delete)
        } else {
            isDownloaded = false
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_icon_dowload)
        }

        if (isDownloaded) {
            textViewDownloadTitle?.text = "Remove From Download"
        } else {
            textViewDownloadTitle?.text = "Download Offline"
        }
        val constraintDownload: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintDownload)
        constraintDownload?.setOnClickListener {
            if (isDownloaded.equals(true)) {
                cacheRepository.deleteDownloadById(mSongDetails.content_Id!!)
                DownloadService.sendRemoveDownload(
                    applicationContext,
                    MyBLDownloadService::class.java,
                    mSongDetails.content_Id!!,
                    false
                )
                val localBroadcastManager = LocalBroadcastManager.getInstance(applicationContext)
                val localIntent = Intent("DELETED")
                    .putExtra("contentID", mSongDetails.content_Id!!)
                localBroadcastManager.sendBroadcast(localIntent)

            } else {
                val url = "${Constants.FILE_BASE_URL}${mSongDetails.playingUrl}"
                val downloadRequest: DownloadRequest =
                    DownloadRequest.Builder(mSongDetails.content_Id!!, url.toUri())
                        .build()
                DownloadService.sendAddDownload(
                    applicationContext,
                    MyBLDownloadService::class.java,
                    downloadRequest,
                    /* foreground= */ false
                )
                if (cacheRepository.isDownloadCompleted().equals(true)) {
//                if (cacheRepository.isDownloadCompleted(mSongDetails.ContentID).equals(true)) {
                    cacheRepository.insertDownload(
                        DownloadedContent(
                            mSongDetails.content_Id!!.toString(),
                            mSongDetails.rootContentId!!,
                            mSongDetails.imageUrl!!,
                            mSongDetails.titleName!!,
                            mSongDetails.content_Type!!,
                            mSongDetails.playingUrl,
                            mSongDetails.content_Type!!,
                            1,
                            0,
                            mSongDetails.artistName!!,
                            mSongDetails.total_duration!!
                        )
                    )
//                    Log.e("TAGGG",
//                        "INSERTED: " + cacheRepository.isTrackDownloaded())
                    Log.e(
                        "TAGGG",
                        "COMPLETED: " + cacheRepository.isDownloadCompleted()
                    )
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
    }

    fun showBottomSheetDialogForPodcast(
        bsdNavController: NavController,
        context: Context,
        track: IMusicModel,
        argHomePatchItem: HomePatchItemModel?,
        argHomePatchDetail: HomePatchDetailModel?,
    ) {
        val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)

        val contentView =
            View.inflate(context, R.layout.my_bl_sdk_bottomsheet_three_dot_menu_layout, null)
        bottomSheetDialog.setContentView(contentView)
        bottomSheetDialog.show()
        val closeButton: ImageView? = bottomSheetDialog.findViewById(R.id.closeButton)
        closeButton?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val image: ImageView? = bottomSheetDialog.findViewById(R.id.thumb)
        val url = track.imageUrl
        val title: TextView? = bottomSheetDialog.findViewById(R.id.name)
        title?.text = track.titleName
        val artistname = bottomSheetDialog.findViewById<TextView>(R.id.desc)
        artistname?.text = track.album_Id
        if (image != null) {
            Glide.with(context)?.load(url?.replace("<\$size\$>", "300"))?.into(image)
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
//
//            )

            bottomSheetDialog.dismiss()
        }
        constraintAlbum?.visibility = GONE
        val downloadImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgDownload)
        val textViewDownloadTitle: TextView? = bottomSheetDialog.findViewById(R.id.tv_download)
        var isDownloaded = false
        var downloaded = cacheRepository.getDownloadById(track.album_Id!!)
        if (downloaded?.track != null) {
            isDownloaded = true
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_ic_delete)
        } else {
            isDownloaded = false
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_icon_dowload)
        }

        if (isDownloaded) {
            textViewDownloadTitle?.text = "Remove From Download"
        } else {
            textViewDownloadTitle?.text = "Download Offline"
        }
        val constraintDownload: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintDownload)
        constraintDownload?.setOnClickListener {
            if (isDownloaded.equals(true)) {
                cacheRepository.deleteDownloadById(track.album_Id!!)
                DownloadService.sendRemoveDownload(
                    applicationContext,
                    MyBLDownloadService::class.java,
                    track.album_Id!!,
                    false
                )
                val localBroadcastManager = LocalBroadcastManager.getInstance(applicationContext)
                val localIntent = Intent("DELETED")
                    .putExtra("contentID", track.album_Id!!)
                localBroadcastManager.sendBroadcast(localIntent)

            } else {
                val url = "${Constants.FILE_BASE_URL}${track.playingUrl}"
                val downloadRequest: DownloadRequest =
                    DownloadRequest.Builder(track.album_Id!!, url.toUri())
                        .build()
                DownloadService.sendAddDownload(
                    applicationContext,
                    MyBLDownloadService::class.java,
                    downloadRequest,
                    /* foreground= */ false
                )

                if (cacheRepository.isDownloadCompleted().equals(true)) {
                    cacheRepository.insertDownload(
                        DownloadedContent(
                            track.album_Id!!.toString(),
                            track.rootContentId!!,
                            track.imageUrl!!,
                            track.titleName!!,
                            track.content_Type!!,
                            track.playingUrl,
                            "N",
                            1,
                            0,
                            track.titleName!!,
                            track.total_duration!!
                        )
                    )
                    Log.e(
                        "TAGGG",
                        "INSERTED: " + cacheRepository.isDownloadCompleted()
                    )
                }
            }
            bottomSheetDialog.dismiss()
        }

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
            View.inflate(context, R.layout.my_bl_sdk_bottomsheet_three_dot_menu_layout, null)
        bottomSheetDialog.setContentView(contentView)
        bottomSheetDialog.show()
        val closeButton: ImageView? = bottomSheetDialog.findViewById(R.id.closeButton)
        closeButton?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val image: ImageView? = bottomSheetDialog.findViewById(R.id.thumb)
        val url = mSongDetails.imageUrl
        val title: TextView? = bottomSheetDialog.findViewById(R.id.name)
        title?.text = mSongDetails.titleName
        val artistname = bottomSheetDialog.findViewById<TextView>(R.id.desc)
        artistname?.text = mSongDetails.artistName
        if (image != null) {
            Glide.with(context).load(UtilHelper.getImageUrlSize300(url!!)).into(image)
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
        val textViewDownloadTitle: TextView? = bottomSheetDialog.findViewById(R.id.tv_download)
        var isDownloaded = false
        var downloaded = cacheRepository.getDownloadById(mSongDetails.content_Id!!)
        if (downloaded?.track != null) {
            isDownloaded = true
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_ic_delete)
        } else {
            isDownloaded = false
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_icon_dowload)
        }

        if (isDownloaded) {
            textViewDownloadTitle?.text = "Remove From Download"
        } else {
            textViewDownloadTitle?.text = "Download Offline"
        }
        val constraintDownload: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintDownload)
        constraintDownload?.setOnClickListener {
            if (isDownloaded.equals(true)) {
                cacheRepository.deleteDownloadById(mSongDetails.content_Id!!)
                DownloadService.sendRemoveDownload(
                    applicationContext,
                    MyBLDownloadService::class.java,
                    mSongDetails.content_Id!!,
                    false
                )
                Log.e("TAG", "DELETED: " + isDownloaded)
                val localBroadcastManager = LocalBroadcastManager.getInstance(applicationContext)
                val localIntent = Intent("DELETED")
                    .putExtra("contentID", mSongDetails.content_Id!!)
                localBroadcastManager.sendBroadcast(localIntent)

            } else {
                val url = "${Constants.FILE_BASE_URL}${mSongDetails.playingUrl!!}"
                val downloadRequest: DownloadRequest =
                    DownloadRequest.Builder(mSongDetails.content_Id!!, url.toUri())
                        .build()
                DownloadService.sendAddDownload(
                    applicationContext,
                    MyBLDownloadService::class.java,
                    downloadRequest,
                    /* foreground= */ false
                )

                if (cacheRepository.isDownloadCompleted().equals(true)) {
                    cacheRepository.insertDownload(
                        DownloadedContent(
                            mSongDetails.content_Id.toString(),
                            mSongDetails.rootContentId!!,
                            mSongDetails.imageUrl!!,
                            mSongDetails.titleName!!,
                            mSongDetails.content_Type!!,
                            mSongDetails.playingUrl!!,
                            mSongDetails.content_Type!!,
                            1,
                            0,
                            mSongDetails.artistName!!,
                            mSongDetails.total_duration!!
                        )
                    )
                    Log.e(
                        "TAGGG",
                        "INSERTED: " + cacheRepository.isDownloadCompleted()
                    )
                }
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
            View.inflate(context, R.layout.my_bl_sdk_bottomsheet_three_dot_menu_layout, null)
        bottomSheetDialog.setContentView(contentView)
        bottomSheetDialog.show()
        val closeButton: ImageView? = bottomSheetDialog.findViewById(R.id.closeButton)
        closeButton?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val artistname = bottomSheetDialog.findViewById<TextView>(R.id.desc)
        artistname?.text = mSongDetails.artistName
        val image: ImageView? = bottomSheetDialog.findViewById(R.id.thumb)
        val url = mSongDetails.imageUrl
        val title: TextView? = bottomSheetDialog.findViewById(R.id.name)
        title?.text = mSongDetails?.titleName
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
            Log.d("TAG", "CLICK: " + argHomePatchItem)
            Log.d("TAG", "CLICK: " + argHomePatchDetail)
            Log.d("TAG", "CLICK: " + mSongDetails)
            bottomSheetDialog.dismiss()
        }
        val downloadImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgDownload)
        val textViewDownloadTitle: TextView? = bottomSheetDialog.findViewById(R.id.tv_download)
        var isDownloaded = false
        var downloaded = cacheRepository.getDownloadById(mSongDetails.content_Id!!)
        if (downloaded?.track != null) {
            isDownloaded = true
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_ic_delete)
        } else {
            isDownloaded = false
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_icon_dowload)
        }

        if (isDownloaded) {
            textViewDownloadTitle?.text = "Remove From Download"
        } else {
            textViewDownloadTitle?.text = "Download Offline"
        }
        val constraintDownload: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintDownload)
        constraintDownload?.setOnClickListener {
            if (isDownloaded.equals(true)) {
                cacheRepository.deleteDownloadById(mSongDetails.content_Id!!)
                DownloadService.sendRemoveDownload(
                    applicationContext,
                    MyBLDownloadService::class.java,
                    mSongDetails.content_Id!!,
                    false
                )
                Log.e("TAG", "DELETED: " + isDownloaded)
                val localBroadcastManager = LocalBroadcastManager.getInstance(applicationContext)
                val localIntent = Intent("DELETED")
                    .putExtra("contentID", mSongDetails.content_Id!!)
                localBroadcastManager.sendBroadcast(localIntent)

            } else {
                val url = "${Constants.FILE_BASE_URL}${mSongDetails.playingUrl}"
                val downloadRequest: DownloadRequest =
                    DownloadRequest.Builder(mSongDetails.content_Id!!, url.toUri())
                        .build()
                DownloadService.sendAddDownload(
                    applicationContext,
                    MyBLDownloadService::class.java,
                    downloadRequest,
                    /* foreground= */ false
                )

                if (cacheRepository.isDownloadCompleted().equals(true)) {
                    cacheRepository.insertDownload(
                        DownloadedContent(
                            mSongDetails.content_Id.toString(),
                            mSongDetails.rootContentId!!,
                            mSongDetails.imageUrl!!,
                            mSongDetails.titleName!!,
                            mSongDetails.content_Type!!,
                            mSongDetails.playingUrl!!,
                            mSongDetails.content_Type!!,
                            1,
                            0,
                            mSongDetails.artistName!!,
                            mSongDetails.total_duration!!
                        )
                    )
                    Log.e(
                        "TAGGG",
                        "INSERTED: " + cacheRepository.isDownloadCompleted()
                    )
                }
            }
            bottomSheetDialog.dismiss()
        }
    }

    private fun gotoArtist(
        bsdNavController: NavController,
        context: Context,
        mSongDetails: IMusicModel,
        argHomePatchItem: HomePatchItemModel?,
        argHomePatchDetail: HomePatchDetailModel?,

        ) {
        //  Log.e("Check", ""+bsdNavController.graph.displayName)
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
        argHomePatchDetail: HomePatchDetailModel?,

        ) {
        bsdNavController.navigate(R.id.artist_details_fragment,
            Bundle().apply {
                putSerializable(
                    PatchItem,
                    argHomePatchItem
                )
                putSerializable(
                    AppConstantUtils.PatchDetail,
                    HomePatchDetailModel(
                        AlbumId = "",
                        ArtistId = mSongDetails.artist_Id ?: "",
                        ContentID = mSongDetails.content_Id ?: "",
                        ContentType = "",
                        PlayUrl = "",
                        AlbumName = "",
                        AlbumImage = "",
                        fav = "",
                        Banner = "",
                        Duration = "",
                        TrackType = "",
                        image = mSongDetails.imageUrl ?: "",
                        ArtistImage = "",
                        Artist = mSongDetails.artistName ?: "",
                        CreateDate = "",
                        Follower = "",
                        imageWeb = "",
                        IsPaid = false,
                        NewBanner = "",
                        PlayCount = 0,
                        PlayListId = "",
                        PlayListImage = "",
                        PlayListName = "",
                        RootId = "",
                        RootType = "P",
                        Seekable = false,
                        TeaserUrl = "",
                        title = "",
                        Type = ""
                    ) as Serializable
                )
            })
    }

    private fun gotoAlbum(
        bsdNavController: NavController,
        context: Context,
        mSongDetails: IMusicModel,
        argHomePatchItem: HomePatchItemModel?,
        argHomePatchDetail: HomePatchDetailModel?,

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
}