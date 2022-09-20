package com.shadhinmusiclibrary.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.VideoAdapter
import com.shadhinmusiclibrary.data.model.Video

class VideoActivity : AppCompatActivity() {

    private lateinit var adapter: VideoAdapter
    private lateinit var videoRecyclerView: RecyclerView
    private var currentPosition = 0
    private var videoList: ArrayList<Video>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        setupUI()
        setupAdapter()
        initData()
    }

    private fun setupUI() {
        videoRecyclerView = findViewById(R.id.videoRecyclerView)
    }

    private fun setupAdapter() {
        adapter = VideoAdapter(this)
        videoRecyclerView.adapter = adapter
        videoRecyclerView.layoutManager = adapter.layoutManager
        adapter.onItemClickListeners { item, isMenu ->

           /* if (!isMenu) {
                togglePlayPause(item)
            } else {

                if (!mainViewModel.isLogin) {
                    showAuthDialogIfNotLogin()
                    return@onItemClickListeners
                }

                openDialogFragment(
                    VideoOptionsMenuDialog.newInstance(
                        item,
                        viewModel.offlineDownloadRepository.offlineDownloadDaoAccess,
                        VideoListFragment.VIDEO_PLAYER
                    ), Constants.FRAGMENT_MENU_OPTIONS
                )*/

            }
        }
    private fun initData() {
        if (intent.hasExtra(INTENT_KEY_DATA_LIST) &&
            intent.hasExtra(INTENT_KEY_POSITION)
        ) {
            currentPosition = intent.getIntExtra(INTENT_KEY_POSITION, 0)
            videoList = intent.getParcelableArrayListExtra(INTENT_KEY_DATA_LIST)
            adapter.submitList(videoList)
        }
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