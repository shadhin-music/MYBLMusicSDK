package com.shadhinmusiclibrary.fragments.fav

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import com.shadhinmusiclibrary.adapter.FavoriteSongsAdapter
import com.shadhinmusiclibrary.adapter.HomeFooterAdapter
import com.shadhinmusiclibrary.callBackService.CommonPSVCallback
import com.shadhinmusiclibrary.callBackService.DownloadedSongOnCallBack
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.DownloadingItem
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.HomePatchItemModel
import com.shadhinmusiclibrary.data.model.fav.FavDataModel
import com.shadhinmusiclibrary.data.model.podcast.SongTrackModel
import com.shadhinmusiclibrary.download.MyBLDownloadService
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.fragments.base.BaseFragment
import com.shadhinmusiclibrary.library.player.Constants
import com.shadhinmusiclibrary.library.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.UtilHelper

internal class PodcastFavFragment : BaseFragment(),
    DownloadedSongOnCallBack,
    CommonPSVCallback {
    private lateinit var favViewModel: FavViewModel
    private lateinit var navController: NavController
    private lateinit var favoriteSongsAdapter: FavoriteSongsAdapter
    private lateinit var parentAdapter: ConcatAdapter
    private lateinit var footerAdapter: HomeFooterAdapter

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
        favViewModel = ViewModelProvider(
            this,
            injector.factoryFavContentVM
        )[FavViewModel::class.java]
    }

    fun loadData() {
        val cacheRepository = CacheRepository(requireContext())
        favoriteSongsAdapter = FavoriteSongsAdapter(this, this, cacheRepository)
        cacheRepository.getPodcastFavContent()
            ?.let {
                favoriteSongsAdapter.setData(
                    it.toMutableList(),
                    argHomePatchDetail ?: HomePatchDetailModel(),
                    playerViewModel.currentMusic?.mediaId
                )
            }!!
        val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val config = ConcatAdapter.Config.Builder().apply { setIsolateViewTypes(false) }.build()
        footerAdapter = HomeFooterAdapter()
        parentAdapter = ConcatAdapter(config, favoriteSongsAdapter)
        recyclerView.adapter = parentAdapter

        playerViewModel.currentMusicLiveData.observe(viewLifecycleOwner) { music ->
            if (music != null) {
                if (music.mediaId != null) {
                    favoriteSongsAdapter.setPlayingSong(music.mediaId!!)
                }
            }
        }
    }

    override fun onClickItem(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int) {
    }

    override fun onClickFavItem(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int) {
        if (playerViewModel.currentMusic != null && (mSongDetails[clickItemPosition].rootContentId == playerViewModel.currentMusic?.rootId)) {
            if ((mSongDetails[clickItemPosition].content_Id != playerViewModel.currentMusic?.mediaId)) {
                playerViewModel.skipToQueueItem(clickItemPosition)
            } else {
                playerViewModel.togglePlayPause()
            }
        } else {
            playItem(
                mSongDetails,
                clickItemPosition
            )
        }
    }

    override fun onFavAlbumClick(itemPosition: Int, mSongDetails: MutableList<IMusicModel>) {

    }

    override fun onClickBottomItemPodcast(mSongDetails: IMusicModel) {
      showBottomSheetDialogForPodcast(
            navController,
            context = requireContext(),
            SongTrackModel().apply {
                content_Type = mSongDetails.content_Type
                artistName = mSongDetails.artistName.toString()
                total_duration = mSongDetails.total_duration.toString()
                content_Id = mSongDetails.content_Id
                imageUrl = mSongDetails.imageUrl.toString()
                titleName = mSongDetails.titleName.toString()
                playingUrl = mSongDetails.playingUrl.toString()
                rootContentId = mSongDetails.rootContentId.toString()
            },
            argHomePatchItem,
            argHomePatchDetail
        )
    }
    fun showBottomSheetDialogForPodcast(
        bsdNavController: NavController,
        context: Context,
        iSongTrack: IMusicModel,
        argHomePatchItem: HomePatchItemModel?,
        argHomePatchDetail: HomePatchDetailModel?,
    ) {
        val cacheRepository = CacheRepository(requireContext())
        val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
        val contentView =
            View.inflate(
                context,
                R.layout.my_bl_sdk_bottomsheet_three_dot_menu_layout,
                null
            )
        bottomSheetDialog.setContentView(contentView)
        bottomSheetDialog.show()
        val closeButton: ImageView? = bottomSheetDialog.findViewById(R.id.closeButton)
        closeButton?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val image: ImageView? = bottomSheetDialog.findViewById(R.id.thumb)
        val url = iSongTrack.imageUrl
        val title: TextView? = bottomSheetDialog.findViewById(R.id.name)
        title?.text = iSongTrack.titleName
        val artistname = bottomSheetDialog.findViewById<TextView>(R.id.desc)
        artistname?.text = iSongTrack.artistName
        if (image != null) {
            Glide.with(context)
                .load(UtilHelper.getImageUrlSize300(url!!))
                .into(image)
        }

        val constraintAlbum: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintAlbum)
        constraintAlbum?.setOnClickListener {
//            gotoArtistFromPlaylist(
//                bsdNavController,
//                context,
//                mSongDetails,
//                argHomePatchItem,
//                argHomePatchDetail
//            )
            bottomSheetDialog.dismiss()
        }
        constraintAlbum?.visibility = View.GONE
        val downloadImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgDownload)
        val textViewDownloadTitle: TextView? =
            bottomSheetDialog.findViewById(R.id.tv_download)
        var isDownloadComplete = false
        val downloaded = cacheRepository.getDownloadById(iSongTrack.content_Id)
        if (downloaded?.playingUrl != null) {
            isDownloadComplete = true
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_ic_delete)
        } else {
            isDownloadComplete = false
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_icon_dowload)
        }

        if (isDownloadComplete) {
            textViewDownloadTitle?.text = "Remove From Download"
        } else {
            textViewDownloadTitle?.text = "Download Offline"
        }
        val constraintDownload: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintDownload)
        constraintDownload?.setOnClickListener {
            if (isDownloadComplete) {
//                cacheRepository.deleteDownloadById(track.EpisodeId)
                cacheRepository.deleteDownloadById(iSongTrack.content_Id)
                DownloadService.sendRemoveDownload(
                    requireContext(),
                    MyBLDownloadService::class.java,
                    iSongTrack.content_Id,
                    false
                )
                val localBroadcastManager =
                    LocalBroadcastManager.getInstance(requireContext())
                val localIntent = Intent("DELETED")
                    .putExtra("contentID", iSongTrack.content_Id)
//                    .putExtra("contentID", track.EpisodeId)
                localBroadcastManager.sendBroadcast(localIntent)

            } else {
                val mPlayingUrl = "${Constants.FILE_BASE_URL}${iSongTrack.playingUrl}"
                val downloadRequest: DownloadRequest =
                    DownloadRequest.Builder(iSongTrack.content_Id, mPlayingUrl.toUri())
                        .build()
                Log.e("TAG", "NAME: " + iSongTrack.titleName + " URL " + mPlayingUrl)
                injector.downloadTitleMap[iSongTrack.content_Id ?: ""] = iSongTrack.titleName ?: ""
                DownloadService.sendAddDownload(
                    requireContext(),
                    MyBLDownloadService::class.java,
                    downloadRequest,
                    /* foreground= */ false
                )

                //Todo iSongTrack.EpisodeId
                if (cacheRepository.isDownloadCompleted(iSongTrack.content_Id)) {
//                    val contentType = iSongTrack.content_Type
//                    val podcastType = contentType?.take(2)
//                    val  Type = contentType?.takeLast(2)
                    cacheRepository.insertDownload(
                        DownloadedContent().apply {

                            content_Id = iSongTrack.content_Id
                            album_Id = iSongTrack.album_Id
                            rootContentId = iSongTrack.rootContentId
                            imageUrl = iSongTrack.imageUrl
                            titleName = iSongTrack.titleName
                            content_Type = iSongTrack.content_Type
                            playingUrl = iSongTrack.playingUrl
                            rootContentType = iSongTrack.rootContentType
                            titleName = iSongTrack.titleName
                            artist_Id = iSongTrack.artist_Id
                            artistName =iSongTrack.artistName.toString()
                            total_duration = iSongTrack.total_duration
                        }
                    )
                }
            }
            bottomSheetDialog.dismiss()
        }

        val constraintFav: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintFav)
        val favImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgLike)
        val textFav: TextView? = bottomSheetDialog.findViewById(R.id.tvFav)
        var isFav = false
        //todo iSongTrack.Id
        val isAddedToFav = cacheRepository.getFavoriteById(iSongTrack.content_Id)
        if (isAddedToFav?.content_Id != null) {

            favImage?.setImageResource(R.drawable.my_bl_sdk_ic_icon_fav)
            isFav = true
            textFav?.text = "Remove From favorite"
        } else {

            favImage?.setImageResource(R.drawable.my_bl_sdk_ic_like)
            isFav = false
            textFav?.text = "Favorite"
        }

        constraintFav?.setOnClickListener {
            if (isFav.equals(true)) {
                val contentType = iSongTrack.content_Type
                val podcastType = contentType?.take(2)
                val Type = contentType?.takeLast(2)
                //todo iSongTrack.Id
                favViewModel.deleteFavContent(
                    iSongTrack.content_Id,
                    iSongTrack.content_Type.toString()
                )
                cacheRepository.deleteFavoriteById(iSongTrack.content_Id)
                Toast.makeText(
                    requireContext(),
                    "Removed from favorite",
                    Toast.LENGTH_LONG
                )
                    .show()
                favImage?.setImageResource(R.drawable.my_bl_sdk_ic_like)
                isFav = false
                favoriteSongsAdapter.updateData(cacheRepository.getPodcastFavContent()?.toMutableList())
                Log.e("TAG", "NAME: " + iSongTrack.content_Type)
            } else {
                val contentType = iSongTrack.content_Type
                val podcastType = contentType?.take(2)
                val Type = contentType?.takeLast(2)
                //todo iSongTrack.EpisodeId
                favViewModel.addFavContent(
                    iSongTrack.content_Id,
                    iSongTrack.content_Type.toString()
                )

                favImage?.setImageResource(R.drawable.my_bl_sdk_ic_icon_fav)

                // todo iSongTrack.Id.toString(),
                //      iSongTrack.Id.toString(),
                cacheRepository.insertFavSingleContent(
                    FavDataModel().apply {
                        content_Id = iSongTrack.content_Id
                        album_Id = iSongTrack.content_Id
                        albumImage = iSongTrack.imageUrl
                        clientValue = 2
                        content_Type = iSongTrack.content_Type.toString()
                        fav = "1"
                        imageUrl = iSongTrack.imageUrl
                        playingUrl = iSongTrack.playingUrl
                        rootContentId = iSongTrack.rootContentId
                        rootContentType = iSongTrack.rootContentType
                        titleName = iSongTrack.titleName
                        total_duration = iSongTrack.total_duration
                    }
                )
                isFav = true
                Toast.makeText(requireContext(), "Added to favorite", Toast.LENGTH_LONG)
                    .show()
            }
            bottomSheetDialog.dismiss()
        }
        val constraintPlaylist: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintAddtoPlaylist)
        constraintPlaylist?.visibility = View.GONE
    }
    override fun onClickBottomItemSongs(mSongDetails: IMusicModel) {
//        (activity as? SDKMainActivity)?.showBottomSheetDialog(
//            navController,
//            context = requireContext(),
//            SongDetail(mSongDetails.contentId,
//                mSongDetails.rootImg,
//                mSongDetails.rootTitle,
//                mSongDetails.rootType,
//                mSongDetails.track.toString(),
//                mSongDetails.artist,
//                mSongDetails.timeStamp,
//                "",
//                "",
//                "",
//                "","","","","","","",false),
//            argHomePatchItem,
//            argHomePatchDetail
//        )
    }

    override fun onClickBottomItemVideo(mSongDetails: IMusicModel) {

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

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(MyBroadcastReceiver())
    }

    private fun progressIndicatorUpdate(downloadingItems: List<DownloadingItem>) {

        downloadingItems.forEach {


            val progressIndicator: CircularProgressIndicator? =
                view?.findViewWithTag(it.contentId)
            val downloaded: ImageView? = view?.findViewWithTag(220)
            progressIndicator?.visibility = View.VISIBLE
            progressIndicator?.progress = it.progress.toInt()
//            val isDownloaded =
//                cacheRepository?.isTrackDownloaded(it.contentId) ?: false
//            if(!isDownloaded){
//                progressIndicator?.visibility = View.GONE
//                downloaded?.visibility = View.GONE
//            }
        }
    }

    inner class MyBroadcastReceiver : BroadcastReceiver() {
        @SuppressLint("NotifyDataSetChanged")
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                "ACTION" -> {
                    //val data = intent.getIntExtra("currentProgress",0)
                    val downloadingItems =
                        intent.getParcelableArrayListExtra<DownloadingItem>("downloading_items")
                    downloadingItems?.let {
                        progressIndicatorUpdate(it)
                    }
                }
                "DELETED" -> {
                    favoriteSongsAdapter.notifyDataSetChanged()
                }
                "PROGRESS" -> {
                    favoriteSongsAdapter.notifyDataSetChanged()
                }
                else -> Toast.makeText(context, "Action Not Found", Toast.LENGTH_LONG).show()
            }
        }
    }
}