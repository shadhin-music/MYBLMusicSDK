package com.shadhinmusiclibrary.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.OnItemClickCallback
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.SongDetailModel
import com.shadhinmusiclibrary.data.model.fav.FavData
import com.shadhinmusiclibrary.fragments.create_playlist.UserSongsPlaylistDataModel
import com.shadhinmusiclibrary.fragments.fav.FavViewModel
import com.shadhinmusiclibrary.library.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.UtilHelper
import kotlin.random.Random

internal class UserCreatedPlaylistHeaderAdapter(
    var homePatchDetail: HomePatchDetailModel?,
    var playlistName: String?,
    private val itemClickCB: OnItemClickCallback, private val cacheRepository: CacheRepository?,
    private val favViewModel: FavViewModel,
    val gradientDrawable: Int
) : RecyclerView.Adapter<UserCreatedPlaylistHeaderAdapter.UserCreatedPlaylistHeaderVH>() {

    private var dataSongDetail: MutableList<SongDetail> = mutableListOf()
    private var parentView: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCreatedPlaylistHeaderVH {
        parentView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_playlist_header, parent, false)
        return UserCreatedPlaylistHeaderVH(parentView!!)
    }

    override fun onBindViewHolder(holder: UserCreatedPlaylistHeaderVH, position: Int) {
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

    fun setSongAndData(data: List<UserSongsPlaylistDataModel>?) {
        this.dataSongDetail = mutableListOf()
        for (songItem in data!!) {
            dataSongDetail.add(
                UtilHelper.getSongDetailAndRootDataForUSERPLAYLIST(songItem)
            )
        }

        //this.homePatchDetail = homePatchDetail
        notifyDataSetChanged()
    }

//    fun setData(homePatchDetail: HomePatchDetail) {
//        this.homePatchDetail = homePatchDetail
//        notifyDataSetChanged()
//    }

    inner class UserCreatedPlaylistHeaderVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mContext = itemView.context
        private lateinit var ivThumbCurrentPlayItem: ImageView
        private lateinit var tvCurrentAlbumName: TextView
        private lateinit var tvArtistName: TextView

              private lateinit var ivFavorite: ImageView
        var ivPlayBtn: ImageView? = null
        var menu: ImageView? = null
        fun bindItems(homePatchDetail: HomePatchDetail?) {

            ivThumbCurrentPlayItem =
                itemView.findViewById(R.id.iv_thumb_current_play_item)

           ivThumbCurrentPlayItem.setImageResource(gradientDrawable)

            ivThumbCurrentPlayItem.setBackgroundResource(R.drawable.my_bl_sdk_playlist_bg)
            tvCurrentAlbumName =
                itemView.findViewById(R.id.tv_current_album_name)
            tvCurrentAlbumName.text = playlistName
            tvArtistName =
                itemView.findViewById(R.id.tv_artist_name)
            tvArtistName.text = dataSongDetail.size.toString() +" Songs"

            ivFavorite = itemView.findViewById(R.id.iv_favorite)
            ivFavorite.visibility = GONE
            ivPlayBtn = itemView.findViewById(R.id.iv_play_btn)
            menu = itemView.findViewById(R.id.iv_song_menu_icon)
            var isFav = false
            val isAddedToFav = cacheRepository?.getFavoriteById(homePatchDetail?.ContentID.toString())
            if (isAddedToFav?.contentID != null) {

                ivFavorite.setImageResource(R.drawable.my_bl_sdk_ic_filled_favorite)
                isFav = true

            } else {

                ivFavorite.setImageResource(R.drawable.my_bl_sdk_ic_favorite_border)
                isFav = false
            }

            ivFavorite.setOnClickListener {
                if(isFav.equals(true)){
                    homePatchDetail?.ContentID?.let { it1 -> favViewModel.deleteFavContent(it1,homePatchDetail?.ContentType) }
                    cacheRepository?.deleteFavoriteById(homePatchDetail?.ContentID.toString())
                    Toast.makeText(mContext,"Removed from favorite", Toast.LENGTH_LONG).show()
                    ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_favorite_border)
                    isFav=false
                    Log.e("TAG","NAME: "+ isFav)
                } else {

                    favViewModel.addFavContent(homePatchDetail?.ContentID.toString(),homePatchDetail?.ContentType.toString())

                    ivFavorite.setImageResource(R.drawable.my_bl_sdk_ic_filled_favorite)
                    Log.e("TAG","NAME123: "+ isFav)
                    cacheRepository?.insertFavSingleContent(FavData(homePatchDetail?.ContentID.toString(),homePatchDetail?.AlbumId,homePatchDetail?.image,"",homePatchDetail?.Artist,homePatchDetail?.ArtistId,
                        "","",2,homePatchDetail?.ContentType.toString(),"","","1","",homePatchDetail?.image,"",
                        false,  "",0,"","","",homePatchDetail?.PlayUrl,homePatchDetail?.RootId,
                        homePatchDetail?.RootType,false,"",homePatchDetail?.title,"",""

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