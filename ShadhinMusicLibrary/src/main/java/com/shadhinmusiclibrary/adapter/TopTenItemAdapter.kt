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
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.SongDetailModel
import com.shadhinmusiclibrary.utils.UtilHelper


internal class TopTenItemAdapter(
    private val seaItemCallback: SearchItemCallBack
) : RecyclerView.Adapter<TopTenItemAdapter.TopTenItemVH>() {
    private val topTenDataItems: MutableList<IMusicModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopTenItemVH {
        return TopTenItemVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.my_bl_sdk_layout_top_ten_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TopTenItemVH, position: Int) {
        val toTenDataItem = topTenDataItems[position]
        holder.bindItems(toTenDataItem, position)
        holder.itemView.setOnClickListener {
            seaItemCallback.onClickPlayItem(topTenDataItems, position)
        }
    }

    fun setData(
        data: MutableList<IMusicModel>,
        rootPatch: HomePatchDetailModel
    ) {
        for (songItem in data) {
            topTenDataItems.add(
                UtilHelper.getMixdUpIMusicWithRootData(
                    songItem.apply {
                        isSeekAble = true
                    }, rootPatch
                )
            )
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return topTenDataItems.size
    }

    internal class TopTenItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(iMusItem: IMusicModel, position: Int) {
            val context = itemView.context
            val songImage: ShapeableImageView = itemView.findViewById(R.id.image)
            val songName: TextView = itemView.findViewById(R.id.txt_title)
            val artistName: TextView = itemView.findViewById(R.id.txt_name)
            Glide.with(context)
                .load(UtilHelper.getImageUrlSize300(iMusItem.imageUrl!!))
                .into(songImage)
            songName.text = iMusItem.titleName
            artistName.text = iMusItem.artistName
        }
    }
}