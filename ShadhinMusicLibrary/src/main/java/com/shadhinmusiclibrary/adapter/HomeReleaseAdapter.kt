package com.shadhinmusiclibrary.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.HomePatchItem


class HomeReleaseAdapter(val homePatchItem: HomePatchItem, private val homeCallBack: HomeCallBack) :
    RecyclerView.Adapter<HomeReleaseAdapter.ViewHolder>() {

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
        return homePatchItem.Data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mContext = itemView.context
        fun bindItems() {
            val imageView: ShapeableImageView = itemView.findViewById(R.id.image)
            val textView: TextView = itemView.findViewById(R.id.txt_title)
            val url: String = homePatchItem.Data[absoluteAdapterPosition].image
            textView.text = homePatchItem.Data[absoluteAdapterPosition].title
            val textViewArtist: TextView= itemView.findViewById(R.id.txt_name)
             textViewArtist.text = homePatchItem.Data[absoluteAdapterPosition].Artist
            Glide.with(mContext)
                .load(url.replace("<\$size\$>", "300"))
                .into(imageView)

        }
    }
}






