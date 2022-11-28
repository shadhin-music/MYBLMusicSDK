package com.shadhinmusiclibrary.adapter

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
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.DownloadedSongOnCallBack
import com.shadhinmusiclibrary.callBackService.favItemClickCallback
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.library.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.AnyTrackDiffCB
import com.shadhinmusiclibrary.utils.TimeParser
import com.shadhinmusiclibrary.utils.UtilHelper

internal class FavoriteSongsAdapter(
    private val lrOnCallBack: DownloadedSongOnCallBack,
    private val openMenu: favItemClickCallback,
    private val cacheRepository: CacheRepository
) : RecyclerView.Adapter<FavoriteSongsAdapter.ViewHolder>() {
    private var allDownloads: MutableList<IMusicModel> = mutableListOf()
    private var contentId: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_download_songs_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mSongDetails = allDownloads[position]
        holder.bindItems()
        val menu = holder.itemView.findViewById<ImageView>(R.id.iv_song_menu_icon)
//        if(allDownloads[position].rootType.equals("V")){
//             holder.itemView.setOnClickListener {
//                 val intent = Intent(holder.itemView.context, VideoActivity::class.java)
//                 val videoArray = ArrayList<Video>()
//                 for (item in allDownloads) {
//                     val video = Video()
//                     video.setDataDownload(item)
//                     videoArray.add(video)
//                 }
//                 val videos :ArrayList<Video> = videoArray
//                 intent.putExtra(VideoActivity.INTENT_KEY_POSITION, position)
//                 intent.putExtra(VideoActivity.INTENT_KEY_DATA_LIST, videos)
//                 holder.itemView.context.startActivity(intent)
//             }
//        }
//        if(allDownloads[position].rootType.equals("S")){
        holder.itemView.setOnClickListener {
            lrOnCallBack.onClickFavItem(allDownloads, position)
        }
        menu.setOnClickListener {
            openMenu.onClickBottomItemSongs(allDownloads[position])
        }
        val contentPodcast = allDownloads[position].content_Type

       if (contentPodcast?.contains("PD")==true) {
            menu.setOnClickListener {
                openMenu.onClickBottomItemPodcast(allDownloads[position])
            }
            Log.e("TAG","DATA: "+ allDownloads[position].content_Type)
            Log.e("TAG","DATA: "+ allDownloads[position].rootContentType)
            Log.e("TAG","DATA: "+ allDownloads[position].content_Id)
            Log.e("TAG","DATA: "+ allDownloads[position].playingUrl)
            holder.itemView.setOnClickListener {
                lrOnCallBack.onClickFavItem(allDownloads, position)
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
        for (songItem in data) {
            Log.e(
                "ALLDowA",
                "setData: " + songItem.content_Id + " " + songItem.titleName + " " + songItem.total_duration
            )
            allDownloads.add(
                songItem.apply {
                    isSeekAble = true
                    rootContentId = "00$content_Id"
                    rootContentType = content_Type
                }
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
        fun bindItems() {
            val sivSongIcon: ImageView = itemView.findViewById(R.id.siv_song_icon)
            Glide.with(context)
                .load(UtilHelper.getImageUrlSize300(allDownloads[absoluteAdapterPosition].imageUrl!!))
                .into(sivSongIcon)
            tvSongName = itemView.findViewById(R.id.tv_song_name)
            tvSongName?.text = allDownloads[absoluteAdapterPosition].titleName
            val tvSingerName: TextView = itemView.findViewById(R.id.tv_singer_name)
            tvSingerName.text = allDownloads[absoluteAdapterPosition].artistName
            val tvSongLength: TextView = itemView.findViewById(R.id.tv_song_length)
            tvSongLength.text =
                TimeParser.secToMin(allDownloads[absoluteAdapterPosition].total_duration)
            val progressIndicator: CircularProgressIndicator = itemView.findViewById(R.id.progress)
            val downloaded: ImageView = itemView.findViewById(R.id.iv_song_type_icon)
            progressIndicator.tag = allDownloads[absoluteAdapterPosition].content_Id
            progressIndicator.visibility = View.GONE
            downloaded.visibility = View.GONE
            downloaded.tag = 220
            val isDownloaded =
                cacheRepository.isTrackDownloaded(allDownloads[absoluteAdapterPosition].content_Id!!)
                    ?: false
            if (isDownloaded) {
                downloaded.visibility = View.VISIBLE
                progressIndicator.visibility = View.GONE
            }
        }
    }
}