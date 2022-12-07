package com.shadhinmusiclibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.CommonPlayControlCallback
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.SongDetailModel
import com.shadhinmusiclibrary.data.model.fav.FavDataModel
import com.shadhinmusiclibrary.fragments.fav.FavViewModel
import com.shadhinmusiclibrary.library.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.UtilHelper

internal class AlbumHeaderAdapter(
    var homePatchDetail: HomePatchDetailModel?,
    private val itemClickCB: CommonPlayControlCallback,
) : RecyclerView.Adapter<AlbumHeaderAdapter.HeaderViewHolder>() {

    var cacheRepository: CacheRepository? = null
    private var favViewModel: FavViewModel? = null
    private var dataSongDetail: MutableList<IMusicModel> = mutableListOf()
    private var parentView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        parentView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_playlist_header, parent, false)
        return HeaderViewHolder(parentView!!)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        if (cacheRepository != null) {
            favViewModel?.let { holder.bindItems(homePatchDetail, cacheRepository!!, it) }
        }

        itemClickCB.getCurrentVH(holder, dataSongDetail)
        holder.ivPlayBtn?.setOnClickListener {
            itemClickCB.onRootClickItem(dataSongDetail, position)
        }
    }

    override fun getItemViewType(position: Int) = VIEW_TYPE

    override fun getItemCount(): Int {
        return 1
    }

    fun setSongAndData(
        data: MutableList<SongDetailModel>,
        homePatchDetail: HomePatchDetailModel,
        cacheRepository: CacheRepository,
        favViewModel: FavViewModel
    ) {
        this.dataSongDetail = mutableListOf()
        for (songItem in data) {
            dataSongDetail.add(
                UtilHelper.getMixdUpIMusicWithRootData(
                    songItem.apply {
                        isSeekAble = true
                    }, homePatchDetail
                )
            )
        }
        this.homePatchDetail = homePatchDetail
        this.cacheRepository = cacheRepository
        this.favViewModel = favViewModel

        notifyDataSetChanged()
    }

    fun setData(homePatchDetail: HomePatchDetailModel) {
        this.homePatchDetail = homePatchDetail
        notifyDataSetChanged()
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mContext = itemView.context
        private lateinit var ivThumbCurrentPlayItem: ImageView
        private lateinit var tvCurrentAlbumName: TextView
        private lateinit var tvArtistName: TextView

        var ivFavorite: ImageView? = null
        var ivPlayBtn: ImageView? = null
        var menu: ImageView? = null
        fun bindItems(
            homePatchDetail: HomePatchDetailModel?,
            cacheRepository: CacheRepository,
            favViewModel: FavViewModel
        ) {

            ivThumbCurrentPlayItem =
                itemView.findViewById(R.id.iv_thumb_current_play_item)
            Glide.with(mContext)
                .load(UtilHelper.getImageUrlSize300(homePatchDetail?.imageUrl!!))
                .into(ivThumbCurrentPlayItem)
            tvCurrentAlbumName =
                itemView.findViewById(R.id.tv_current_album_name)
            tvCurrentAlbumName.text = homePatchDetail.titleName ?: ""
            if (homePatchDetail.titleName.isNullOrEmpty()) {
                tvCurrentAlbumName.text = homePatchDetail.album_Name ?: ""
            }
//            if(root.Artist.isNullOrEmpty()){
//                tvArtistName.text = rootDataContent?.AlbumName
//            }
            tvArtistName =
                itemView.findViewById(R.id.tv_artist_name)
            tvArtistName.text = homePatchDetail.artistName

            ivFavorite = itemView.findViewById(R.id.iv_favorite)
            ivPlayBtn = itemView.findViewById(R.id.iv_play_btn)
            menu = itemView.findViewById(R.id.iv_song_menu_icon)
            var isFav = false
            val isAddedToFav = cacheRepository.getFavoriteById(homePatchDetail.content_Id)
            if (isAddedToFav?.content_Id != null) {
                ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_filled_favorite)
                isFav = true
            } else {
                ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_favorite_border)
                isFav = false
            }

            ivFavorite?.setOnClickListener {
                if (isFav.equals(true)) {
                    favViewModel.deleteFavContent(
                        homePatchDetail.content_Id,
                        homePatchDetail.content_Type ?: ""
                    )
                    cacheRepository.deleteFavoriteById(homePatchDetail.content_Id)
                    Toast.makeText(mContext, "Removed from favorite", Toast.LENGTH_LONG).show()
                    ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_favorite_border)
                    isFav = false
                } else {
                    favViewModel.addFavContent(
                        homePatchDetail.content_Id,
                        homePatchDetail.content_Type ?: ""
                    )
                    ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_filled_favorite)
                    cacheRepository.insertFavSingleContent(
                        FavDataModel().apply {
                            content_Id = homePatchDetail.content_Id
                            album_Id = homePatchDetail.album_Id
                            albumImage = homePatchDetail.imageUrl
                            artistName = homePatchDetail.artistName
                            artist_Id = homePatchDetail.artist_Id
                            clientValue = 2
                            content_Type = homePatchDetail.content_Type
                            fav = "1"
                            imageUrl = homePatchDetail.imageUrl
                            playingUrl = homePatchDetail.playingUrl
                            rootContentId = homePatchDetail.rootContentId
                            rootContentType = homePatchDetail.rootContentType
                            titleName = homePatchDetail.titleName
                        }
                    )
                    isFav = true
                    Toast.makeText(mContext, "Added to favorite", Toast.LENGTH_LONG).show()
                }
                // favClickCallback.favItemClick(songDetail)
            }
        }
    }

    companion object {
        const val VIEW_TYPE = 1
    }
}