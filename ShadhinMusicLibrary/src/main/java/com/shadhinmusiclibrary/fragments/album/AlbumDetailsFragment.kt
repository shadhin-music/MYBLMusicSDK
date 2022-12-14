package com.shadhinmusiclibrary.fragments.album

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
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.adapter.AlbumAdapter
import com.shadhinmusiclibrary.adapter.AlbumHeaderAdapter
import com.shadhinmusiclibrary.adapter.AlbumsTrackAdapter
import com.shadhinmusiclibrary.adapter.HomeFooterAdapter
import com.shadhinmusiclibrary.callBackService.BottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.callBackService.OnItemClickCallback
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModelData
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumsViewModel
import com.shadhinmusiclibrary.fragments.artist.ArtistContentData
import com.shadhinmusiclibrary.fragments.base.BaseFragment
import com.shadhinmusiclibrary.player.utils.isPlaying
import com.shadhinmusiclibrary.utils.Status

class AlbumDetailsFragment :
    BaseFragment<AlbumViewModel, AlbumViewModelFactory>(),
    FragmentEntryPoint,
    OnItemClickCallback,
    BottomSheetDialogItemCallback,
    HomeCallBack {

    private lateinit var navController: NavController
    private lateinit var adapter: AlbumAdapter
    private lateinit var albumHeaderAdapter: AlbumHeaderAdapter
    private lateinit var footerAdapter: HomeFooterAdapter
    private lateinit var albumsTrackAdapter: AlbumsTrackAdapter
    var artistAlbumModelData: ArtistAlbumModelData? = null

    //private lateinit var albumViewModel: AlbumViewModel
    private lateinit var artistAlbumsAdapter: ArtistAlbumsAdapter

    private lateinit var viewModelArtistAlbum: ArtistAlbumsViewModel
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

        albumHeaderAdapter = AlbumHeaderAdapter(argHomePatchDetail, this)

        footerAdapter = HomeFooterAdapter()
        albumsTrackAdapter = AlbumsTrackAdapter(this, this)
        setupViewModel()
        observeData(
            argHomePatchDetail!!.ContentID.toInt(),
            argHomePatchDetail!!.ArtistId.toInt(),
            argHomePatchDetail!!.ContentType
        )

        artistAlbumsAdapter = ArtistAlbumsAdapter(argHomePatchItem, this)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val config = ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .build()
        val concatAdapter = ConcatAdapter(
            config,
            albumHeaderAdapter,
            albumsTrackAdapter,
            artistAlbumsAdapter,
            footerAdapter
        )
        recyclerView.adapter = concatAdapter
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            if (ShadhinMusicSdkCore.pressCountDecrement() == 0) {
                requireActivity().finish()
            } else {
                navController.popBackStack()
            }
        }
    }

    private fun setupViewModel() {
        viewModelArtistAlbum = ViewModelProvider(
            this,
            injector.artistAlbumViewModelFactory
        )[ArtistAlbumsViewModel::class.java]
    }

    private fun observeData(contentId: Int, artistId: Int, contentType: String) {
        val progressBar: ProgressBar = requireView().findViewById(R.id.progress_bar)
        viewModel!!.fetchAlbumContent(contentId)
        viewModel!!.albumContent.observe(requireActivity()) { res ->
            if (res.status == Status.SUCCESS) {
                progressBar.visibility = GONE
                albumsTrackAdapter.setData(res.data!!.data, argHomePatchDetail!!)
                //  updateAndSetAdapter(res.data!!.data)
            } else {
                progressBar.visibility = VISIBLE
            }
        }

        viewModelArtistAlbum.fetchArtistAlbum("r", artistId.toInt())
        viewModelArtistAlbum.artistAlbumContent.observe(viewLifecycleOwner) { res ->

            if (res.status == Status.SUCCESS) {
                Log.e("TAG", "ARTISTDATA: " + res.data!!.data)
                artistAlbumsAdapter.setData(res.data)
            } else {
                // showDialog()
            }


        }
    }

    override fun onRootClickItem(_mSongDetails: MutableList<SongDetail>, clickItemPosition: Int) {
        val mSongDetails = albumsTrackAdapter.dataSongDetail
        Log.e("Check", "array size ->" + mSongDetails.size + "  index -> " + clickItemPosition)
        if (mSongDetails.size > clickItemPosition) {
            Log.e(
                "Check",
                "rhs ->" + mSongDetails[clickItemPosition].rootContentID + "  lfs -> " + playerViewModel.currentMusic?.rootId
            )
            if ((mSongDetails[clickItemPosition].rootContentID == playerViewModel.currentMusic?.rootId)) {
                playerViewModel.togglePlayPause()
            } else {
                playItem(mSongDetails, clickItemPosition)
            }
        }
    }

    override fun onClickItem(mSongDetails: MutableList<SongDetail>, clickItemPosition: Int) {
        if ((mSongDetails[clickItemPosition].rootContentID == playerViewModel.currentMusic?.rootId)) {
            if ((mSongDetails[clickItemPosition].ContentID != playerViewModel.currentMusic?.mediaId)) {
                playerViewModel.skipToQueueItem(clickItemPosition)
            } else {
                playerViewModel.togglePlayPause()
            }
        } else {
            playItem(mSongDetails, clickItemPosition)
        }
    }


    override fun getCurrentVH(
        currentVH: RecyclerView.ViewHolder,
        songDetails: MutableList<SongDetail>
    ) {
        val _songDetails = albumsTrackAdapter.dataSongDetail
        val albumVH = currentVH as AlbumHeaderAdapter.HeaderViewHolder
        if (_songDetails.size > 0 && isAdded) {
            playerViewModel.currentMusicLiveData.observe(requireActivity()) { itMusic ->
                if (itMusic != null) {
                    if ((_songDetails.indexOfFirst {
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
        (activity as? SDKMainActivity)?.showBottomSheetDialog(
            navController,
            context = requireContext(),
            mSongDetails,
            argHomePatchItem,
            argHomePatchDetail
        )
    }

    override fun onClickItemAndAllItem(itemPosition: Int, selectedHomePatchItem: HomePatchItem) {
//       val  argHomePatchDetail = selectedHomePatchItem.Data[itemPosition]
//        albumHeaderAdapter.setData(argHomePatchDetail)
//        observeData(argHomePatchDetail.ContentID.toInt(),argHomePatchDetail.ArtistId.toInt(),"r")
    }

    override fun onClickSeeAll(selectedHomePatchItem: HomePatchItem) {

    }

    override fun onClickItemPodcastEpisode(itemPosition: Int, selectedEpisode: List<Episode>) {

    }

    override fun onArtistAlbumClick(
        itemPosition: Int,
        artistAlbumModelData: List<ArtistAlbumModelData>
    ) {
        val mArtAlbumMod = artistAlbumModelData[itemPosition]
        val data = HomePatchDetail(
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
            title = mArtAlbumMod.title,
            Type = ""
        )
        argHomePatchDetail = data
        albumHeaderAdapter.setData(data)
        observeData(mArtAlbumMod.ContentID.toInt(), mArtAlbumMod.ArtistId.toInt(), "r")
    }
}