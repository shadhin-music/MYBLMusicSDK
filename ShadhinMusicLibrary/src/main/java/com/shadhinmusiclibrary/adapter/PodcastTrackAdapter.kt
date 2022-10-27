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
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.podcast.SongTrack
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
        holder.bindItems(position)
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
        data: MutableList<SongTrack>,
        rootPatch: HomePatchDetail,
        mediaId: String?
    ) {
        this.tracks = mutableListOf()
        for (songItem in data) {
            tracks.add(
                UtilHelper.getTrackToRootData(songItem, rootPatch)
            )
        }
        notifyDataSetChanged()

        if (mediaId != null) {
            setPlayingSong(mediaId)
        }
    }

    fun setPlayingSong(mediaId: String) {
        val newList: List<IMusicModel> =
            UtilHelper.getCurrentRunningSongToNewSongList(mediaId, tracks)
        val callback = AnyTrackDiffCB(tracks, newList)
        val diffResult = DiffUtil.calculateDiff(callback)
        tracks.clear()
        tracks.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

//    private class PodcastTrackDiffCB() : DiffUtil.Callback() {
//        private lateinit var oldSongDetails: List<IMusicModel>
//        private lateinit var newSongDetails: List<IMusicModel>
//
//        constructor(oldSongDetails: List<IMusicModel>, newSongDetails: List<IMusicModel>) : this() {
//            this.oldSongDetails = oldSongDetails
//            this.newSongDetails = newSongDetails
//        }
//
//        override fun getOldListSize(): Int = oldSongDetails.size
//
//        override fun getNewListSize(): Int = newSongDetails.size
//
//        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
////            oldSongDetails[oldItemPosition].Id == newSongDetails[newItemPosition].Id
//            oldSongDetails[oldItemPosition].content_Id == newSongDetails[newItemPosition].content_Id
//
//        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
//            oldSongDetails[oldItemPosition].isPlaying == newSongDetails[newItemPosition].isPlaying
//    }

    inner class PodcastTrackVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.getContext()

        var tvSongName: TextView? = null
        fun bindItems(position: Int) {
            val image: ShapeableImageView = itemView.findViewById(R.id.siv_song_icon)
            val textArtistName: TextView = itemView.findViewById(R.id.tv_song_length)
            textArtistName.text = tracks[position].total_duration
            val url: String = tracks[position].imageUrl!!
            Glide.with(context)
                .load(UtilHelper.getImageUrlSize300(url))
                .into(image)
            tvSongName = itemView.findViewById(R.id.tv_song_name)
            tvSongName?.text = tracks[position].titleName
//            val linearLayout: LinearLayout = itemView.findViewById(R.id.linear)
//            entityId = banner.entityIdo
            //getActorName(entityId!!)

//            //textViewName.setText(banner.name)
//            textViewName.text = LOADING_TXT
//            textViewName.tag = banner.entityId
        }
    }

    companion object {
        const val VIEW_TYPE = 2
    }
}






