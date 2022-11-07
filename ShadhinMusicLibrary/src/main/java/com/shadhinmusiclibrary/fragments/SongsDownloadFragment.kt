package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.adapter.DownloadedSongsAdapter
import com.shadhinmusiclibrary.callBackService.DownloadBottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.DownloadedSongOnCallBack
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.UtilHelper


internal class SongsDownloadFragment : CommonBaseFragment(),DownloadedSongOnCallBack ,DownloadBottomSheetDialogItemCallback{
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


    }
      fun loadData(){
          val cacheRepository= CacheRepository(requireContext())
          val dataAdapter =
              cacheRepository.getAllSongsDownloads()?.let { DownloadedSongsAdapter(it,this,this) }

          val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
          recyclerView.layoutManager =
              LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false )
          recyclerView.adapter = dataAdapter
         // Log.e("TAG","VIDEOS: "+ cacheRepository.getAllVideosDownloads())

      }

    companion object {

        @JvmStatic
        fun newInstance() =
            SongsDownloadFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onClickItem(mSongDetails: MutableList<DownloadedContent>, clickItemPosition: Int) {

            if (playerViewModel.currentMusic != null && (mSongDetails[clickItemPosition].rootId == playerViewModel.currentMusic?.rootId)) {
                if ((mSongDetails[clickItemPosition].contentId != playerViewModel.currentMusic?.mediaId)) {
                    Log.e("TAG","SONG :"+ mSongDetails[clickItemPosition].contentId )
                    Log.e("TAG","SONG :"+ playerViewModel.currentMusic?.mediaId )
                    playerViewModel.skipToQueueItem(clickItemPosition)
                } else {
                    playerViewModel.togglePlayPause()
                }
            } else {
                playItem(
                    UtilHelper.getSongDetailToDownloadedSongDetailList(mSongDetails),
                    clickItemPosition
                )
            }
    }

    override fun onClickBottomItemPodcast(mSongDetails: DownloadedContent) {
        TODO("Not yet implemented")
    }

    override fun onClickBottomItemSongs(mSongDetails: DownloadedContent) {
        (activity as? SDKMainActivity)?.showBottomSheetDialog(
            navController,
            context = requireContext(),
            SongDetail(mSongDetails.contentId,
                mSongDetails.rootImg,
                mSongDetails.rootTitle,
                mSongDetails.rootType,
                mSongDetails.track.toString(),
                mSongDetails.artist,
                mSongDetails.timeStamp,
                "",
                "",
                "",
                "","","","","","","",false),
            argHomePatchItem,
            HomePatchDetail( "","","",mSongDetails.artist,"","","",
            mSongDetails.contentId,mSongDetails.rootType,"","","",false,"",
            0,"","","",mSongDetails.track.toString(),"","",
            false,"","","","",mSongDetails.rootImg,"",mSongDetails.rootTitle)
        )
    }

    override fun onClickBottomItemVideo(mSongDetails: DownloadedContent) {
        TODO("Not yet implemented")
    }

}
