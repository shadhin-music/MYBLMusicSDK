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
import android.widget.ImageView
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
import com.shadhinmusiclibrary.adapter.PlaylistAdapter
import com.shadhinmusiclibrary.adapter.PlaylistAdapter.PlaylistVH
import com.shadhinmusiclibrary.callBackService.BottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.OnItemClickCallback
import com.shadhinmusiclibrary.data.model.DownloadingItem
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.fragments.album.AlbumViewModel
import com.shadhinmusiclibrary.fragments.album.AlbumViewModelFactory
import com.shadhinmusiclibrary.fragments.base.BaseFragment
import com.shadhinmusiclibrary.player.utils.CacheRepository
import com.shadhinmusiclibrary.player.utils.isPlaying
import com.shadhinmusiclibrary.utils.Status
import com.shadhinmusiclibrary.utils.UtilHelper

internal class PlaylistDetailsFragment : BaseFragment<AlbumViewModel, AlbumViewModelFactory>(),
    OnItemClickCallback , BottomSheetDialogItemCallback{

    private lateinit var navController: NavController
    private lateinit var adapter: PlaylistAdapter
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
        val viewRef = inflater.inflate(R.layout.my_bl_sdk_fragment_playlist_details, container, false)
        navController = findNavController()

        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cacheRepository= CacheRepository(requireContext())
        adapter = PlaylistAdapter(this,this, cacheRepository!!)
        footerAdapter = HomeFooterAdapter()
        ///read data from online
        fetchOnlineData(argHomePatchDetail!!.ContentID)
        adapter.setRootData(argHomePatchDetail!!)
        val config = ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .build()
        val concatAdapter=  ConcatAdapter(config,adapter,footerAdapter)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = concatAdapter
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            /*if (ShadhinMusicSdkCore.pressCountDecrement() == 0) {
                requireActivity().finish()
            } else {
                navController.popBackStack()
            }*/
            requireActivity().onBackPressed()
        }
    }

    private fun fetchOnlineData(contentId: String) {
        viewModel?.fetchPlaylistContent(contentId)
        viewModel?.albumContent?.observe(viewLifecycleOwner) { res ->
            if (res.status == Status.SUCCESS) {
                updateAndSetAdapter(res!!.data!!.data)
            } else {
                updateAndSetAdapter(mutableListOf())
            }
        }
    }

    private fun updateAndSetAdapter(songList: MutableList<SongDetail>) {
        updatedSongList = mutableListOf()
        for (songItem in songList) {
            updatedSongList.add(
                UtilHelper.getSongDetailAndRootData(songItem, argHomePatchDetail!!)
            )
        }
        adapter.setSongData(updatedSongList)
    }

    override fun onRootClickItem(mSongDetails: MutableList<SongDetail>, clickItemPosition: Int) {
        if ((mSongDetails[clickItemPosition].rootContentID == playerViewModel.currentMusic?.rootId)) {
            playerViewModel.togglePlayPause()

           // Log.e("TAG","Post: ")
        } else {
            playItem(mSongDetails, clickItemPosition)
        }
    }

    override fun onClickItem(mSongDetails: MutableList<SongDetail>, clickItemPosition: Int) {

        if ((mSongDetails[clickItemPosition].rootContentID == playerViewModel.currentMusic?.rootId)) {

            if ((mSongDetails[clickItemPosition].ContentID != playerViewModel.currentMusic?.mediaId)) {
                playerViewModel.skipToQueueItem(clickItemPosition)


            } else {
                playerViewModel.togglePlayPause()

               // Log.e("TAG","Post123: ")
            }
        } else {
            playItem(mSongDetails, clickItemPosition)
        }
    }

    override fun getCurrentVH(
        currentVH: RecyclerView.ViewHolder,
        songDetails: MutableList<SongDetail>
    ) {
        val albumVH = currentVH as PlaylistVH
        if (songDetails.size > 0 && isAdded) {

            //DO NOT USE requireActivity()
            playerViewModel.currentMusicLiveData.observe(viewLifecycleOwner) { itMusic ->
                if (itMusic != null) {
                   // view?.let { adapter.PlaylistVH(it).tvSongName?.setTextColor(Color.BLUE) }
                   // Log.e("TAG","Position : ")
                    if ((songDetails.indexOfFirst {
                            it.rootContentType == itMusic.rootType &&
                                    it.ContentID == itMusic.mediaId
                        } != -1)
                    ) {
                        //DO NOT USE requireActivity()
                        playerViewModel.playbackStateLiveData.observe(viewLifecycleOwner) { itPla ->
                            playPauseState(itPla!!.isPlaying, albumVH.ivPlayBtn!!)
                            //albumVH.tvSongName?.setTextColor(Color.BLUE)


                        }
                        //DO NOT USE requireActivity()
                        playerViewModel.musicIndexLiveData.observe(viewLifecycleOwner) {
                            Log.e(
                                "ADF",
                                "AdPosition: " + albumVH.bindingAdapterPosition + " itemId: " + albumVH.itemId + " musicIndex" + it
                            )
                        }
                    }
                }
            }
        }
    }
    override fun onClickBottomItem(mSongDetails:SongDetail) {
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
        val f = IntentFilter("ACTION")
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(MyBroadcastReceiver(), f)
    }
    private fun progressIndicatorUpdate(downloadingItems: List<DownloadingItem>) {

        downloadingItems.forEach {


            val progressIndicator: CircularProgressIndicator? =
                view?.findViewWithTag(it.contentId)
            val downloaded: ImageView?= view?.findViewWithTag(200)
            progressIndicator?.visibility = View.VISIBLE
            progressIndicator?.progress = it.progress.toInt()


//                if(!isDownloaded){
//                    progressIndicator?.visibility = GONE
//                    downloaded?.visibility = VISIBLE
//                }

            Log.e("getDownloadManagerx",
                "habijabi: ${it.toString()} ${progressIndicator == null}")


        }


    }
    inner class MyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent){
            when (intent.action) {
                "ACTION" -> {

                    //val data = intent.getIntExtra("currentProgress",0)
                    val downloadingItems = intent.getParcelableArrayListExtra<DownloadingItem>("downloading_items")

                    downloadingItems?.let {

                        progressIndicatorUpdate(it)
//                        Log.e("getDownloadManagerx",
//                            "habijabi: ${it.toString()} ")
                    }
                }
                else -> Toast.makeText(context, "Action Not Found", Toast.LENGTH_LONG).show()
            }

        }
    }
}