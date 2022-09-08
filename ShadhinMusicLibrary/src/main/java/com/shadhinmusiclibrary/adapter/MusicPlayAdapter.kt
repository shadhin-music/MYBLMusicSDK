package com.shadhinmusiclibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.view_holder.BaseViewHolder
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem

class MusicPlayAdapter() : RecyclerView.Adapter<MusicPlayAdapter.MusicPlayVH>() {
    private var listMusicData: MutableList<HomePatchDetail>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicPlayVH {
        return MusicPlayVH(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.music_play_item_view, parent, false)
        )
    }

    fun setMusicData(data: MutableList<HomePatchDetail>) {
        this.listMusicData = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MusicPlayVH, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return listMusicData!!.size
    }

    inner class MusicPlayVH(itemView: View) : BaseViewHolder(itemView) {
        val cvBannerParent: CardView =
            itemView.findViewById(R.id.cv_banner_parent)
        private val ivCurrentPlayImage: ImageView =
            itemView.findViewById(R.id.iv_current_play_image)

        override fun onBind(position: Int) {
            super.onBind(position)
            val sMusicData = listMusicData!![position]

            Glide.with(itemView.context)
                .load(sMusicData.image)
                .transition(DrawableTransitionOptions().crossFade(500))
                .fitCenter()
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA))
                .placeholder(R.drawable.ic_rectangle_music)
                .error(R.drawable.ic_rectangle_music)
                .into(ivCurrentPlayImage)
        }
    }
}