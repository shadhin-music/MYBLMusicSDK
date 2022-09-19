package com.shadhinmusiclibrary.fragments.album

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

class AlbumDetailsFragment :
    BaseFragment<AlbumViewModel, AlbumViewModelFactory>(),
    FragmentEntryPoint, OnItemClickCallback {

    private lateinit var navController: NavController
    private lateinit var adapter: AlbumAdapter
    private lateinit var playerViewModel: PlayerViewModel

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
        createPlayerVM()
//        vmMusicPlayer = ViewModelProvider(this).get(MusicPlayerVM::class.java)

        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    private fun playPauseState(playing: Boolean, ivPlayPause: ImageView) {
        if (playing) {
            ivPlayPause.setImageResource(R.drawable.ic_pause_circle_filled)
        } else {
            ivPlayPause.setImageResource(R.drawable.ic_play_linear)
        }
    }

    private fun createPlayerVM() {
        playerViewModel = ViewModelProvider(
            requireActivity(),
            injector.playerViewModelFactory
        )[PlayerViewModel::class.java]
    }

    private fun fetchOnlineData(contentId: Int) {
        val progressBar: ProgressBar = requireView().findViewById(R.id.progress_bar)
        viewModel!!.fetchAlbumContent(contentId)
        viewModel!!.albumContent.observe(requireActivity()) { res ->
            if (res.status == Status.SUCCESS) {
                progressBar.visibility = GONE
                adapter.setSongData(res.data!!.data)
            } else {
                progressBar.visibility = VISIBLE
                adapter.setSongData(mutableListOf())
            }
        }
    }

    override fun onClickItem(mSongDet: SongDetail) {
//        playerViewModel.currentMusicLiveData.observe(this, Observer { itMusic ->
//            if ((itMusic!!.contentType!! == adapter.getCurrentItem().ContentType)
//                    .and(itMusic.mediaId!! == adapter.getCurrentItem().ContentID)
//            ) {
//                playItem(mSongDet)
//            }
//        })
        playItem(mSongDet)
//        vmMusicPlayer.setPlayMusic(mSongDet)
    }

    override fun getCurrentVH(currentVH: RecyclerView.ViewHolder, mSongDet: SongDetail) {
        val albumVH = currentVH as AlbumAdapter.AlbumVH
        if (albumVH.ivPlayBtn != null) {
            playerViewModel.currentMusicLiveData.observe(this, Observer { itMusic ->
                if ((itMusic!!.contentType!! == mSongDet.ContentType)
                        .and(itMusic.mediaId!! == mSongDet.ContentID)
                ) {
                    playerViewModel.playbackStateLiveData.observe(this, Observer {
                        playPauseState(it.isPlaying, albumVH.ivPlayBtn!!)
                    })
                }
            })

        }
    }
}