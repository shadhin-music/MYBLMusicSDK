package com.shadhinmusiclibrary.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.activities.video.VideoActivity
import com.shadhinmusiclibrary.callBackService.DownloadBottomSheetDialogItemCallback

import com.shadhinmusiclibrary.data.model.FeaturedSongDetail
import com.shadhinmusiclibrary.data.model.Video
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.download.room.WatchLaterContent
import com.shadhinmusiclibrary.fragments.WatchLaterFragment
import com.shadhinmusiclibrary.fragments.WatchlaterBottomSheetDialogItemCallback
import com.shadhinmusiclibrary.utils.TimeParser


internal class WatchlaterAdapter( val allWatchlater: List<WatchLaterContent>, private val openMenu: WatchlaterBottomSheetDialogItemCallback
) : RecyclerView.Adapter<WatchlaterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.my_bl_sdk_download_songs_item, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems()
        val menu = holder.itemView.findViewById<ImageView>(R.id.iv_song_menu_icon)
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, VideoActivity::class.java)
                val videoArray = ArrayList<Video>()
                for (item in allWatchlater) {
                    val video = Video()
                    video.setDataWatchlater(item)
                    videoArray.add(video)
                }
                val videos :ArrayList<Video> = videoArray
                intent.putExtra(VideoActivity.INTENT_KEY_POSITION, position)
                intent.putExtra(VideoActivity.INTENT_KEY_DATA_LIST, videos)
                holder.itemView.context.startActivity(intent)
            }
        menu.setOnClickListener {
            openMenu.onClickBottomItemVideo(allWatchlater[position])
            Log.e("TAG","ALL DownloadsVideo: "+ allWatchlater[position].rootType)
        }
        }




    override fun getItemCount(): Int {
        return allWatchlater.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tag:String?=null
        val  context = itemView.getContext()
        fun bindItems() {

            val sivSongIcon: ImageView = itemView.findViewById(R.id.siv_song_icon)
            Glide.with(context)
                .load(allWatchlater[absoluteAdapterPosition].getImageUrl300Size())
                .into(sivSongIcon)
            val tvSongName: TextView = itemView.findViewById(R.id.tv_song_name)
            tvSongName.text = allWatchlater[absoluteAdapterPosition].rootTitle

            val tvSingerName: TextView = itemView.findViewById(R.id.tv_singer_name)
            tvSingerName.text = allWatchlater[absoluteAdapterPosition].artist

            val tvSongLength: TextView =itemView.findViewById(R.id.tv_song_length)
            tvSongLength.text = TimeParser.secToMin(allWatchlater[absoluteAdapterPosition].timeStamp)

        }

    }
}