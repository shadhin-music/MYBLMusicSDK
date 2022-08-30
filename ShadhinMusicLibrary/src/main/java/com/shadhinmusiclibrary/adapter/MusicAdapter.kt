package com.shadhinmusiclibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.view_holder.BaseViewHolder

/**
 * Rezaul Khan
 * https://github.com/rezaulkhan111
 **/
internal class MusicAdapter() : RecyclerView.Adapter<MusicAdapter.MusicVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicVH {
        return MusicVH(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.music_view_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MusicVH, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return 5
    }

    internal class MusicVH(itemView: View) : BaseViewHolder(itemView) {
        val cvMusicViewParent: CardView =
            itemView.findViewById(R.id.cv_music_view_parent)
        private val llMusicItemLayout: LinearLayout =
            itemView.findViewById(R.id.ll_music_item_layout)
        private val tvMusicItemTitle: TextView = itemView.findViewById(R.id.tv_music_item_title)

        override fun onBind(position: Int) {
            super.onBind(position)
        }
    }
}