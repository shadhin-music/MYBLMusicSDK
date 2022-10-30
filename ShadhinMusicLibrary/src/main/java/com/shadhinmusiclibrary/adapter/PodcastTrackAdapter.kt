package com.shadhinmusiclibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.PodcastOnItemClickCallback
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.podcast.SongTrackModel
import com.shadhinmusiclibrary.utils.AnyTrackDiffCB
import com.shadhinmusiclibrary.utils.UtilHelper

internal class PodcastTrackAdapter(private val itemClickCB: PodcastOnItemClickCallback) :
    RecyclerView.Adapter<PodcastTrackAdapter.PodcastTrackVH>() {
    var tracks: MutableList<IMusicModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastTrackVH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_podcast_episodes_item, parent, false)
        return PodcastTrackVH(v)
    }

    override fun onBindViewHolder(holder: PodcastTrackVH, position: Int) {
        val trackItem = tracks[position]
        holder.bindItems(trackItem)
        holder.itemView.setOnClickListener {
            itemClickCB.onClickItem(tracks, position)
        }

        if (trackItem.isPlaying) {
            holder.tvSongName?.setTextColor(
                ContextCompat.getColor(holder.context, R.color.my_sdk_color_primary)
            )
        } else {
            holder.tvSongName?.setTextColor(
                ContextCompat.getColor(
                    holder.context,
                    R.color.my_sdk_black2
                )
            )
        }
    }

    override fun getItemViewType(position: Int) = VIEW_TYPE

    override fun getItemCount(): Int {
        return tracks.size
    }

    fun setData(
        songTrack: MutableList<SongTrackModel>,
        rootPatch: HomePatchDetailModel,
        mediaId: String?
    ) {
        this.tracks = mutableListOf()
        for (songItem in songTrack) {
            tracks.add(
                UtilHelper.getTrackToRootData(songItem, rootPatch)
            )
        }
        if (mediaId != null) {
            setPlayingSong(mediaId)
        }

        notifyDataSetChanged()
    }

    fun setPlayingSong(mediaId: String) {
        val newList: List<IMusicModel> =
            UtilHelper.getCurrentRunningSongToNewSongList(mediaId, tracks)
        val callback = AnyTrackDiffCB(tracks, newList)
        val diffResult = DiffUtil.calculateDiff(callback)
        tracks.clear()
        tracks.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    inner class PodcastTrackVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.getContext()

        var tvSongName: TextView? = null
        fun bindItems(iMusicModel: IMusicModel) {
            val image: ShapeableImageView = itemView.findViewById(R.id.siv_song_icon)
            val textArtistName: TextView = itemView.findViewById(R.id.tv_song_length)
            textArtistName.text = iMusicModel.total_duration
            val url: String? = iMusicModel.imageUrl
            Glide.with(context)
                .load(UtilHelper.getImageUrlSize300(url!!))
                .into(image)
            tvSongName = itemView.findViewById(R.id.tv_song_name)
            tvSongName?.text = iMusicModel.titleName
        }
    }

    companion object {
        const val VIEW_TYPE = 2
    }
}






