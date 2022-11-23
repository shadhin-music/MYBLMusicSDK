package com.shadhinmusiclibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.view_holder.BaseViewHolder
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.SongDetailModel
import com.shadhinmusiclibrary.utils.UtilHelper

internal class RadioTrackAdapter : RecyclerView.Adapter<RadioTrackAdapter.RadioTrackVH>() {
    private var listRadioTrack: MutableList<IMusicModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadioTrackVH {
        return RadioTrackVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.my_bl_sdk_music_radio_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RadioTrackVH, position: Int) {
        holder.onBind(position)
        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return listRadioTrack.size
    }

    fun setRadioTrackData(
        data: MutableList<SongDetailModel>,
        rootPatch: HomePatchDetailModel,
        mediaId: String?
    ) {
        this.listRadioTrack = mutableListOf()
        for (songItem in data) {
            listRadioTrack.add(
                UtilHelper.getSongDetailAndRootData(
                    songItem.apply { isSeekAble = false },
                    rootPatch
                )
            )
        }

//        if (mediaId != null) {
//            setPlayingSong(mediaId)
//        }

        notifyDataSetChanged()
    }

    inner class RadioTrackVH(itemView: View) : BaseViewHolder(itemView) {

        var ivRadioPlay: ImageView? = null
        override fun onBind(position: Int) {
            val mSongDetMod = listRadioTrack[position]
            val sivRadioIcon: ShapeableImageView = itemView.findViewById(R.id.siv_radio_icon)
            val tvRadioSongName: TextView = itemView.findViewById(R.id.tv_radio_song_name)
//            val ivRadioFavorite: ImageView = itemView.findViewById(R.id.iv_radio_favorite)
            ivRadioPlay = itemView.findViewById(R.id.iv_radio_play)

            Glide.with(itemView.context)
                .load(UtilHelper.getImageUrlSize300(mSongDetMod.imageUrl!!))
                .into(sivRadioIcon)
            tvRadioSongName.text = mSongDetMod.titleName
        }
    }
}