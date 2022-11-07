package com.shadhinmusiclibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.SongDetailModel

class RadioTrackAdapter : RecyclerView.Adapter<RadioTrackAdapter.RadioTrackVH>() {
    private val listRadioTrack: MutableList<SongDetailModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadioTrackVH {
        return RadioTrackVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.my_bl_sdk_music_radio_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RadioTrackVH, position: Int) {

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class RadioTrackVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}