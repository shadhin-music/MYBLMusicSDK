package com.shadhinmusiclibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.SearchItemCallBack
import com.shadhinmusiclibrary.data.model.search.TopTrendingdata


internal class TopTenItemAdapter(
    val data: List<TopTrendingdata>,
    private val seaItemCallback: SearchItemCallBack
) :
    RecyclerView.Adapter<TopTenItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.my_bl_sdk_layout_top_ten_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(data, position)
        holder.itemView.setOnClickListener {
            val songData: TopTrendingdata = data[position]
            seaItemCallback.onClickPlayItem(data, position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(data: List<TopTrendingdata>, position: Int) {
            val context = itemView.context
            val songImage: ShapeableImageView = itemView.findViewById(R.id.image)
            val songName: TextView = itemView.findViewById(R.id.txt_title)
            val artistName: TextView = itemView.findViewById(R.id.txt_name)
            val url: String = data[position].getImageUrl300Size()
            Glide.with(context).load(url).into(songImage)
            songName.text = data[position].title
            artistName.text = data[position].artistname
        }
    }
}