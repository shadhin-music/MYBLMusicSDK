package com.shadhinmusiclibrary.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.activities.video.VideoActivity
import com.shadhinmusiclibrary.callBackService.DownloadedSongOnCallBack
import com.shadhinmusiclibrary.data.model.VideoModel
import com.shadhinmusiclibrary.download.room.DownloadedContent
import com.shadhinmusiclibrary.utils.TimeParser

internal class DownloadedVideoAdapter(
    val allDownloads: List<DownloadedContent>,
    private val lrOnCallBack: DownloadedSongOnCallBack
) : RecyclerView.Adapter<DownloadedVideoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_row_video_li, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems()
        if (allDownloads[position].rootType.equals("V")) {
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, VideoActivity::class.java)
                val videoArray = ArrayList<VideoModel>()
                for (item in allDownloads) {
                    val video = VideoModel()
                    video.setDataDownload(item)
                    videoArray.add(video)
                }
                val videos: ArrayList<VideoModel> = videoArray
                intent.putExtra(VideoActivity.INTENT_KEY_POSITION, position)
                intent.putExtra(VideoActivity.INTENT_KEY_DATA_LIST, videos)
                holder.itemView.context.startActivity(intent)
            }
        }
//        if(allDownloads[position].rootType.equals("S")){
//            holder.itemView.setOnClickListener {
//            lrOnCallBack.onClickItem(allDownloads as MutableList<DownloadedContent>, position)
//             Log.e("TAG","ALL Downloads: "+ allDownloads)
        // }
        //}
    }

    override fun getItemCount(): Int {
        return allDownloads.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tag: String? = null
        val context = itemView.getContext()
        private var titleTextView: TextView = itemView.findViewById(R.id.videoTitle)
        private var subTitleTextView: TextView = itemView.findViewById(R.id.videoDesc)
        private var durationTextView: TextView = itemView.findViewById(R.id.video_duration)
        private var playPauseImage: ImageView = itemView.findViewById(R.id.play_pause)
        private var videoImage: ImageView = itemView.findViewById(R.id.videoImage)
        private var threeDotButton: ImageButton = itemView.findViewById(R.id.threeDotButton)
        fun bindItems() {
            titleTextView.text = allDownloads[absoluteAdapterPosition].rootTitle
            durationTextView.text =
                TimeParser.secToMin(allDownloads[absoluteAdapterPosition].timeStamp)
//            if (item.isPlaying) {
//                titleTextView.setTextColor( ContextCompat.getColor(itemView.context,R.color.my_sdk_color_primary))
//            } else {
//                titleTextView.setTextColor(ContextCompat.getColor(itemView.context,R.color.my_sdk_down_title))
//            }
            subTitleTextView.text = allDownloads[absoluteAdapterPosition].artist
            Glide.with(itemView.context)
                .load(allDownloads[absoluteAdapterPosition].getImageUrl300Size())
                .placeholder(R.drawable.my_bl_sdk_default_video)
                .into(videoImage)
//            if (item.isPlaystate) {
//                playPauseImage.setImageResource(R.drawable.my_bl_sdk_ic_pause_n)
//            } else {
//                playPauseImage.setImageResource(R.drawable.my_bl_sdk_ic_play_n)
//            }
//            itemView.setOnClickListener {
//                videoItemClickFunc?.invoke(getItem(absoluteAdapterPosition),false)
//            }
//            threeDotButton.setOnClickListener {
//                bottomsheetDialog.openDialog(item)
//            }
        }
    }
}