package com.shadhinmusiclibrary.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.Content


class PlaylistAdapter() : RecyclerView.Adapter<PlaylistAdapter.DataAdapterViewHolder>() {
    private var dataContent: List<Content> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapterViewHolder {
        val layout = when (viewType) {
            VIEW_ALBUM -> R.layout.playlist_header
            VIEW_TRACK_ITEM -> R.layout.latest_music_view_item
            else -> throw IllegalArgumentException("Invalid view type")
        }
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)
        return DataAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataAdapterViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> holder.bindAlbum()
            1 -> holder.bindTrackItem(dataContent[position - 1])
        }
//        holder.bind(dataContent[position])
    }

    override fun getItemCount(): Int = 1 + dataContent.size

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_ALBUM
            else -> {
                VIEW_TRACK_ITEM
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Content>) {
        this.dataContent = data
        notifyDataSetChanged()
        Log.e("PLA", ": $dataContent")
    }

    inner class DataAdapterViewHolder(private val viewItem: View) :
        RecyclerView.ViewHolder(viewItem) {
        val mContext = viewItem.context
        private lateinit var ivThumbCurrentPlayItem: ImageView
        private lateinit var tvNameCurrentPlayItem: TextView
        private lateinit var ivFavorite: ImageView
        private lateinit var ivPlayBtn: ImageView
        private lateinit var ivShareBtnFab: ImageView
        fun bindAlbum() {
            ivThumbCurrentPlayItem =
                viewItem.findViewById(R.id.iv_thumb_current_play_item)
            tvNameCurrentPlayItem =
                viewItem.findViewById(R.id.tv_name_current_play_item)
            ivFavorite = viewItem.findViewById(R.id.iv_favorite)
            ivPlayBtn = viewItem.findViewById(R.id.iv_play_btn)
            ivShareBtnFab = viewItem.findViewById(R.id.iv_share_btn_fab)
        }


        fun bindTrackItem(mContent: Content) {
//            private lateinit var ivSongTypeIcon: ImageView
            val sivSongIcon: ImageView = viewItem.findViewById(R.id.siv_song_icon)
            Glide.with(mContext)
                .load(mContent.image.replace("<\$size\$>", "300"))
                .into(sivSongIcon)
            val tvSongName: TextView = viewItem.findViewById(R.id.tv_song_name)
            tvSongName.text = mContent.title

            val tvSingerName: TextView = viewItem.findViewById(R.id.tv_singer_name)
            tvSingerName.text = mContent.artist

            val tvSongLength: TextView = viewItem.findViewById(R.id.tv_song_length)
            tvSongLength.text = mContent.duration

//            ivSongTypeIcon = viewItem.findViewById(R.id.iv_song_type_icon)

            val ivSongMenuIcon: ImageView = viewItem.findViewById(R.id.iv_song_menu_icon)
            ivSongMenuIcon.setOnClickListener {
                showBottomSheetDialog(viewItem.context)
            }
        }

        private fun showBottomSheetDialog(context: Context) {
            val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
            val contentView =
                View.inflate(context, R.layout.bottomsheet_three_dot_menu_layout, null)
            bottomSheetDialog.setContentView(contentView)
            //  bottomSheetDialog.setContentView(R.layout.bottomsheet_three_dot_menu_layout)
            //bottomSheetDialog.setBackgroundColor(getResources().getColor(android.R.color.transparent))
            //contentView.setBackgroundResource(R.drawable.dialog_bg)

            //(contentView.parent as View).setBackgroundColor(context.getResources().getColor(android.R.color.transparent))
            bottomSheetDialog.show()
        }
    }

    companion object {
        const val VIEW_ALBUM = 0
        const val VIEW_TRACK_ITEM = 1
        val VIEW_BROWSE_ALL3 = 3
    }
}