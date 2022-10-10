package com.shadhinmusiclibrary.fragments.podcast

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.adapter.*
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.callBackService.PodcustOnItemClickCallback
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.podcast.Data
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.data.model.podcast.Track
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.utils.Status
import com.shadhinmusiclibrary.utils.UtilHelper


class PodcastDetailsFragment : CommonBaseFragment(), FragmentEntryPoint, HomeCallBack,
    PodcustOnItemClickCallback {
    private lateinit var navController: NavController

    //    var homePatchItem: HomePatchItem? = null
//    var homePatchDetail: HomePatchDetail? = null
    private lateinit var viewModel: PodcastViewModel
    private lateinit var parentAdapter: ConcatAdapter
    private lateinit var podcastHeaderAdapter: PodcastHeaderAdapter
    private lateinit var podcastEpisodesAdapter: PodcastEpisodesAdapter
    private lateinit var podcastMoreEpisodesAdapter: PodcastMoreEpisodesAdapter
    var data: Data? = null
    var episode: List<Episode>? = null

    var podcastType: String = ""
    var contentType: String = ""
    var selectedEpisodeID: Int = 0
    private lateinit var footerAdapter: HomeFooterAdapter
    private lateinit var parentRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewRef = inflater.inflate(R.layout.fragment_artist_details, container, false)
        navController = findNavController()
        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        argHomePatchDetail?.let {
            val Type: String = it.ContentType
            podcastType = Type.take(2)
            contentType = Type.takeLast(2)
            selectedEpisodeID = it.AlbumId.toInt()
            //  Log.d("TAG", "PODCAST DATA: " + contentType + podcastType+it.AlbumId+false)
            //viewModel.fetchPodcastContent(podcastType,it.AlbumId.toInt(),contentType,false)
        }
        // Log.e("Check", "yes iam called")
        initialize()
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            if (ShadhinMusicSdkCore.pressCountDecrement() == 0) {
                requireActivity().finish()
            } else {
                navController.popBackStack()
            }
        }
    }

    private fun initialize() {
        setupAdapters()
        setupViewModel()
        observeData()
    }

    private fun setupAdapters() {
        parentRecycler = requireView().findViewById(R.id.recyclerView)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val config = ConcatAdapter.Config.Builder().apply { setIsolateViewTypes(false) }.build()
        podcastHeaderAdapter = PodcastHeaderAdapter(this)
        podcastEpisodesAdapter = PodcastEpisodesAdapter(this)
        podcastMoreEpisodesAdapter = PodcastMoreEpisodesAdapter(data, this)
        footerAdapter = HomeFooterAdapter()
//        artistsYouMightLikeAdapter =
//            ArtistsYouMightLikeAdapter(argHomePatchItem, this, argHomePatchDetail?.ArtistId)
        parentAdapter = ConcatAdapter(
            config,
            podcastHeaderAdapter,
            PodcastTrackHeaderAdapter(),
            podcastEpisodesAdapter,
            podcastMoreEpisodesAdapter, footerAdapter
        )
        parentAdapter.notifyDataSetChanged()
        parentRecycler.setLayoutManager(layoutManager)
        parentRecycler.setAdapter(parentAdapter)
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, injector.podcastViewModelFactory)[PodcastViewModel::class.java]
    }

    private fun observeData() {
        viewModel.fetchPodcastContent(podcastType, selectedEpisodeID, contentType, false)
        viewModel.podcastDetailsContent.observe(viewLifecycleOwner) { response ->
            if (response.status == Status.SUCCESS) {
                response?.data?.data?.EpisodeList?.let {
                    podcastHeaderAdapter.setHeader(
                        it,
                        it[0].TrackList
                    )
                }
                response.data?.data?.EpisodeList?.get(0)
                    ?.let { podcastEpisodesAdapter.setData(it.TrackList.toMutableList()) }
                response.data?.data?.let {
                    it.EpisodeList.let { it1 ->
                        podcastMoreEpisodesAdapter.setData(it1)
                    }
                }
            } else {
            }
//            ArtistHeaderAdapter(it)
            // viewDataInRecyclerView(it)
            //Log.e("TAG","DATA: "+ it.artist)
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext()) //set icon
            .setIcon(android.R.drawable.ic_dialog_alert) //set title
            .setTitle("An Error Happend") //set message
            .setMessage("Go back to previous page") //set positive button
            .setPositiveButton("Okay",
                DialogInterface.OnClickListener { dialogInterface, i ->
                })
            .show()
    }


    override fun onClickItemAndAllItem(itemPosition: Int, selectedHomePatchItem: HomePatchItem) {
        Log.e("TAG", "onClickItemAndAllItem: " + selectedHomePatchItem)
        //  setAdapter(patch)
//        argHomePatchDetail = selectedHomePatchItem.Data[itemPosition]
//        artistHeaderAdapter.setData(argHomePatchDetail!!)
//        observeData()
//        artistsYouMightLikeAdapter.artistIDToSkip = argHomePatchDetail!!.ArtistId
//        parentAdapter.notifyDataSetChanged()
//        parentRecycler.scrollToPosition(0)
    }

    override fun onClickSeeAll(selectedHomePatchItem: HomePatchItem) {
    }

    override fun onClickItemPodcastEpisode(itemPosition: Int, selectedEpisode: List<Episode>) {
        val episode = selectedEpisode[itemPosition]
        selectedEpisodeID = episode.Id
        observeData()

        //podcastHeaderAdapter.setHeader(data)
        // podcastEpisodesAdapter.setData
//        artistHeaderAdapter.setData(argHomePatchDetail!!)
//        observeData()
//        artistsYouMightLikeAdapter.artistIDToSkip = argHomePatchDetail!!.ArtistId
//    parentAdapter.notifyDataSetChanged()
//        parentRecycler.scrollToPosition(0)
        parentAdapter.notifyDataSetChanged()
        parentRecycler.scrollToPosition(0)
    }

    override fun onRootClickItem(mSongDetails: MutableList<Track>, clickItemPosition: Int) {
        Log.e("PCDF", "onRootClickItem: " + mSongDetails.size + " " + clickItemPosition)
        if ((mSongDetails[clickItemPosition].ShowId == playerViewModel.currentMusic?.rootId)) {
            playerViewModel.togglePlayPause()
        } else {
            playItem(UtilHelper.getSongDetailToTrackList(mSongDetails), clickItemPosition)
        }
    }

    override fun onClickItem(mTracks: MutableList<Track>, clickItemPosition: Int) {
        if (playerViewModel.currentMusic != null) {
            if ((mTracks[clickItemPosition].ShowId == playerViewModel.currentMusic?.rootId)) {
                if ((mTracks[clickItemPosition].ShowId != playerViewModel.currentMusic?.mediaId)) {
                    playerViewModel.skipToQueueItem(clickItemPosition)
                } else {
                    playerViewModel.togglePlayPause()
                }
            } else {
                playItem(UtilHelper.getSongDetailToTrackList(mTracks), clickItemPosition)
            }
        } else {
            playItem(UtilHelper.getSongDetailToTrackList(mTracks), clickItemPosition)
        }
    }

    override fun getCurrentVH(currentVH: RecyclerView.ViewHolder, mTracks: MutableList<Track>) {

    }
}