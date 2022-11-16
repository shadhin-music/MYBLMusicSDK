package com.shadhinmusiclibrary.fragments.album

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
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
import com.shadhinmusiclibrary.callBackService.BottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.callBackService.OnItemClickCallback
import com.shadhinmusiclibrary.callBackService.favItemClickCallback
import com.shadhinmusiclibrary.data.model.DownloadingItem
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModelData
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumsViewModel
import com.shadhinmusiclibrary.fragments.base.BaseFragment
import com.shadhinmusiclibrary.fragments.fav.FavViewModel
import com.shadhinmusiclibrary.player.utils.CacheRepository
import com.shadhinmusiclibrary.player.utils.isPlaying
import com.shadhinmusiclibrary.utils.Status

internal class AlbumDetailsFragment :
    BaseFragment<AlbumViewModel, AlbumViewModelFactory>(),
    FragmentEntryPoint,
    OnItemClickCallback,
    BottomSheetDialogItemCallback,
    HomeCallBack {
    private var cacheRepository: CacheRepository? = null
    private lateinit var navController: NavController
    private lateinit var albumHeaderAdapter: AlbumHeaderAdapter
    private lateinit var albumsTrackAdapter: AlbumsTrackAdapter
    private lateinit var artistAlbumsAdapter: ArtistAlbumsAdapter
    private lateinit var footerAdapter: HomeFooterAdapter
    var artistAlbumModelData: ArtistAlbumModelData? = null

    private lateinit var favViewModel: FavViewModel

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
        cacheRepository= CacheRepository(requireContext())
        albumHeaderAdapter = AlbumHeaderAdapter(argHomePatchDetail, this)
        albumsTrackAdapter = AlbumsTrackAdapter(this, this,cacheRepository)
        footerAdapter = HomeFooterAdapter()
        setupViewModel()
        observeData(
            argHomePatchDetail!!.ContentID,
            argHomePatchDetail!!.ArtistId,
            argHomePatchDetail!!.ContentType
        )
        artistAlbumsAdapter = ArtistAlbumsAdapter(argHomePatchItem, this)
        Log.e("TAG","ARTISTID: "+argHomePatchDetail!!.ArtistId)
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
            requireActivity().onBackPressed()
        }
        playerViewModel.currentMusicLiveData.observe(viewLifecycleOwner) { music ->
            if (music?.mediaId != null) {
                albumsTrackAdapter.setPlayingSong(music.mediaId!!)
            }
        }
    }

    private fun setupViewModel() {
        viewModelArtistAlbum = ViewModelProvider(
            this,
            injector.artistAlbumViewModelFactory
        )[ArtistAlbumsViewModel::class.java]
        favViewModel = ViewModelProvider(this,injector.factoryFavContentVM)[FavViewModel::class.java]
    }

    private fun observeData(contentId: String, artistId: String, contentType: String) {
        val progressBar: ProgressBar = requireView().findViewById(R.id.progress_bar)
        viewModel?.fetchAlbumContent(contentId)
        Log.e("TAG","DATA : "+contentId)
        Log.e("TAG","DATA : "+artistId)
        viewModel?.albumContent?.observe(viewLifecycleOwner) { res ->
            if (res.status == Status.SUCCESS) {
                progressBar.visibility = GONE
                if (res.data?.data != null && argHomePatchDetail != null) {
                    albumsTrackAdapter.setData(
                        res.data.data,
                        argHomePatchDetail!!,
                        playerViewModel.currentMusic?.mediaId
                    )
                    albumHeaderAdapter.setSongAndData(res.data.data, argHomePatchDetail!!,  cacheRepository!!,favViewModel)
                }
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
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter()
        intentFilter.addAction("ACTION")
        intentFilter.addAction("DELETED")
        intentFilter.addAction("PROGRESS")
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(MyBroadcastReceiver(), intentFilter)
    }
    override fun getCurrentVH(
        currentVH: RecyclerView.ViewHolder,
        songDetails: MutableList<SongDetail>,
    ) {
//        val mSongDet = albumsTrackAdapter.dataSongDetail
        val albumHeaderVH = currentVH as AlbumHeaderAdapter.HeaderViewHolder
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
                        playerViewModel.playbackStateLiveData.observe(viewLifecycleOwner) { itPla ->
                            albumHeaderVH.ivPlayBtn?.let { playPauseState(itPla.isPlaying, it) }
                        }
                    } else {
                        albumHeaderVH.ivPlayBtn?.let { playPauseState(false, it) }
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
        artistAlbumModelData: List<ArtistAlbumModelData>,
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
    private fun progressIndicatorUpdate(downloadingItems: List<DownloadingItem>) {

        downloadingItems.forEach {


                val progressIndicatorAlbum: CircularProgressIndicator? =
                    view?.findViewWithTag(it.contentId)
               val downloaded: ImageView?= view?.findViewWithTag(300)
            progressIndicatorAlbum?.visibility = VISIBLE
            progressIndicatorAlbum?.progress = it.progress.toInt()
//            val isDownloaded =
//                cacheRepository?.isTrackDownloaded(it.contentId) ?: false
//            if(!isDownloaded){
//                progressIndicator?.visibility = View.GONE
//                downloaded?.visibility = View.GONE
//            }
//            val isDownloaded =
//                cacheRepository?.isTrackDownloaded(it.contentId) ?: false
//                if(isDownloaded.equals(true)){
//                   progressIndicator?.visibility = GONE
//                   downloaded?.visibility = VISIBLE
//                }
//            Log.e("getDownloadManagerx",
//                "habijabi:"+ isDownloaded )
//                Log.e("getDownloadManagerx",
//                    "habijabi: ${it.toString()} ${progressIndicator == null}")


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
                "DELETED" -> {
                    albumsTrackAdapter.notifyDataSetChanged()
                    Log.e("DELETED", "broadcast fired")
                }
                "PROGRESS" -> {

                   albumsTrackAdapter.notifyDataSetChanged()
                    Log.e("PROGRESS", "broadcast fired")
                }
                else -> Toast.makeText(context, "Action Not Found", Toast.LENGTH_LONG).show()
            }

        }
    }




}