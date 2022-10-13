package com.shadhinmusiclibrary.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.PodcastOnItemClickCallback
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.podcast.Track
import com.shadhinmusiclibrary.utils.UtilHelper


internal class PodcastTrackAdapter(private val itemClickCB: PodcastOnItemClickCallback) :
    RecyclerView.Adapter<PodcastTrackAdapter.PodcastTrackVH>() {
    var tracks: MutableList<Track> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastTrackVH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.podcast_episodes_item, parent, false)
        return PodcastTrackVH(v)
    }

    override fun onBindViewHolder(holder: PodcastTrackVH, position: Int) {
        holder.bindItems(position)
        holder.itemView.setOnClickListener {
            itemClickCB.onClickItem(tracks, position)
        }
    }

    override fun getItemViewType(position: Int) = VIEW_TYPE

    override fun getItemCount(): Int {
        return tracks.size
    }

    fun setData(data: MutableList<Track>, rootPatch: HomePatchDetail) {
        this.tracks =  mutableListOf()
        for (songItem in data) {
            tracks.add(
                UtilHelper.getTrackToRootData(songItem, rootPatch)
            )
        }
        notifyDataSetChanged()
    }

    inner class PodcastTrackVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.getContext()
        fun bindItems(position: Int) {

            val image: ShapeableImageView = itemView.findViewById(R.id.siv_song_icon)
            val textArtistName: TextView = itemView.findViewById(R.id.tv_song_length)
            textArtistName.text = tracks[position].Duration
            val url: String = tracks[position].ImageUrl
            Glide.with(context)
                .load(url.replace("<\$size\$>", "300"))
                .into(image)
            val textView: TextView = itemView.findViewById(R.id.tv_song_name)
            textView.text = tracks[position].Name
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






