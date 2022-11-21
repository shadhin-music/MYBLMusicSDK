package com.shadhinmusiclibrary.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.activities.video.VideoActivity
import com.shadhinmusiclibrary.callBackService.DownloadedSongOnCallBack
import com.shadhinmusiclibrary.callBackService.favItemClickCallback
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.VideoModel
import com.shadhinmusiclibrary.data.model.fav.FavData
import com.shadhinmusiclibrary.utils.TimeParser
import com.shadhinmusiclibrary.utils.UtilHelper

internal class AllFavoriteAdapter(
    val allDownloads: MutableList<IMusicModel>,
    private val lrOnCallBack: DownloadedSongOnCallBack,
    private val openMenu: favItemClickCallback
) : RecyclerView.Adapter<AllFavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_download_songs_item, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems()
        val menu = holder.itemView.findViewById<ImageView>(R.id.iv_song_menu_icon)
        if (allDownloads[position].rootContentType.equals("V")) {


            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, VideoActivity::class.java)
                val videoArray = ArrayList<VideoModel>()

                for (item in allDownloads) {
                    val video = VideoModel()
                    if (item.rootContentType.equals("V")) {
                        video.setDataFavoriteIM(item)
                        videoArray.add(video)
                    }
                }
                val index =
                    videoArray.indexOfFirst { it.contentID == allDownloads[position].content_Id }
                intent.putExtra(VideoActivity.INTENT_KEY_POSITION, index)
                intent.putExtra(VideoActivity.INTENT_KEY_DATA_LIST, videoArray)
                holder.itemView.context.startActivity(intent)
            }
            menu.setOnClickListener {
                openMenu.onClickBottomItemVideo(allDownloads[position])
            }
        }
        if (allDownloads[position].rootContentType.equals("S")) {
            holder.itemView.setOnClickListener {
                lrOnCallBack.onClickFavItem(allDownloads, position)
            }
            menu.setOnClickListener {
                openMenu.onClickBottomItemSongs(allDownloads[position])
            }
        }
        if (allDownloads[position].rootContentType.equals("PDJG")) {
            holder.itemView.setOnClickListener {
                lrOnCallBack.onClickFavItem(allDownloads, position)
            }
            menu.setOnClickListener {
                openMenu.onClickBottomItemPodcast(allDownloads[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return allDownloads.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tag: String? = null
        val context = itemView.getContext()
        fun bindItems() {

            val sivSongIcon: ImageView = itemView.findViewById(R.id.siv_song_icon)
            Glide.with(context)
                .load(UtilHelper.getImageUrlSize300(allDownloads[absoluteAdapterPosition].imageUrl!!))
                .into(sivSongIcon)
            val tvSongName: TextView = itemView.findViewById(R.id.tv_song_name)
            tvSongName.text = allDownloads[absoluteAdapterPosition].titleName

            val tvSingerName: TextView = itemView.findViewById(R.id.tv_singer_name)
            tvSingerName.text = allDownloads[absoluteAdapterPosition].artistName

            val tvSongLength: TextView = itemView.findViewById(R.id.tv_song_length)
            tvSongLength.text =
                TimeParser.secToMin(allDownloads[absoluteAdapterPosition].total_duration)
        }
    }
}