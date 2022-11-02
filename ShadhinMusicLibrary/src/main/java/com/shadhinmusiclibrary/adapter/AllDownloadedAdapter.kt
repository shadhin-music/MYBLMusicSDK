package com.shadhinmusiclibrary.adapter

import android.content.Intent
import android.util.Log
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
import com.shadhinmusiclibrary.callBackService.LatestReleaseOnCallBack
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.Video
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.utils.TimeParser

internal class AllDownloadedAdapter(val allDownloads: List<DownloadedContent>, private val lrOnCallBack: DownloadedSongOnCallBack) : RecyclerView.Adapter<AllDownloadedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.my_bl_sdk_download_songs_item, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.bindItems()
//
        if(allDownloads[position].rootType.equals("V")){

             holder.itemView.setOnClickListener {
                 val intent = Intent(holder.itemView.context, VideoActivity::class.java)
                 val videoArray = ArrayList<Video>()
                 for (item in allDownloads) {
                     val video = Video()
                     video.setDataDownload(item)
                     videoArray.add(video)
                 }
                 val videos :ArrayList<Video> = videoArray
                 intent.putExtra(VideoActivity.INTENT_KEY_POSITION, position)
                 intent.putExtra(VideoActivity.INTENT_KEY_DATA_LIST, videos)
                 holder.itemView.context.startActivity(intent)
             }
        }
        if(allDownloads[position].rootType.equals("S")){
            holder.itemView.setOnClickListener {
            lrOnCallBack.onClickItem(allDownloads as MutableList<DownloadedContent>, position)
             Log.e("TAG","ALL Downloads: "+ allDownloads)
        }

        }

    }

    override fun getItemCount(): Int {
        return allDownloads.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tag:String?=null
        val  context = itemView.getContext()
        fun bindItems() {

            val sivSongIcon: ImageView = itemView.findViewById(R.id.siv_song_icon)
            Glide.with(context)
                .load(allDownloads[absoluteAdapterPosition].getImageUrl300Size())
                .into(sivSongIcon)
            val tvSongName: TextView = itemView.findViewById(R.id.tv_song_name)
            tvSongName.text = allDownloads[absoluteAdapterPosition].rootTitle

            val tvSingerName: TextView = itemView.findViewById(R.id.tv_singer_name)
            tvSingerName.text = allDownloads[absoluteAdapterPosition].artist

            val tvSongLength: TextView =itemView.findViewById(R.id.tv_song_length)
            tvSongLength.text = TimeParser.secToMin(allDownloads[absoluteAdapterPosition].timeStamp)

        }

    }
}