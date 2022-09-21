package com.shadhinmusiclibrary.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.FeaturedLatestTrackListData


class FeaturedLatestTracksAdapter(val data: List<FeaturedLatestTrackListData>) :
    RecyclerView.Adapter<FeaturedLatestTracksAdapter.ViewHolder>() {

    private var parentView:View?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
        parentView = LayoutInflater.from(parent.context).inflate(R.layout.video_podcast_epi_single_item, parent, false)
        return ViewHolder(parentView!!)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(data[position])


    }


    override fun getItemCount(): Int {
        return data.size

    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.getContext()
        fun bindItems(featuredLatestTrackListData: FeaturedLatestTrackListData) {
            val imageView: ShapeableImageView? = itemView.findViewById(R.id.siv_song_icon)
            val url: String = featuredLatestTrackListData.image
            // val textArtist:TextView = itemView.findViewById(R.id.txt_name)
            //textArtist.setText(data.Data[absoluteAdapterPosition].Artist)
            // textView.setText(data.Data[absoluteAdapterPosition].title)
            Log.d("TAG", "ImageUrl: " + url.replace("<\$size\$>", "300"))
            Glide.with(context)
                .load(url.replace("<\$size\$>", "300"))
                .into(imageView!!)
            val textTitle:TextView = itemView.findViewById(R.id.tv_song_name)
            val textArtist:TextView = itemView.findViewById(R.id.tv_singer_name)
            val textDuration:TextView = itemView.findViewById(R.id.tv_song_length)
            textTitle.text= featuredLatestTrackListData.title
            textArtist.text = featuredLatestTrackListData.artistname
            textDuration.text = featuredLatestTrackListData.duration
            //Log.e("TAG","DATA123: "+ artistContent?.image)
            itemView.setOnClickListener {
//                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
//                manager.beginTransaction()
//                    .replace(R.id.container , AlbumFragment.newInstance())
//                    .commit()
            }
//            val linearLayout: LinearLayout = itemView.findViewById(R.id.linear)
//            entityId = banner.entityId
            //getActorName(entityId!!)

//            //textViewName.setText(banner.name)
//            textViewName.text = LOADING_TXT
//            textViewName.tag = banner.entityId


        }

    }


}






