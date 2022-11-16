package com.shadhinmusiclibrary.fragments.create_playlist

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.offline.DownloadRequest
import com.google.android.exoplayer2.offline.DownloadService
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.adapter.HomeFooterAdapter
import com.shadhinmusiclibrary.adapter.UserCreatedPlaylistHeaderAdapter
import com.shadhinmusiclibrary.adapter.UserCreatedPlaylistTrackAdapter
import com.shadhinmusiclibrary.callBackService.BottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.CreatedPlaylistSongBottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.OnItemClickCallback
import com.shadhinmusiclibrary.data.model.DownloadingItem
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.HomePatchItemModel
import com.shadhinmusiclibrary.data.model.SongDetailModel
import com.shadhinmusiclibrary.data.model.fav.FavData
import com.shadhinmusiclibrary.download.MyBLDownloadService
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.fragments.fav.FavViewModel
import com.shadhinmusiclibrary.library.player.Constants
import com.shadhinmusiclibrary.library.player.utils.CacheRepository
import com.shadhinmusiclibrary.library.player.utils.isPlaying

internal class UserCreatedPlaylistDetailsFragment :PlaylistBaseFragment(),OnItemClickCallback,
    CreatedPlaylistSongBottomSheetDialogItemCallback {
    private lateinit var viewModel: CreateplaylistViewModel
    private lateinit var navController: NavController

    private lateinit var playlistHeaderAdapter: UserCreatedPlaylistHeaderAdapter
    private lateinit var playlistTrackAdapter: UserCreatedPlaylistTrackAdapter

    //    private lateinit var adapter: PlaylistAdapter
    private lateinit var footerAdapter: HomeFooterAdapter
    private lateinit var cacheRepository: CacheRepository

    private lateinit var favViewModel: FavViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRef =
            inflater.inflate(R.layout.my_bl_sdk_fragment_user_playlist_details, container, false)
        navController = findNavController()

        return viewRef
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this,
                injector.factoryCreatePlaylistVM)[CreateplaylistViewModel::class.java]
        favViewModel = ViewModelProvider(this,injector.factoryFavContentVM)[FavViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        cacheRepository = CacheRepository(requireContext())
        // playlistTrackAdapter = PlaylistTrackAdapter(this, cacheRepository!!)
        playlistHeaderAdapter = UserCreatedPlaylistHeaderAdapter(argHomePatchDetail,playlistName, this,cacheRepository!!,favViewModel, gradientDrawable!!)
        playlistTrackAdapter = UserCreatedPlaylistTrackAdapter(this,this, cacheRepository!!)
//        adapter = PlaylistAdapter(this, this)
        footerAdapter = HomeFooterAdapter()
        ///read data from online
        playlistId?.let { fetchOnlineData(it,playlistName) }
        Log.e("TAG", "SONGS: " + playlistId)
//        adapter.setRootData(argHomePatchDetail!!)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val config = ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .build()
        val concatAdapter = ConcatAdapter(
            config,
            playlistHeaderAdapter,
            playlistTrackAdapter,
            /*adapter,*/
            footerAdapter
        )
        recyclerView.adapter = concatAdapter
       concatAdapter.notifyDataSetChanged()
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        playerViewModel.currentMusicLiveData.observe(viewLifecycleOwner) { music ->
            if (music?.mediaId != null) {
                playlistTrackAdapter.setPlayingSong(music.mediaId!!)
            }
        }
    }

    private fun fetchOnlineData(contentId: String, playlistName: String?) {
        viewModel.getuserSongsInPlaylist(contentId)
        viewModel.getUserSongsPlaylist.observe(viewLifecycleOwner) { res ->
            Log.e("TAG", "SONGS: " + res.data)
            if (res.data != null) {

                playlistTrackAdapter.setData(
                    res.data,
                    playerViewModel.currentMusic?.mediaId
                )
                playlistHeaderAdapter.setSongAndData(
                    res.data,

                )
//                updateAndSetAdapter(res!!.data!!.data)
            } else {
//                updateAndSetAdapter(mutableListOf())
            }

            val imageView: AppCompatImageView = requireView().findViewById(R.id.imgMore)
            imageView.setOnClickListener {
                showBottomSheetDialogDeletePlaylist(navController,
                    context = requireContext(),
                    SongDetail(contentId, "",playlistName.toString(),"","","",
                        "","","","","","",
                        "",playlistId,"","","",false),
                    argHomePatchItem,
                    argHomePatchDetail)
            }
        }




//        viewModel.getUserSongsPlaylist.observe(viewLifecycleOwner) { res ->
//
//        }
    }



    override fun onRootClickItem(mSongDetails: MutableList<SongDetail>, clickItemPosition: Int) {
        val lSongDetails = playlistTrackAdapter.dataSongDetail
        if (lSongDetails.size > clickItemPosition) {
            if ((mSongDetails[clickItemPosition].rootContentID == playerViewModel.currentMusic?.rootId)) {
                playerViewModel.togglePlayPause()
                // Log.e("TAG","Post: ")
            } else {
                playItem(mSongDetails, clickItemPosition)
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

    override fun getCurrentVH(
        currentVH: RecyclerView.ViewHolder,
        songDetails: MutableList<SongDetail>
    ) {
        val playlistHeaderVH = currentVH as UserCreatedPlaylistHeaderAdapter.UserCreatedPlaylistHeaderVH
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
                            playPauseState(itPla!!.isPlaying, playlistHeaderVH.ivPlayBtn!!)
                        }
                    } else {
                        playlistHeaderVH.ivPlayBtn?.let { playPauseState(false, it) }
                    }
                }
            }
        }
    }
    override fun onClickBottomItem(mSongDetails: SongDetail) {
       showBottomSheetDialogForPlaylist(
            navController,
            context = requireContext(),
            mSongDetails,
            argHomePatchItem,
            argHomePatchDetail
        )

    }
    fun showBottomSheetDialogDeletePlaylist(
        bsdNavController: NavController,
        context: Context,
        mSongDetails: SongDetail,
        argHomePatchItem: HomePatchItem?,
        argHomePatchDetail: HomePatchDetail?,
    ) {
        val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)

        val contentView =
            View.inflate(context, R.layout.my_bl_sdk_bottomsheet_three_dot_menu_layout, null)
        bottomSheetDialog.setContentView(contentView)
        bottomSheetDialog.show()
        val closeButton: ImageView? = bottomSheetDialog.findViewById(R.id.closeButton)
        closeButton?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val image: ImageView? = bottomSheetDialog.findViewById(R.id.thumb)
        val url = mSongDetails.image
        val title: TextView? = bottomSheetDialog.findViewById(R.id.name)
        title?.text = mSongDetails.title
        val artistname = bottomSheetDialog.findViewById<TextView>(R.id.desc)
        artistname?.text = mSongDetails.artist
//        if (image != null) {
//            Glide.with(context)?.load(url?.replace("<\$size\$>", "300"))?.into(image)
//        }
        gradientDrawable?.let { image?.setImageResource(it) }

        image?.setBackgroundResource(R.drawable.my_bl_sdk_playlist_bg)
//        image?.let {
//            Glide.with(requireContext()).load(R.drawable.my_bl_sdk_playlist_bg).centerCrop().into(it)
//        }
//        image?.setImageResource(R.drawable.my_bl_sdk_playlist_bg)
        val constraintAlbum: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintAlbum)
        constraintAlbum?.visibility= GONE
//            constraintAlbum?.setOnClickListener {
//                gotoArtistFromPlaylist(
//                    bsdNavController,
//                    context,
//                    mSongDetails,
//                    argHomePatchItem,
//                    argHomePatchDetail
//
//                )
//
//                bottomSheetDialog.dismiss()
//            }
        val downloadImage:ImageView ?= bottomSheetDialog.findViewById(R.id.imgDownload)
        val textViewDownloadTitle: TextView?= bottomSheetDialog.findViewById(R.id.tv_download)
        var isDownloaded = false
        var downloaded=cacheRepository?.getDownloadById(mSongDetails.ContentID)
        if(downloaded?.track != null){
            isDownloaded=true
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_ic_delete)
        }else{
            isDownloaded=false
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_icon_dowload)
        }

        if(isDownloaded){
            textViewDownloadTitle?.text="Remove From Download"
        }else{
            textViewDownloadTitle?.text="Download Offline"
        }

        val constraintDownload: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintDownload)
        constraintDownload?.visibility= GONE
        constraintDownload?.setOnClickListener {
            if(isDownloaded.equals(true)){
                cacheRepository?.deleteDownloadById(mSongDetails.ContentID)
                DownloadService.sendRemoveDownload(context,
                    MyBLDownloadService::class.java,mSongDetails.ContentID, false)
                Log.e("TAG","DELETED: "+ isDownloaded)
                val localBroadcastManager = LocalBroadcastManager.getInstance(context)
                val localIntent = Intent("DELETED")
                    .putExtra("contentID", mSongDetails.ContentID)
                localBroadcastManager.sendBroadcast(localIntent)

            } else {
                val url = "${Constants.FILE_BASE_URL}${mSongDetails.PlayUrl}"
                val downloadRequest: DownloadRequest =
                    DownloadRequest.Builder(mSongDetails.ContentID, url.toUri())
                        .build()
                DownloadService.sendAddDownload(
                    context,
                    MyBLDownloadService::class.java,
                    downloadRequest,
                    /* foreground= */ false)

                if (cacheRepository?.isDownloadCompleted(mSongDetails.ContentID)?.equals(true) == true) {
                    cacheRepository?.insertDownload(DownloadedContent(mSongDetails.ContentID.toString(),
                        mSongDetails.rootContentID,
                        mSongDetails.image,
                        mSongDetails.title,
                        mSongDetails.ContentType,
                        mSongDetails.PlayUrl,
                        mSongDetails.ContentType,
                        0,
                        0,
                        mSongDetails.artist,mSongDetails.ArtistId.toString(),
                        mSongDetails.duration))
                    Log.e("TAGGG",
                        "INSERTED: " + cacheRepository?.isDownloadCompleted(mSongDetails.ContentID))
                }
            }
            bottomSheetDialog.dismiss()

        }
        val constraintLayoutFav:ConstraintLayout? = bottomSheetDialog.findViewById(R.id.constraintFav)
        constraintLayoutFav?.visibility = GONE
        val constraintPlaylist: ConstraintLayout? = bottomSheetDialog.findViewById(R.id.constraintAddtoPlaylist)
        val playlisttext:TextView ?= bottomSheetDialog.findViewById(R.id.tvPlaylist)
        playlisttext?.text="Delete Playlist"
        constraintPlaylist?.setOnClickListener {
            deletePlayList2(context,playlistId.toString())
            Toast.makeText(context,"Removed Successfully",Toast.LENGTH_LONG).show()
//          viewModel.deleteUserSongFromPlaylist.observe(viewLifecycleOwner){
//              res->
//              Log.e("TAG", "CLICKArtist: " + res.message)
//                   }
            bottomSheetDialog.dismiss()
        }
        Log.d("TAG", "CLICKArtist: " + argHomePatchItem)
        Log.d("TAG", "CLICKArtist: " + argHomePatchDetail)
        Log.d("TAG", "CLICKArtist: " + mSongDetails)
    }

        fun showBottomSheetDialogForPlaylist(
            bsdNavController: NavController,
            context: Context,
            mSongDetails: SongDetail,
            argHomePatchItem: HomePatchItem?,
            argHomePatchDetail: HomePatchDetail?,
        ) {
            val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)

            val contentView =
                View.inflate(context, R.layout.my_bl_sdk_bottomsheet_three_dot_menu_layout, null)
            bottomSheetDialog.setContentView(contentView)
            bottomSheetDialog.show()
            val closeButton: ImageView? = bottomSheetDialog.findViewById(R.id.closeButton)
            closeButton?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
            val image: ImageView? = bottomSheetDialog.findViewById(R.id.thumb)
            val url = mSongDetails.image
            val title: TextView? = bottomSheetDialog.findViewById(R.id.name)
            title?.text = mSongDetails.title
            val artistname = bottomSheetDialog.findViewById<TextView>(R.id.desc)
            artistname?.text = mSongDetails.artist
            if (image != null) {
                Glide.with(context)?.load(url?.replace("<\$size\$>", "300"))?.into(image)
            }
            val constraintAlbum: ConstraintLayout? =
                bottomSheetDialog.findViewById(R.id.constraintAlbum)
            constraintAlbum?.visibility= GONE
//            constraintAlbum?.setOnClickListener {
//                gotoArtistFromPlaylist(
//                    bsdNavController,
//                    context,
//                    mSongDetails,
//                    argHomePatchItem,
//                    argHomePatchDetail
//
//                )
//
//                bottomSheetDialog.dismiss()
//            }
            val downloadImage:ImageView ?= bottomSheetDialog.findViewById(R.id.imgDownload)
            val textViewDownloadTitle: TextView?= bottomSheetDialog.findViewById(R.id.tv_download)
            var isDownloaded = false
            var downloaded=cacheRepository?.getDownloadById(mSongDetails.ContentID)
            if(downloaded?.track != null){
                isDownloaded=true
                downloadImage?.setImageResource(R.drawable.my_bl_sdk_ic_delete)
            }else{
                isDownloaded=false
                downloadImage?.setImageResource(R.drawable.my_bl_sdk_icon_dowload)
            }

            if(isDownloaded){
                textViewDownloadTitle?.text="Remove From Download"
            }else{
                textViewDownloadTitle?.text="Download Offline"
            }

            val constraintDownload: ConstraintLayout? =
                bottomSheetDialog.findViewById(R.id.constraintDownload)
            constraintDownload?.visibility= GONE
            constraintDownload?.setOnClickListener {
                if(isDownloaded.equals(true)){
                    cacheRepository?.deleteDownloadById(mSongDetails.ContentID)
                    DownloadService.sendRemoveDownload(context,
                        MyBLDownloadService::class.java,mSongDetails.ContentID, false)
                    Log.e("TAG","DELETED: "+ isDownloaded)
                    val localBroadcastManager = LocalBroadcastManager.getInstance(context)
                    val localIntent = Intent("DELETED")
                        .putExtra("contentID", mSongDetails.ContentID)
                    localBroadcastManager.sendBroadcast(localIntent)

                } else {
                    val url = "${Constants.FILE_BASE_URL}${mSongDetails.PlayUrl}"
                    val downloadRequest: DownloadRequest =
                        DownloadRequest.Builder(mSongDetails.ContentID, url.toUri())
                            .build()
                    DownloadService.sendAddDownload(
                        context,
                        MyBLDownloadService::class.java,
                        downloadRequest,
                        /* foreground= */ false)

                    if (cacheRepository?.isDownloadCompleted(mSongDetails.ContentID)?.equals(true) == true) {
                        cacheRepository?.insertDownload(DownloadedContent(mSongDetails.ContentID.toString(),
                            mSongDetails.rootContentID,
                            mSongDetails.image,
                            mSongDetails.title,
                            mSongDetails.ContentType,
                            mSongDetails.PlayUrl,
                            mSongDetails.ContentType,
                            0,
                            0,
                            mSongDetails.artist,mSongDetails.ArtistId.toString(),
                            mSongDetails.duration))
                        Log.e("TAGGG",
                            "INSERTED: " + cacheRepository?.isDownloadCompleted(mSongDetails.ContentID))
                    }
                }
                bottomSheetDialog.dismiss()
            }
            val constraintPlaylist: ConstraintLayout? = bottomSheetDialog.findViewById(R.id.constraintAddtoPlaylist)
             val playlisttext:TextView ?= bottomSheetDialog.findViewById(R.id.tvPlaylist)
            playlisttext?.text="Delete from Playlist"
            constraintPlaylist?.setOnClickListener {
                deleteSongFromPlayList(context,mSongDetails,playlistId)
                Toast.makeText(context,"Removed Successfully",Toast.LENGTH_LONG).show()
//          viewModel.deleteUserSongFromPlaylist.observe(viewLifecycleOwner){
//              res->
//              Log.e("TAG", "CLICKArtist: " + res.message)
//                   }
                bottomSheetDialog.dismiss()
            }
            val constraintFav: ConstraintLayout? = bottomSheetDialog.findViewById(R.id.constraintFav)
            constraintFav?.visibility = GONE
            val favImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgLike)
            val textFav: TextView? = bottomSheetDialog.findViewById(R.id.tvFav)
            var isFav = false
            val isAddedToFav = cacheRepository?.getFavoriteById(mSongDetails.ContentID)
            if (isAddedToFav?.contentID != null) {

                favImage?.setImageResource(R.drawable.my_bl_sdk_ic_icon_fav)
                isFav = true
                textFav?.text = "Remove From favorite"
            } else {

                favImage?.setImageResource(R.drawable.my_bl_sdk_ic_like)
                isFav = false
                textFav?.text = "Favorite"
            }


            constraintFav?.setOnClickListener {
                if(isFav.equals(true)){
                    favViewModel.deleteFavContent(mSongDetails.ContentID,mSongDetails.ContentType)
                    cacheRepository?.deleteFavoriteById(mSongDetails.ContentID)
                    Toast.makeText(requireContext(),"Removed from favorite",Toast.LENGTH_LONG).show()
                    favImage?.setImageResource(R.drawable.my_bl_sdk_ic_like)
                    isFav=false
                    Log.e("TAG","NAME: "+ isFav)
                } else {

                    favViewModel.addFavContent(mSongDetails.ContentID,mSongDetails.ContentType)

                    favImage?.setImageResource(R.drawable.my_bl_sdk_ic_icon_fav)
                    Log.e("TAG","NAME123: "+ isFav)
                    cacheRepository.insertFavSingleContent(FavData(mSongDetails.ContentID,mSongDetails.albumId,mSongDetails.image,"",mSongDetails.artist,mSongDetails.ArtistId,
                        "","",2,mSongDetails.ContentType,"","","1","",mSongDetails.image,"",
                        false,  "",0,"","","",mSongDetails.PlayUrl,mSongDetails.rootContentID,
                        mSongDetails.rootContentType,false,"",mSongDetails.title,"",""

                    ))
                    isFav = true
                    Toast.makeText(requireContext(),"Added to favorite",Toast.LENGTH_LONG).show()
                }
                bottomSheetDialog.dismiss()
            }
            Log.d("TAG", "CLICKArtist: " + argHomePatchItem)
            Log.d("TAG", "CLICKArtist: " + argHomePatchDetail)
            Log.d("TAG", "CLICKArtist: " + mSongDetails)
        }

    private fun deleteSongFromPlayList(context: Context, mSongDetails: SongDetail, playlistId: String?) {
        Log.e("TAG", "CLICKArtist: " + mSongDetails.ContentID)
        Log.e("TAG", "CLICKArtist: " + playlistId)
         viewModel.deleteuserSongfromPlaylist(playlistId.toString(),mSongDetails.ContentID)
    }
    private fun deletePlayList2(context: Context, playlistId: String?) {
        Log.e("TAG", "CLICKArtist: " + playlistId)
        viewModel.deleteuserPlaylist(playlistId.toString())
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

    private fun progressIndicatorUpdate(downloadingItems: List<DownloadingItem>) {

        downloadingItems.forEach {


            val progressIndicator: CircularProgressIndicator? =
                view?.findViewWithTag(it.contentId)
                val downloaded: ImageView?= view?.findViewWithTag(220)
            progressIndicator?.visibility = View.VISIBLE
            progressIndicator?.progress = it.progress.toInt()
//            val isDownloaded =
//                cacheRepository?.isTrackDownloaded(it.contentId) ?: false
//            if(!isDownloaded){
//                progressIndicator?.visibility = View.GONE
//                downloaded?.visibility = View.GONE
//            }

            Log.e("getDownloadManagerx",
                "habijabi: ${it.toString()} ${progressIndicator == null}")


        }


    }

    inner class MyBroadcastReceiver : BroadcastReceiver() {
        @SuppressLint("NotifyDataSetChanged")
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
                   // playlistTrackAdapter.notifyDataSetChanged()
                    Log.e("DELETED", "broadcast fired")
                }
                "PROGRESS" -> {

                    //playlistTrackAdapter.notifyDataSetChanged()
                    Log.e("PROGRESS", "broadcast fired")
                }
                else -> Toast.makeText(context, "Action Not Found", Toast.LENGTH_LONG).show()
            }

        }
    }
}

