package com.shadhinmusiclibrary.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.BottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.PodcastBottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.PodcastOnItemClickCallback
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.podcast.Track
import com.shadhinmusiclibrary.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.UtilHelper


internal class PodcastTrackAdapter(private val itemClickCB: PodcastOnItemClickCallback, private val bsDialogItemCallback: PodcastBottomSheetDialogItemCallback, val cacheRepository: CacheRepository
) :
    RecyclerView.Adapter<PodcastTrackAdapter.PodcastTrackVH>() {
    var tracks: MutableList<Track> = mutableListOf()

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
        val ivSongMenuIcon: ImageView = holder.itemView.findViewById(R.id.iv_song_menu_icon)
        ivSongMenuIcon.setOnClickListener {

            bsDialogItemCallback.onClickBottomItem(trackItem)
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
        data: MutableList<Track>,
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
        val newList: List<Track> = UtilHelper.podcastTrackNewList(mediaId, tracks)
        val callback = PodcastTrackDiffCB(tracks, newList)
        val diffResult = DiffUtil.calculateDiff(callback)
        tracks.clear()
        tracks.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    private class PodcastTrackDiffCB() : DiffUtil.Callback() {
        private lateinit var oldSongDetails: List<Track>
        private lateinit var newSongDetails: List<Track>

        constructor(oldSongDetails: List<Track>, newSongDetails: List<Track>) : this() {
            this.oldSongDetails = oldSongDetails
            this.newSongDetails = newSongDetails
        }

        override fun getOldListSize(): Int = oldSongDetails.size

        override fun getNewListSize(): Int = newSongDetails.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldSongDetails[oldItemPosition].Id == newSongDetails[newItemPosition].Id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldSongDetails[oldItemPosition].isPlaying == newSongDetails[newItemPosition].isPlaying
    }

    inner class PodcastTrackVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.getContext()

        var tvSongName: TextView? = null
        fun bindItems(position: Int) {
            val image: ShapeableImageView = itemView.findViewById(R.id.siv_song_icon)
            val textTime: TextView = itemView.findViewById(R.id.tv_song_length)
            val textSingerName: TextView = itemView.findViewById(R.id.tv_singer_name)
            textSingerName.visibility = GONE
            textTime.text = tracks[position].Duration
            val url: String = tracks[position].ImageUrl
            Glide.with(context)
                .load(url.replace("<\$size\$>", "300"))
                .into(image)
            tvSongName = itemView.findViewById(R.id.tv_song_name)
            tvSongName?.text = tracks[position].Name
            val progressIndicator: CircularProgressIndicator = itemView.findViewById(R.id.progress)
            val downloaded:ImageView = itemView.findViewById(R.id.iv_song_type_icon)
            progressIndicator.tag = tracks[position].EpisodeId
            progressIndicator.visibility = View.GONE
            downloaded.visibility = View.GONE
            val isDownloaded = cacheRepository.isTrackDownloaded(tracks[position].EpisodeId) ?: false

            if(isDownloaded){
                Log.e("TAG","ISDOWNLOADED: "+ isDownloaded)
                downloaded.visibility = View.VISIBLE
                progressIndicator.visibility = View.GONE
            }
        }
    }

    companion object {
        const val VIEW_TYPE = 2
    }
}






