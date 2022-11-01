package com.shadhinmusiclibrary.activities.video

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
import com.google.android.exoplayer2.offline.DownloadRequest
import com.google.android.exoplayer2.offline.DownloadService
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.player.audio_focus.AudioFocusManager
import com.shadhinmusiclibrary.player.audio_focus.AudioFocusManagerFactory
import com.shadhinmusiclibrary.adapter.VideoAdapter
import com.shadhinmusiclibrary.data.model.DownloadingItem
import com.shadhinmusiclibrary.data.model.Video
import com.shadhinmusiclibrary.di.ActivityEntryPoint
import com.shadhinmusiclibrary.download.MyBLDownloadService
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.download.room.WatchLaterContent
import com.shadhinmusiclibrary.player.Constants
import com.shadhinmusiclibrary.player.ShadhinMusicQueueNavigator
import com.shadhinmusiclibrary.player.data.source.MediaSources
import com.shadhinmusiclibrary.player.data.source.ShadhinVideoMediaSource
import com.shadhinmusiclibrary.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.UtilHelper
import com.shadhinmusiclibrary.utils.calculateVideoHeight
import com.shadhinmusiclibrary.utils.px


internal class VideoActivity : AppCompatActivity(), ActivityEntryPoint,
    AudioManager.OnAudioFocusChangeListener ,BottomsheetDialog{

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
    private lateinit var favLayout: LinearLayout
    private lateinit var downloadLayout: LinearLayout
    private lateinit var watchlaterLayout: LinearLayout
    private lateinit var fullscreenToggleButton:ImageButton
    private lateinit var favImageView: ImageView
    private lateinit var watchIcon: ImageView
    private lateinit var downloadImage: ImageView
    private lateinit var favTextView: TextView
    private lateinit var watchlatertext: TextView
    private lateinit var downloadTextview:TextView
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
    val cacheRepository by lazy {
        CacheRepository(this)
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
        favImageView = findViewById(R.id.favoriteIcon)
        downloadImage = findViewById(R.id.imageDownload)
        watchIcon = findViewById(R.id.watchIcon)
        favTextView = findViewById(R.id.txtFav)
        watchlatertext = findViewById(R.id.txtWatch)
        downloadTextview = findViewById(R.id.txtDownload)
        layoutToggle = findViewById(R.id.layoutToggle)
        favLayout = findViewById(R.id.favourite)
        watchlaterLayout = findViewById(R.id.watchLater)
        downloadLayout = findViewById(R.id.download)
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
        adapter = VideoAdapter(this,this,cacheRepository)
        videoRecyclerView.adapter = adapter
        videoRecyclerView.layoutManager = adapter.layoutManager

        adapter.onItemClickListeners { item, isMenu ->
            if (!isMenu) {
                togglePlayPause(item)
                var iswatched = false
                var watched = cacheRepository.getWatchedVideoById(item.contentID.toString())
                if (watched?.track != null) {
                    iswatched = true
                   // watchIcon.setImageResource(R.drawable.my_bl_sdk_watch_later_remove)
                    watchIcon.setColorFilter(applicationContext.getResources().getColor(R.color.my_sdk_down_item_desc))
                } else {
                    iswatched = false
                    watchIcon.setColorFilter(applicationContext.getResources().getColor(R.color.my_sdk_down_item_desc))
                   // watchIcon.setImageResource(R.drawable.my_bl_sdk_watch_later)
                }

//                if (iswatched) {
//                    watchlatertext.text = "Remove From Watchlater"
//                } else {
//                    watchlatertext.text = "Watch Later"
//                }
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
        viewModel.currentVideo.observe(this, Observer { video ->
            videoTitleTextView.text = video.title
            videoDescTextView.text = video.artist
            var iswatched = false
            var watched = cacheRepository.getWatchedVideoById(video.contentID.toString())
            if (watched?.track != null) {
                iswatched = true
                watchIcon.setColorFilter(applicationContext.getResources().getColor(R.color.my_sdk_color_primary))
            } else {
                iswatched = false
                watchIcon.setColorFilter(applicationContext.getResources().getColor(R.color.my_sdk_down_item_desc))
               // watchIcon.setImageResource(R.drawable.my_bl_sdk_watch_later)
            }

//            if (iswatched) {
//                watchlatertext?.text = "Remove From Watchlater"
//            } else {
//                watchlatertext?.text = "Watch Later"
//            }
//            watchlaterLayout.setOnClickListener {
//               if(iswatched.equals(true)){
//
//                    cacheRepository.deleteWatchlaterById(video.contentID.toString())
//                   watchIcon?.setImageResource(R.drawable.my_bl_sdk_watch_later_remove)
//                   watchlatertext?.text = "Remove From Watchlater"
//                    Log.e("TAG","CLICKED: "+ video.contentID)
//                }else{
//                   watchIcon.setImageResource(R.drawable.my_bl_sdk_ic_watch_later)
//                   watchlatertext.text = "Watch Later"
//                    val url = "${Constants.FILE_BASE_URL}${video.playUrl}"
//                    cacheRepository.insertWatchLater(WatchLaterContent(video.contentID.toString(),
//                        video.rootId.toString(),
//                        video.image.toString(),
//                        video.title.toString(),
//                        video.contentType.toString(),
//                        url ,
//                        video.contentType.toString(),
//                        0,
//                        0,
//                        video.artist.toString(),
//                        video.duration.toString()))
//                    Log.e("TAGGG",
//                        "INSERTED: " + cacheRepository.getAllWatchlater())
//                }
//
//
//
//            }
        })
    }


    private fun initializePlayer() {

           if(exoPlayer == null) {
               exoPlayer = ExoPlayer.Builder(this)
                   .setSeekBackIncrementMs(1000*10)
                   .setSeekForwardIncrementMs(1000*10)
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
        LocalBroadcastManager.getInstance(applicationContext)
            .unregisterReceiver(MyBroadcastReceiver())
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

    override fun openDialog(item: Video) {
            val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialog)

            val contentView =
                View.inflate(this, R.layout.my_bl_sdk_video_bottomsheet_three_dot_menu, null)
            bottomSheetDialog.setContentView(contentView)
            bottomSheetDialog.show()
            val closeButton: ImageView? = bottomSheetDialog.findViewById(R.id.closeButton)
            closeButton?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
            val artistname = bottomSheetDialog.findViewById<TextView>(R.id.desc)
            artistname?.text = item.artist
            val image: ImageView? = bottomSheetDialog.findViewById(R.id.thumb)
            val url = item.image
            val title: TextView? = bottomSheetDialog.findViewById(R.id.name)
            title?.text = item.title
            if (image != null) {
                Glide.with(this).load(url?.replace("<\$size\$>", "300")).into(image)
            }

            val downloadImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgDownload)
            val textViewDownloadTitle: TextView? = bottomSheetDialog.findViewById(R.id.tv_download)
            var isDownloaded = false
            var downloaded = cacheRepository.getDownloadById(item.contentID.toString())
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
                    cacheRepository.deleteDownloadById(item.contentID.toString())
                    DownloadService.sendRemoveDownload(applicationContext,
                        MyBLDownloadService::class.java, item.contentID.toString(), false)
                    Log.e("TAG", "DELETED: " + isDownloaded)
                    val localBroadcastManager = LocalBroadcastManager.getInstance(applicationContext)
                    val localIntent = Intent("DELETED")
                        .putExtra("contentID", item.contentID.toString())
                    localBroadcastManager.sendBroadcast(localIntent)

                } else {
                    val url = "${Constants.FILE_BASE_URL}${item.playUrl}"
                    val downloadRequest: DownloadRequest =
                        DownloadRequest.Builder(item.contentID.toString(), url.toUri())
                            .build()
                    DownloadService.sendAddDownload(
                        applicationContext,
                        MyBLDownloadService::class.java,
                        downloadRequest,
                        /* foreground= */ false)

                    if (cacheRepository.isDownloadCompleted().equals(true)) {
                        cacheRepository.insertDownload(DownloadedContent(item.contentID.toString(),
                           item.rootId.toString(),
                            item.image.toString(),
                            item.title.toString(),
                            item.contentType.toString(),
                            item.playUrl,
                            item.contentType.toString(),
                            1,
                            0,
                            item.artist.toString(),
                            item.duration.toString()))
                        Log.e("TAGGG",
                            "INSERTED: " + cacheRepository.isDownloadCompleted())
                    }
                }
                bottomSheetDialog.dismiss()
            }
        val watchlaterImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgWatchlater)
        val textViewWatchlaterTitle: TextView? = bottomSheetDialog.findViewById(R.id.txtwatchLater)
        var iswatched = false
        var watched = cacheRepository.getWatchedVideoById(item.contentID.toString())
        if (watched?.track != null) {
            iswatched = true
            watchlaterImage?.setImageResource(R.drawable.my_bl_sdk_watch_later_remove)
                watchIcon.setColorFilter(applicationContext.getResources().getColor(R.color.my_sdk_color_primary))

        } else {
            iswatched = false
            watchlaterImage?.setImageResource(R.drawable.my_bl_sdk_ic_watch_later)
            watchIcon.setColorFilter(applicationContext.getResources().getColor(R.color.my_sdk_down_item_desc))
        }

        if (iswatched) {
            textViewWatchlaterTitle?.text = "Remove From Watchlater"
        } else {
            textViewWatchlaterTitle?.text = "Watch Later"
        }
        val constraintWatchlater: ConstraintLayout ?= bottomSheetDialog.findViewById(R.id.constraintAddtoWatch)
            constraintWatchlater?.setOnClickListener {
                if (iswatched.equals(true)) {
                    cacheRepository.deleteWatchlaterById(item.contentID.toString())
                } else {
                    val url = "${Constants.FILE_BASE_URL}${item.playUrl}"
                    cacheRepository.insertWatchLater(WatchLaterContent(item.contentID.toString(),
                        item.rootId.toString(),
                        item.image.toString(),
                        item.title.toString(),
                        item.contentType.toString(),
                        url ,
                        item.contentType.toString(),
                        0,
                        0,
                        item.artist.toString(),
                        item.duration.toString()))
                    Log.e("TAGGG",
                        "INSERTED: " + cacheRepository.getAllWatchlater())

                }
                bottomSheetDialog.dismiss()
            }

        }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter()
        intentFilter.addAction("ACTION")
        intentFilter.addAction("DELETED")
        intentFilter.addAction("PROGRESS")
        LocalBroadcastManager.getInstance(applicationContext)
            .registerReceiver(MyBroadcastReceiver(), intentFilter)
    }
    private fun progressIndicatorUpdate(downloadingItems: List<DownloadingItem>) {

        downloadingItems.forEach {


            val progressIndicator: CircularProgressIndicator? =
                videoRecyclerView.findViewWithTag(it.contentId)
//                val downloaded: ImageView?= view?.findViewWithTag(200)

            progressIndicator?.progress = it.progress.toInt()
          // if(it.progress==1f) {
                progressIndicator?.visibility = View.VISIBLE
           // }
            if(it.progress==100f){
                progressIndicator?.visibility = View.GONE

//                // downloaded?.visibility = VISIBLE
            }
//            val isDownloaded =
//                cacheRepository.isTrackDownloaded(it.contentId) ?: false
//            if(isDownloaded){
//                progressIndicator?.visibility = View.GONE
//                // downloaded?.visibility = VISIBLE
//            }

            Log.e("getDownloadManagerx",
                "habijabi123: ${it.toString()} ${progressIndicator == null}")


        }


    }

    inner class MyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent){
            Log.e("DELETED", "onReceive "+intent.action)
            Log.e("PROGRESS", "onReceive "+intent)
            when (intent.action) {
                "ACTION" -> {

                    //val data = intent.getIntExtra("currentProgress",0)
                    val downloadingItems = intent.getParcelableArrayListExtra<DownloadingItem>("downloading_items")

                    downloadingItems?.let {
                        progressIndicatorUpdate(it)


                        Log.e("getDownloadManagerx",
                            "habijabi: ${it.toString()} ")
                    }
                }
                "DELETED" -> {
                    adapter.notifyDataSetChanged()
                    Log.e("DELETED", "broadcast fired")
                }
                "PROGRESS" -> {

                    adapter.notifyDataSetChanged()
                    Log.e("PROGRESS", "broadcast fired")
                }
                else -> Toast.makeText(context, "Action Not Found", Toast.LENGTH_LONG).show()
            }

        }
    }

}
interface BottomsheetDialog{
    fun openDialog(item: Video)
}