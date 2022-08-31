package com.shadhinmusiclibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.view_holder.BaseViewHolder
/**
 * Rezaul Khan
 * https://github.com/rezaulkhan111
 **/
internal class TrendingPodcastAdapter() :
    RecyclerView.Adapter<TrendingPodcastAdapter.TrendingPodcastVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingPodcastVH {
        return TrendingPodcastVH(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.mid_audio_view_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TrendingPodcastVH, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return 0
    }

    internal class TrendingPodcastVH(itemView: View) : BaseViewHolder(itemView) {
        val llVideoViewParent: LinearLayout = itemView.findViewById(R.id.ll_audio_view_parent)
        private val cvAudioCard: CardView = itemView.findViewById(R.id.cv_audio_card)
        private val sivThumbnailImage: ShapeableImageView =
            itemView.findViewById(R.id.siv_thumbnail_image)
        private val tvAudioTitle: TextView = itemView.findViewById(R.id.tv_audio_title)
        private val tvAudioLength: TextView = itemView.findViewById(R.id.tv_audio_length)

        override fun onBind(position: Int) {
            super.onBind(position)
        }
    }
}