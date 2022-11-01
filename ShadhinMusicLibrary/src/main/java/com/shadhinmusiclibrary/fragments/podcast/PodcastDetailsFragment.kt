package com.shadhinmusiclibrary.fragments.podcast

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.*
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.callBackService.PodcastOnItemClickCallback
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.HomePatchItemModel
import com.shadhinmusiclibrary.data.model.podcast.DataModel
import com.shadhinmusiclibrary.data.model.podcast.EpisodeModel
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.library.player.utils.isPlaying
import com.shadhinmusiclibrary.utils.Status


internal class PodcastDetailsFragment : CommonBaseFragment(), HomeCallBack,
    PodcastOnItemClickCallback {

    private lateinit var viewModel: PodcastViewModel
    private lateinit var navController: NavController

    private lateinit var podcastHeaderAdapter: PodcastHeaderAdapter
    private lateinit var podcastTrackAdapter: PodcastTrackAdapter
    private lateinit var podcastMoreEpisodesAdapter: PodcastMoreEpisodesAdapter
    private lateinit var concatAdapter: ConcatAdapter

    var data: DataModel? = null
    var episode: List<EpisodeModel>? = null

    var podcastType: String = ""
    var contentType: String = ""
    private var selectedEpisodeID: Int = 0
    var contentId: Int = 0
    private lateinit var footerAdapter: HomeFooterAdapter
    private lateinit var parentRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewRef = inflater.inflate(R.layout.my_bl_sdk_common_rv_pb_layout, container, false)
        navController = findNavController()
        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        argHomePatchDetail?.let {
            val Type: String = it.ContentType
            podcastType = Type.take(2)
            contentType = Type.takeLast(2)
            contentId = it.ContentID.toInt()
            selectedEpisodeID = it.AlbumId.toInt()
        }

        setupViewModel()
        if (selectedEpisodeID == contentId) {
            getPodcastShowDetailsInitialize()
        } else {
            getPodcastDetailsInitialize()
        }

        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        playerViewModel.currentMusicLiveData.observe(viewLifecycleOwner) { music ->
            if (music?.mediaId != null) {
                podcastTrackAdapter.setPlayingSong(music.mediaId!!)
            }
        }
    }

    private fun getPodcastShowDetailsInitialize() {
        setupAdapters()
        observePodcastShowData()
    }

    private fun getPodcastDetailsInitialize() {
        setupAdapters()
        observePodcastDetailsData()
    }

    private fun setupAdapters() {
        parentRecycler = requireView().findViewById(R.id.recyclerView)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val config = ConcatAdapter.Config.Builder().apply { setIsolateViewTypes(false) }.build()
        podcastHeaderAdapter = PodcastHeaderAdapter(this)
        podcastTrackAdapter = PodcastTrackAdapter(this)
        podcastMoreEpisodesAdapter = PodcastMoreEpisodesAdapter(data, this)
        footerAdapter = HomeFooterAdapter()
//        artistsYouMightLikeAdapter =
//            ArtistsYouMightLikeAdapter(argHomePatchItem, this, argHomePatchDetail?.ArtistId)
        concatAdapter = ConcatAdapter(
            config,
            podcastHeaderAdapter,
            PodcastTrackHeaderAdapter(),
            podcastTrackAdapter,
            podcastMoreEpisodesAdapter, footerAdapter
        )
        concatAdapter.notifyDataSetChanged()
        parentRecycler.layoutManager = layoutManager
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, injector.podcastViewModelFactory)[PodcastViewModel::class.java]
    }

    private fun observePodcastShowData() {
        viewModel.fetchPodcastShowContent(podcastType, contentType, false)
        val progressBar: ProgressBar = requireView().findViewById(R.id.progress_bar)
        viewModel.podcastDetailsContent.observe(viewLifecycleOwner) { response ->
            if (response.status == Status.SUCCESS) {
                response?.data?.data?.EpisodeList?.let { itEpisod ->
//                    podcastHeaderAdapter.setHeader(
//                        itEpisod,
//                        itEpisod[0].TrackList
//                    )
                    podcastHeaderAdapter.setTrackData(
                        itEpisod,
                        itEpisod[0].TrackList,
                        argHomePatchDetail!!
                    )
                }
                response.data?.data?.EpisodeList?.get(0)
                    ?.let {
                        podcastTrackAdapter.setData(
                            it.TrackList,
                            argHomePatchDetail!!,
                            playerViewModel.currentMusic?.mediaId
                        )
                    }
                response.data?.data?.let {
                    it.EpisodeList.let { it1 ->
                        podcastMoreEpisodesAdapter.setData(it1)
                    }
                }
                parentRecycler.adapter = concatAdapter
                progressBar.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun observePodcastDetailsData() {
        viewModel.fetchPodcastContent(podcastType, selectedEpisodeID, contentType, false)
        val progressBar: ProgressBar = requireView().findViewById(R.id.progress_bar)
        viewModel.podcastDetailsContent.observe(viewLifecycleOwner) { response ->
            if (response.status == Status.SUCCESS) {
                response?.data?.data?.EpisodeList?.let { itEpisod ->
//                    podcastHeaderAdapter.setHeader(
//                        itEpisod,
//                        itEpisod[0].TrackList
//                    )
                    podcastHeaderAdapter.setTrackData(
                        itEpisod,
                        itEpisod[0].TrackList,
                        argHomePatchDetail!!
                    )
                }
                response.data?.data?.EpisodeList?.get(0)
                    ?.let {
                        podcastTrackAdapter.setData(
                            it.TrackList,
                            argHomePatchDetail!!,
                            playerViewModel.currentMusic?.mediaId
                        )
                    }
                response.data?.data?.let {
                    it.EpisodeList.let { it1 ->
                        podcastMoreEpisodesAdapter.setData(it1)
                    }
                }
                parentRecycler.adapter = concatAdapter
                progressBar.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
            }
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


    override fun onClickItemAndAllItem(
        itemPosition: Int,
        selectedHomePatchItem: HomePatchItemModel
    ) {
        Log.e("TAG", "onClickItemAndAllItem: " + selectedHomePatchItem)
        //  setAdapter(patch)
//        argHomePatchDetail = selectedHomePatchItem.Data[itemPosition]
//        artistHeaderAdapter.setData(argHomePatchDetail!!)
//        observeData()
//        artistsYouMightLikeAdapter.artistIDToSkip = argHomePatchDetail!!.ArtistId
//        parentAdapter.notifyDataSetChanged()
//        parentRecycler.scrollToPosition(0)
    }

    override fun onClickSeeAll(selectedHomePatchItem: HomePatchItemModel) {
    }

    override fun onClickItemPodcastEpisode(itemPosition: Int, selectedEpisode: List<EpisodeModel>) {
        val episode = selectedEpisode[itemPosition]
        selectedEpisodeID = episode.Id
        argHomePatchDetail?.ContentID = episode.Id.toString()
        observePodcastDetailsData()
        //podcastHeaderAdapter.setHeader(data)
        // podcastEpisodesAdapter.setData
//        artistHeaderAdapter.setData(argHomePatchDetail!!)
//        observeData()
//        artistsYouMightLikeAdapter.artistIDToSkip = argHomePatchDetail!!.ArtistId
//    parentAdapter.notifyDataSetChanged()
//        parentRecycler.scrollToPosition(0)
        concatAdapter.notifyDataSetChanged()
        parentRecycler.scrollToPosition(0)
    }

    override fun onRootClickItem(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int) {
        Log.e("PCDF", "onRootClickItem: " + mSongDetails.size + " " + clickItemPosition)
        val lSongDetails = podcastTrackAdapter.tracks
        if (lSongDetails.size > clickItemPosition) {
            if ((lSongDetails[clickItemPosition].rootContentId == playerViewModel.currentMusic?.rootId)) {
                playerViewModel.togglePlayPause()
            } else {
                playItem(lSongDetails, clickItemPosition)
            }
        }
    }

    override fun onClickItem(mTracks: MutableList<IMusicModel>, clickItemPosition: Int) {
        if (playerViewModel.currentMusic != null) {
            if ((mTracks[clickItemPosition].rootContentId == playerViewModel.currentMusic?.rootId)) {
                /*if ((mTracks[clickItemPosition].Id.toString() != playerViewModel.currentMusic?.mediaId)) {*/
                if ((mTracks[clickItemPosition].content_Id.toString() != playerViewModel.currentMusic?.mediaId)) {
                    playerViewModel.skipToQueueItem(clickItemPosition)
                } else {
                    playerViewModel.togglePlayPause()
                }
            } else {
                playItem(mTracks, clickItemPosition)
            }
        } else {
            playItem(mTracks, clickItemPosition)
        }
    }

    override fun getCurrentVH(
        currentVH: RecyclerView.ViewHolder,
        mTracks: MutableList<IMusicModel>
    ) {
//        val mSongDet = podcastTrackAdapter.tracks
        val podcastHeaderVH = currentVH as PodcastHeaderAdapter.PodcastHeaderVH
        if (mTracks.size > 0 && isAdded) {
            //DO NOT USE requireActivity()
            playerViewModel.currentMusicLiveData.observe(viewLifecycleOwner) { itMusic ->
                if (itMusic != null) {
                    if ((mTracks.indexOfFirst {
                            it.rootContentType == itMusic.rootType &&
                                    it.rootContentId == itMusic.rootId &&
                                    it.content_Id.toString() == itMusic.mediaId
                            /*     it.Id.toString() == itMusic.mediaId*/
                        } != -1)
                    ) {
                        playerViewModel.playbackStateLiveData.observe(viewLifecycleOwner) { itPla ->
                            playPauseState(itPla!!.isPlaying, podcastHeaderVH.ivPlayBtn!!)
                        }
                    }
                } else {
                    podcastHeaderVH.ivPlayBtn?.let { playPauseState(false, it) }
                }
            }
        }
    }
}