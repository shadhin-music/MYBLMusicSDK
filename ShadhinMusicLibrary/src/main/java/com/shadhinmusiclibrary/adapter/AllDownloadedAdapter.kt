package com.shadhinmusiclibrary.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.activities.video.VideoActivity
import com.shadhinmusiclibrary.callBackService.DownloadBottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.DownloadedSongOnCallBack
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.SongDetailModel
import com.shadhinmusiclibrary.data.model.VideoModel
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.utils.AnyTrackDiffCB
import com.shadhinmusiclibrary.utils.TimeParser
import com.shadhinmusiclibrary.utils.UtilHelper

internal class AllDownloadedAdapter(
    private val lrOnCallBack: DownloadedSongOnCallBack,
    private val openMenu: DownloadBottomSheetDialogItemCallback
) : RecyclerView.Adapter<AllDownloadedAdapter.ViewHolder>() {
    private var allDownloads: MutableList<IMusicModel> = mutableListOf()
    private var contentId: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_download_songs_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mSongDetails = allDownloads[position]
        holder.bindItems(mSongDetails)
        val menu: ImageView = holder.itemView.findViewById(R.id.iv_song_menu_icon)

        if (mSongDetails.content_Type.equals("V")) {
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, VideoActivity::class.java)
                val videoArray = ArrayList<VideoModel>()
                for (item in allDownloads) {
                    val video = VideoModel()
                    video.setDataDownloadIM(item)
                    videoArray.add(video)
                }
                val videos: ArrayList<VideoModel> = videoArray
                intent.putExtra(VideoActivity.INTENT_KEY_POSITION, position)
                intent.putExtra(VideoActivity.INTENT_KEY_DATA_LIST, videos)
                holder.itemView.context.startActivity(intent)
            }
            menu.setOnClickListener {
                openMenu.onClickBottomItemVideo(allDownloads[position])
                Log.e("TAG", "ALL DownloadsVideo: " + allDownloads[position].rootContentType)
            }
        }

        if (mSongDetails.content_Type.equals("S")) {
            holder.itemView.setOnClickListener {
                lrOnCallBack.onClickItem(allDownloads, position)
                Log.e("TAG", "ALL Downloads: " + allDownloads)
            }
            menu.setOnClickListener {
                openMenu.onClickBottomItemSongs(allDownloads[position])
            }
        }
        if (allDownloads[position].rootContentType.equals("PDJG")) {
            holder.itemView.setOnClickListener {
                lrOnCallBack.onClickItem(allDownloads, position)
                Log.e("TAG", "ALL Downloads: " + allDownloads)
            }
            menu.setOnClickListener {
                openMenu.onClickBottomItemPodcast(allDownloads[position])
            }
        }

        if (mSongDetails.isPlaying) {
            holder.tvSongName?.setTextColor(
                ContextCompat.getColor(holder.context, R.color.my_sdk_color_primary)
            )
        } else {
            holder.tvSongName?.setTextColor(
                ContextCompat.getColor(
                    holder.context,
                    R.color.my_sdk_black2
                )
            )
        }
    }

    fun setData(
        data: MutableList<IMusicModel>,
        rootPatch: HomePatchDetailModel,
        mediaId: String?
    ) {
//        this.allDownloads = mutableListOf()
        for (songItem in data) {
            Log.e(
                "ALLDowA",
                "setData: " + songItem.content_Id + " " + songItem.titleName + " " + songItem.total_duration
            )
            allDownloads.add(
                UtilHelper.getSongDetailAndRootData(
                    songItem.apply {
                        isSeekAble = true
                    }, rootPatch
                )
            )
        }

        if (mediaId != null) {
            setPlayingSong(mediaId)
        }

        notifyDataSetChanged()
    }

    fun setPlayingSong(mediaId: String) {
        contentId = mediaId
        val newList: List<IMusicModel> =
            UtilHelper.getCurrentRunningSongToNewSongList(mediaId, allDownloads)
        val callback = AnyTrackDiffCB(allDownloads, newList)
        val diffResult = DiffUtil.calculateDiff(callback)
        allDownloads.clear()
        allDownloads.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return allDownloads.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tag: String? = null
        val context = itemView.getContext()
        var tvSongName: TextView? = null
        fun bindItems(mImusicItem: IMusicModel) {
            val sivSongIcon: ImageView = itemView.findViewById(R.id.siv_song_icon)
            Glide.with(context)
                .load(UtilHelper.getImageUrlSize300(mImusicItem.imageUrl!!))
                .into(sivSongIcon)

            tvSongName = itemView.findViewById(R.id.tv_song_name)
            tvSongName?.text = mImusicItem.titleName

            val tvSingerName: TextView = itemView.findViewById(R.id.tv_singer_name)
            tvSingerName.text = mImusicItem.artistName

            val tvSongLength: TextView = itemView.findViewById(R.id.tv_song_length)
            tvSongLength.text =
                TimeParser.secToMin(mImusicItem.total_duration)
        }
    }
}