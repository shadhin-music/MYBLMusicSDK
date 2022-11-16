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
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.adapter.AllFavoriteAdapter
import com.shadhinmusiclibrary.adapter.CreatePlaylistListAdapter
import com.shadhinmusiclibrary.callBackService.DownloadBottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.DownloadedSongOnCallBack
import com.shadhinmusiclibrary.callBackService.favItemClickCallback
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.model.Video
import com.shadhinmusiclibrary.data.model.fav.FavData
import com.shadhinmusiclibrary.data.model.podcast.Track
import com.shadhinmusiclibrary.download.MyBLDownloadService
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.download.room.WatchLaterContent
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.fragments.create_playlist.CreateplaylistViewModel
import com.shadhinmusiclibrary.player.Constants
import com.shadhinmusiclibrary.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.AppConstantUtils
import com.shadhinmusiclibrary.utils.UtilHelper
import java.io.Serializable


internal class AllFavoriteDetailsFragment : CommonBaseFragment(),DownloadedSongOnCallBack ,
    favItemClickCallback, ItemClickListener {
    private var isDownloaded:Boolean = false
    private var iswatched: Boolean = false
    private lateinit var favViewModel: FavViewModel
    private lateinit var viewModel: CreateplaylistViewModel
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

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
            ViewModelProvider(this, injector.factoryCreatePlaylistVM)[CreateplaylistViewModel::class.java]

    }
      fun loadData(){
          favViewModel = ViewModelProvider(this,injector.factoryFavContentVM)[FavViewModel::class.java]
          val cacheRepository= CacheRepository(requireContext())
          val dataAdapter = AllFavoriteAdapter(cacheRepository.getAllFavoriteContent()!!,this,this)
         Log.e("TAG", "Track123: " + cacheRepository.getAllFavoriteContent())
          val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
          recyclerView.layoutManager =
              LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false )
          recyclerView.adapter = dataAdapter
         // Log.e("TAG","VIDEOS: "+ cacheRepository.getAllVideosDownloads())

      }

    companion object {

        @JvmStatic
        fun newInstance() =
            AllFavoriteDetailsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onClickItem(mSongDetails: MutableList<DownloadedContent>, clickItemPosition: Int) {


    }

    override fun onClickFavItem(mSongDetails: MutableList<FavData>, clickItemPosition: Int) {
        if (playerViewModel.currentMusic != null && (mSongDetails[clickItemPosition].rootId == playerViewModel.currentMusic?.rootId)) {
            if ((mSongDetails[clickItemPosition].contentID != playerViewModel.currentMusic?.mediaId)) {
                Log.e("TAG","SONG :"+ mSongDetails[clickItemPosition].contentID)
                Log.e("TAG","SONG :"+ playerViewModel.currentMusic?.mediaId )
                playerViewModel.skipToQueueItem(clickItemPosition)
            } else {
                playerViewModel.togglePlayPause()
            }
        } else {
            playItem(
                UtilHelper.getSongDetailToFavoriteSongDetailList(mSongDetails),
                clickItemPosition
            )
            Log.e("TAG","SONG :"+ mSongDetails.toString() )
        }
    }

    override fun onClickBottomItemPodcast(track: FavData) {
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

    override fun onClickBottomItemSongs(mSongDetails: FavData) {
   showBottomSheetDialog(
            navController,
            context = requireContext(),
            SongDetail(mSongDetails.contentID,
                mSongDetails.image.toString(),
                mSongDetails.title.toString(),
                mSongDetails.contentType.toString(),
                mSongDetails.playUrl.toString(),
                mSongDetails.artist.toString(),
                mSongDetails.duration.toString(),
                "",
                "",
                "",
                "",mSongDetails.artistId,mSongDetails.albumId,"","","","",false),
            argHomePatchItem,
            HomePatchDetail( mSongDetails.albumId.toString(),"",mSongDetails.albumName.toString(),mSongDetails.artist.toString(),mSongDetails.artistId.toString(),"","",
                mSongDetails.contentID.toString(),mSongDetails.contentType.toString(),"","","",false,"",
                0,"","","",mSongDetails.playUrl.toString(),"","",
                false,"","","","",mSongDetails.image.toString(),"",mSongDetails.title.toString())
        )

    }

    override fun onClickBottomItemVideo(mSongDetails: FavData) {
       openDialog(Video(mSongDetails.albumId,
              "",mSongDetails.title,mSongDetails.artist,"","","",2,
                  mSongDetails.contentID,mSongDetails.rootType,"",mSongDetails.duration,"","",
                 mSongDetails.image,"",false,"",0,"","",""
               ,mSongDetails.playUrl,"","",false,"",mSongDetails.title,"",""))
        Log.e("TAG","CLICKED: "+ mSongDetails.rootType)
    }

 fun openDialog(item: Video) {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
     val cacheRepository= CacheRepository(requireContext())
        val contentView =
            View.inflate(requireContext(), R.layout.my_bl_sdk_video_bottomsheet_three_dot_menu, null)
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
                DownloadService.sendRemoveDownload(requireContext(),
                    MyBLDownloadService::class.java, item.contentID.toString(), false)

                val localBroadcastManager = LocalBroadcastManager.getInstance(requireContext())
                val localIntent = Intent("DELETED")
                    .putExtra("contentID", item.contentID.toString())
                localBroadcastManager.sendBroadcast(localIntent)
                isDownloaded =false

            } else {
                val url = "${Constants.FILE_BASE_URL}${item.playUrl}"
                val downloadRequest: DownloadRequest =
                    DownloadRequest.Builder(item.contentID.toString(), url.toUri())
                        .build()
                DownloadService.sendAddDownload(
                    requireContext(),
                    MyBLDownloadService::class.java,
                    downloadRequest,
                    /* foreground= */ false)
                Log.e("TAG", "DELETED: " +  item.playUrl)
                 if (cacheRepository.isDownloadCompleted(item.contentID.toString()).equals(true)) {
                cacheRepository.insertDownload(
                    DownloadedContent(item.contentID.toString(),
                        item.rootId.toString(),
                        item.image.toString(),
                        item.title.toString(),
                        item.contentType.toString(),
                        item.playUrl,
                        item.contentType.toString(),
                        0,
                        0,
                        item.artist.toString(),item.artistId.toString(),
                        item.duration.toString()))
                isDownloaded =true
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
        val constraintWatchlater: ConstraintLayout?= bottomSheetDialog.findViewById(R.id.constraintAddtoWatch)
        constraintWatchlater?.setOnClickListener {
            if (iswatched) {
                cacheRepository.deleteWatchlaterById(item.contentID.toString())
                iswatched = false
            } else {
                val url = "${Constants.FILE_BASE_URL}${item.playUrl}"
                cacheRepository.insertWatchLater(WatchLaterContent(item.contentID.toString(),
                    item.rootId.toString(),
                    item.image.toString(),
                    item.title.toString(),
                    item.contentType.toString(),
                    url ,
                    item.contentType.toString(),
                    0,
                    0,
                    item.artist.toString(),
                    item.duration.toString()))
                iswatched = true
                Log.e("TAGGG",
                    "INSERTED: " + cacheRepository.getAllWatchlater())

            }
            bottomSheetDialog.dismiss()
        }
     val constraintFav: ConstraintLayout? = bottomSheetDialog.findViewById(R.id.constraintFav)
     val favImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgLike)
     val textFav: TextView? = bottomSheetDialog.findViewById(R.id.tvFav)
     var isFav = false
     val isAddedToFav = cacheRepository.getFavoriteById(item.contentID.toString())
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
             favViewModel.deleteFavContent(item.contentID.toString(),"V")
             cacheRepository.deleteFavoriteById(item.contentID.toString())
             Toast.makeText(context,"Removed from favorite",Toast.LENGTH_LONG).show()
             favImage?.setImageResource(R.drawable.my_bl_sdk_ic_like)
             isFav=false
             Log.e("TAG","NAME: "+ isFav)
         } else {

             favViewModel.addFavContent(item.contentID.toString(),"V")

             favImage?.setImageResource(R.drawable.my_bl_sdk_ic_icon_fav)
             Log.e("TAG","NAME123: "+ isFav)
             cacheRepository.insertFavSingleContent(FavData(item.contentID.toString(),item.albumId,item.image,"",item.artist,item.artistId,
                 "","",2,"V","","","1","",item.image,"",
                 false,  "",0,"","","",item.playUrl,item.rootId,
                 "",false,"",item.title,"",""

             ))
             isFav = true
             Toast.makeText(context,"Added to favorite",Toast.LENGTH_LONG).show()
         }
         bottomSheetDialog.dismiss()
     }

    }
    fun showBottomSheetDialog(
        bsdNavController: NavController,
        context: Context,
        mSongDetails: SongDetail,
        argHomePatchItem: HomePatchItem?,
        argHomePatchDetail: HomePatchDetail?,
    ) {

        val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
        val cacheRepository= CacheRepository(requireContext())
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
        artistname?.text = mSongDetails.artist
        if (image != null) {
            Glide.with(context)?.load(url?.replace("<\$size\$>", "300"))?.into(image)
        }
        val downloadImage: ImageView? = bottomSheetDialog.findViewById(R.id.imgDownload)
        val textViewDownloadTitle: TextView? = bottomSheetDialog.findViewById(R.id.tv_download)
        var isDownloaded = false
        var downloaded = cacheRepository.getDownloadById(mSongDetails.ContentID)
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
                cacheRepository.deleteDownloadById(mSongDetails.ContentID)
                DownloadService.sendRemoveDownload(requireContext(),
                    MyBLDownloadService::class.java,
                    mSongDetails.ContentID,
                    false)
                Log.e("TAG", "DELETED: " + isDownloaded)
                val localBroadcastManager = LocalBroadcastManager.getInstance(requireContext())
                val localIntent = Intent("DELETED")
                    .putExtra("contentID", mSongDetails.ContentID)
                localBroadcastManager.sendBroadcast(localIntent)
                isDownloaded=false
            } else {
                val url = "${Constants.FILE_BASE_URL}${mSongDetails.PlayUrl}"
                var downloadRequest: DownloadRequest =
                    DownloadRequest.Builder(mSongDetails.ContentID, url.toUri())
                        .build()
                DownloadService.sendAddDownload(
                    requireContext(),
                    MyBLDownloadService::class.java,
                    downloadRequest,
                    /* foreground= */ false)
                if (cacheRepository.isDownloadCompleted(mSongDetails.ContentID).equals(true)) {
//                if (cacheRepository.isDownloadCompleted(mSongDetails.ContentID).equals(true)) {
                    cacheRepository.insertDownload(DownloadedContent(mSongDetails.ContentID.toString(),
                        mSongDetails.rootContentID,
                        mSongDetails.image,
                        mSongDetails.title,
                        mSongDetails.ContentType,
                        mSongDetails.PlayUrl,
                        mSongDetails.ContentType,
                        0,
                        0,
                        mSongDetails.artist,
                        mSongDetails.ArtistId.toString(),
                        mSongDetails.duration))
                    isDownloaded =true
                    Log.e("TAGGG",
                        "INSERTED: " + url)
                    Log.e("TAG", "INSERTED: " + cacheRepository.getAllDownloads())
//                    Log.e("TAGGG",
//                        "INSERTED: " + cacheRepository.isTrackDownloaded())
//                    Log.e("TAGGG",
//                        "COMPLETED: " + cacheRepository.isDownloadCompleted(mSongDetails.ContentID))
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

            Log.e("TAG", "CLICKArtist: " + argHomePatchItem)
            Log.e("TAG", "ARTISTID: " + mSongDetails.ArtistId)
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
        val isAddedToFav = cacheRepository.getFavoriteById(mSongDetails.ContentID)
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
                cacheRepository.deleteFavoriteById(mSongDetails.ContentID)
                Toast.makeText(requireContext(),"Removed from favorite", Toast.LENGTH_LONG).show()
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
                Toast.makeText(requireContext(),"Added to favorite", Toast.LENGTH_LONG).show()
            }
            bottomSheetDialog.dismiss()
        }
    }
    private fun gotoArtist(
        bsdNavController: NavController,
        context: Context,
        mSongDetails: SongDetail,
        argHomePatchItem: HomePatchItem?,
        argHomePatchDetail: HomePatchDetail?,

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
                    HomePatchItem("","A", mutableListOf(),"Artist","",0,0)
                )
                putSerializable(
                    AppConstantUtils.PatchDetail,
                    HomePatchDetail(mSongDetails.albumId.toString(),"","",mSongDetails.artist,mSongDetails.ArtistId.toString(),"","",
                        mSongDetails.ContentID,mSongDetails.ContentType,"","","",false,"",
                        0,"","","",mSongDetails.PlayUrl.toString(),"","",
                        false,"","","","",mSongDetails.image.toString(),"",mSongDetails.title.toString()) as Serializable
                    )
            })
    }
    private fun gotoPlayList(context: Context, mSongDetails: SongDetail) {
        val bottomSheetDialogPlaylist = BottomSheetDialog(context, R.style.BottomSheetDialog)
        val contentView =
            View.inflate(context, R.layout.my_bl_sdk_bottomsheet_create_playlist_with_list, null)
        bottomSheetDialogPlaylist.setContentView(contentView)
        bottomSheetDialogPlaylist.show()
        val closeButton: ImageView? = bottomSheetDialogPlaylist.findViewById(R.id.closeButton)
        closeButton?.setOnClickListener {
            bottomSheetDialogPlaylist.dismiss()
        }
        val recyclerView:RecyclerView ? = bottomSheetDialogPlaylist.findViewById(R.id.recyclerView)
        viewModel.getuserPlaylist()
        viewModel.getUserPlaylist.observe(this) { res ->
            recyclerView?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false )
            recyclerView?.adapter = res.data?.let { CreatePlaylistListAdapter(it,this,mSongDetails)
            }

        }
        val btnCreateplaylist: AppCompatButton? = bottomSheetDialogPlaylist.findViewById(R.id.btnCreatePlaylist)
        btnCreateplaylist?.setOnClickListener {
            openCreatePlaylist(context)
            bottomSheetDialogPlaylist.dismiss()
        }
        viewModel.createPlaylist.observe(this){res->

            Toast.makeText(context,res.status.toString(),Toast.LENGTH_LONG).show()
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
            if (focused) keyboard.showSoftInput(etCreatePlaylist,
                0) else keyboard.hideSoftInputFromWindow(
                etCreatePlaylist.getWindowToken(),
                0)
        }
        etCreatePlaylist?.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val name:String = etCreatePlaylist.getText().toString()
                Log.e("TAG","NAME: "+ name)
                savePlaylist?.setBackgroundResource(R.drawable.my_bl_sdk_rounded_button_red)
                savePlaylist?.isEnabled= true
                savePlaylist?.setOnClickListener {

                    viewModel.createPlaylist(name)
                    // requireActivity().onBackPressed()
                    bottomSheetDialog.dismiss()

                }
                if(etCreatePlaylist.text.isNullOrEmpty()){
                    savePlaylist?.setBackgroundResource(R.drawable.my_bl_sdk_rounded_button_gray)
                    savePlaylist?.isEnabled= false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        etCreatePlaylist?.requestFocus()

    }

    override fun onClick(position: Int, mSongDetails: SongDetail, id: String?) {
        addSongsToPlaylist(mSongDetails,id)
    }
    fun addSongsToPlaylist(mSongDetails: SongDetail, id: String?) {

        id?.let { viewModel.songsAddedToPlaylist(it, mSongDetails.ContentID) }
        viewModel.songsAddedToPlaylist.observe(this) { res ->


            Toast.makeText(requireContext(), res.status.toString(), Toast.LENGTH_LONG).show()


        }
    }

}
