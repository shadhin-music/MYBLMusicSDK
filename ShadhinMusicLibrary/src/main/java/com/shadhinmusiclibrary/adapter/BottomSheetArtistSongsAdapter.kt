package com.shadhinmusiclibrary.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.fragments.artist.ArtistContent
import com.shadhinmusiclibrary.fragments.artist.ArtistContentData


class BottomSheetArtistSongsAdapter:
    RecyclerView.Adapter<BottomSheetArtistSongsAdapter.ViewHolder>() {
 //   private var artistContent: ArtistContent? = null
    private var artistContentList:MutableList<ArtistContentData> = ArrayList()
    private var parentView:View?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
        parentView = LayoutInflater.from(parent.context).inflate(R.layout.video_podcast_epi_single_item, parent, false)
        return ViewHolder(parentView!!)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(artistContentList[position])


    }

    override fun getItemViewType(position: Int) = VIEW_TYPE
    override fun getItemCount(): Int {
        return artistContentList.size

    }

    fun artistContent(artistContent: ArtistContent?) {

        artistContent?.data?.let {

            this.artistContentList.clear()
            this.artistContentList.addAll(it)
            this.notifyDataSetChanged()

        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.getContext()
        fun bindItems(artistContent: ArtistContentData) {
            val imageView: ShapeableImageView? = itemView.findViewById(R.id.siv_song_icon)
            val url: String = artistContent.image
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
            textTitle.text= artistContent.title
            textArtist.text = artistContent.artistname
            textDuration.text = artistContent.duration
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

    companion object {
        const val VIEW_TYPE = 2
    }
}






