package com.shadhinmusiclibrary.activities

import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.NavigationRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.MusicPlayAdapter
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.di.ActivityEntryPoint
import com.shadhinmusiclibrary.library.discretescrollview.DSVOrientation
import com.shadhinmusiclibrary.library.discretescrollview.DiscreteScrollView
import com.shadhinmusiclibrary.library.discretescrollview.transform.ScaleTransformer
import com.shadhinmusiclibrary.library.slidinguppanel.SlidingUpPanelLayout
import com.shadhinmusiclibrary.player.Constants
import com.shadhinmusiclibrary.player.data.model.Music
import com.shadhinmusiclibrary.player.data.model.MusicPlayList
import com.shadhinmusiclibrary.player.ui.PlayerViewModel
import com.shadhinmusiclibrary.player.utils.isPlaying
import com.shadhinmusiclibrary.utils.AppConstantUtils
import com.shadhinmusiclibrary.utils.AppConstantUtils.PatchItem
import com.shadhinmusiclibrary.utils.DataContentType
import com.shadhinmusiclibrary.utils.ImageSizeParser
import com.shadhinmusiclibrary.utils.UtilHelper
import java.io.Serializable


internal class SDKMainActivity : BaseActivity(), ActivityEntryPoint,
    DiscreteScrollView.OnItemChangedListener<MusicPlayAdapter.MusicPlayVH>,
    DiscreteScrollView.ScrollStateChangeListener<MusicPlayAdapter.MusicPlayVH> {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private var isPlayOrPause = false
    private lateinit var slCustomBottomSheet: SlidingUpPanelLayout

    //mini music player
    private lateinit var cvMiniPlayer: CardView
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

    private lateinit var listData: MutableList<HomePatchDetail>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sdk_main)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_navigation_host) as NavHostFragment
        navController = navHostFragment.navController
        slCustomBottomSheet = findViewById(R.id.sl_custom_bottom_sheet)
        rlContentMain = findViewById(R.id.rl_content_main)
        createPlayerVM()

        uiInitMiniMusicPlayer()
        uiInitMainMusicPlayer()

        //Will received data from Home Fragment from MYBLL App
        val patch = intent.extras!!.getBundle(AppConstantUtils.PatchItem)!!
            .getSerializable(AppConstantUtils.PatchItem) as HomePatchItem
        var selectedPatchIndex: Int? = null
        if (intent.hasExtra(AppConstantUtils.SelectedPatchIndex)) {
            selectedPatchIndex = intent.extras!!.getInt(AppConstantUtils.SelectedPatchIndex)
        }
        routeData(patch, selectedPatchIndex)

        playerViewModel.currentMusicLiveData.observe(this, Observer {
            if (it != null) {
                setMiniMusicPlayerData(UtilHelper.getSongDetailToMusic(it))
            }
        })

        slCustomBShOnMaximized()

        setMusicBannerAdapter()
        mainMusicPlayerUiFunctionality()
    }

    private fun createPlayerVM() {
        playerViewModel = ViewModelProvider(
            this, injector.playerViewModelFactory
        )[PlayerViewModel::class.java]
        playerViewModel.connect()
    }

    private fun miniPlayerShowHide(playing: Boolean) {
        //at fast show mini player
        // getDPfromPX paramerer pass pixel. how many height layout show.
        // this mini player height 72dp thats why i set 73dp view show
        if (playing) {
            slCustomBottomSheet.panelHeight = ImageSizeParser.getDPfromPX(73, this)
            slCustomBottomSheet.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }
    }

    private fun uiInitMiniMusicPlayer() {
        cvMiniPlayer = findViewById(R.id.include_mini_music_player)
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

    private fun slCustomBShOnMaximized() {
        //when music player up side then mini player hide and big player show.
        //and when big player hide mini player show
        slCustomBottomSheet.addPanelSlideListener(object :
            SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                cvMiniPlayer.alpha = (1 - slideOffset)
                clMainMusicPlayer.alpha = slideOffset
                if (slideOffset == 1f) {
                    playerMode = PlayerMode.MAXIMIZED
                    cvMiniPlayer.visibility = View.GONE
                } else {
                    playerMode = PlayerMode.MINIMIZED
                    cvMiniPlayer.visibility = View.VISIBLE
                }
                val params = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
                )
                rlContentMain.layoutParams = params
            }

            override fun onPanelStateChanged(
                panel: View?,
                previousState: SlidingUpPanelLayout.PanelState?,
                newState: SlidingUpPanelLayout.PanelState?
            ) {
            }
        })
    }

    private fun setupNavGraphAndArg(@NavigationRes graphResId: Int, bundleData: Bundle) {
        val inflater = navHostFragment.navController.navInflater
        val navGraph = inflater.inflate(graphResId)
        navController.setGraph(navGraph, bundleData)
    }

    fun setMiniMusicPlayerData(mSongDet: SongDetail) {
        playerViewModel.subscribe(
            MusicPlayList(
                mutableListOf<Music>().apply {
                    add(UtilHelper.getMusicToSongDetail(mSongDet))
                },
                0
            ),
            false,
            0
        )
        Glide.with(this)
            .load(mSongDet.getImageUrl300Size())
            .transition(DrawableTransitionOptions().crossFade(500))
            .fitCenter()
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA))
            .placeholder(R.drawable.ic_asif_300)
            .error(R.drawable.ic_asif_300)
            .into(ivSongThumbMini)
        tvSongNameMini.text = mSongDet.title
        tvSingerNameMini.text = mSongDet.artist
//        tvTotalDurationMini.text = TimeParser.secToMin(mSongDet.duration)
        playerViewModel.startObservePlayerProgress(this)
        cvMiniPlayer.visibility = View.VISIBLE
        playerViewModel.playerProgress.observe(this, Observer {
            tvTotalDurationMini.text = it.currentPositionTimeLabel()
        })

        playerViewModel.playbackStateLiveData.observe(this, Observer {
            playPauseState(it.isPlaying)
            miniPlayerShowHide(it.isPlaying)
        })

        ibtnSkipPreviousMini.setOnClickListener {
            playerViewModel.skipToPrevious()
        }

        ibtnPlayPauseMini.setOnClickListener {
            playerViewModel.togglePlayPause()
        }

        ibtnSkipNextMini.setOnClickListener {
            playerViewModel.skipToNext()
        }
    }

    private fun playPauseState(playing: Boolean) {
        if (playing) {
            ibtnPlayPauseMini.setImageResource(R.drawable.ic_baseline_pause_24)
        } else {
            ibtnPlayPauseMini.setImageResource(R.drawable.ic_baseline_play_arrow_black_24)
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
        if (isVisible) {
            playerMode = PlayerMode.MINIMIZED
            cvMiniPlayer.visibility = View.VISIBLE
            slCustomBottomSheet.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        } else {
            playerMode = PlayerMode.MAXIMIZED
            slCustomBottomSheet.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            cvMiniPlayer.visibility = View.INVISIBLE
        }
    }

    private fun routeData(homePatchItem: HomePatchItem, selectedIndex: Int?) {
        if (selectedIndex != null) {
            //Single Item Click event
            val homePatchDetail = homePatchItem.Data[selectedIndex]
            when (homePatchDetail.ContentType.uppercase()) {
                DataContentType.CONTENT_TYPE_A -> {
                    //open artist details
                    setupNavGraphAndArg(R.navigation.nav_graph_artist_details,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.PatchItem,
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
                    setupNavGraphAndArg(R.navigation.nav_graph_album_details,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.PatchItem,
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
                    setupNavGraphAndArg(R.navigation.nav_graph_playlist_details,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.PatchItem,
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
                    setupNavGraphAndArg(R.navigation.nav_graph_s_type_details,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.PatchItem,
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
                    setupNavGraphAndArg(R.navigation.nav_graph_podcast_details,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.PatchItem,
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
            when (homePatchItem.ContentType.uppercase()) {
                DataContentType.CONTENT_TYPE_A -> {
                    //open artist details
                    setupNavGraphAndArg(R.navigation.nav_graph_artist_list_details,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.PatchItem,
                                homePatchItem as Serializable
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_R -> {
                    //open artist details
                    setupNavGraphAndArg(R.navigation.nav_graph_album_list,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.PatchItem,
                                homePatchItem
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_P -> {
                    //open playlist
                    setupNavGraphAndArg(R.navigation.nav_graph_playlist_list,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.PatchItem,
                                homePatchItem as Serializable
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_S -> {
                    //open songs
                    setupNavGraphAndArg(R.navigation.nav_graph_s_type_list_details,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.PatchItem,
                                homePatchItem as Serializable
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_PD -> {
                    //open podcast
                    setupNavGraphAndArg(R.navigation.nav_graph_podcast_list_and_details,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.PatchItem,
                                homePatchItem as Serializable
                            )
                            Log.d("TAG", "CLICK ITEM123: " + PatchItem)
                        })
                }
            }
        }
    }

    private fun setMusicBannerAdapter() {
        val adapter = MusicPlayAdapter(this)
        listData = mutableListOf()
        for (i in 1..3) {
            listData.add(
                HomePatchDetail(
                    "",
                    "",
                    "test 11111",
                    "Habib",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    false,
                    "",
                    0,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    false,
                    "",
                    "",
                    "",
                    "",
                    "https://shadhinmusiccontent.sgp1.digitaloceanspaces.com/ArtistPreviewImageFile/HridoyKhan_<\$size\$>.jpg",
                    "",
                    ""
                )
            )
            listData.add(
                HomePatchDetail(
                    "",
                    "",
                    "test 2222",
                    "Balam",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    false,
                    "",
                    0,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    false,
                    "",
                    "",
                    "",
                    "",
                    "https://shadhinmusiccontent.sgp1.digitaloceanspaces.com/ArtistPreviewImageFile/Asif_<\$size\$>.jpg",
                    "",
                    ""
                )
            )
        }
        adapter.setMusicData(listData)

        dsvCurrentPlaySongsThumb.adapter = adapter
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
        dsvCurrentPlaySongsThumb.addScrollStateChangeListener(this)
        dsvCurrentPlaySongsThumb.addOnItemChangedListener(this)
    }

    private fun mainMusicPlayerUiFunctionality() {
        ibtnShuffle.setOnClickListener {
            setControlColor(ibtnShuffle)
        }

        ibtnSkipPrevious.setOnClickListener {

        }

        ibtnPlayPause.setOnClickListener {
            setControlColor(ibtnPlayPause)
        }

        ibtnSkipNext.setOnClickListener { }

        ibtnRepeatSong.setOnClickListener {
            setControlColor(ibtnRepeatSong)
        }

        ibtnVolume.setOnClickListener {
            setControlColor(ibtnVolume)
        }

        ibtnLibraryAdd.setOnClickListener {
            setControlColor(ibtnLibraryAdd)
        }

        ibtnQueueMusic.setOnClickListener {
            setControlColor(ibtnQueueMusic)
        }

        ibtnDownload.setOnClickListener {
            setControlColor(ibtnDownload)
        }
    }

    override fun onCurrentItemChanged(
        viewHolder: MusicPlayAdapter.MusicPlayVH?,
        adapterPosition: Int
    ) {
        if (viewHolder != null) {
            viewHolder.sMusicData.Artist
            tvSongName.text = viewHolder.sMusicData.AlbumName.toString()
            tvSingerName.text = viewHolder.sMusicData.Artist
            setPaletteGradientColor(getBitmapFromVH(viewHolder))
        }
    }

    override fun onScrollStart(
        currentItemHolder: MusicPlayAdapter.MusicPlayVH,
        adapterPosition: Int
    ) {
    }

    override fun onScrollEnd(
        currentItemHolder: MusicPlayAdapter.MusicPlayVH,
        adapterPosition: Int
    ) {
    }

    override fun onScroll(
        scrollPosition: Float,
        currentPosition: Int,
        newPosition: Int,
        currentHolder: MusicPlayAdapter.MusicPlayVH?,
        newCurrent: MusicPlayAdapter.MusicPlayVH?
    ) {
        if (currentHolder != null) {
            setPaletteGradientColor(getBitmapFromVH(currentHolder))
        }
    }

    private fun setPaletteGradientColor(imBitmapData: Bitmap) {
        val palette: Palette = Palette.from(imBitmapData).generate()
        val vibrantSwatch: Palette.Swatch? = palette.vibrantSwatch
        if (vibrantSwatch != null) {
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(
                    ContextCompat.getColor(this, R.color.shadinRequiredColor),
                    vibrantSwatch.rgb
                )
            )
            gradientDrawable.cornerRadius = 0f
            clMainMusicPlayer.background = gradientDrawable
        }
    }

    private fun getBitmapFromVH(currentItemHolder: MusicPlayAdapter.MusicPlayVH): Bitmap {
        val imageV = currentItemHolder.ivCurrentPlayImage
        val traDaw = imageV.drawable
        return (traDaw.toBitmap())
    }

    override fun onBackPressed() {
        if (playerMode === PlayerMode.MAXIMIZED) {
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
        super.onDestroy()
        playerViewModel.disconnect()
    }
}