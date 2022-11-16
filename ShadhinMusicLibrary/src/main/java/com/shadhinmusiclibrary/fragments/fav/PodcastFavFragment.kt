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
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.adapter.FavoriteSongsAdapter
import com.shadhinmusiclibrary.callBackService.DownloadedSongOnCallBack
import com.shadhinmusiclibrary.callBackService.favItemClickCallback
import com.shadhinmusiclibrary.data.model.DownloadingItem
import com.shadhinmusiclibrary.data.model.fav.FavData
import com.shadhinmusiclibrary.data.model.podcast.Track
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.UtilHelper


internal class PodcastFavFragment : CommonBaseFragment(),DownloadedSongOnCallBack ,
   favItemClickCallback {
    private lateinit var navController: NavController
    private lateinit var dataAdapter: FavoriteSongsAdapter
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


    }
      fun loadData(){
          val cacheRepository= CacheRepository(requireContext())
           dataAdapter =
               cacheRepository.getPodcastFavContent()?.let { FavoriteSongsAdapter(it,this,this, cacheRepository) }!!

          val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
          recyclerView.layoutManager =
              LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false )
          recyclerView.adapter = dataAdapter
         // Log.e("TAG","VIDEOS: "+ cacheRepository.getAllVideosDownloads())

      }

    companion object {

        @JvmStatic
        fun newInstance() =
            PodcastFavFragment().apply {
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
        }
    }

    override fun onClickBottomItemPodcast(mSongDetails: FavData) {
        (activity as? SDKMainActivity)?.showBottomSheetDialogForPodcast(
            navController,
            context = requireContext(),
            Track("",
                mSongDetails.contentType.toString(),
                mSongDetails.artist.toString(),
                mSongDetails.duration.toString(),
                mSongDetails.contentID,
                0,
                mSongDetails.image.toString(),
                false,
                mSongDetails.title.toString(),
                mSongDetails.playUrl.toString(),
                false,
                "",
                0,
                "",
                "",
                "",
                0,

                mSongDetails.rootId.toString(),
                "",
                "",
                false),
            argHomePatchItem,
            argHomePatchDetail
        )
    }

    override fun onClickBottomItemSongs(mSongDetails: FavData) {

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

    override fun onClickBottomItemVideo(mSongDetails: FavData) {

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
}
