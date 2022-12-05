package com.shadhinmusiclibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.FeaturedPodcastDataModel
import com.shadhinmusiclibrary.data.model.HomePatchItemModel
import com.shadhinmusiclibrary.utils.CircleImageView
import com.shadhinmusiclibrary.utils.UtilHelper

internal class PodcastPPTypeAdapter(
    var homePatchItem: FeaturedPodcastDataModel
) : RecyclerView.Adapter<PodcastPPTypeAdapter.ViewHolder>() {

    private var filteredHomePatchItem: HomePatchItemModel? = null

//    init {
//        initialize()
//    }

    fun initialize() {
        var items = homePatchItem?.Data
//        items = items?.filter { it. != artistIDtoSkip }
//        filteredHomePatchItem = homePatchItem?.copy()
//        if (items != null) {
//            filteredHomePatchItem?.Data = items
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_podcast_pp_type_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems()
        holder.itemView.setOnClickListener {
         //  val artistID = filteredHomePatchItem!!.Data[position].artist_Id
            //val index = homePatchItem!!.Data.indexOfFirst { it.artist_Id == artistID }
           // homeCallBack.onClickItemAndAllItem(index, homePatchItem!!)
        }
    }

    override fun getItemCount(): Int {
        return homePatchItem.Data.size ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mContext = itemView.context
        fun bindItems() {
            val textViewName = itemView.findViewById(R.id.txt_name) as TextView
            val imageView2 = itemView.findViewById(R.id.image) as CircleImageView

            Glide.with(mContext)
                .load(
                    UtilHelper.getImageUrlSize300(
                        homePatchItem!!.Data[absoluteAdapterPosition].ImageUrl ?: ""
                    )
                )
                .into(imageView2)
            textViewName.text = homePatchItem!!.Data[absoluteAdapterPosition].ShowName
        }
    }
}