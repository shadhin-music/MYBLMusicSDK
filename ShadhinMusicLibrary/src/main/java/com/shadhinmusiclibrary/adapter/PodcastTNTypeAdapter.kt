package com.shadhinmusiclibrary.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.PodcastTrackCallback
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.FeaturedPodcastDataModel
import com.shadhinmusiclibrary.data.model.HomePatchItemModel
import com.shadhinmusiclibrary.utils.UtilHelper


internal class PodcastTNTypeAdapter(
    var homePatchItem: FeaturedPodcastDataModel
) : RecyclerView.Adapter<PodcastTNTypeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_pod_child_item_trending_now, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems()
        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return homePatchItem.Data.size ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mContext = itemView.context
        fun bindItems() {
            val textViewName = itemView.findViewById(R.id.show_title) as TextView
            val textViewSubtitle = itemView.findViewById(R.id.sub_title) as TextView
            val show_des = itemView.findViewById(R.id.show_des) as TextView
            val txt_time =itemView.findViewById(R.id.txt_time) as TextView
            val imageView2 = itemView.findViewById(R.id.image) as ImageView

            Glide.with(mContext)
                .load(
                    UtilHelper.getImageUrlSize300(
                        homePatchItem.Data[absoluteAdapterPosition].ImageUrl ?: ""
                    )
                )
                .into(imageView2)
            val result = Html.fromHtml(homePatchItem.Data[absoluteAdapterPosition].About).toString()
            textViewName.text = homePatchItem.Data[absoluteAdapterPosition].TrackName
            txt_time.text = homePatchItem.Data[absoluteAdapterPosition].Duration
            textViewSubtitle.text =homePatchItem.Data[absoluteAdapterPosition].Presenter
            show_des.text = result
        }
    }
}