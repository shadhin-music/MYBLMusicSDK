package com.shadhinmusiclibrary.fragments.album

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.adapter.*
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
import com.shadhinmusiclibrary.fragments.base.BaseFragment
import com.shadhinmusiclibrary.player.utils.isPlaying
import com.shadhinmusiclibrary.utils.Status
import com.shadhinmusiclibrary.utils.UtilHelper

internal class AlbumDetailsFragment :
    BaseFragment<AlbumViewModel, AlbumViewModelFactory>(),
    FragmentEntryPoint,
    OnItemClickCallback,
    BottomSheetDialogItemCallback,
    HomeCallBack {

    private lateinit var navController: NavController

    //    private lateinit var albumAdapter: AlbumAdapter
    private lateinit var albumHeaderAdapter: AlbumHeaderAdapter
    private lateinit var footerAdapter: HomeFooterAdapter
    private lateinit var albumsTrackAdapter: AlbumsTrackAdapter
    private lateinit var artistAlbumsAdapter: ArtistAlbumsAdapter
    var artistAlbumModelData: ArtistAlbumModelData? = null

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
        val viewRef = inflater.inflate(R.layout.my_bl_sdk_fragment_album_details, container, false)
        navController = findNavController()

        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        albumHeaderAdapter = AlbumHeaderAdapter(argHomePatchDetail, this)
        albumsTrackAdapter = AlbumsTrackAdapter(this, this)
        footerAdapter = HomeFooterAdapter()
        setupViewModel()
        observeData(
            argHomePatchDetail!!.AlbumId,
            argHomePatchDetail!!.ArtistId,
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
            /* if (ShadhinMusicSdkCore.pressCountDecrement() == 0) {
                 requireActivity().finish()
             } else {
                 navController.popBackStack()
             }*/
            requireActivity().onBackPressed()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            Log.i("getMainLooper", "postDelayed: ")
            playerViewModel.currentMusicLiveData.observe(viewLifecycleOwner) { music ->
                if (music?.mediaId != null) {
                    albumsTrackAdapter.setPlayingSong(music.mediaId!!)
                }
            }
        }, 1000 * 5)

        /*   try {
               //DO NOT USE requireActivity()
               playerViewModel.playListLiveData.observe(viewLifecycleOwner) { itMusicPlay ->
                   playerViewModel.musicIndexLiveData.observe(viewLifecycleOwner) { itCurrPlayItem ->
                       albumsTrackAdapter.setPlayingSong(itMusicPlay.list[itCurrPlayItem].mediaId!!)
                       albumsTrackAdapter.notifyDataSetChanged()
   //                    albumsTrackAdapter.setPlayingSong(
   //                        itCurrPlayItem.toString(),
   //                        UtilHelper.getSongDetailToMusicList(itMusicPlay.list.toMutableList())
   //                    )
                   }
               }
           } catch (ex: Exception) {
               ex.printStackTrace()
           }*/
    }

    private fun setupViewModel() {
        viewModelArtistAlbum = ViewModelProvider(
            this,
            injector.artistAlbumViewModelFactory
        )[ArtistAlbumsViewModel::class.java]
    }

    private fun observeData(contentId: String, artistId: String, contentType: String) {
        val progressBar: ProgressBar = requireView().findViewById(R.id.progress_bar)
        viewModel?.fetchAlbumContent(contentId)
        viewModel?.albumContent?.observe(viewLifecycleOwner) { res ->
            if (res.status == Status.SUCCESS) {
                progressBar.visibility = GONE
                if (res.data?.data != null && argHomePatchDetail != null) {
                    albumsTrackAdapter.setData(res.data.data, argHomePatchDetail!!)
                    albumHeaderAdapter.setSongAndData(res.data.data, argHomePatchDetail!!)
                }
                //  updateAndSetAdapter(res.data!!.data)
            } else {
                progressBar.visibility = VISIBLE
            }
        }
        viewModelArtistAlbum.fetchArtistAlbum("r", artistId)
        viewModelArtistAlbum.artistAlbumContent.observe(viewLifecycleOwner) { res ->
            if (res.status == Status.SUCCESS) {
                artistAlbumsAdapter.setData(res.data)
            } else {
            }
        }
    }

    override fun onRootClickItem(mSongDetails: MutableList<SongDetail>, clickItemPosition: Int) {
        val lSongDetails = albumsTrackAdapter.dataSongDetail
        if (lSongDetails.size > clickItemPosition) {
            if ((lSongDetails[clickItemPosition].rootContentID == playerViewModel.currentMusic?.rootId)) {
                playerViewModel.togglePlayPause()
            } else {
                playItem(lSongDetails, clickItemPosition)
            }
            //  albumsTrackAdapter.setPlayingSong(mSongDetails[clickItemPosition].ContentID)
            // albumsTrackAdapter.notifyDataSetChanged()
        }
    }

    override fun onClickItem(mSongDetails: MutableList<SongDetail>, clickItemPosition: Int) {
        if (playerViewModel.currentMusic != null) {
            if ((mSongDetails[clickItemPosition].rootContentID == playerViewModel.currentMusic?.rootId)) {
                if ((mSongDetails[clickItemPosition].ContentID != playerViewModel.currentMusic?.mediaId)) {
                    playerViewModel.skipToQueueItem(clickItemPosition)
                } else {
                    playerViewModel.togglePlayPause()
                }
            } else {
                playItem(mSongDetails, clickItemPosition)
            }
        } else {
            playItem(mSongDetails, clickItemPosition)
        }
        // albumsTrackAdapter.setPlayingSong(mSongDetails[clickItemPosition].ContentID)
        //  albumsTrackAdapter.notifyDataSetChanged()
    }

    override fun getCurrentVH(
        currentVH: RecyclerView.ViewHolder,
        songDetails: MutableList<SongDetail>
    ) {
        Log.e("ADF", "getCurrentVH: ")
        val mSongDet = albumsTrackAdapter.dataSongDetail
        val albumVH = currentVH as AlbumHeaderAdapter.HeaderViewHolder
        if (songDetails.size > 0 && isAdded) {
            //DO NOT USE requireActivity()
            playerViewModel.currentMusicLiveData.observe(viewLifecycleOwner) { itMusic ->
                if (itMusic != null) {
                    if ((songDetails.indexOfFirst {
                            it.rootContentType == itMusic.rootType &&
                                    it.rootContentID == itMusic.rootId &&
                                    it.ContentID == itMusic.mediaId
                        } != -1)
                    ) {
                        //DO NOT USE requireActivity()
                        playerViewModel.playbackStateLiveData.observe(viewLifecycleOwner) { itPla ->
                            albumVH.ivPlayBtn?.let { playPauseState(itPla.isPlaying, it) }
                        }
                        //   albumsTrackAdapter.setPlayingSong(itMusic.mediaId!!)

                        //DO NOT USE requireActivity()
                        playerViewModel.musicIndexLiveData.observe(viewLifecycleOwner) { itCurrPlayPos ->
//                            albumsTrackAdapter.getItemId(albumsTrackAdapter)
//                            if ((mSongDet[itCurrPlayPos].rootContentType == playerViewModel.currentMusic?.rootId))
//                            albumsTrackAdapter.notifyDataSetChanged()
//                            Log.e(
//                                "AlbumDF",
//                                "albumVH\n rootId: " + songDetails[itCurrPlayPos].rootContentID + " " + itMusic.rootId
//                                        + "\n ContentID: " + songDetails[itCurrPlayPos].ContentID + " " + itMusic.mediaId
//                                        + "\n rootType: " + songDetails[itCurrPlayPos].rootContentType + " " + itMusic.rootType
//                                        + "\n ContentType: " + songDetails[itCurrPlayPos].ContentType + " " + itMusic.contentType
//                            )
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
        observeData(mArtAlbumMod.ContentID, mArtAlbumMod.ArtistId, "r")
    }
}