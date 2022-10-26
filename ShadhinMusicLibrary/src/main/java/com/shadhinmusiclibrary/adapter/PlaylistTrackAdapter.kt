package com.shadhinmusiclibrary.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.BottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.OnItemClickCallback
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.TimeParser
import com.shadhinmusiclibrary.utils.UtilHelper

internal class PlaylistTrackAdapter(
    private val itemClickCB: OnItemClickCallback, private val bsDialogItemCallback: BottomSheetDialogItemCallback,
    val cacheRepository: CacheRepository
) : RecyclerView.Adapter<PlaylistTrackAdapter.PlaylistTrackVH>() {
    var dataSongDetail: MutableList<SongDetail> = mutableListOf()
    private var parentView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistTrackVH {
//        val v = LayoutInflater.from(parent.context)
        parentView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_latest_music_view_item, parent, false)
        return PlaylistTrackVH(parentView!!)
    }

    override fun onBindViewHolder(holder: PlaylistTrackVH, position: Int) {
        val mSongDetails = dataSongDetail[position]
        holder.bindTrackItem(mSongDetails)
        holder.itemView.setOnClickListener {
            itemClickCB.onClickItem(dataSongDetail, position)
        }

        val ivSongMenuIcon: ImageView = holder.itemView.findViewById(R.id.iv_song_menu_icon)
        ivSongMenuIcon.setOnClickListener {
            val songDetail = dataSongDetail[position]
            bsDialogItemCallback.onClickBottomItem(songDetail)
        }

        if (mSongDetails.isPlaying) {
            holder.tvSongName?.setTextColor(
                ContextCompat.getColor(holder.mContext, R.color.my_sdk_color_primary)
            )
        } else {
            holder.tvSongName?.setTextColor(
                ContextCompat.getColor(
                    holder.mContext,
                    R.color.my_sdk_black2
                )
            )
        }
    }

    override fun getItemViewType(position: Int) = VIEW_TYPE

    override fun getItemCount(): Int {
        return dataSongDetail.size
    }

    fun setData(data: MutableList<SongDetail>, rootPatch: HomePatchDetail, mediaId: String?) {
        this.dataSongDetail = mutableListOf()
        for (songItem in data) {
            dataSongDetail.add(
                UtilHelper.getSongDetailAndRootData(songItem, rootPatch)
            )
        }
        notifyDataSetChanged()

        if (mediaId != null) {
            setPlayingSong(mediaId)
        }
    }

    fun setPlayingSong(mediaId: String) {
        val newList: List<SongDetail> = UtilHelper.albumSongDetailsNewList(mediaId, dataSongDetail)
        val callback = PlaylistTrackDiffCB(dataSongDetail, newList)
        val diffResult = DiffUtil.calculateDiff(callback)
        dataSongDetail.clear()
        dataSongDetail.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    private class PlaylistTrackDiffCB() : DiffUtil.Callback() {
        private lateinit var oldSongDetails: List<SongDetail>
        private lateinit var newSongDetails: List<SongDetail>

        constructor(oldSongDetails: List<SongDetail>, newSongDetails: List<SongDetail>) : this() {
            this.oldSongDetails = oldSongDetails
            this.newSongDetails = newSongDetails
        }

        override fun getOldListSize(): Int = oldSongDetails.size

        override fun getNewListSize(): Int = newSongDetails.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldSongDetails[oldItemPosition].ContentID == newSongDetails[newItemPosition].ContentID

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldSongDetails[oldItemPosition].isPlaying == newSongDetails[newItemPosition].isPlaying
    }

    inner class PlaylistTrackVH(private val viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        val mContext = itemView.getContext()
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
            val progressIndicator: CircularProgressIndicator = itemView.findViewById(R.id.progress)
            val downloaded:ImageView = itemView.findViewById(R.id.iv_song_type_icon)
            progressIndicator.tag = mSongDetail.ContentID
            progressIndicator.visibility = View.GONE
            downloaded.visibility = View.GONE
            val isDownloaded = cacheRepository.isTrackDownloaded(mSongDetail.ContentID) ?: false

            if(isDownloaded){
                Log.e("TAG","ISDOWNLOADED: "+ isDownloaded)
                downloaded.visibility = View.VISIBLE
                progressIndicator.visibility = View.GONE
            }
            ivSongMenuIcon.setOnClickListener {
//                bsDialogItemCallback.onClickBottomItem(mSongDetail)
//                Log.e("TAGGY", "ID: " + mSongDetail)
            }
        }
    }

    companion object {
        const val VIEW_TYPE = 2
    }
}