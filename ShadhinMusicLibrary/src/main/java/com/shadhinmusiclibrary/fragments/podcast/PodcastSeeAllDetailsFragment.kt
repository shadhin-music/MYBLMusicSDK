package com.shadhinmusiclibrary.fragments.podcast

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.ParentAdapter
import com.shadhinmusiclibrary.adapter.PlaylistHeaderAdapter
import com.shadhinmusiclibrary.adapter.PodcastSeeAllDetailsAdapter
import com.shadhinmusiclibrary.adapter.PodcastTNTypeAdapter
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.FeaturedPodcastDetailsModel
import com.shadhinmusiclibrary.fragments.base.BaseFragment
import com.shadhinmusiclibrary.fragments.fav.FavViewModel
import com.shadhinmusiclibrary.library.player.data.model.MusicPlayList
import com.shadhinmusiclibrary.library.player.utils.CacheRepository
import com.shadhinmusiclibrary.library.player.utils.isPlaying
import com.shadhinmusiclibrary.utils.AppConstantUtils
import com.shadhinmusiclibrary.utils.UtilHelper
import java.io.Serializable


internal class PodcastSeeAllDetailsFragment : BaseFragment(), PodcastDetailsCallback {
    lateinit var viewModel: FeaturedPodcastViewModel
    private lateinit var navController: NavController
    private var dataAdapter: PodcastSeeAllDetailsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                injector.featuredpodcastViewModelFactory
            )[FeaturedPodcastViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewRef = inflater.inflate(R.layout.my_bl_sdk_common_rv_layout, container, false)
        navController = findNavController()
        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()

        viewModel.fetchPodcastSeeAll(false)
        observeData()
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    fun observeData() {
        val progress: ProgressBar = requireView().findViewById(R.id.progress_bar)
        val favViewModel =
            ViewModelProvider(
                this,
                injector.factoryFavContentVM
            )[FavViewModel::class.java]
        val cacheRepository = CacheRepository(requireContext())
        viewModel.podcastSeeAllContent.observe(viewLifecycleOwner) { res ->
            dataAdapter = PodcastSeeAllDetailsAdapter(this, cacheRepository, favViewModel, injector)
           // progress.visibility = GONE
            val recyclerView: RecyclerView = view?.findViewById(R.id.recyclerView)!!
            val layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = dataAdapter
            Log.e("TAG", "PodcastDATA: " + res.data?.data)
            for (item in res?.data?.data?.indices!!) {
                if (isValidDesign(res.data.data[item].PatchType) == -1) {
                    res.data.data[item].PatchType = ""
                }
                if (res.data.data[item].PatchType.isNotEmpty()) {
                    res.data.data[item].let { it1 ->
                        dataAdapter?.setData(listOf(it1))
                        //dataAdapter?.notifyItemChanged(pageNum)
                        dataAdapter?.notifyDataSetChanged()
                    }
//                  it.data.let {
                    //   it1 ->
                    // }
                }
            }
        }

    }

    private fun isValidDesign(patchType: String): Int {
        return when (patchType) {

            "PP" -> ParentAdapter.VIEW_ARTIST
            "TN" -> ParentAdapter.VIEW_PLAYLIST
            "SS" -> ParentAdapter.VIEW_RELEASE

            else -> {
                -1
            }
        }
    }

    override fun onPodcastLatestShowClick(show: List<FeaturedPodcastDetailsModel>, position: Int) {

        var mEpisode = UtilHelper.getHomePatchDetailToFeaturedPodcastDetails(show[position])

        navController.navigate(
            R.id.to_podcast_details,
            Bundle().apply {

                putSerializable(
                    AppConstantUtils.PatchDetail,
                    mEpisode as Serializable
                )
            })
    }

    override fun onPodcastEpisodeClick(episode: List<FeaturedPodcastDetailsModel>, position: Int) {
        var mEpisode = UtilHelper.getHomePatchDetailToFeaturedPodcastDetails(episode[position])

        navController.navigate(
            R.id.to_podcast_details,
            Bundle().apply {

                putSerializable(
                    AppConstantUtils.PatchDetail,
                    mEpisode as Serializable
                )
            })
    }

    override fun onPodcastTrackClick(data: List<FeaturedPodcastDetailsModel>, position: Int) {
        setMusicPlayerInitData(data as MutableList<FeaturedPodcastDetailsModel>, position)
    }

//    override fun getCurrentVH(
//        currentVH: RecyclerView.ViewHolder,
//        data: List<FeaturedPodcastDetailsModel>,
//    ) {
//        var trackViewHolder = currentVH as PodcastTNTypeAdapter.ViewHolder
//        Log.e("TAG", "HELLO: " + trackViewHolder)
//        if (data.size > 0 && isAdded) {
//
//            playerViewModel.currentMusicLiveData.observe(viewLifecycleOwner) { itMusic ->
//                if (itMusic != null) {
////                    Log.e("TAG", "HELLO: "
////                            + data[0].ContentType
////                            + " " + itMusic.rootType
////                            + " " + data[0].TracktId
////                            + " " + itMusic.rootId
////                            + " " + data[0].TracktId
////                            + " " + itMusic.mediaId)
//                    if ((data.indexOfFirst {
//                            it.ContentType == itMusic.rootType &&
//                                    it.TracktId == itMusic.rootId &&
//                                    it.TracktId == itMusic.mediaId
//                        } != -1)
//                    ) {
//                        //DO NOT USE requireActivity()
//
//                        playerViewModel.playbackStateLiveData.observe(viewLifecycleOwner) { itPla ->
//                            playPauseStatePodcast(itPla!!.isPlaying, trackViewHolder.ivPlayBtn!!)
//                        }
//                    } else {
//                        trackViewHolder.ivPlayBtn?.let { playPauseStatePodcast(false, it) }
//                    }
//                }
//            }
//        }
//    }


    fun playPauseStatePodcast(playing: Boolean, ivPlayPause: ImageView) {
        if (playing) {
            ivPlayPause.setImageResource(R.drawable.my_bl_sdk_ic_pd_play)
        } else {
            ivPlayPause.setImageResource(R.drawable.ic_pd_pause)
        }
    }

    fun setMusicPlayerInitData(
        mSongDetails: MutableList<FeaturedPodcastDetailsModel>,
        clickItemPosition: Int,
    ) {

//
        if (playerViewModel.currentMusic != null && (mSongDetails[clickItemPosition].TracktId == playerViewModel.currentMusic?.rootId)) {
            if ((mSongDetails[clickItemPosition].TracktId != playerViewModel.currentMusic?.mediaId)) {
                playerViewModel.skipToQueueItem(clickItemPosition)
            } else {
                playerViewModel.togglePlayPause()
            }
        } else {
//            playItem(
//                mSongDetails,
//                clickItemPosition
//            )
//            playerViewModel.unSubscribe()
//            playerViewModel.subscribe(
//                MusicPlayList(
//                    UtilHelper.getMusicListToPodcastDetailsList(mSongDetails),
//                    0
//                ),
//                false,
//                clickItemPosition
//            )
        }
    }
}

interface PodcastDetailsCallback {
    fun onPodcastLatestShowClick(patchItem: List<FeaturedPodcastDetailsModel>, position: Int)
    fun onPodcastEpisodeClick(data: List<FeaturedPodcastDetailsModel>, position: Int)
    fun onPodcastTrackClick(data: List<FeaturedPodcastDetailsModel>, position: Int)
//    fun getCurrentVH(
//        currentVH: RecyclerView.ViewHolder,
//        data: List<FeaturedPodcastDetailsModel>,
//    )
}




