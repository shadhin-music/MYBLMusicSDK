package com.shadhinmusiclibrary.fragments.album

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.adapter.AlbumAdapter
import com.shadhinmusiclibrary.callBackService.OnItemClickCallback
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.base.BaseFragment
import com.shadhinmusiclibrary.player.ui.PlayerViewModel
import com.shadhinmusiclibrary.player.utils.isPlaying
import com.shadhinmusiclibrary.utils.Status
import com.shadhinmusiclibrary.utils.UtilHelper

class AlbumDetailsFragment :
    BaseFragment<AlbumViewModel, AlbumViewModelFactory>(), OnItemClickCallback {

    private lateinit var navController: NavController
    private lateinit var adapter: AlbumAdapter
//    private lateinit var playerViewModel: PlayerViewModel

    override fun getViewModel(): Class<AlbumViewModel> {
        return AlbumViewModel::class.java
    }

    override fun getViewModelFactory(): AlbumViewModelFactory {
        return injector.factoryAlbumVM
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewRef = inflater.inflate(R.layout.fragment_album_details, container, false)
        navController = findNavController()

        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        createPlayerVM()
        adapter = AlbumAdapter(this)

        ///read data from online
        fetchOnlineData(argHomePatchDetail!!.ContentID.toInt())
        adapter.setRootData(argHomePatchDetail!!)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            if (ShadhinMusicSdkCore.pressCountDecrement() == 0) {
                requireActivity().finish()
            } else {
                navController.popBackStack()
            }
        }
    }

    private fun fetchOnlineData(contentId: Int) {
        val progressBar: ProgressBar = requireView().findViewById(R.id.progress_bar)
        viewModel!!.fetchAlbumContent(contentId)
        viewModel!!.albumContent.observe(requireActivity()) { res ->
            if (res.status == Status.SUCCESS) {
                progressBar.visibility = GONE
                updateAndSetAdapter(res.data!!.data)
            } else {
                progressBar.visibility = VISIBLE
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

    override fun onClickItem(mSongDetails: MutableList<SongDetail>, clickItemPosition: Int) {
        playItem(mSongDetails, clickItemPosition)
    }

    override fun getCurrentVH(
        currentVH: RecyclerView.ViewHolder,
        songDetails: MutableList<SongDetail>
    ) {
        val albumVH = currentVH as AlbumAdapter.AlbumVH
        if (songDetails.size > 0) {
            playerViewModel.currentMusicLiveData.observe(requireActivity(), Observer {
                if (it != null) {
                    if ((it.rootType!! == songDetails[0].rootContentType)
                        && (it.mediaId!! == songDetails[0].ContentID)
                    ) {
                        playerViewModel.playbackStateLiveData.observe(requireActivity()) { itPla ->
                            playPauseState(itPla!!.isPlaying, albumVH.ivPlayBtn!!)
                        }

                        playerViewModel.musicIndexLiveData.observe(requireActivity()) {
//                            albumVH.itemView.setBackgroundColor(Color.BLUE)
                        }
                    }
                }
            })
        }
    }
}