package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.DownloadedVideoAdapter
import com.shadhinmusiclibrary.callBackService.DownloadedSongOnCallBack
import com.shadhinmusiclibrary.data.model.VideoModel
import com.shadhinmusiclibrary.data.model.fav.FavData
import com.shadhinmusiclibrary.download.MyBLDownloadService
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.fragments.base.BaseFragment
import com.shadhinmusiclibrary.library.player.utils.CacheRepository

internal class VideosDownloadFragment : BaseFragment(), DownloadedSongOnCallBack {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.my_bl_sdk_fragment_download_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    fun loadData() {
        val cacheRepository = CacheRepository(requireContext())
        val dataAdapter =
            cacheRepository.getAllVideosDownloads()?.let { DownloadedVideoAdapter(it, this) }
        val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = dataAdapter
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
        TODO("Not yet implemented")
    }

    override fun onClickBottomItemPodcast(mSongDetails: DownloadedContent) {
        TODO("Not yet implemented")
    }

    override fun onClickBottomItemSongs(mSongDetails: DownloadedContent) {
        TODO("Not yet implemented")
    }

    override fun onClickBottomItemVideo(mSongDetails: DownloadedContent) {
        openDialog(
            Video(
                "",
                "",
                mSongDetails.rootTitle,
                mSongDetails.artist,
                "",
                "",
                "",
                2,
                mSongDetails.contentId,
                mSongDetails.rootType,
                "",
                mSongDetails.timeStamp,
                "",
                "",
                mSongDetails.rootImg,
                "",
                false,
                "",
                0,
                "",
                "",
                "",
                mSongDetails.track,
                "",
                "",
                false,
                "",
                mSongDetails.rootTitle,
                "",
                ""
            )
        )
    }

    fun openDialog(item: Video) {
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
                Log.e("TAG", "DELETED: " + isDownloaded)
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

    }
}
