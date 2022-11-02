package com.shadhinmusiclibrary.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.adapter.HomeFooterAdapter
import com.shadhinmusiclibrary.adapter.PlaylistHeaderAdapter
import com.shadhinmusiclibrary.adapter.PlaylistTrackAdapter
import com.shadhinmusiclibrary.callBackService.BottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.OnItemClickCallback
import com.shadhinmusiclibrary.data.model.DownloadingItem
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.SongDetailModel
import com.shadhinmusiclibrary.fragments.album.AlbumViewModel
import com.shadhinmusiclibrary.fragments.album.AlbumViewModelFactory
import com.shadhinmusiclibrary.fragments.base.BaseFragment
import com.shadhinmusiclibrary.library.player.utils.CacheRepository
import com.shadhinmusiclibrary.library.player.utils.isPlaying
import com.shadhinmusiclibrary.utils.Status
import com.shadhinmusiclibrary.utils.UtilHelper

internal class PlaylistDetailsFragment : BaseFragment<AlbumViewModel, AlbumViewModelFactory>(),
    OnItemClickCallback, BottomSheetDialogItemCallback {

    private lateinit var navController: NavController

    private lateinit var playlistHeaderAdapter: PlaylistHeaderAdapter
    private lateinit var playlistTrackAdapter: PlaylistTrackAdapter

    private lateinit var footerAdapter: HomeFooterAdapter
    private var cacheRepository: CacheRepository? = null
    override fun getViewModel(): Class<AlbumViewModel> {
        return AlbumViewModel::class.java
    }

    override fun getViewModelFactory(): AlbumViewModelFactory {
        return injector.factoryAlbumVM
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRef =
            inflater.inflate(R.layout.my_bl_sdk_common_rv_pb_layout, container, false)
        navController = findNavController()
        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cacheRepository = CacheRepository(requireContext())
        // playlistTrackAdapter = PlaylistTrackAdapter(this, cacheRepository!!)
        playlistHeaderAdapter = PlaylistHeaderAdapter(argHomePatchDetail, this)
        playlistTrackAdapter = PlaylistTrackAdapter(this, this, cacheRepository!!)
//        adapter = PlaylistAdapter(this, this)
        footerAdapter = HomeFooterAdapter()
        //read data from online
        fetchOnlineData(argHomePatchDetail!!.ContentID)
//        adapter.setRootData(argHomePatchDetail!!)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val config = ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .build()
        val concatAdapter = ConcatAdapter(
            config,
            playlistHeaderAdapter,
            playlistTrackAdapter,
            /*adapter,*/
            footerAdapter
        )
        recyclerView.adapter = concatAdapter
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        playerViewModel.currentMusicLiveData.observe(viewLifecycleOwner) { music ->
            if (music?.mediaId != null) {
                playlistTrackAdapter.setPlayingSong(music.mediaId!!)
            }
        }
    }

    private fun fetchOnlineData(contentId: String) {
        val progressBar: ProgressBar = requireView().findViewById(R.id.progress_bar)
        viewModel?.fetchPlaylistContent(contentId)
        viewModel?.albumContent?.observe(viewLifecycleOwner) { res ->
            if (res.data?.data != null && res.status == Status.SUCCESS) {
                playlistTrackAdapter.setData(
                    res.data.data,
                    argHomePatchDetail!!,
                    playerViewModel.currentMusic?.mediaId
                )
                playlistHeaderAdapter.setSongAndData(
                    res.data.data,
                    argHomePatchDetail!!
                )
//                updateAndSetAdapter(res!!.data!!.data)
                progressBar.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
//                updateAndSetAdapter(mutableListOf())
            }
        }
    }

    private fun updateAndSetAdapter(songList: MutableList<SongDetailModel>) {
        updatedSongList = mutableListOf()
        for (songItem in songList) {
            updatedSongList.add(
                UtilHelper.getSongDetailAndRootData(songItem, argHomePatchDetail!!)
            )
        }
//        adapter.setSongData(updatedSongList)
    }

    override fun onRootClickItem(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int) {
        val lSongDetails = playlistTrackAdapter.dataSongDetail
        if (lSongDetails.size > clickItemPosition) {
            if ((mSongDetails[clickItemPosition].rootContentId == playerViewModel.currentMusic?.rootId)) {
                playerViewModel.togglePlayPause()
            } else {
                playItem(mSongDetails, clickItemPosition)
            }
        }
    }

    override fun onClickItem(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int) {
        if (playerViewModel.currentMusic != null) {
            if ((mSongDetails[clickItemPosition].rootContentId == playerViewModel.currentMusic?.rootId)) {
                if ((mSongDetails[clickItemPosition].content_Id != playerViewModel.currentMusic?.mediaId)) {
                    playerViewModel.skipToQueueItem(clickItemPosition)
                } else {
                    playerViewModel.togglePlayPause()
                }
            } else {
                playItem(mSongDetails, clickItemPosition)
            }
        } else {
            playItem(mSongDetails, clickItemPosition)
        }
    }

    override fun getCurrentVH(
        currentVH: RecyclerView.ViewHolder,
        songDetails: MutableList<IMusicModel>
    ) {
        val playlistHeaderVH = currentVH as PlaylistHeaderAdapter.PlaylistHeaderVH
        if (songDetails.size > 0 && isAdded) {
            //DO NOT USE requireActivity()
            playerViewModel.currentMusicLiveData.observe(viewLifecycleOwner) { itMusic ->
                if (itMusic != null) {
                    if ((songDetails.indexOfFirst {
                            it.rootContentType == itMusic.rootType &&
                                    it.rootContentId == itMusic.rootId &&
                                    it.content_Id == itMusic.mediaId
                        } != -1)
                    ) {
                        //DO NOT USE requireActivity()
                        playerViewModel.playbackStateLiveData.observe(viewLifecycleOwner) { itPla ->
                            playPauseState(itPla!!.isPlaying, playlistHeaderVH.ivPlayBtn!!)
                        }
                    } else {
                        playlistHeaderVH.ivPlayBtn?.let { playPauseState(false, it) }
                    }
                }
            }
        }
    }

    override fun onClickBottomItem(mSongDetails: IMusicModel) {
        (activity as? SDKMainActivity)?.showBottomSheetDialogForPlaylist(
            navController,
            context = requireContext(),
            mSongDetails,
            argHomePatchItem,
            argHomePatchDetail
        )
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter()
        intentFilter.addAction("ACTION")
        intentFilter.addAction("DELETED")
        intentFilter.addAction("PROGRESS")
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(MyBroadcastReceiver(), intentFilter)
    }

    private fun progressIndicatorUpdate(downloadingItems: List<DownloadingItem>) {

        downloadingItems.forEach {


            val progressIndicator: CircularProgressIndicator? =
                view?.findViewWithTag(it.contentId)
//                val downloaded: ImageView?= view?.findViewWithTag(200)
            progressIndicator?.visibility = View.VISIBLE
            progressIndicator?.progress = it.progress.toInt()
            val isDownloaded =
                cacheRepository?.isTrackDownloaded(it.contentId) ?: false
            if (!isDownloaded) {
                progressIndicator?.visibility = View.GONE
                // downloaded?.visibility = VISIBLE
            }

            Log.e(
                "getDownloadManagerx",
                "habijabi: ${it.toString()} ${progressIndicator == null}"
            )


        }


    }

    inner class MyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.e("DELETED", "onReceive " + intent.action)
            Log.e("PROGRESS", "onReceive " + intent)
            when (intent.action) {
                "ACTION" -> {

                    //val data = intent.getIntExtra("currentProgress",0)
                    val downloadingItems =
                        intent.getParcelableArrayListExtra<DownloadingItem>("downloading_items")

                    downloadingItems?.let {

                        progressIndicatorUpdate(it)

//                        Log.e("getDownloadManagerx",
//                            "habijabi: ${it.toString()} ")
                    }
                }
                "DELETED" -> {
                    playlistTrackAdapter.notifyDataSetChanged()
                    Log.e("DELETED", "broadcast fired")
                }
                "PROGRESS" -> {

                    playlistTrackAdapter.notifyDataSetChanged()
                    Log.e("PROGRESS", "broadcast fired")
                }
                else -> Toast.makeText(context, "Action Not Found", Toast.LENGTH_LONG).show()
            }

        }
    }
}

interface DownloadOrDeleteActionSubscriber {
    fun notifyOnChange()
}