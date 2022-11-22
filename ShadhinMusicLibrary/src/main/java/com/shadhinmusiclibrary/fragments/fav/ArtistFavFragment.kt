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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.offline.DownloadRequest
import com.google.android.exoplayer2.offline.DownloadService
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.activities.ItemClickListener
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.adapter.CreatePlaylistListAdapter
import com.shadhinmusiclibrary.adapter.FavoriteArtistAdapter
import com.shadhinmusiclibrary.adapter.FavoriteSongsAdapter
import com.shadhinmusiclibrary.callBackService.DownloadedSongOnCallBack
import com.shadhinmusiclibrary.callBackService.favItemClickCallback
import com.shadhinmusiclibrary.data.model.DownloadingItem
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.model.fav.FavData
import com.shadhinmusiclibrary.download.MyBLDownloadService
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.fragments.create_playlist.CreateplaylistViewModel
import com.shadhinmusiclibrary.player.Constants
import com.shadhinmusiclibrary.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.AppConstantUtils
import com.shadhinmusiclibrary.utils.UtilHelper
import java.io.Serializable


internal class ArtistFavFragment : CommonBaseFragment(),DownloadedSongOnCallBack ,favItemClickCallback,onFavArtistClick,
    ItemClickListener {
    private lateinit var favViewModel: FavViewModel
    private lateinit var viewModel: CreateplaylistViewModel
    private lateinit var navController: NavController
    private lateinit var dataAdapter: FavoriteArtistAdapter
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


        favViewModel = ViewModelProvider(this,injector.factoryFavContentVM)[FavViewModel::class.java]

    }
      fun loadData(){
          val cacheRepository= CacheRepository(requireContext())
         dataAdapter =
             cacheRepository.getArtistFavoriteContent()?.let { FavoriteArtistAdapter(it,
                 this,
                 this,
                 cacheRepository,this) }!!

          val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
          recyclerView.layoutManager =
              LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false )
          recyclerView.adapter = dataAdapter
         // Log.e("TAG","VIDEOS: "+ cacheRepository.getAllVideosDownloads())

      }

    companion object {

        @JvmStatic
        fun newInstance() =
            ArtistFavFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onClickItem(mSongDetails: MutableList<DownloadedContent>, clickItemPosition: Int) {


//            if (playerViewModel.currentMusic != null && (mSongDetails[clickItemPosition].rootId == playerViewModel.currentMusic?.rootId)) {
//                if ((mSongDetails[clickItemPosition].contentId != playerViewModel.currentMusic?.mediaId)) {
//                    Log.e("TAG","SONG :"+ mSongDetails[clickItemPosition].contentId )
//                    Log.e("TAG","SONG :"+ playerViewModel.currentMusic?.mediaId )
//                    playerViewModel.skipToQueueItem(clickItemPosition)
//                } else {
//                    playerViewModel.togglePlayPause()
//                }
//            } else {
//                playItem(
//                    UtilHelper.getSongDetailToDownloadedSongDetailList(mSongDetails),
//                    clickItemPosition
//                )
//            }
    }

    override fun onClickFavItem(mSongDetails: MutableList<FavData>, clickItemPosition: Int) {

//        if (playerViewModel.currentMusic != null && (mSongDetails[clickItemPosition].rootId == playerViewModel.currentMusic?.rootId)) {
//            if ((mSongDetails[clickItemPosition].contentID != playerViewModel.currentMusic?.mediaId)) {
//                Log.e("TAG","SONG :"+ mSongDetails[clickItemPosition].contentID)
//                Log.e("TAG","SONG :"+ playerViewModel.currentMusic?.mediaId )
//                playerViewModel.skipToQueueItem(clickItemPosition)
//            } else {
//                playerViewModel.togglePlayPause()
//            }
//        } else {
//            playItem(
//                UtilHelper.getSongDetailToFavoriteSongDetailList(mSongDetails),
//                clickItemPosition
//            )
//            Log.e("TAG","SONG :"+ mSongDetails.toString() )
//        }
    }

    override fun onClickBottomItemPodcast(mSongDetails: FavData) {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
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



    override fun onFavArtistClick(itemPosition: Int, favData: List<FavData>) {
       // val homePatchDetail = this.argHomePatchItem!!.Data[itemPosition]
       // Log.e("i am being called", "test test test "+navController.graph.displayName)

        navController.navigate(
            R.id.favoriteArtist,
            Bundle().apply {
                putSerializable(
                    AppConstantUtils.PatchItem,
                   HomePatchItem("","A", mutableListOf(),"Artist","",0,0)
                )
                putSerializable(
                    AppConstantUtils.PatchDetail,
                    HomePatchDetail(favData.get(itemPosition).albumId.toString(),"","",
                        favData.get(itemPosition).artist.toString(),favData.get(itemPosition).artistId.toString(),"","",
                        favData.get(itemPosition).contentID,favData.get(itemPosition).contentType.toString(),
                    "","","",false,"",
                    0,"","","",favData.get(itemPosition).playUrl.toString(), "","",false,
                        "",
                   "",
               "",
                "",
                        favData.get(itemPosition).image.toString() ,
               "",favData.get(itemPosition).title.toString()
               ))
            })
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
        constraintDownload?.visibility = GONE
        constraintDownload?.setOnClickListener {
            if (isDownloaded.equals(true)) {
                cacheRepository.deleteDownloadById(mSongDetails.ContentID)
                Log.e("DELETEDX", "openDialog: ${Thread.currentThread().stackTrace.map { it.methodName }.toString()}")
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
        constraintAlbum?.visibility = GONE
        constraintAlbum?.setOnClickListener {
            gotoArtist(
                bsdNavController,
                context,
                mSongDetails,
                argHomePatchItem,
                argHomePatchDetail

            )

            Log.e("TAG", "CLICKArtist: " + argHomePatchItem)
            Log.e("TAG", "CLICKArtist: " + mSongDetails.ArtistId)
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
            R.id.favoriteArtist,
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
interface onFavArtistClick{
    fun onFavArtistClick(itemPosition: Int, favData: List<FavData>)
}
