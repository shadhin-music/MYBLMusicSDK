package com.shadhinmusiclibrary.activities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.blue
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.MusicPlayAdapter
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.di.ActivityEntryPoint
import com.shadhinmusiclibrary.fragments.artist.BottomSheetArtistDetailsFragment
import com.shadhinmusiclibrary.library.discretescrollview.DSVOrientation
import com.shadhinmusiclibrary.library.discretescrollview.DiscreteScrollView
import com.shadhinmusiclibrary.library.discretescrollview.transform.ScaleTransformer
import com.shadhinmusiclibrary.library.slidinguppanel.SlidingUpPanelLayout
import com.shadhinmusiclibrary.player.data.model.MusicPlayList
import com.shadhinmusiclibrary.player.ui.PlayerViewModel
import com.shadhinmusiclibrary.player.utils.isPlaying
import com.shadhinmusiclibrary.utils.*
import com.shadhinmusiclibrary.utils.AppConstantUtils
import com.shadhinmusiclibrary.utils.AppConstantUtils.PatchItem
import java.io.Serializable
import androidx.annotation.NavigationRes as NavigationRes1


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

    private lateinit var mainMusicPlayerAdapter: MusicPlayAdapter

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
        mainMusicPlayerAdapter = MusicPlayAdapter(this)

        //Will received data from Home Fragment from MYBLL App
        val patch = intent.extras!!.getBundle(PatchItem)!!
            .getSerializable(PatchItem) as HomePatchItem
        var selectedPatchIndex: Int? = null
        if (intent.hasExtra(AppConstantUtils.SelectedPatchIndex)) {
            selectedPatchIndex = intent.extras!!.getInt(AppConstantUtils.SelectedPatchIndex)
        }
        routeData(patch, selectedPatchIndex)

        playerViewModel.currentMusicLiveData.observe(this) { itMus ->
            if (itMus != null) {
                setupMiniMusicPlayerAndFunctionality(UtilHelper.getSongDetailToMusic(itMus))
                isPlayOrPause = itMus.isPlaying!!
            }
        }

        playerViewModel.playListLiveData.observe(this) { itMusicList ->
            playerViewModel.musicIndexLiveData.observe(this) {
                setupMainMusicPlayerAdapter(
                    UtilHelper.getSongDetailToMusicList(itMusicList.list.toMutableList()),
                    it
                )
            }
        }

        miniPlayerHideShow(playerViewModel.isMediaDataAvailable())
        slCustomBShOnMaximized()

        cvMiniPlayer.setOnClickListener {
            //Mini player show. when mini player click
            toggleMiniPlayerView(false)
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
                    setupNavGraphAndArg(R.navigation.nav_graph_album_details,
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
                    setupNavGraphAndArg(R.navigation.nav_graph_playlist_details,
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
                    setupNavGraphAndArg(R.navigation.nav_graph_s_type_details,
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
                    setupNavGraphAndArg(R.navigation.nav_graph_podcast_details,
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
            when (homePatchItem.ContentType.uppercase()) {
                DataContentType.CONTENT_TYPE_A -> {
                    //open artist details
                    setupNavGraphAndArg(R.navigation.nav_graph_artist_list_details,
                        Bundle().apply {
                            putSerializable(
                                PatchItem,
                                homePatchItem as Serializable
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_R -> {
                    //open artist details
                    setupNavGraphAndArg(R.navigation.nav_graph_album_list,
                        Bundle().apply {
                            putSerializable(
                                PatchItem,
                                homePatchItem
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_P -> {
                    //open playlist
                    setupNavGraphAndArg(R.navigation.nav_graph_playlist_list,
                        Bundle().apply {
                            putSerializable(
                                PatchItem,
                                homePatchItem as Serializable
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_S -> {
                    //open songs
                    setupNavGraphAndArg(R.navigation.nav_graph_s_type_list_details,
                        Bundle().apply {
                            putSerializable(
                                PatchItem,
                                homePatchItem as Serializable
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_PD -> {
                    //open podcast
                    setupNavGraphAndArg(R.navigation.nav_graph_podcast_list_and_details,
                        Bundle().apply {
                            putSerializable(
                                PatchItem,
                                homePatchItem as Serializable
                            )
                            Log.d("TAG", "CLICK ITEM123: " + PatchItem)
                        })
                }
                DataContentType.CONTENT_TYPE_V-> {
                    //open podcast
                    setupNavGraphAndArg(R.navigation.nav_graph_video_list_and_details,
                        Bundle().apply {
                            putSerializable(
                                PatchItem,
                                homePatchItem as Serializable
                            )
                            Log.d("TAG", "CLICK ITEM123: " + PatchItem)
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

    private fun setupNavGraphAndArg(
        bsdNavController: NavController,
        @NavigationRes1 graphResId: Int,
        bundleData: Bundle,
    ) {
        val inflater = navHostFragment.navController.navInflater
        val navGraph = inflater.inflate(graphResId)
        bsdNavController.setGraph(navGraph, bundleData)
    }

    private fun createPlayerVM() {
        playerViewModel = ViewModelProvider(
            this, injector.playerViewModelFactory
        )[PlayerViewModel::class.java]
        playerViewModel.connect()
    }

    private fun miniPlayerHideShow(playing: Boolean) {
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
                newState: SlidingUpPanelLayout.PanelState?,
            ) {
            }
        })
    }

    fun setMusicPlayerInitData(mSongDetails: MutableList<SongDetail>, clickItemPosition: Int) {
        playerViewModel.unSubscribe()
        playerViewModel.subscribe(
            MusicPlayList(
                UtilHelper.getMusicListToSongDetailList(mSongDetails),
                0
            ),
            false,
            clickItemPosition
        )
        miniPlayerHideShow(true)
        setupMainMusicPlayerAdapter(mSongDetails, clickItemPosition)
    }

    private fun miniPlayerPlayPauseState(playing: Boolean) {
        if (playing) {
            ibtnPlayPauseMini.setImageResource(R.drawable.ic_baseline_pause_24)
        } else {
            ibtnPlayPauseMini.setImageResource(R.drawable.ic_baseline_play_arrow_black_24)
        }
    }

    private fun mainPlayerPlayPauseState(playing: Boolean) {
        if (playing) {
            ibtnPlayPause.setImageResource(R.drawable.ic_baseline_pause_circle_filled_60)
        } else {
            ibtnPlayPause.setImageResource(R.drawable.ic_baseline_play_circle_filled_60)
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

    private fun setupMainMusicPlayerAdapter(
        mSongDetails: MutableList<SongDetail>,
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
        dsvCurrentPlaySongsThumb.addScrollStateChangeListener(this)
//        dsvCurrentPlaySongsThumb.addOnItemChangedListener(this)

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
            }
        })

        playerViewModel.playbackStateLiveData.observe(this, Observer {
            mainPlayerPlayPauseState(it.isPlaying)
        })

        playerViewModel.repeatModeLiveData.observe(this, Observer {
            when (it) {
                PlaybackStateCompat.REPEAT_MODE_NONE -> {
                    setResource(ibtnRepeatSong, R.drawable.ic_baseline_repeat_24)
                    ibtnShuffle.isEnabled = true
                    ibtnShuffle.setColorFilter(0)
//                setControlColor(false, ibtnControl)
                }
                PlaybackStateCompat.REPEAT_MODE_ONE -> {
                    setResource(ibtnRepeatSong, R.drawable.ic_baseline_repeat_one_on_24)
                    ibtnShuffle.isEnabled = false
                    ibtnShuffle.setColorFilter(
                        ContextCompat.getColor(
                            this,
                            R.color.colorTransparent
                        ), PorterDuff.Mode.SRC_IN
                    )
//                setControlColor(true, ibtnControl)
                }
                PlaybackStateCompat.REPEAT_MODE_ALL -> {
                    setResource(ibtnRepeatSong, R.drawable.ic_baseline_repeat_on_24)
//                setControlColor(true, ibtnControl)
                    ibtnShuffle.isEnabled = true
                    ibtnShuffle.setColorFilter(0)
                }
            }
        })

        playerViewModel.shuffleLiveData.observe(this, Observer {
            when (it) {
                PlaybackStateCompat.SHUFFLE_MODE_NONE -> {
//                    setControlColor(false, ibtnShuffle)
                    ibtnShuffle.setImageResource(R.drawable.ic_baseline_shuffle_24)
                }
                PlaybackStateCompat.SHUFFLE_MODE_ALL -> {
//                    setControlColor(true, ibtnShuffle)
                    ibtnShuffle.setImageResource(R.drawable.ic_baseline_shuffle_on_24)
                }
            }
        })

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
//            setControlColor(true, ibtnVolume)
        }

        ibtnLibraryAdd.setOnClickListener {
//            setControlColor(true, ibtnLibraryAdd)
        }

        ibtnQueueMusic.setOnClickListener {
//            setControlColor(true, ibtnQueueMusic)
        }

        ibtnDownload.setOnClickListener {
//            setControlColor(true, ibtnDownload)
        }

        acivMinimizePlayerBtn.setOnClickListener {
            toggleMiniPlayerView(true)
        }
    }

    private fun setupMiniMusicPlayerAndFunctionality(mSongDetails: SongDetail) {
        Glide.with(this)
            .load(mSongDetails.getImageUrl300Size())
            .transition(DrawableTransitionOptions().crossFade(500))
            .fitCenter()
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA))
            .placeholder(R.drawable.default_song)
            .error(R.drawable.default_song)
            .into(ivSongThumbMini)
        setMainPlayerBackgroundColor(getBitmapFromIV(ivSongThumbMini))

        tvSongNameMini.text = mSongDetails.title
        tvSingerNameMini.text = mSongDetails.artist
        tvTotalDurationMini.text = TimeParser.secToMin(mSongDetails.duration)
        cvMiniPlayer.visibility = View.VISIBLE

        playerViewModel.playerProgress.observe(this, Observer {
            tvTotalDurationMini.text = it.currentPositionTimeLabel()
        })

        playerViewModel.playbackStateLiveData.observe(this, Observer {
            miniPlayerPlayPauseState(it.isPlaying)
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

    override fun onCurrentItemChanged(
        viewHolder: MusicPlayAdapter.MusicPlayVH?,
        adapterPosition: Int,
    ) {
        //ToDO testing are running rezaul khan
        if (viewHolder != null) {
            viewHolder.sMusicData.artist
            tvSongName.text = viewHolder.sMusicData.title
            tvSingerName.text = viewHolder.sMusicData.artist
            setMainPlayerBackgroundColor(getBitmapFromVH(viewHolder))
//        }
//
//
//        if (viewHolder != null) {
//            setMainPlayerBackgroundColor(getBitmapFromVH(viewHolder))

            playerViewModel.skipToQueueItem(adapterPosition)
        }
    }

    override fun onScrollStart(
        currentItemHolder: MusicPlayAdapter.MusicPlayVH,
        adapterPosition: Int,
    ) {
    }

    override fun onScrollEnd(
        currentItemHolder: MusicPlayAdapter.MusicPlayVH,
        adapterPosition: Int,
    ) {
        //ToDO testing are running rezaul khan
//        if (currentItemHolder != null) {
//            currentItemHolder.sMusicData.artist
//            tvSongName.text = currentItemHolder.sMusicData.title
//            tvSingerName.text = currentItemHolder.sMusicData.artist
//            setMainPlayerBackgroundColor(getBitmapFromVH(currentItemHolder))
//        }
    }

    override fun onScroll(
        scrollPosition: Float,
        currentPosition: Int,
        newPosition: Int,
        currentHolder: MusicPlayAdapter.MusicPlayVH?,
        newCurrent: MusicPlayAdapter.MusicPlayVH?,
    ) {
        //ToDO testing are running rezaul khan
//        if (currentHolder != null) {
//            setMainPlayerBackgroundColor(getBitmapFromVH(currentHolder))
//
//            playerViewModel.skipToQueueItem(newPosition)
//        }
    }

    private fun setMainPlayerBackgroundColor(imBitmapData: Bitmap) {
        val palette: Palette = Palette.from(imBitmapData).generate()
        val vibrantSwatch: Palette.Swatch? = palette.vibrantSwatch
        if (vibrantSwatch != null) {
            if (vibrantSwatch.rgb.red > 0.90 && vibrantSwatch.rgb.green > 0.90 && vibrantSwatch.rgb.blue > 0.90) {
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

    fun showBottomSheetDialog(
        bsdNavController: NavController,
        context: Context,
        mSongDetails: SongDetail,
        argHomePatchItem: HomePatchItem?,
        argHomePatchDetail: HomePatchDetail?,
    ) {
        Log.e("SDKMA", "showBottomSheetDialog: ")
        val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)

        val contentView =
            View.inflate(context, R.layout.bottomsheet_three_dot_menu_layout, null)
        bottomSheetDialog.setContentView(contentView)
        bottomSheetDialog.show()
        val closeButton: ImageView? = bottomSheetDialog.findViewById(R.id.closeButton)
        closeButton?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val image: ImageView? = bottomSheetDialog.findViewById(R.id.thumb)
        val url = argHomePatchDetail?.image
         val title:TextView ?= bottomSheetDialog.findViewById(R.id.name)
         title?.text = argHomePatchDetail?.title
        if (image != null) {
            Glide.with(context)?.load(url?.replace("<\$size\$>", "300"))?.into(image)
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

    private fun gotoArtist(
        bsdNavController: NavController,
        context: Context,
        mSongDetails: SongDetail,
        argHomePatchItem: HomePatchItem?,
        argHomePatchDetail: HomePatchDetail?,

        ) {
        Log.e("SDKMA", "gotoArtist: ")
        when (argHomePatchDetail?.ContentType?.uppercase()) {
            DataContentType.CONTENT_TYPE_A -> {
                //open artist details
                bsdNavController.navigate(R.id.action_album_fragment_to_artist_details_fragment,
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
            /*   DataContentType.CONTENT_TYPE_R -> {
                   //open album details
                   setupNavGraphAndArg(R.navigation.nav_graph_album_details,
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
               DataContentType.CONTENT_TYPE_P -> {
                   //open playlist
                   setupNavGraphAndArg(R.navigation.nav_graph_playlist_details,
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
               DataContentType.CONTENT_TYPE_S -> {
                   //open songs
                   setupNavGraphAndArg(R.navigation.nav_graph_s_type_details,
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
               DataContentType.CONTENT_TYPE_PD -> {
                   //open podcast
                   setupNavGraphAndArg(R.navigation.nav_graph_podcast_details,
                       Bundle().apply {
                           putSerializable(
                               PatchItem,
                               argHomePatchItem as Serializable
                           )
                           putSerializable(
                               AppConstantUtils.PatchDetail,
                               argHomePatchDetail as Serializable
                           )
                       })
               }*/
        }
//        val manager: FragmentManager = supportFragmentManager
//        manager.beginTransaction()
//            .replace(R.id.frame, BottomSheetArtistDetailsFragment.newInstance(mSongDetails,argHomePatchItem,argHomePatchDetail))
//            .addToBackStack("Fragment")
//            .commit()
//        Log.e("TAGGY","SONGDETAILS: "+ argHomePatchItem)
    }
}