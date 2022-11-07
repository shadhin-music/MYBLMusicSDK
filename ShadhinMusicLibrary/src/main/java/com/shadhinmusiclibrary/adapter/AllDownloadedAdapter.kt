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
import com.shadhinmusiclibrary.callBackService.DownloadBottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.DownloadedSongOnCallBack
import com.shadhinmusiclibrary.data.model.Video
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.utils.TimeParser

internal class AllDownloadedAdapter(val allDownloads: List<DownloadedContent>, private val lrOnCallBack: DownloadedSongOnCallBack, private val openMenu: DownloadBottomSheetDialogItemCallback) : RecyclerView.Adapter<AllDownloadedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.my_bl_sdk_download_songs_item, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.bindItems()
        val menu = holder.itemView.findViewById<ImageView>(R.id.iv_song_menu_icon)
        if(allDownloads[position].rootType.equals("V")){


             holder.itemView.setOnClickListener {
                 val intent = Intent(holder.itemView.context, VideoActivity::class.java)
                 val videoArray = ArrayList<Video>()

                 for (item in allDownloads) {
                     val video = Video()
                     if(item.rootType.equals("V")) {
                         video.setDataDownload(item)
                         videoArray.add(video)
                     }
                     Log.e("TAG","SONG :"+ item.track )
                 }
                 val index = videoArray.indexOfFirst { it.contentID ==  allDownloads[position].contentId}
                 intent.putExtra(VideoActivity.INTENT_KEY_POSITION, index)
                 intent.putExtra(VideoActivity.INTENT_KEY_DATA_LIST, videoArray)
                 holder.itemView.context.startActivity(intent)
             }
            menu.setOnClickListener {
                openMenu.onClickBottomItemVideo(allDownloads[position])
                Log.e("TAG","ALL DownloadsVideo: "+ allDownloads[position].rootType)
            }
        }
        if(allDownloads[position].rootType.equals("S")){
            holder.itemView.setOnClickListener {
            lrOnCallBack.onClickItem(allDownloads as MutableList<DownloadedContent>, position)
             Log.e("TAG","ALL Downloads: "+ allDownloads)
        }
            menu.setOnClickListener {
                openMenu.onClickBottomItemSongs(allDownloads[position])
            }
        }
            if(allDownloads[position].rootType.equals("PDJG")){
                holder.itemView.setOnClickListener {
                    lrOnCallBack.onClickItem(allDownloads as MutableList<DownloadedContent>, position)
                    Log.e("TAG","ALL Downloads: "+ allDownloads)
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