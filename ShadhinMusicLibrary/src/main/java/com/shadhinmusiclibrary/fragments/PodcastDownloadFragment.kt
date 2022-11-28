package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.adapter.DownloadedSongsAdapter
import com.shadhinmusiclibrary.adapter.HomeFooterAdapter
import com.shadhinmusiclibrary.callBackService.CommonPSVCallback
import com.shadhinmusiclibrary.callBackService.DownloadedSongOnCallBack
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.SongDetailModel
import com.shadhinmusiclibrary.data.model.podcast.SongTrackModel
import com.shadhinmusiclibrary.fragments.base.BaseFragment
import com.shadhinmusiclibrary.library.player.utils.CacheRepository

internal class PodcastDownloadFragment : BaseFragment(),
    DownloadedSongOnCallBack,
    CommonPSVCallback {
    private lateinit var parentAdapter: ConcatAdapter
    private lateinit var footerAdapter: HomeFooterAdapter
    private lateinit var navController: NavController
    private lateinit var downloadedSongsAdapter: DownloadedSongsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        navController = findNavController()
        return inflater.inflate(R.layout.my_bl_sdk_fragment_download_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        downloadedSongsAdapter = DownloadedSongsAdapter(this, this)
        loadData()
    }

    fun loadData() {
        val cacheRepository = CacheRepository(requireContext())
        cacheRepository.getAllPodcastDownloads()
            ?.let {
                downloadedSongsAdapter.setData(
                    it.toMutableList(),
                    argHomePatchDetail ?: HomePatchDetailModel(),
                    playerViewModel.currentMusic?.mediaId
                )
            }

        val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val config = ConcatAdapter.Config.Builder().apply { setIsolateViewTypes(false) }.build()
        footerAdapter = HomeFooterAdapter()
        parentAdapter = ConcatAdapter(config, downloadedSongsAdapter)
        recyclerView.adapter = parentAdapter

        playerViewModel.currentMusicLiveData.observe(viewLifecycleOwner) { music ->
            if (music != null) {
                if (music.mediaId != null) {
                    downloadedSongsAdapter.setPlayingSong(music.mediaId!!)
                }
            }
        }
    }

    override fun onClickItem(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int) {
        Log.e(
            "ALLDDF",
            "click rcid: " + mSongDetails[clickItemPosition].titleName + " id " + mSongDetails[clickItemPosition].content_Id
        )
        if (playerViewModel.currentMusic != null && (mSongDetails[clickItemPosition].rootContentId == playerViewModel.currentMusic?.rootId)) {
            if ((mSongDetails[clickItemPosition].content_Id != playerViewModel.currentMusic?.mediaId)) {
                playerViewModel.skipToQueueItem(clickItemPosition)
            } else {
                playerViewModel.togglePlayPause()
            }
        } else {
            playItem(
                mSongDetails,
                clickItemPosition
            )
        }
    }

    //Todo net to review this codes
    override fun onClickFavItem(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int) {

    }

    override fun onFavAlbumClick(itemPosition: Int, mSongDetails: MutableList<IMusicModel>) {

    }

    override fun onClickBottomItemPodcast(mSongDetails: IMusicModel) {
        (activity as? SDKMainActivity)?.showBottomSheetDialogForPodcast(
            navController,
            context = requireContext(),
            SongTrackModel().apply {
                rootContentType = mSongDetails.rootContentType
                content_Type = mSongDetails.content_Type
                artistName = mSongDetails.artistName
                total_duration = mSongDetails.total_duration
                content_Id = mSongDetails.content_Id
                imageUrl = mSongDetails.imageUrl
                titleName = mSongDetails.titleName
                playingUrl = mSongDetails.playingUrl
                rootContentId = mSongDetails.rootContentId
                rootImage = mSongDetails.imageUrl
            },
            argHomePatchItem,
            argHomePatchDetail
        )
    }

    override fun onClickBottomItemSongs(mSongDetails: IMusicModel) {
        (activity as? SDKMainActivity)?.showBottomSheetDialog(
            navController,
            context = requireContext(),
            SongDetailModel().apply {
                content_Id = mSongDetails.content_Id
                imageUrl = mSongDetails.imageUrl
                titleName = mSongDetails.titleName
                rootContentType = mSongDetails.rootContentType
                playingUrl = mSongDetails.playingUrl
                artistName = mSongDetails.artistName
                total_duration = mSongDetails.total_duration
            },
            argHomePatchItem,
            argHomePatchDetail
        )
    }

    override fun onClickBottomItemVideo(mSongDetails: IMusicModel) {

    }
}