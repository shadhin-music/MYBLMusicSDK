package com.shadhinmusiclibrary.activities.video

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.media.session.MediaSessionCompat
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.player.audio_focus.AudioFocusManager
import com.shadhinmusiclibrary.player.audio_focus.AudioFocusManagerFactory
import com.shadhinmusiclibrary.adapter.VideoAdapter
import com.shadhinmusiclibrary.data.model.Video
import com.shadhinmusiclibrary.di.ActivityEntryPoint
import com.shadhinmusiclibrary.player.ShadhinMusicQueueNavigator
import com.shadhinmusiclibrary.player.data.source.MediaSources
import com.shadhinmusiclibrary.player.data.source.ShadhinVideoMediaSource
import com.shadhinmusiclibrary.player.ui.PlayerViewModel
import com.shadhinmusiclibrary.utils.UtilHelper
import com.shadhinmusiclibrary.utils.calculateVideoHeight
import com.shadhinmusiclibrary.utils.px


internal class VideoActivity : AppCompatActivity(), ActivityEntryPoint,
    AudioManager.OnAudioFocusChangeListener {

    /**1144x480 OR 856x480*/
    private val videoWidth: Int = 856
    private val videoHeight: Int = 480

    private lateinit var mainLayout:FrameLayout
    private lateinit var adapter: VideoAdapter
    private lateinit var videoRecyclerView: RecyclerView
    private lateinit var videoTitleTextView: TextView
    private lateinit var videoDescTextView: TextView
    private lateinit var layoutToggle:ImageButton
    private lateinit var backButton:ImageButton
    private lateinit var fullscreenToggleButton:ImageButton
    private lateinit var mainProgressBar: ProgressBar
    private lateinit var bufferProgress: ProgressBar
    private lateinit var playerLayout: FrameLayout
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private lateinit var playerOnScaleGestureListener: PlayerOnScaleGestureListener

    private  var exoPlayer: ExoPlayer?=null
    private lateinit var playerView:PlayerView
    private lateinit var mediaSession:MediaSessionCompat
    private val contactMediaSource = ConcatenatingMediaSource()
    private lateinit var shadhinQueueNavigator: TimelineQueueNavigator
    private var  videoMediaSource: MediaSources?=null
    private lateinit var audioFocusManager: AudioFocusManager

    private var currentPosition = 0
    private var videoList: ArrayList<Video>? = ArrayList()
    private lateinit var viewModel: VideoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_bl_sdk_activity_video)
        initAudioFocus()
        setupUI()
        setupViewModel()
        setupAdapter()
        initData()
        initializePlayer()
        gestureSetup()
        observe()

    }


    private fun initAudioFocus() {
        audioFocusManager = AudioFocusManagerFactory.createAudioFocusManager()
        audioFocusManager.initialize(applicationContext,this)
        audioFocusManager.requestAudioFocus()
    }


    override fun onAudioFocusChange(focusChange: Int) {
        if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
            exoPlayer?.pause()
        }
    }



    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[VideoViewModel::class.java]

    }
    private fun setupUI() {

        setSupportActionBar(findViewById(R.id.toolbar))
       /* supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)*/
       // supportActionBar?.setHomeAsUpIndicator(R.drawable.my_bl_sdk_ic_arrow_back)
        
        supportActionBar?.title = ""
        findViewById<ImageView>(R.id.imageBack).setOnClickListener {
            onBackPressed()
        }





        mainLayout = findViewById(R.id.main)
        videoRecyclerView = findViewById(R.id.videoRecyclerView)
        videoTitleTextView = findViewById(R.id.videoTitle)
        videoDescTextView = findViewById(R.id.videoDesc)

        layoutToggle = findViewById(R.id.layoutToggle)
        mainProgressBar = findViewById(R.id.main_progress)
        bufferProgress = findViewById(R.id.bufferProgress)
        playerLayout = findViewById(R.id.playerLayout)
        playerView = findViewById(R.id.playerView)
        backButton = playerView.findViewById(R.id.backButton)
        fullscreenToggleButton = playerView.findViewById(R.id.toggleOrientationButton)
        layoutToggle.setOnClickListener { viewModel.layoutToggle() }
        backButton.setOnClickListener { onBackPressed() }
        fullscreenToggleButton.setOnClickListener {  toggleOrientation() }
        configOrientation(resources.configuration.orientation)
    }
    private fun setupAdapter() {
        adapter = VideoAdapter(this)
        videoRecyclerView.adapter = adapter
        videoRecyclerView.layoutManager = adapter.layoutManager

        adapter.onItemClickListeners { item, isMenu ->
            if (!isMenu) {
                togglePlayPause(item)
            }
        }
    }
    private fun initData() {
        if (intent.hasExtra(INTENT_KEY_DATA_LIST) &&
            intent.hasExtra(INTENT_KEY_POSITION)
        ) {
            currentPosition = intent.getIntExtra(INTENT_KEY_POSITION, 0)
            videoList = intent.getParcelableArrayListExtra(INTENT_KEY_DATA_LIST)
            viewModel.videos(videoList)
        }
    }
    private fun observe() {
        viewModel.isListLiveData.observe(this, Observer { isListLayout->
            if (isListLayout) {
                layoutToggle.setImageResource(R.drawable.my_bl_sdk_ic_grid_view)
                adapter.changeToList()
                videoRecyclerView.setPadding(0,  8.px, 0, 16.px)
            } else {
                layoutToggle.setImageResource(R.drawable.my_bl_sdk_ic_list_view_do)
                adapter.changeToGrid()
                videoRecyclerView.setPadding(
                    8.px,
                    8.px,
                    8.px,
                    16.px
                )
            }

        })
        viewModel.progressbarVisibility.observe(this, Observer {
            mainProgressBar.visibility = it
        })
        viewModel.videoListLiveData.observe(this, Observer {  videoList->
            adapter.submitList(videoList)
            addOnPlayerQueue(videoList)
        })
        viewModel.currentVideo.observe(this, Observer {  video->
            videoTitleTextView.text = video.title
            videoDescTextView.text = video.artist
        })
    }


    private fun initializePlayer() {

           if(exoPlayer == null) {
               exoPlayer = ExoPlayer.Builder(this)
                   .build()

               exoPlayer?.addListener(playbackStatus())
               playerView.player = exoPlayer


               mediaSession = MediaSessionCompat(this, packageName)
               val mediaSessionConnector = MediaSessionConnector(mediaSession)
               shadhinQueueNavigator = ShadhinMusicQueueNavigator(mediaSession)
               mediaSessionConnector.setPlayer(exoPlayer)
               mediaSessionConnector.setQueueNavigator(shadhinQueueNavigator)
               mediaSession.isActive = true

           }


    }
    private fun playbackStatus() = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)

            when (playbackState) {
                ExoPlayer.STATE_BUFFERING -> showBuffering()
                ExoPlayer.STATE_READY -> hideBuffering()
                else -> hideBuffering()
            }

        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            adapter.currentItem(isPlaying, exoPlayer?.currentMediaItem?.mediaId)
            playerView.keepScreenOn = isPlaying
            if(isPlaying){
               audioFocusManager.requestAudioFocus()
            }
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            super.onMediaItemTransition(mediaItem, reason)
            adapter.currentItem(false, mediaItem?.mediaId)
            viewModel.currentVideo(mediaItem?.mediaId)
        }

    }

    private fun gestureSetup() {
        playerOnScaleGestureListener = PlayerOnScaleGestureListener(playerView, this)
        scaleGestureDetector = ScaleGestureDetector(this, playerOnScaleGestureListener)
    }

    private fun togglePlayPause(item: Video) {

        if (item.contentID == exoPlayer?.currentMediaItem?.mediaId) {
            if (exoPlayer?.isPlaying == true) {
                exoPlayer?.pause()
            } else {
                exoPlayer?.play()
            }
        } else {
            val windowIndex = videoList?.indexOfFirst { it.contentID == item.contentID }
            if (windowIndex != -1) {
                exoPlayer?.seekTo(windowIndex ?: 0, 0L)
                exoPlayer?.playWhenReady = true
            }
        }

    }
    private fun addOnPlayerQueue(videoList: List<Video>) {
        contactMediaSource.clear()
        exoPlayer?.clearMediaItems()
        videoMediaSource = ShadhinVideoMediaSource(this.applicationContext,
            videoList,
            injector.exoplayerCache,
            injector.musicRepository
        )
        val mediaSources = videoMediaSource?.createSources()
        if(!mediaSources.isNullOrEmpty()){
            contactMediaSource.addMediaSources(mediaSources)
            exoPlayer?.addMediaSource(contactMediaSource)
            exoPlayer?.seekTo(currentPosition, 0)
            exoPlayer?.prepare()
            exoPlayer?.playWhenReady = true
        }
    }
    private fun hideBuffering() {
        bufferProgress.visibility = View.GONE
        playerView.useController = true
    }
    private fun showBuffering() {
        bufferProgress.visibility = View.VISIBLE
        playerView.useController = false
    }
    private fun toggleOrientation() {
        requestedOrientation = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                fullscreenToggleButton.setImageResource(R.drawable.my_bl_sdk_ic_video_fullscreen_minimize)
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                fullscreenToggleButton.setImageResource(R.drawable.my_bl_sdk_ic_video_fullscreen)
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            else -> {
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        }
    }
    private fun configOrientation(orientation: Int) {
        when (orientation) {
            Configuration.ORIENTATION_PORTRAIT -> preparePortraitUI()
            Configuration.ORIENTATION_LANDSCAPE -> prepareLandscapeUI()
            else -> preparePortraitUI()
        }
    }
    private fun preparePortraitUI() {
        showSystemUI()
        supportActionBar?.show()
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            setPortraitPlayerSize()
        }, 100)
        fullscreenToggleButton.setImageResource(R.drawable.my_bl_sdk_ic_video_fullscreen)
    }
    private fun prepareLandscapeUI() {
        hideSystemUI()
        supportActionBar?.hide()
        setLandscapePlayerSize()
        fullscreenToggleButton.setImageResource(R.drawable.my_bl_sdk_ic_video_fullscreen_minimize)
    }
    private fun setPortraitPlayerSize() {
        val displayWidth = UtilHelper.getScreenSize(this)?.x
        val height = calculateVideoHeight(displayWidth?:0, videoWidth, videoHeight)
        playerLayout.layoutParams =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height)
    }
    private fun setLandscapePlayerSize() {
        playerLayout.layoutParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
    }
    private fun showSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(
            window,
            mainLayout
        ).show(WindowInsetsCompat.Type.systemBars())
    }
    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, mainLayout).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            scaleGestureDetector.onTouchEvent(event)
        }
        return true
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        configOrientation(newConfig.orientation)
    }




    override fun onBackPressed() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            toggleOrientation()
        } else {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.pause()
    }
    override fun onDestroy() {
        exoPlayer?.release()
        exoPlayer = null
        super.onDestroy()
    }

    companion object {
        const val INTENT_KEY_THEME = "theme"
        const val INTENT_KEY_DATA_LIST = "data_list"
        const val INTENT_KEY_DATA = "data"
        const val INTENT_KEY_CONTENT_ID = "content_id"
        const val INTENT_KEY_POSITION = "currentPosition"
        const val INTENT_KEY_BACK_TO_MAIN = "back_to_main"
        const val LAST_PLAYED_TRACK = "last_track"
        const val LAST_PLAYED_POSITION = "last_position"
    }



}