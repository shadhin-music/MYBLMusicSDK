package com.shadhinmusiclibrary.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.OnItemClickCallback
import com.shadhinmusiclibrary.callBackService.favItemClickCallback
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.model.fav.FavData
import com.shadhinmusiclibrary.fragments.fav.FavViewModel
import com.shadhinmusiclibrary.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.UtilHelper

internal class AlbumHeaderAdapter(
    var homePatchDetail: HomePatchDetail?,
    private val itemClickCB: OnItemClickCallback
) :
    RecyclerView.Adapter<AlbumHeaderAdapter.HeaderViewHolder>() {
    var cacheRepository:CacheRepository?=null
    private var favViewModel:FavViewModel?=null
    private var dataSongDetail: MutableList<SongDetail> = mutableListOf()
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
        data: MutableList<SongDetail>,
        homePatchDetail: HomePatchDetail,
        cacheRepository: CacheRepository,
        favViewModel: FavViewModel
    ) {
        this.dataSongDetail = mutableListOf()
        for (songItem in data) {
            dataSongDetail.add(
                UtilHelper.getSongDetailAndRootData(songItem, homePatchDetail)
            )
        }
        this.homePatchDetail = homePatchDetail
        this.cacheRepository =cacheRepository
        this.favViewModel = favViewModel
        notifyDataSetChanged()
    }

    fun setData(homePatchDetail: HomePatchDetail) {
        this.homePatchDetail = homePatchDetail
        notifyDataSetChanged()
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mContext = itemView.context
        private lateinit var ivThumbCurrentPlayItem: ImageView
        private lateinit var tvCurrentAlbumName: TextView
        private lateinit var tvArtistName: TextView

       var ivFavorite: ImageView ?= null
        var ivPlayBtn: ImageView? = null
        var menu: ImageView? = null
        fun bindItems(homePatchDetail: HomePatchDetail?,cacheRepository: CacheRepository,favViewModel: FavViewModel) {

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

            ivFavorite = itemView.findViewById(R.id.iv_favorite)
            ivPlayBtn = itemView.findViewById(R.id.iv_play_btn)
            menu = itemView.findViewById(R.id.iv_song_menu_icon)
            var isFav = false
            val isAddedToFav = cacheRepository.getFavoriteById(homePatchDetail?.ContentID!!)
            if (isAddedToFav?.contentID != null) {

           ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_filled_favorite)
                isFav = true

            } else {

           ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_favorite_border)
                isFav = false
            }

      ivFavorite?.setOnClickListener {
                if(isFav.equals(true)){
                    favViewModel.deleteFavContent(homePatchDetail?.ContentID,homePatchDetail.ContentType)
                    cacheRepository.deleteFavoriteById(homePatchDetail.ContentID)
                    Toast.makeText(mContext,"Removed from favorite", Toast.LENGTH_LONG).show()
                    ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_favorite_border)
                    isFav=false
                    Log.e("TAG","NAME: "+ isFav)
                } else {

                    favViewModel.addFavContent(homePatchDetail.ContentID,homePatchDetail.ContentType)

                    ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_filled_favorite)
                     Log.e("TAG","NAME123: "+ isFav)
                    cacheRepository.insertFavSingleContent(FavData(homePatchDetail.ContentID,homePatchDetail.AlbumId,homePatchDetail.image,"",homePatchDetail.Artist,homePatchDetail.ArtistId,
                        "","",2,homePatchDetail.ContentType,"","","1","",homePatchDetail.image,"",
                        false,  "",0,"","","",homePatchDetail.PlayUrl,homePatchDetail.RootId,
                        homePatchDetail.RootType,false,"",homePatchDetail.title,"",""

                    ))
                    isFav = true
                    Toast.makeText(mContext,"Added to favorite", Toast.LENGTH_LONG).show()
                }
                // favClickCallback.favItemClick(songDetail)
            }
        }
    }

    companion object {
        const val VIEW_TYPE = 1
    }
}