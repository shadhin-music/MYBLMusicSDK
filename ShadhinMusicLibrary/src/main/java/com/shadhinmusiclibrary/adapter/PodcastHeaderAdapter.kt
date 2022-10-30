package com.shadhinmusiclibrary.adapter

import android.annotation.SuppressLint
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.PodcastOnItemClickCallback
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.podcast.EpisodeModel
import com.shadhinmusiclibrary.data.model.podcast.SongTrackModel
import com.shadhinmusiclibrary.utils.ExpandableTextView
import com.shadhinmusiclibrary.utils.UtilHelper

internal class PodcastHeaderAdapter(
    private val pcOnCallback: PodcastOnItemClickCallback
) : RecyclerView.Adapter<PodcastHeaderAdapter.PodcastHeaderVH>() {
    var episode: List<EpisodeModel>? = null
    private var listSongTrack: MutableList<IMusicModel> = mutableListOf()
    private var parentView: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastHeaderVH {
        parentView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_podcast_header_layout, parent, false)
        return PodcastHeaderVH(parentView!!)
    }

    override fun onBindViewHolder(holder: PodcastHeaderVH, position: Int) {
        holder.bindItems(position)
        pcOnCallback.getCurrentVH(holder, listSongTrack)
        holder.ivPlayBtn?.setOnClickListener {
            pcOnCallback.onRootClickItem(listSongTrack, position)
        }
    }

    override fun getItemViewType(position: Int) = VIEW_TYPE

    override fun getItemCount(): Int {
        return 1
    }

    fun setTrackData(
        episode: List<EpisodeModel>,
        data: MutableList<SongTrackModel>,
        rootPatch: HomePatchDetailModel
    ) {
        this.listSongTrack = mutableListOf()
        for (songItem in data) {
            listSongTrack.add(
                UtilHelper.getTrackToRootData(songItem, rootPatch)
            )
        }
        this.episode = episode
        this.listSongTrack = listSongTrack
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
//    fun setHeader(episode: List<Episode>, trackList: MutableList<Track>) {
//        this.episode = episode
//        listTrack = trackList
//        notifyDataSetChanged()
//    }

    inner class PodcastHeaderVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context
        var ivPlayBtn: ImageView? = null
        fun bindItems(position: Int) {
            val imageView: ImageView = itemView.findViewById(R.id.thumb)
            val textArtist: TextView? = itemView.findViewById(R.id.name)
            ivPlayBtn = itemView.findViewById(R.id.iv_play_btn)
            val url: String? = episode?.get(0)?.ImageUrl
            val details: String = episode?.get(position)?.Details.toString()
            val result = Html.fromHtml(details).toString()
//            if(textArtist?.text.isNullOrEmpty())
            // Log.e("TAG","Name :"+episode?.get(position))
            textArtist?.text = episode?.get(position)?.Name.toString()
            val textView: ExpandableTextView? = itemView.findViewById(R.id.tvDescription)
            val moreText: TextView? = itemView.findViewById(R.id.tvReadMore)
            textView?.setText(result)
            moreText?.setOnClickListener {
                if (textView!!.isExpanded) {
                    textView.collapse()
                    moreText.text = "Read More"
                } else {
                    textView.expand()
                    moreText.text = "Less"
                }
            }
//            val tvName: TextView = itemView?.findViewById(R.id.tvName)!!
//            tvName.text = argHomePatchDetail.Artist + "'s"
            // val imageArtist: ImageView = itemView!!.findViewById(R.id.imageArtist)
            Glide.with(context)
                .load(url?.replace("<\$size\$>", "300"))
                .into(imageView)


        }
    }

    companion object {
        const val VIEW_TYPE = 1
    }
}