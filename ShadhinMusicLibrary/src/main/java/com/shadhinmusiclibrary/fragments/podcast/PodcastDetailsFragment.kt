package com.shadhinmusiclibrary.fragments.podcast

import android.app.AlertDialog
import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.adapter.*
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.callBackService.PodcastBottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.PodcastOnItemClickCallback
import com.shadhinmusiclibrary.data.model.DownloadingItem
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.podcast.Data
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.data.model.podcast.Track
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.fragments.fav.FavViewModel
import com.shadhinmusiclibrary.player.utils.CacheRepository
import com.shadhinmusiclibrary.player.utils.isPlaying
import com.shadhinmusiclibrary.utils.Status
import com.shadhinmusiclibrary.utils.UtilHelper


internal class PodcastDetailsFragment : CommonBaseFragment(), FragmentEntryPoint, HomeCallBack,
    PodcastOnItemClickCallback , PodcastBottomSheetDialogItemCallback {

    private lateinit var viewModel: PodcastViewModel
    private lateinit var navController: NavController

    private lateinit var podcastHeaderAdapter: PodcastHeaderAdapter
    private lateinit var podcastTrackAdapter: PodcastTrackAdapter
    private lateinit var podcastMoreEpisodesAdapter: PodcastMoreEpisodesAdapter
    private lateinit var concatAdapter: ConcatAdapter

    var data: Data? = null
    var episode: List<Episode>? = null

    var podcastType: String = ""
    var contentType: String = ""
    var selectedEpisodeID: Int = 0
    private lateinit var footerAdapter: HomeFooterAdapter
    private lateinit var parentRecycler: RecyclerView
    private var cacheRepository: CacheRepository? = null
    private lateinit var favViewModel: FavViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewRef = inflater.inflate(R.layout.my_bl_sdk_fragment_artist_details, container, false)
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
        }
        cacheRepository= CacheRepository(requireContext())
        initialize()
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

    private fun initialize() {
        setupViewModel()
        setupAdapters()
        observeData()
    }

    private fun setupAdapters() {
        parentRecycler = requireView().findViewById(R.id.recyclerView)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val config = ConcatAdapter.Config.Builder().apply { setIsolateViewTypes(false) }.build()
        podcastHeaderAdapter = PodcastHeaderAdapter(this,cacheRepository,favViewModel, argHomePatchDetail)
        podcastTrackAdapter = PodcastTrackAdapter(this,this, cacheRepository!! )
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
        favViewModel = ViewModelProvider(this,injector.factoryFavContentVM)[FavViewModel::class.java]
    }

    private fun observeData() {
        viewModel.fetchPodcastContent(podcastType, selectedEpisodeID, contentType, false)
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
                            it.TrackList.toMutableList(),
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
            } else {
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
        argHomePatchDetail?.ContentID = episode.Id.toString()
        observeData()
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

    override fun onRootClickItem(mSongDetails: MutableList<Track>, clickItemPosition: Int) {
        Log.e("PCDF", "onRootClickItem: " + mSongDetails.size + " " + clickItemPosition)
        val lSongDetails = podcastTrackAdapter.tracks
        if (lSongDetails.size > clickItemPosition) {
            if ((lSongDetails[clickItemPosition].rootContentID == playerViewModel.currentMusic?.rootId)) {
                playerViewModel.togglePlayPause()
            } else {
                playItem(UtilHelper.getSongDetailToTrackList(lSongDetails), clickItemPosition)
            }
        }
    }

    override fun onClickItem(mTracks: MutableList<Track>, clickItemPosition: Int) {
        if (playerViewModel.currentMusic != null) {
            if ((mTracks[clickItemPosition].rootContentID == playerViewModel.currentMusic?.rootId)) {
                if ((mTracks[clickItemPosition].Id.toString() != playerViewModel.currentMusic?.mediaId)) {
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
//        val mSongDet = podcastTrackAdapter.tracks
        val podcastHeaderVH = currentVH as PodcastHeaderAdapter.PodcastHeaderVH
        if (mTracks.size > 0 && isAdded) {
            //DO NOT USE requireActivity()
            playerViewModel.currentMusicLiveData.observe(viewLifecycleOwner) { itMusic ->
                if (itMusic != null) {
                    if ((mTracks.indexOfFirst {
                            it.rootContentType == itMusic.rootType &&
                                    it.rootContentID == itMusic.rootId &&
                                    it.Id.toString() == itMusic.mediaId
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

    override fun onClickBottomItem(track: Track) {
        (activity as? SDKMainActivity)?.showBottomSheetDialogForPodcast(
            navController,
            context = requireContext(),
            track,
            argHomePatchItem,
            argHomePatchDetail
        )
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter()
        intentFilter.addAction("ACTION")
        intentFilter.addAction("REMOVE")
        intentFilter.addAction("PROGRESS")
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(MyBroadcastReceiver(), intentFilter)
    }
    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(MyBroadcastReceiver())
    }
    private fun progressIndicatorUpdate(downloadingItems: List<DownloadingItem>) {

        downloadingItems.forEach {


            val progressIndicator: CircularProgressIndicator? =
                view?.findViewWithTag(it.contentId)
//                val downloaded: ImageView?= view?.findViewWithTag(200)
            progressIndicator?.visibility = View.VISIBLE
            progressIndicator?.progress = it.progress.toInt()
//            val isDownloaded =
//                cacheRepository?.isTrackDownloaded(it.contentId) ?: false
//            if(isDownloaded){
//                progressIndicator?.visibility = View.GONE
//                // downloaded?.visibility = VISIBLE
//            }

            Log.e("getDownloadManagerx",
                "habijabi: ${it.toString()} ${progressIndicator == null}")


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

//                        Log.e("getDownloadManagerx",
//                            "habijabi: ${it.toString()} ")
                    }
                }
                "REMOVE" -> {
                   podcastTrackAdapter.notifyDataSetChanged()
                    Log.e("DELETED", "broadcast fired")
                }
                "PROGRESS" -> {

                    podcastTrackAdapter.notifyDataSetChanged()
                    Log.e("PROGRESS", "broadcast fired")
                }
                else -> Toast.makeText(context, "Action Not Found", Toast.LENGTH_LONG).show()
            }

        }
    }
}