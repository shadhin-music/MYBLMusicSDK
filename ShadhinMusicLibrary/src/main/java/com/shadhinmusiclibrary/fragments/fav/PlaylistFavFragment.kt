package com.shadhinmusiclibrary.fragments.fav

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
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
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.offline.DownloadRequest
import com.google.android.exoplayer2.offline.DownloadService
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.activities.ItemClickListener
import com.shadhinmusiclibrary.adapter.CreatePlaylistListAdapter
import com.shadhinmusiclibrary.adapter.FavoritePlaylistAdapter
import com.shadhinmusiclibrary.adapter.HomeFooterAdapter
import com.shadhinmusiclibrary.callBackService.DownloadedSongOnCallBack
import com.shadhinmusiclibrary.callBackService.favItemClickCallback
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.DownloadingItem
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.HomePatchItemModel
import com.shadhinmusiclibrary.data.model.SongDetailModel
import com.shadhinmusiclibrary.data.model.fav.FavData
import com.shadhinmusiclibrary.download.MyBLDownloadService
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.fragments.base.BaseFragment
import com.shadhinmusiclibrary.fragments.create_playlist.CreateplaylistViewModel
import com.shadhinmusiclibrary.library.player.Constants
import com.shadhinmusiclibrary.library.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.AppConstantUtils
import com.shadhinmusiclibrary.utils.UtilHelper
import java.io.Serializable

internal class PlaylistFavFragment : BaseFragment(),
    DownloadedSongOnCallBack,
    favItemClickCallback,
    onFavPlaylistClick,
    ItemClickListener {

    private lateinit var navController: NavController
    private lateinit var dataAdapter: FavoritePlaylistAdapter
    private lateinit var favViewModel: FavViewModel
    private lateinit var viewModel: CreateplaylistViewModel
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

        viewModel =
            ViewModelProvider(
                this,
                injector.factoryCreatePlaylistVM
            )[CreateplaylistViewModel::class.java]

        favViewModel =
            ViewModelProvider(
                this,
                injector.factoryFavContentVM
            )[FavViewModel::class.java]
    }

    fun loadData() {
        val cacheRepository = CacheRepository(requireContext())
        dataAdapter =
            cacheRepository.getPlaylistFavoriteContent()?.let {
                FavoritePlaylistAdapter(
                    it,
                    this,
                    this,
                    cacheRepository, this
                )
            }!!

        val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val config = ConcatAdapter.Config.Builder().apply { setIsolateViewTypes(false) }.build()
        footerAdapter = HomeFooterAdapter()
        parentAdapter = ConcatAdapter(config,dataAdapter,footerAdapter)
        recyclerView.adapter = parentAdapter
    }

    override fun onClickItem(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int) {
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

    override fun onClickFavItem(mSongDetails: MutableList<IMusicModel>, clickItemPosition: Int) {
        if (playerViewModel.currentMusic != null && (mSongDetails[clickItemPosition].rootContentId == playerViewModel.currentMusic?.rootId)) {
            if ((mSongDetails[clickItemPosition].content_Id != playerViewModel.currentMusic?.mediaId)) {
                playerViewModel.skipToQueueItem(clickItemPosition)
            } else {
                playerViewModel.togglePlayPause()
            }
        } else {
            //Todo song play no
            playItem(
                mSongDetails,
                clickItemPosition
            )
        }
    }

    override fun onClickBottomItemPodcast(mSongDetails: IMusicModel) {

    }

    override fun onClickBottomItemSongs(mSongDetails: IMusicModel) {
        showBottomSheetDialog(
            navController,
            context = requireContext(),
            SongDetailModel()
                .apply {
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
            HomePatchDetailModel().apply {
                artistName = mSongDetails.artistName.toString()
                content_Id = mSongDetails.content_Id.toString()
                content_Type = mSongDetails.content_Type.toString()
                playingUrl = mSongDetails.playingUrl.toString()
                imageUrl = mSongDetails.imageUrl.toString()
                titleName = mSongDetails.titleName.toString()
            }
        )
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
                    dataAdapter.notifyDataSetChanged()
                    Log.e("DELETED", "broadcast fired")
                }
                "PROGRESS" -> {

                    dataAdapter.notifyDataSetChanged()
                    Log.e("PROGRESS", "broadcast fired")
                }
                else -> Toast.makeText(context, "Action Not Found", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onFavPlaylistClick(itemPosition: Int, favData: List<FavData>) {
        val mfavData = favData[itemPosition]
        navController.navigate(
            R.id.favoritePlaylist,
            Bundle().apply {
                putSerializable(
                    AppConstantUtils.PatchItem,
                    HomePatchItemModel("", "P", mutableListOf(), "Playlist", "", 0, 0)
                )
                putSerializable(
                    AppConstantUtils.PatchDetail,
                    HomePatchDetailModel().apply {
                        album_Id = mfavData.album_Id.toString()
                        artistName = mfavData.artistName.toString()
                        artist_Id = mfavData.artist_Id.toString()
                        content_Id = mfavData.content_Id
                        content_Type = mfavData.content_Type.toString()
                        playingUrl = mfavData.playingUrl.toString()
                        imageUrl = mfavData.imageUrl.toString()
                        titleName = mfavData.titleName.toString()
                    } as Serializable
                )
            })
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

        val title: TextView? = bottomSheetDialog.findViewById(R.id.name)
        title?.text = argHomePatchDetail?.titleName
        val tvArtistName = bottomSheetDialog.findViewById<TextView>(R.id.desc)
        tvArtistName?.text = mSongDetails.artistName
        if (image != null) {
            Glide.with(context)
                .load(UtilHelper.getImageUrlSize300(argHomePatchDetail?.imageUrl!!))
                .into(image)
        }
        val downloadImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgDownload)
        val textViewDownloadTitle: TextView? = bottomSheetDialog.findViewById(R.id.tv_download)
        var isDownloaded = false
        var downloaded = cacheRepository.getDownloadById(mSongDetails.content_Id ?: "")
        if (downloaded?.playingUrl != null) {
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
        constraintDownload?.visibility = GONE
        constraintDownload?.setOnClickListener {
            if (isDownloaded.equals(true)) {
                cacheRepository.deleteDownloadById(mSongDetails.content_Id ?: "")
                DownloadService.sendRemoveDownload(
                    requireContext(),
                    MyBLDownloadService::class.java,
                    mSongDetails.content_Id ?: "",
                    false
                )
                Log.e("TAG", "DELETED: " + isDownloaded)
                val localBroadcastManager = LocalBroadcastManager.getInstance(requireContext())
                val localIntent = Intent("DELETED")
                    .putExtra("contentID", mSongDetails.content_Id ?: "")
                localBroadcastManager.sendBroadcast(localIntent)
                isDownloaded = false
            } else {
                val url = "${Constants.FILE_BASE_URL}${mSongDetails.playingUrl ?: ""}"
                var downloadRequest: DownloadRequest =
                    DownloadRequest.Builder(mSongDetails.content_Id ?: "", url.toUri())
                        .build()
                DownloadService.sendAddDownload(
                    requireContext(),
                    MyBLDownloadService::class.java,
                    downloadRequest,
                    /* foreground= */ false
                )
                if (cacheRepository.isDownloadCompleted(mSongDetails.content_Id ?: "")
                        .equals(true)
                ) {
//                if (cacheRepository.isDownloadCompleted(mSongDetails.ContentID).equals(true)) {
                    cacheRepository.insertDownload(
                        DownloadedContent().apply {
                            content_Id = mSongDetails.content_Id.toString()
                            rootContentId = mSongDetails.rootContentId
                            imageUrl = mSongDetails.imageUrl
                            titleName = mSongDetails.titleName
                            content_Type = mSongDetails.content_Type
                            playingUrl = mSongDetails.playingUrl
                            rootContentType = mSongDetails.content_Type
                            artistName = mSongDetails.artistName
                            artist_Id = mSongDetails.artist_Id
                            total_duration = mSongDetails.total_duration
                        }
                    )
                    isDownloaded = true
                }
            }
            bottomSheetDialog.dismiss()
        }
        val constraintAlbum: ConstraintLayout? =
            bottomSheetDialog.findViewById(R.id.constraintAlbum)
        constraintAlbum?.visibility = GONE
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
        constraintPlaylist?.visibility = GONE
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
        val isAddedToFav = cacheRepository.getFavoriteById(mSongDetails.content_Id ?: "")
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
                    mSongDetails.content_Id ?: "",
                    mSongDetails.content_Type ?: ""
                )
                cacheRepository.deleteFavoriteById(mSongDetails.content_Id ?: "")
                Toast.makeText(requireContext(), "Removed from favorite", Toast.LENGTH_LONG).show()
                favImage?.setImageResource(R.drawable.my_bl_sdk_ic_like)
                isFav = false
            } else {
                favViewModel.addFavContent(
                    mSongDetails.content_Id ?: "",
                    mSongDetails.content_Type ?: ""
                )
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
                        imageUrl = mSongDetails.imageUrl
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
        argHomePatchDetail: HomePatchDetailModel?,

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
            R.id.favoriteArtist,
            Bundle().apply {
                putSerializable(
                    AppConstantUtils.PatchItem,
                    HomePatchItemModel("", "A", mutableListOf(), "Artist", "", 0, 0)
                )
                putSerializable(
                    AppConstantUtils.PatchDetail,
                    HomePatchDetailModel().apply {
                        album_Id = mSongDetails.album_Id.toString()
                        artistName = mSongDetails.artistName ?: ""
                        artist_Id = mSongDetails.artist_Id.toString()
                        content_Id = mSongDetails.content_Id ?: ""
                        content_Type = mSongDetails.content_Type ?: ""
                        playingUrl = mSongDetails.playingUrl.toString()
                        imageUrl = mSongDetails.imageUrl.toString()
                        titleName = mSongDetails.titleName.toString()
                    } as Serializable
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

    private fun addSongsToPlaylist(mSongDetails: IMusicModel, id: String?) {
        id?.let { viewModel.songsAddedToPlaylist(it, mSongDetails.content_Id ?: "") }
        viewModel.songsAddedToPlaylist.observe(this) { res ->
            Toast.makeText(requireContext(), res.status.toString(), Toast.LENGTH_LONG).show()
        }
    }
}

internal interface onFavPlaylistClick {
    fun onFavPlaylistClick(itemPosition: Int, favData: List<FavData>)
}