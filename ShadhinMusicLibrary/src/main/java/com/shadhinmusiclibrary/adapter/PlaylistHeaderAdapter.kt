package com.shadhinmusiclibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.OnItemClickCallback
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.SongDetailModel
import com.shadhinmusiclibrary.utils.UtilHelper

internal class PlaylistHeaderAdapter(
    var homePatchDetail: HomePatchDetailModel?,
    private val itemClickCB: OnItemClickCallback,
) : RecyclerView.Adapter<PlaylistHeaderAdapter.PlaylistHeaderVH>() {

    private var dataSongDetail: MutableList<IMusicModel> = mutableListOf()
    private var parentView: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistHeaderVH {
        parentView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_playlist_header, parent, false)
        return PlaylistHeaderVH(parentView!!)
    }

    override fun onBindViewHolder(holder: PlaylistHeaderVH, position: Int) {
        holder.bindItems(homePatchDetail)
        itemClickCB.getCurrentVH(holder, dataSongDetail)
        holder.ivPlayBtn?.setOnClickListener {
            itemClickCB.onRootClickItem(dataSongDetail, position)
        }
    }

    override fun getItemViewType(position: Int) = AlbumHeaderAdapter.VIEW_TYPE

    override fun getItemCount(): Int {
        return 1
    }

    fun setSongAndData(data: MutableList<SongDetailModel>, homePatchDetail: HomePatchDetailModel) {
        this.dataSongDetail = mutableListOf()
        for (songItem in data) {
            dataSongDetail.add(
                UtilHelper.getSongDetailAndRootData(songItem, homePatchDetail)
            )
        }
        this.homePatchDetail = homePatchDetail
        notifyDataSetChanged()
    }

    fun setData(homePatchDetail: HomePatchDetailModel) {
        this.homePatchDetail = homePatchDetail
        notifyDataSetChanged()
    }

    inner class PlaylistHeaderVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mContext = itemView.context
        private lateinit var ivThumbCurrentPlayItem: ImageView
        private lateinit var tvCurrentAlbumName: TextView
        private lateinit var tvArtistName: TextView

        //        private lateinit var ivFavorite: ImageView
        var ivPlayBtn: ImageView? = null
        var menu: ImageView? = null
        fun bindItems(homePatchDetail: HomePatchDetailModel?) {

            ivThumbCurrentPlayItem =
                itemView.findViewById(R.id.iv_thumb_current_play_item)
            Glide.with(mContext)
                .load(homePatchDetail?.getImageUrl300Size())
                .into(ivThumbCurrentPlayItem)
            tvCurrentAlbumName =
                itemView.findViewById(R.id.tv_current_album_name)
            tvCurrentAlbumName.text = homePatchDetail?.title
            if (homePatchDetail?.title.isNullOrEmpty()) {
                tvCurrentAlbumName.text = homePatchDetail?.AlbumName
            }
//            if(root.Artist.isNullOrEmpty()){
//                tvArtistName.text = rootDataContent?.AlbumName
//            }
            tvArtistName =
                itemView.findViewById(R.id.tv_artist_name)
            tvArtistName.text = homePatchDetail?.Artist

//            ivFavorite = viewItem.findViewById(R.id.iv_favorite)
            ivPlayBtn = itemView.findViewById(R.id.iv_play_btn)
            menu = itemView.findViewById(R.id.iv_song_menu_icon)
        }
    }

    companion object {
        const val VIEW_TYPE = 1
    }
}