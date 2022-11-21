package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.adapter.DownloadedSongsAdapter
import com.shadhinmusiclibrary.callBackService.DownloadBottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.DownloadedSongOnCallBack
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.SongDetailModel
import com.shadhinmusiclibrary.data.model.fav.FavData
import com.shadhinmusiclibrary.data.model.podcast.SongTrackModel
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.fragments.base.BaseFragment
import com.shadhinmusiclibrary.library.player.utils.CacheRepository


internal class PodcastDownloadFragment : BaseFragment(), DownloadedSongOnCallBack,
    DownloadBottomSheetDialogItemCallback {

    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        navController = findNavController()
        return inflater.inflate(R.layout.my_bl_sdk_fragment_download_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    fun loadData() {
        val cacheRepository = CacheRepository(requireContext())
        val dataAdapter =
            cacheRepository.getAllPodcastDownloads()?.let { DownloadedSongsAdapter(it, this) }

        val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = dataAdapter
    }

    override fun onClickItem(mSongDetails: MutableList<DownloadedContent>, clickItemPosition: Int) {
        if (playerViewModel.currentMusic != null && (mSongDetails[clickItemPosition].rootContentId == playerViewModel.currentMusic?.rootId)) {
            if ((mSongDetails[clickItemPosition].content_Id != playerViewModel.currentMusic?.mediaId)) {
                playerViewModel.skipToQueueItem(clickItemPosition)
            } else {
                playerViewModel.togglePlayPause()
            }
        } else {
            //Todo Mehenaz ap please flowe as link artist/album
//                playItem(
//                    UtilHelper.getSongDetailToDownloadedSongDetailList(mSongDetails),
//                    clickItemPosition
//                )
        }
    }

    //Todo net to review this codes
    override fun onClickFavItem(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int) {

    }

    override fun onClickBottomItemPodcast(mSongDetails: DownloadedContent) {
        (activity as? SDKMainActivity)?.showBottomSheetDialogForPodcast(
            navController,
            context = requireContext(),
            SongTrackModel().apply {
                rootContentType = mSongDetails.rootContentType
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

    override fun onClickBottomItemSongs(mSongDetails: DownloadedContent) {
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

    override fun onClickBottomItemVideo(mSongDetails: DownloadedContent) {

    }
}