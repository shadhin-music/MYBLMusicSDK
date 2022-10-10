package com.shadhinmusiclibrary.fragments.artist

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibra.ArtistAlbumsAdapter
import com.shadhinmusiclibra.ArtistsYouMightLikeAdapter
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.adapter.ArtistHeaderAdapter
import com.shadhinmusiclibrary.adapter.ArtistTrackAdapter
import com.shadhinmusiclibrary.adapter.HomeFooterAdapter
import com.shadhinmusiclibrary.callBackService.ArtistOnItemClickCallback
import com.shadhinmusiclibrary.callBackService.BottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.player.utils.isPlaying
import com.shadhinmusiclibrary.utils.AppConstantUtils
import com.shadhinmusiclibrary.utils.Status
import com.shadhinmusiclibrary.utils.UtilHelper
import java.io.Serializable


class ArtistDetailsFragment : CommonBaseFragment(), HomeCallBack,
    ArtistOnItemClickCallback, BottomSheetDialogItemCallback {
    private lateinit var navController: NavController
    var artistContent: ArtistContent? = null
    private lateinit var viewModel: ArtistViewModel
    private lateinit var viewModelArtistBanner: ArtistBannerViewModel
    private lateinit var viewModelArtistSong: ArtistContentViewModel
    private lateinit var viewModelArtistAlbum: ArtistAlbumsViewModel
    private lateinit var parentAdapter: ConcatAdapter
    private lateinit var footerAdapter: HomeFooterAdapter
    private lateinit var artistHeaderAdapter: ArtistHeaderAdapter
    private lateinit var artistsYouMightLikeAdapter: ArtistsYouMightLikeAdapter
    private lateinit var artistTrackAdapter: ArtistTrackAdapter
    private lateinit var artistAlbumsAdapter: ArtistAlbumsAdapter
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
        Log.e("Check", "yes iam called")
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
        artistHeaderAdapter = ArtistHeaderAdapter(argHomePatchDetail, this)
        artistTrackAdapter = ArtistTrackAdapter(this, this)
        artistAlbumsAdapter = ArtistAlbumsAdapter(argHomePatchItem, this)
        artistsYouMightLikeAdapter =
            ArtistsYouMightLikeAdapter(argHomePatchItem, this, argHomePatchDetail?.ArtistId)
        footerAdapter = HomeFooterAdapter()
        parentAdapter = ConcatAdapter(
            config,
            artistHeaderAdapter,
            artistTrackAdapter,
            artistAlbumsAdapter,
            artistsYouMightLikeAdapter,
            footerAdapter
        )
        parentAdapter.notifyDataSetChanged()
        parentRecycler.setLayoutManager(layoutManager)
        parentRecycler.setAdapter(parentAdapter)
    }

    private fun setupViewModel() {

        viewModel =
            ViewModelProvider(this, injector.factoryArtistVM)[ArtistViewModel::class.java]
        viewModelArtistBanner = ViewModelProvider(
            this,
            injector.factoryArtistBannerVM
        )[ArtistBannerViewModel::class.java]
        viewModelArtistSong = ViewModelProvider(
            this,
            injector.factoryArtistSongVM
        )[ArtistContentViewModel::class.java]
        viewModelArtistAlbum = ViewModelProvider(
            this,
            injector.artistAlbumViewModelFactory
        )[ArtistAlbumsViewModel::class.java]
    }

    private fun observeData() {
        argHomePatchDetail?.let {
            viewModel.fetchArtistBioData(it.Artist)
            Log.e("TAG", "DATA: " + it.Artist)
        }
        val progressBar: ProgressBar = requireView().findViewById(R.id.progress_bar)
        viewModel.artistBioContent.observe(viewLifecycleOwner) { response ->

            if (response.status == Status.SUCCESS) {
                artistHeaderAdapter.artistBio(response.data)
                Log.e("TAG", "DATA321: " + response.data?.artist)
//                Log.e("TAG","DATA: "+ response.message)
                progressBar.visibility = GONE
            } else {
                progressBar.visibility = GONE
            }
        }
        argHomePatchDetail.let {
            it?.ArtistId?.let { it1 ->
                it1.toInt().let { it2 -> viewModelArtistBanner.fetchArtistBannerData(it2) }
            }
            viewModelArtistBanner.artistBannerContent.observe(viewLifecycleOwner) { response ->
                if (response.status == Status.SUCCESS) {
                    progressBar.visibility = GONE
                    artistHeaderAdapter.artistBanner(response.data)
                } else {
                    progressBar.visibility = VISIBLE
                }
            }
        }
        argHomePatchDetail.let {
            viewModelArtistSong.fetchArtistSongData(it!!.ArtistId.toInt())
            viewModelArtistSong.artistSongContent.observe(viewLifecycleOwner) { res ->
                if (res.status == Status.SUCCESS) {
                    artistTrackAdapter.artistContent(res.data)
                } else {
                    showDialog()
                }
            }
        }
        argHomePatchDetail.let {
            viewModelArtistAlbum.fetchArtistAlbum("r", it!!.ArtistId?.toInt()!!)
            viewModelArtistAlbum.artistAlbumContent.observe(viewLifecycleOwner) { res ->

                if (res.status == Status.SUCCESS) {
                    artistAlbumsAdapter.setData(res.data)
                } else {
                    showDialog()
                }
            }
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext()) //set icon
            .setIcon(android.R.drawable.ic_dialog_alert) //set title
            .setTitle("An error occurred") //set message
            .setMessage("Go back to previous page") //set positive button
            .setPositiveButton(
                "Okay"
            ) { _, _ ->
                requireActivity().finish()
            }

            .show()
    }

    override fun onClickItemAndAllItem(itemPosition: Int, selectedHomePatchItem: HomePatchItem) {
        Log.e("TAG", "DATA ARtist: " + selectedHomePatchItem)
        //  setAdapter(patch)
        argHomePatchDetail = selectedHomePatchItem.Data[itemPosition]
        artistHeaderAdapter.setData(argHomePatchDetail!!)
        observeData()
        artistsYouMightLikeAdapter.artistIDToSkip = argHomePatchDetail!!.ArtistId
        parentAdapter.notifyDataSetChanged()
        parentRecycler.scrollToPosition(0)

    }

    fun loadNewArtist(patchDetails: HomePatchDetail) {
        Log.e("Check", "loadNewArtist")
    }

    override fun onArtistAlbumClick(
        itemPosition: Int,
        artistAlbumModelData: List<ArtistAlbumModelData>,
    ) {
        ShadhinMusicSdkCore.pressCountIncrement()
        val mArtAlbumMod = artistAlbumModelData[itemPosition]
        navController.navigate(R.id.action_artist_details_fragment_to_album_details_fragment,
            Bundle().apply {
                putSerializable(
                    AppConstantUtils.PatchItem,
                    HomePatchItem("", "", listOf(), "", "", 0, 0)
                )
                putSerializable(
                    AppConstantUtils.PatchDetail,
                    HomePatchDetail(
                        AlbumId = mArtAlbumMod.AlbumId,
                        ArtistId = mArtAlbumMod.ArtistId,
                        ContentID = mArtAlbumMod.ContentID,
                        ContentType = mArtAlbumMod.ContentType,
                        PlayUrl = mArtAlbumMod.PlayUrl,
                        AlbumName = mArtAlbumMod.title,
                        AlbumImage = "",
                        fav = mArtAlbumMod.fav,
                        Banner = "",
                        Duration = mArtAlbumMod.duration,
                        TrackType = "",
                        image = mArtAlbumMod.image,
                        ArtistImage = "",
                        Artist = mArtAlbumMod.artistname,
                        CreateDate = "",
                        Follower = "",
                        imageWeb = "",
                        IsPaid = false,
                        NewBanner = "",
                        PlayCount = 0,
                        PlayListId = "",
                        PlayListImage = "",
                        PlayListName = "",
                        RootId = "",
                        RootType = "",
                        Seekable = false,
                        TeaserUrl = "",
                        title = "",
                        Type = ""

                    ) as Serializable
                )
            })
    }

    override fun onClickItemPodcastEpisode(itemPosition: Int, selectedEpisode: List<Episode>) {

    }

    override fun onClickSeeAll(selectedHomePatchItem: HomePatchItem) {
    }

    override fun onRootClickItem(
        mSongDetails: MutableList<ArtistContentData>,
        clickItemPosition: Int
    ) {
        val lSongDetails = artistTrackAdapter.artistSongList
        if (lSongDetails.size > clickItemPosition) {
            Log.e("ADF", "onRootClickItem: " + lSongDetails[clickItemPosition].rootContentID)
            if ((lSongDetails[clickItemPosition].ContentID == playerViewModel.currentMusic?.rootId)) {
                playerViewModel.togglePlayPause()
            } else {
                playItem(
                    UtilHelper.getSongDetailToArtistContentDataList(lSongDetails),
                    clickItemPosition
                )
            }
        }
    }

    override fun onClickItem(
        mSongDetails: MutableList<ArtistContentData>,
        clickItemPosition: Int
    ) {
        if (playerViewModel.currentMusic != null) {
            if ((mSongDetails[clickItemPosition].ContentID == playerViewModel.currentMusic?.rootId)) {
                if ((mSongDetails[clickItemPosition].ContentID != playerViewModel.currentMusic?.mediaId)) {
                    playerViewModel.skipToQueueItem(clickItemPosition)
                } else {
                    playerViewModel.togglePlayPause()
                }
            } else {
                playItem(
                    UtilHelper.getSongDetailToArtistContentDataList(mSongDetails),
                    clickItemPosition
                )
            }
        } else {
            playItem(
                UtilHelper.getSongDetailToArtistContentDataList(mSongDetails),
                clickItemPosition
            )
        }
    }

    override fun getCurrentVH(
        currentVH: RecyclerView.ViewHolder,
        songDetails: MutableList<ArtistContentData>
    ) {
        val mSongDet = artistTrackAdapter.artistSongList
        val albumVH = currentVH as ArtistHeaderAdapter.ArtistHeaderVH
        if (mSongDet.size > 0 && isAdded) {
            playerViewModel.currentMusicLiveData.observe(requireActivity()) { itMusic ->
                if (itMusic != null) {
                    if ((mSongDet.indexOfFirst {
                            it.rootContentType == itMusic.rootType &&
                                    it.ContentID == itMusic.mediaId
                        } != -1)
                    ) {
                        playerViewModel.playbackStateLiveData.observe(requireActivity()) { itPla ->
                            playPauseState(itPla!!.isPlaying, albumVH.ivPlayBtn!!)
                        }

                        playerViewModel.musicIndexLiveData.observe(requireActivity()) {
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

    override fun onClickBottomItem(mSongDetails: SongDetail) {
        (activity as? SDKMainActivity)?.showBottomSheetDialog2(
            navController,
            context = requireContext(),
            mSongDetails,
            argHomePatchItem,
            argHomePatchDetail
        )
    }
}