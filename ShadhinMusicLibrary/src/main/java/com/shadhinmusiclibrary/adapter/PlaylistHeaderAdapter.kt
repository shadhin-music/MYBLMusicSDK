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
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.SongDetailModel
import com.shadhinmusiclibrary.data.model.fav.FavData
import com.shadhinmusiclibrary.fragments.fav.FavViewModel
import com.shadhinmusiclibrary.library.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.UtilHelper

internal class PlaylistHeaderAdapter(
    var homePatchDetail: HomePatchDetailModel?,
    private val itemClickCB: OnItemClickCallback,
    private val cacheRepository: CacheRepository?,
    private val favViewModel: FavViewModel
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
                UtilHelper.getSongDetailAndRootData(
                    songItem.apply { isSeekAble = true },
                    homePatchDetail
                )
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
        var ivFavorite: ImageView? = null
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
            ivFavorite = itemView.findViewById(R.id.iv_favorite)
            ivPlayBtn = itemView.findViewById(R.id.iv_play_btn)
            menu = itemView.findViewById(R.id.iv_song_menu_icon)

            var isFav = false
            val isAddedToFav = cacheRepository?.getFavoriteById(homePatchDetail?.ContentID!!)
            if (isAddedToFav?.content_Id != null) {
                ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_filled_favorite)
                isFav = true
            } else {
                ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_favorite_border)
                isFav = false
            }

            ivFavorite?.setOnClickListener {
                if (isFav.equals(true)) {
                    homePatchDetail?.ContentID?.let { it1 ->
                        favViewModel.deleteFavContent(
                            it1,
                            homePatchDetail?.ContentType
                        )
                    }
                    cacheRepository?.deleteFavoriteById(homePatchDetail?.ContentID.toString())
                    Toast.makeText(mContext, "Removed from favorite", Toast.LENGTH_LONG).show()
                    ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_favorite_border)
                    isFav = false
                } else {
                    favViewModel.addFavContent(
                        homePatchDetail?.ContentID.toString(),
                        homePatchDetail?.ContentType.toString()
                    )
                    ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_filled_favorite)
                    Log.e("TAG", "NAME123: " + isFav)
                    cacheRepository?.insertFavSingleContent(
                        FavData()
                            .apply {
                                content_Id = homePatchDetail?.ContentID.toString()
                                album_Id = homePatchDetail?.AlbumId
                                albumImage = homePatchDetail?.image
                                artistName = homePatchDetail?.Artist
                                artist_Id = homePatchDetail?.ArtistId
                                clientValue = 2
                                content_Type = homePatchDetail?.ContentType.toString()
                                fav = "1"
                                imageUrl = homePatchDetail?.image
                                playingUrl = homePatchDetail?.PlayUrl
                                rootContentId = homePatchDetail?.RootId
                                rootContentType = homePatchDetail?.RootType
                                titleName = homePatchDetail?.title
                            }
                    )
                    isFav = true
                    Toast.makeText(mContext, "Added to favorite", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        const val VIEW_TYPE = 1
    }
}