package com.shadhinmusiclibrary.adapter

import android.annotation.SuppressLint
import android.text.Html
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
import com.shadhinmusiclibrary.data.model.fav.FavDataModel
import com.shadhinmusiclibrary.data.model.podcast.EpisodeModel
import com.shadhinmusiclibrary.data.model.podcast.SongTrackModel
import com.shadhinmusiclibrary.fragments.fav.FavViewModel
import com.shadhinmusiclibrary.library.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.ExpandableTextView
import com.shadhinmusiclibrary.utils.UtilHelper

internal class PodcastHeaderAdapter(
    private val pcOnCallback: CommonPlayControlCallback,
    private val cacheRepository: CacheRepository?,
    private val favViewModel: FavViewModel,
    private val homePatchDetail: HomePatchDetailModel?
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
    fun setHeader(episode: List<EpisodeModel>, trackList: MutableList<IMusicModel>) {
        this.episode = episode
        listSongTrack = trackList
        notifyDataSetChanged()
    }

    inner class PodcastHeaderVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context
        var ivPlayBtn: ImageView? = null
        var ivFavorite: ImageView? = null

        fun bindItems(position: Int) {
            val imageView: ImageView = itemView.findViewById(R.id.thumb)
            val textArtist: TextView? = itemView.findViewById(R.id.name)
            ivPlayBtn = itemView.findViewById(R.id.iv_play_btn)
            val url: String? = episode?.get(0)?.ImageUrl
            val details: String = episode?.get(position)?.Details.toString()
            ivFavorite = itemView.findViewById(R.id.favorite)
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

            var isFav = false
            val isAddedToFav = cacheRepository?.getFavoriteById(homePatchDetail?.content_Id ?: "")
            if (isAddedToFav?.content_Id != null) {

                ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_filled_favorite)
                isFav = true

            } else {

                ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_favorite_border)
                isFav = false
            }

            ivFavorite?.setOnClickListener {
                if (isFav.equals(true)) {
                    homePatchDetail?.content_Id?.let { it1 ->
                        favViewModel.deleteFavContent(
                            it1,
                            homePatchDetail?.content_Type ?: ""
                        )
                    }
                    cacheRepository?.deleteFavoriteById(homePatchDetail?.content_Id.toString())
                    Toast.makeText(context, "Removed from favorite", Toast.LENGTH_LONG).show()
                    ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_favorite_border)
                    isFav = false
                } else {

                    favViewModel.addFavContent(
                        homePatchDetail?.content_Id.toString(),
                        homePatchDetail?.content_Type.toString()
                    )

                    ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_filled_favorite)
                    cacheRepository?.insertFavSingleContent(
                        FavDataModel().apply {
                            content_Id = homePatchDetail?.content_Id.toString()
                            album_Id = homePatchDetail?.album_Id
                            albumImage = homePatchDetail?.imageUrl
                            artistName = homePatchDetail?.artistName
                            artist_Id = homePatchDetail?.artist_Id
                            clientValue = 2
                            content_Type = homePatchDetail?.content_Type.toString()
                            fav = "1"
                            imageUrl = homePatchDetail?.imageUrl
                            playingUrl = homePatchDetail?.playingUrl
                            rootContentId = homePatchDetail?.rootContentId
                            rootContentType = homePatchDetail?.rootContentType
                            titleName = homePatchDetail?.titleName
                        }
                    )
                    isFav = true
                    Toast.makeText(context, "Added to favorite", Toast.LENGTH_LONG).show()
                }
                // favClickCallback.favItemClick(songDetail)
            }
        }
    }

    companion object {
        const val VIEW_TYPE = 1
    }
}