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
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.Video


internal class TopTrendingVideosAdapter(val homePatchItemModel: HomePatchItem, val homePatchDetail: List<HomePatchDetail>) : RecyclerView.Adapter<TopTrendingVideosAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.trending_music_video_list, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems()


    }

    override fun getItemCount(): Int {
        return homePatchItemModel.Data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems() {

            val textViewName = itemView.findViewById(R.id.txt_title) as TextView
            val textViewArtist = itemView.findViewById(R.id.txt_name) as TextView
            val imageView = itemView.findViewById(R.id.image) as ImageView
            val url:String = homePatchItemModel.Data[position].image
            textViewName.text= homePatchItemModel.Data[position].title
            textViewArtist.text = homePatchItemModel.Data[absoluteAdapterPosition].Artist
              Glide.with(itemView.context).load(url.replace("<\$size\$>", "300")).into(imageView)
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, VideoActivity::class.java)
                val videoArray = ArrayList<Video>()
                for (item in  homePatchItemModel.Data){
                    val video = Video()
                    video.setData(item)
                    videoArray.add(video)
                }
                val videos :ArrayList<Video> = videoArray
                intent.putExtra(VideoActivity.INTENT_KEY_POSITION, absoluteAdapterPosition)
                intent.putExtra(VideoActivity.INTENT_KEY_DATA_LIST, videos)
                itemView.context.startActivity(intent)
            }


        }

    }

}






