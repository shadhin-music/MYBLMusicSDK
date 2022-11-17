package com.shadhinmusiclibrary.fragments.fav

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.offline.DownloadRequest
import com.google.android.exoplayer2.offline.DownloadService
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.activities.ItemClickListener
import com.shadhinmusiclibrary.adapter.AllFavoriteAdapter
import com.shadhinmusiclibrary.adapter.CreatePlaylistListAdapter
import com.shadhinmusiclibrary.callBackService.DownloadedSongOnCallBack
import com.shadhinmusiclibrary.callBackService.favItemClickCallback
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.HomePatchItemModel
import com.shadhinmusiclibrary.data.model.SongDetailModel
import com.shadhinmusiclibrary.data.model.VideoModel
import com.shadhinmusiclibrary.data.model.fav.FavData
import com.shadhinmusiclibrary.download.MyBLDownloadService
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.download.room.WatchLaterContent
import com.shadhinmusiclibrary.fragments.base.BaseFragment
import com.shadhinmusiclibrary.fragments.create_playlist.CreateplaylistViewModel
import com.shadhinmusiclibrary.library.player.Constants
import com.shadhinmusiclibrary.library.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.AppConstantUtils
import com.shadhinmusiclibrary.utils.UtilHelper
import java.io.Serializable


internal class AllFavoriteDetailsFragment : BaseFragment(), DownloadedSongOnCallBack,
    favItemClickCallback, ItemClickListener {
    private var isDownloaded: Boolean = false
    private var iswatched: Boolean = false
    private lateinit var favViewModel: FavViewModel
    private lateinit var viewModel: CreateplaylistViewModel
    private lateinit var navController: NavController

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
        viewModel =
            ViewModelProvider(
                this,
                injector.factoryCreatePlaylistVM
            )[CreateplaylistViewModel::class.java]

    }

    fun loadData() {
        favViewModel =
            ViewModelProvider(this, injector.factoryFavContentVM)[FavViewModel::class.java]
        val cacheRepository = CacheRepository(requireContext())
        val dataAdapter = AllFavoriteAdapter(cacheRepository.getAllFavoriteContent()!!, this, this)
        Log.e("TAG", "Track123: " + cacheRepository.getAllFavoriteContent())
        val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = dataAdapter
        // Log.e("TAG","VIDEOS: "+ cacheRepository.getAllVideosDownloads())

    }

    override fun onClickItem(mSongDetails: MutableList<DownloadedContent>, clickItemPosition: Int) {

    }

    override fun onClickFavItem(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int) {
        if (playerViewModel.currentMusic != null && (mSongDetails[clickItemPosition].rootContentId == playerViewModel.currentMusic?.rootId)) {
            if ((mSongDetails[clickItemPosition].content_Id != playerViewModel.currentMusic?.mediaId)) {
                playerViewModel.skipToQueueItem(clickItemPosition)
            } else {
                playerViewModel.togglePlayPause()
            }
        } else {
            //Todo song play and need test
            playItem(
                mSongDetails,
                clickItemPosition
            )
        }
    }

    override fun onClickBottomItemPodcast(mSongDetails: IMusicModel) {
//        (activity as? SDKMainActivity)?.showBottomSheetDialogForPodcast(
//            navController,
//            context = requireContext(),
//            Track("",
//                track.rootType,
//                track.artist,
//                track.timeStamp,
//                track.contentId,
//                0,
//                track.rootImg,
//                false,
//                track.rootTitle,
//                track.track.toString(),
//                false,
//                "",
//                0,
//                "",
//                "",
//                "",
//                0,
//
//                track.rootId,
//                track.rootImg,
//                "",
//                false),
//            argHomePatchItem,
//            argHomePatchDetail
//        )
    }

    override fun onClickBottomItemSongs(mSongDetails: IMusicModel) {
        showBottomSheetDialog(
            navController,
            context = requireContext(),
            SongDetailModel().apply {
                content_Id = mSongDetails.content_Id
                imageUrl = mSongDetails.imageUrl.toString()
                titleName = mSongDetails.titleName.toString()
                content_Type = mSongDetails.content_Type.toString()
                playingUrl = mSongDetails.playingUrl.toString()
                artistName = mSongDetails.artistName.toString()
                total_duration = mSongDetails.total_duration.toString()
                artist_Id = mSongDetails.artist_Id
                album_Id = mSongDetails.album_Id
            },
            argHomePatchItem,
            HomePatchDetailModel(
                mSongDetails.album_Id.toString(),
                "",
                mSongDetails.album_Name.toString(),
                mSongDetails.artistName.toString(),
                mSongDetails.artist_Id.toString(),
                "",
                "",
                mSongDetails.content_Id.toString(),
                mSongDetails.content_Type.toString(),
                "",
                "",
                "",
                false,
                "",
                0,
                "",
                "",
                "",
                mSongDetails.playingUrl.toString(),
                "",
                "",
                false,
                "",
                "",
                "",
                "",
                mSongDetails.imageUrl.toString(),
                "",
                mSongDetails.titleName.toString()
            )
        )
    }

    override fun onClickBottomItemVideo(mSongDetails: IMusicModel) {
        openDialog(
            VideoModel(
                mSongDetails.album_Id,
                "",
                mSongDetails.titleName,
                mSongDetails.artistName,
                "",
                "",
                "",
                2,
                mSongDetails.content_Id,
                mSongDetails.rootContentType,
                "",
                mSongDetails.total_duration,
                "",
                "",
                mSongDetails.imageUrl,
                "",
                false,
                "",
                0,
                "",
                "",
                "",
                mSongDetails.playingUrl,
                "",
                "",
                false,
                "",
                mSongDetails.titleName,
                "",
                ""
            )
        )
    }

    fun openDialog(item: VideoModel) {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val cacheRepository = CacheRepository(requireContext())
        val contentView =
            View.inflate(
                requireContext(),
                R.layout.my_bl_sdk_video_bottomsheet_three_dot_menu,
                null
            )
        bottomSheetDialog.setContentView(contentView)
        bottomSheetDialog.show()
        val closeButton: ImageView? = bottomSheetDialog.findViewById(R.id.closeButton)
        closeButton?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val artistname = bottomSheetDialog.findViewById<TextView>(R.id.desc)
        artistname?.text = item.artist
        val image: ImageView? = bottomSheetDialog.findViewById(R.id.thumb)
        val url = item.image
        val title: TextView? = bottomSheetDialog.findViewById(R.id.name)
        title?.text = item.title
        if (image != null) {
            Glide.with(this).load(url?.replace("<\$size\$>", "300")).into(image)
        }

        val downloadImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgDownload)
        val textViewDownloadTitle: TextView? = bottomSheetDialog.findViewById(R.id.tv_download)

        var downloaded = cacheRepository.getDownloadById(item.contentID.toString())
        if (downloaded?.track != null) {
            isDownloaded = true
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_ic_delete)
        } else {
            isDownloaded = false
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_icon_dowload)
        }

        if (isDownloaded) {
            textViewDownloadTitle?.text = "Remove From Download"
        } else {
            textViewDownloadTitle?.text = "Download Offline"
        }
        val constraintDownload: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintDownload)
        constraintDownload?.setOnClickListener {
            if (isDownloaded.equals(true)) {
                cacheRepository.deleteDownloadById(item.contentID.toString())
                DownloadService.sendRemoveDownload(
                    requireContext(),
                    MyBLDownloadService::class.java, item.contentID.toString(), false
                )

                val localBroadcastManager = LocalBroadcastManager.getInstance(requireContext())
                val localIntent = Intent("DELETED")
                    .putExtra("contentID", item.contentID.toString())
                localBroadcastManager.sendBroadcast(localIntent)
                isDownloaded = false

            } else {
                val url = "${Constants.FILE_BASE_URL}${item.playUrl}"
                val downloadRequest: DownloadRequest =
                    DownloadRequest.Builder(item.contentID.toString(), url.toUri())
                        .build()
                DownloadService.sendAddDownload(
                    requireContext(),
                    MyBLDownloadService::class.java,
                    downloadRequest,
                    /* foreground= */ false
                )
                Log.e("TAG", "DELETED: " + item.playUrl)
                if (cacheRepository.isDownloadCompleted(item.contentID.toString()).equals(true)) {
                    cacheRepository.insertDownload(
                        DownloadedContent(
                            item.contentID.toString(),
                            item.rootId.toString(),
                            item.image.toString(),
                            item.title.toString(),
                            item.contentType.toString(),
                            item.playUrl,
                            item.contentType.toString(),
                            0,
                            0,
                            item.artist.toString(), item.artistId.toString(),
                            item.duration.toString()
                        )
                    )
                    isDownloaded = true
                }
            }
            bottomSheetDialog.dismiss()
        }
        val watchlaterImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgWatchlater)
        val textViewWatchlaterTitle: TextView? = bottomSheetDialog.findViewById(R.id.txtwatchLater)

        var watched = cacheRepository.getWatchedVideoById(item.contentID.toString())

        if (watched?.track != null) {
            iswatched = true
            watchlaterImage?.setImageResource(R.drawable.my_bl_sdk_watch_later_remove)
//            watchIcon.setColorFilter(applicationContext.getResources().getColor(R.color.my_sdk_color_primary))

        } else {
            iswatched = false
            watchlaterImage?.setImageResource(R.drawable.my_bl_sdk_ic_watch_later)
//            watchIcon.setColorFilter(applicationContext.getResources().getColor(R.color.my_sdk_down_item_desc))
        }

        if (iswatched) {
            textViewWatchlaterTitle?.text = "Remove From Watchlater"
        } else {
            textViewWatchlaterTitle?.text = "Watch Later"
        }
        val constraintWatchlater: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintAddtoWatch)
        constraintWatchlater?.setOnClickListener {
            if (iswatched) {
                cacheRepository.deleteWatchlaterById(item.contentID.toString())
                iswatched = false
            } else {
                val url = "${Constants.FILE_BASE_URL}${item.playUrl}"
                cacheRepository.insertWatchLater(
                    WatchLaterContent(
                        item.contentID.toString(),
                        item.rootId.toString(),
                        item.image.toString(),
                        item.title.toString(),
                        item.contentType.toString(),
                        url,
                        item.contentType.toString(),
                        0,
                        0,
                        item.artist.toString(),
                        item.duration.toString()
                    )
                )
                iswatched = true
                Log.e(
                    "TAGGG",
                    "INSERTED: " + cacheRepository.getAllWatchlater()
                )

            }
            bottomSheetDialog.dismiss()
        }
        val constraintFav: ConstraintLayout? = bottomSheetDialog.findViewById(R.id.constraintFav)
        val favImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgLike)
        val textFav: TextView? = bottomSheetDialog.findViewById(R.id.tvFav)
        var isFav = false
        val isAddedToFav = cacheRepository.getFavoriteById(item.contentID.toString())
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
                favViewModel.deleteFavContent(item.contentID.toString(), "V")
                cacheRepository.deleteFavoriteById(item.contentID.toString())
                Toast.makeText(context, "Removed from favorite", Toast.LENGTH_LONG).show()
                favImage?.setImageResource(R.drawable.my_bl_sdk_ic_like)
                isFav = false
                Log.e("TAG", "NAME: " + isFav)
            } else {

                favViewModel.addFavContent(item.contentID.toString(), "V")

                favImage?.setImageResource(R.drawable.my_bl_sdk_ic_icon_fav)
                Log.e("TAG", "NAME123: " + isFav)
                cacheRepository.insertFavSingleContent(
                    FavData().apply {
                        content_Id = item.contentID.toString()
                        album_Id = item.albumId
                        imageUrl = item.image
                        artistName = item.artist
                        artist_Id = item.artistId
                        clientValue = 2
                        content_Type = "V"
                        fav = "1"
                        playingUrl = item.playUrl
                        rootContentId = item.rootId
                        titleName = item.title
                    }
                )
                isFav = true
                Toast.makeText(context, "Added to favorite", Toast.LENGTH_LONG).show()
            }
            bottomSheetDialog.dismiss()
        }
    }

    fun showBottomSheetDialog(
        bsdNavController: NavController,
        context: Context,
        mSongDetails: SongDetailModel,
        argHomePatchItem: HomePatchItemModel?,
        argHomePatchDetail: HomePatchDetailModel?,
    ) {

        val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
        val cacheRepository = CacheRepository(requireContext())
        val contentView =
            View.inflate(context, R.layout.my_bl_sdk_bottomsheet_three_dot_menu_layout, null)
        bottomSheetDialog.setContentView(contentView)
        bottomSheetDialog.show()
        val closeButton: ImageView? = bottomSheetDialog.findViewById(R.id.closeButton)
        closeButton?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val imageArtist: ImageView? = bottomSheetDialog.findViewById(R.id.imgAlbum)
        val textAlbum: TextView? = bottomSheetDialog.findViewById(R.id.tvAlbums)
        textAlbum?.text = "Go to Artist"
        val image: ImageView? = bottomSheetDialog.findViewById(R.id.thumb)
        val url = argHomePatchDetail?.image
        val title: TextView? = bottomSheetDialog.findViewById(R.id.name)
        title?.text = argHomePatchDetail?.title
        val artistname = bottomSheetDialog.findViewById<TextView>(R.id.desc)
        artistname?.text = mSongDetails.artistName
        if (image != null) {
            Glide.with(context)?.load(url?.replace("<\$size\$>", "300"))?.into(image)
        }
        val downloadImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgDownload)
        val textViewDownloadTitle: TextView? = bottomSheetDialog.findViewById(R.id.tv_download)
        var isDownloaded = false
        var downloaded = cacheRepository.getDownloadById(mSongDetails.content_Id!!)
        if (downloaded?.track != null) {
            isDownloaded = true
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_ic_delete)
        } else {
            isDownloaded = false
            downloadImage?.setImageResource(R.drawable.my_bl_sdk_icon_dowload)
        }

        if (isDownloaded) {
            textViewDownloadTitle?.text = "Remove From Download"
        } else {
            textViewDownloadTitle?.text = "Download Offline"
        }
        val constraintDownload: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintDownload)
        constraintDownload?.setOnClickListener {
            if (isDownloaded.equals(true)) {
                cacheRepository.deleteDownloadById(mSongDetails.content_Id!!)
                DownloadService.sendRemoveDownload(
                    requireContext(),
                    MyBLDownloadService::class.java,
                    mSongDetails.content_Id!!,
                    false
                )
                Log.e("TAG", "DELETED: " + isDownloaded)
                val localBroadcastManager = LocalBroadcastManager.getInstance(requireContext())
                val localIntent = Intent("DELETED")
                    .putExtra("contentID", mSongDetails.content_Id)
                localBroadcastManager.sendBroadcast(localIntent)
                isDownloaded = false
            } else {
                val url = "${Constants.FILE_BASE_URL}${mSongDetails.playingUrl}"
                var downloadRequest: DownloadRequest =
                    DownloadRequest.Builder(mSongDetails.content_Id!!, url.toUri())
                        .build()
                DownloadService.sendAddDownload(
                    requireContext(),
                    MyBLDownloadService::class.java,
                    downloadRequest,
                    /* foreground= */ false
                )
                if (cacheRepository.isDownloadCompleted(mSongDetails.content_Id!!).equals(true)) {
//                if (cacheRepository.isDownloadCompleted(mSongDetails.ContentID).equals(true)) {
                    cacheRepository.insertDownload(
                        DownloadedContent(
                            mSongDetails.content_Id.toString(),
                            mSongDetails.rootContentId!!,
                            mSongDetails.imageUrl!!,
                            mSongDetails.titleName!!,
                            mSongDetails.content_Type!!,
                            mSongDetails.playingUrl!!,
                            mSongDetails.content_Type!!,
                            0,
                            0,
                            mSongDetails.artistName!!,
                            mSongDetails.artist_Id.toString(),
                            mSongDetails.total_duration!!
                        )
                    )
                    isDownloaded = true
                }
            }
            bottomSheetDialog.dismiss()
        }
        val constraintAlbum: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintAlbum)
        constraintAlbum?.setOnClickListener {
            gotoArtist(
                bsdNavController,
                context,
                mSongDetails,
                argHomePatchItem,
                argHomePatchDetail
            )
            bottomSheetDialog.dismiss()
        }
        val constraintPlaylist: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintAddtoPlaylist)
        constraintPlaylist?.setOnClickListener {
            gotoPlayList(context, mSongDetails)

            bottomSheetDialog.dismiss()
        }

        val constraintFav: ConstraintLayout? = bottomSheetDialog.findViewById(R.id.constraintFav)
        val favImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgLike)
        val textFav: TextView? = bottomSheetDialog.findViewById(R.id.tvFav)
        var isFav = false
//        favViewModel.getFavContentSong("S")
//        favViewModel.getFavContentSong.observe(this){res->
//            res.data?.forEach{
//                if(it.contentID.equals(mSongDetails.ContentID)) {
//
//                    isFav = true
//                    favImage?.setImageResource(R.drawable.my_bl_sdk_ic_icon_fav)
//                }
//                else {
//                    isFav = false
//                 favImage?.setImageResource(R.drawable.my_bl_sdk_ic_like)
//                }
//
//            }
//        }
        val isAddedToFav = cacheRepository.getFavoriteById(mSongDetails.content_Id!!)
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
                favViewModel.deleteFavContent(
                    mSongDetails.content_Id!!,
                    mSongDetails.content_Type!!
                )
                cacheRepository.deleteFavoriteById(mSongDetails.content_Id!!)
                Toast.makeText(requireContext(), "Removed from favorite", Toast.LENGTH_LONG).show()
                favImage?.setImageResource(R.drawable.my_bl_sdk_ic_like)
                isFav = false
                Log.e("TAG", "NAME: " + isFav)
            } else {

                favViewModel.addFavContent(mSongDetails.content_Id!!, mSongDetails.content_Type!!)

                favImage?.setImageResource(R.drawable.my_bl_sdk_ic_icon_fav)
                cacheRepository.insertFavSingleContent(
                    FavData().apply {
                        content_Id = mSongDetails.content_Id
                        album_Id = mSongDetails.album_Id
                        imageUrl = mSongDetails.imageUrl
                        artistName = mSongDetails.artistName
                        artist_Id = mSongDetails.artist_Id
                        clientValue = 2
                        content_Type = mSongDetails.content_Type
                        fav = "1"
                        content_Id = mSongDetails.imageUrl
                        playingUrl = mSongDetails.playingUrl
                        rootContentId = mSongDetails.rootContentId
                        rootContentType = mSongDetails.rootContentType
                        titleName = mSongDetails.titleName
                    }
                )
                isFav = true
                Toast.makeText(requireContext(), "Added to favorite", Toast.LENGTH_LONG).show()
            }
            bottomSheetDialog.dismiss()
        }
    }

    private fun gotoArtist(
        bsdNavController: NavController,
        context: Context,
        mSongDetails: SongDetailModel,
        argHomePatchItem: HomePatchItemModel?,
        argHomePatchDetail: HomePatchDetailModel?
    ) {
        //  Log.e("Check", ""+bsdNavController.graph.displayName)
//        bsdNavController.navigate(R.id.action_download_to_to_artistDetailsFragment,
//            Bundle().apply {
//                putSerializable(
//                    AppConstantUtils.PatchItem,
//                    HomePatchItem("","A", mutableListOf(),"Artist","",0,0)
//                )
//                putSerializable(
//                    AppConstantUtils.PatchDetail,
//                    HomePatchDetail(mSongDetails.albumId.toString(),"","",mSongDetails.artist,mSongDetails.ArtistId.toString(),"","",
//                        mSongDetails.ContentID,mSongDetails.ContentType,"","","",false,"",
//                        0,"","","",mSongDetails.PlayUrl.toString(),"","",
//                        false,"","","","",mSongDetails.image.toString(),"",mSongDetails.title.toString()) as Serializable
//                )
//            })

        bsdNavController.navigate(
            R.id.action_favorite_to_artist_details_fragment,
            Bundle().apply {
                putSerializable(
                    AppConstantUtils.PatchItem,
                    HomePatchItemModel("", "A", mutableListOf(), "Artist", "", 0, 0)
                )
                putSerializable(
                    AppConstantUtils.PatchDetail,
                    HomePatchDetailModel(
                        mSongDetails.album_Id.toString(),
                        "",
                        "",
                        mSongDetails.artistName!!,
                        mSongDetails.artist_Id.toString(),
                        "",
                        "",
                        mSongDetails.content_Id!!,
                        mSongDetails.content_Type!!,
                        "",
                        "",
                        "",
                        false,
                        "",
                        0,
                        "",
                        "",
                        "",
                        mSongDetails.playingUrl.toString(),
                        "",
                        "",
                        false,
                        "",
                        "",
                        "",
                        "",
                        mSongDetails.imageUrl.toString(),
                        "",
                        mSongDetails.titleName.toString()
                    ) as Serializable
                )
            })
    }

    private fun gotoPlayList(context: Context, mSongDetails: SongDetailModel) {
        val bottomSheetDialogPlaylist = BottomSheetDialog(context, R.style.BottomSheetDialog)
        val contentView =
            View.inflate(context, R.layout.my_bl_sdk_bottomsheet_create_playlist_with_list, null)
        bottomSheetDialogPlaylist.setContentView(contentView)
        bottomSheetDialogPlaylist.show()
        val closeButton: ImageView? = bottomSheetDialogPlaylist.findViewById(R.id.closeButton)
        closeButton?.setOnClickListener {
            bottomSheetDialogPlaylist.dismiss()
        }
        val recyclerView: RecyclerView? = bottomSheetDialogPlaylist.findViewById(R.id.recyclerView)
        viewModel.getuserPlaylist()
        viewModel.getUserPlaylist.observe(this) { res ->
            recyclerView?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerView?.adapter = res.data?.let {
                CreatePlaylistListAdapter(it, this, mSongDetails)
            }

        }
        val btnCreateplaylist: AppCompatButton? =
            bottomSheetDialogPlaylist.findViewById(R.id.btnCreatePlaylist)
        btnCreateplaylist?.setOnClickListener {
            openCreatePlaylist(context)
            bottomSheetDialogPlaylist.dismiss()
        }
        viewModel.createPlaylist.observe(this) { res ->

            Toast.makeText(context, res.status.toString(), Toast.LENGTH_LONG).show()
        }
    }

    fun openCreatePlaylist(context: Context) {
        val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)

        val contentView =
            View.inflate(context, R.layout.my_bl_sdk_bottomsheet_create_new_playlist, null)
        bottomSheetDialog.setContentView(contentView)
        bottomSheetDialog.show()
        val closeButton: ImageView? = bottomSheetDialog.findViewById(R.id.closeButton)
        closeButton?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val etCreatePlaylist: EditText? = bottomSheetDialog.findViewById(R.id.etCreatePlaylist)
        var savePlaylist: AppCompatButton? = bottomSheetDialog.findViewById(R.id.btnSavePlaylist)
        etCreatePlaylist?.setOnFocusChangeListener { view, focused ->
            val keyboard: InputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (focused) keyboard.showSoftInput(
                etCreatePlaylist,
                0
            ) else keyboard.hideSoftInputFromWindow(
                etCreatePlaylist.getWindowToken(),
                0
            )
        }
        etCreatePlaylist?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val name: String = etCreatePlaylist.getText().toString()
                Log.e("TAG", "NAME: " + name)
                savePlaylist?.setBackgroundResource(R.drawable.my_bl_sdk_rounded_button_red)
                savePlaylist?.isEnabled = true
                savePlaylist?.setOnClickListener {
                    viewModel.createPlaylist(name)
                    // requireActivity().onBackPressed()
                    bottomSheetDialog.dismiss()
                }
                if (etCreatePlaylist.text.isNullOrEmpty()) {
                    savePlaylist?.setBackgroundResource(R.drawable.my_bl_sdk_rounded_button_gray)
                    savePlaylist?.isEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        etCreatePlaylist?.requestFocus()

    }

    override fun onClick(position: Int, mSongDetails: IMusicModel, id: String?) {
        addSongsToPlaylist(mSongDetails, id)
    }

    fun addSongsToPlaylist(mSongDetails: IMusicModel, id: String?) {
        id?.let { viewModel.songsAddedToPlaylist(it, mSongDetails.content_Id!!) }
        viewModel.songsAddedToPlaylist.observe(this) { res ->
            Toast.makeText(requireContext(), res.status.toString(), Toast.LENGTH_LONG).show()
        }
    }
}