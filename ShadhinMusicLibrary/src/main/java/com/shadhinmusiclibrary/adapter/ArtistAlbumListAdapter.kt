package com.shadhinmusiclibrary.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModel


class ArtistAlbumListAdapter(val homePatchItem: HomePatchItem, val artistAlbumModel: ArtistAlbumModel?, private val homeCallBack: HomeCallBack) :
    RecyclerView.Adapter<ArtistAlbumListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.top_trending_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems()
        holder.itemView.setOnClickListener {
            homeCallBack.onClickItemAndAllItem(position, homePatchItem)
        }
    }

    override fun getItemCount(): Int {
        return artistAlbumModel?.data?.size ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mContext = itemView.context
        fun bindItems() {
//            itemView.setOnClickListener {
//                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
//                manager.beginTransaction()
//                    .replace(R.id.container, TopTrendingPlaylistFragment.newInstance())
//                    .addToBackStack("Trending")
//                    .commit()
//            }
            val imageView: ShapeableImageView = itemView.findViewById(R.id.image)
            val textView: TextView = itemView.findViewById(R.id.txt_title)
            val url: String =artistAlbumModel?.data?.get(absoluteAdapterPosition)?.image.toString()
            textView.text = artistAlbumModel?.data!![absoluteAdapterPosition]?.title
            val textViewArtist:TextView = itemView.findViewById(R.id.txt_name)
               textViewArtist.text = artistAlbumModel.data.get(absoluteAdapterPosition).artistname
            //Log.d("TAG","ImageUrl: " + url.replace("<\$size\$>","300"))
            Glide.with(mContext)
                .load(url.replace("<\$size\$>", "300"))
                .into(imageView)
//            val linearLayout: LinearLayout = itemView.findViewById(R.id.linear)
//            entityId = banner.entityId
            //getActorName(entityId!!)
//            //textViewName.setText(banner.name)
//            textViewName.text = LOADING_TXT
//            textViewName.tag = banner.entityId
        }
    }
}






