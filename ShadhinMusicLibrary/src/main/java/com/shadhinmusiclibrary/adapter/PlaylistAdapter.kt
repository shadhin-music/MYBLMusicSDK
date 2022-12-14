package com.shadhinmusiclibrary.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.OnItemClickCallback
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.utils.TimeParser
import com.shadhinmusiclibrary.utils.UtilHelper

class PlaylistAdapter(private val itemClickCB: OnItemClickCallback) :
    RecyclerView.Adapter<PlaylistAdapter.PlaylistVH>() {
    private var rootDataContent: HomePatchDetail? = null
    private var dataSongDetail: MutableList<SongDetail> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistVH {
        val layout = when (viewType) {
            VIEW_ALBUM -> R.layout.playlist_header
            VIEW_TRACK_ITEM -> R.layout.latest_music_view_item
            else -> throw IllegalArgumentException("Invalid view type")
        }
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)
        return PlaylistVH(view)
    }

    override fun onBindViewHolder(holder: PlaylistVH, position: Int) {
        when (holder.itemViewType) {
            0 -> holder.bindRoot(rootDataContent!!)
            1 -> holder.bindTrackItem(dataSongDetail[position - 1])
        }

        holder.itemView.setOnClickListener {
            if (holder.itemViewType == VIEW_TRACK_ITEM) {
                val mSongDetItem = dataSongDetail[position]

            }
        }

        if (dataSongDetail.isNotEmpty()) {
            // val mSongDetItem = dataSongDetail[position-1]
            when (holder.itemViewType) {
                0 -> {
                    itemClickCB.getCurrentVH(holder, dataSongDetail)
                    holder.ivPlayBtn?.setOnClickListener {
                        /*
                        if player.state == playing && player.rootid == this.rootId{
                            player.pause
                        }else if player.state = paused && player.rootid == this.rootId{
                            player.play
                        }else{
                            this.startPlaying(trackArray, index = 0)
                        }
                         */
                        itemClickCB.onClickItem(dataSongDetail, position)
                    }
                }
                1 -> {
//                    itemClickCB.getCurrentVH(holder, dataSongDetail)
                    holder.itemView.setOnClickListener {
                        if (holder.itemViewType == PlaylistAdapter.VIEW_TRACK_ITEM) {
                            val mSongDetItem = dataSongDetail[position - 1]
                            itemClickCB.onClickItem(dataSongDetail, (position - 1))
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        var count = dataSongDetail.size
        if (rootDataContent != null) {
            count += 1
        }
        return count
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_ALBUM
            else -> {
                VIEW_TRACK_ITEM
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSongData(data: MutableList<SongDetail>) {
        this.dataSongDetail = data
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setRootData(data: HomePatchDetail?) {
        this.rootDataContent = data
        notifyDataSetChanged()
    }

    inner class PlaylistVH(private val viewItem: View) :
        RecyclerView.ViewHolder(viewItem) {
        val mContext = viewItem.context
        private lateinit var ivThumbCurrentPlayItem: ImageView
        private lateinit var tvCurrentAlbumName: TextView
        private lateinit var tvArtistName: TextView

        //        private lateinit var ivFavorite: ImageView
        var ivPlayBtn: ImageView? = null

        //        private lateinit var ivShareBtnFab: ImageView
        fun bindRoot(root: HomePatchDetail) {
            ivThumbCurrentPlayItem =
                viewItem.findViewById(R.id.iv_thumb_current_play_item)
            Glide.with(mContext)
                .load(UtilHelper.getImageUrlSize300(root.image))
                .into(ivThumbCurrentPlayItem)
            tvCurrentAlbumName =
                viewItem.findViewById(R.id.tv_current_album_name)
            tvCurrentAlbumName.text = root.title

            tvArtistName =
                viewItem.findViewById(R.id.tv_artist_name)
            tvArtistName.text = root.Artist

//            ivFavorite = viewItem.findViewById(R.id.iv_favorite)
            ivPlayBtn = viewItem.findViewById(R.id.iv_play_btn)
//            ivShareBtnFab = viewItem.findViewById(R.id.iv_share_btn_fab)
        }

        var tvSongName: TextView? = null
        fun bindTrackItem(mSongDetail: SongDetail) {
            val sivSongIcon: ImageView = viewItem.findViewById(R.id.siv_song_icon)
            Glide.with(mContext)
                .load(mSongDetail.getImageUrl300Size())
                .into(sivSongIcon)
            tvSongName = viewItem.findViewById(R.id.tv_song_name)
            tvSongName!!.text = mSongDetail.title

            val tvSingerName: TextView = viewItem.findViewById(R.id.tv_singer_name)
            tvSingerName.text = mSongDetail.artist

            val tvSongLength: TextView = viewItem.findViewById(R.id.tv_song_length)
            tvSongLength.text = TimeParser.secToMin(mSongDetail.duration)
            val ivSongMenuIcon: ImageView = viewItem.findViewById(R.id.iv_song_menu_icon)
            ivSongMenuIcon.setOnClickListener {
//                showBottomSheetDialog(viewItem.context)
            }
        }
    }

    private companion object {
        const val VIEW_ALBUM = 0
        const val VIEW_TRACK_ITEM = 1
    }
}