package com.shadhinmusiclibrary.adapter

import android.annotation.SuppressLint
import android.text.Html
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
import com.shadhinmusiclibrary.callBackService.PodcastOnItemClickCallback
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.model.fav.FavData
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.data.model.podcast.Track
import com.shadhinmusiclibrary.fragments.fav.FavViewModel
import com.shadhinmusiclibrary.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.ExpandableTextView
import com.shadhinmusiclibrary.utils.UtilHelper

internal class PodcastHeaderAdapter(
    private val pcOnCallback: PodcastOnItemClickCallback,
    private val cacheRepository: CacheRepository?,
    private val favViewModel: FavViewModel,
    private val  homePatchDetail: HomePatchDetail?
) : RecyclerView.Adapter<PodcastHeaderAdapter.PodcastHeaderVH>() {
    var episode: List<Episode>? = null
    private var listTrack: MutableList<Track> = mutableListOf()
    private var parentView: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastHeaderVH {
        parentView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_podcast_header_layout, parent, false)
        return PodcastHeaderVH(parentView!!)
    }

    override fun onBindViewHolder(holder: PodcastHeaderVH, position: Int) {
        holder.bindItems(position)
        pcOnCallback.getCurrentVH(holder, listTrack)
        holder.ivPlayBtn?.setOnClickListener {
            pcOnCallback.onRootClickItem(listTrack, position)
        }
    }

    override fun getItemViewType(position: Int) = VIEW_TYPE

    override fun getItemCount(): Int {
        return 1
    }

    fun setTrackData(
        episode: List<Episode>,
        data: MutableList<Track>,
        rootPatch: HomePatchDetail
    ) {
        this.listTrack = mutableListOf()
        for (songItem in data) {
            listTrack.add(
                UtilHelper.getTrackToRootData(songItem, rootPatch)
            )
        }
        this.episode = episode
        this.listTrack = listTrack
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setHeader(episode: List<Episode>, trackList: MutableList<Track>) {
        this.episode = episode
        listTrack = trackList
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
            val isAddedToFav = homePatchDetail?.ContentID?.let { cacheRepository?.getFavoriteById(it) }
            if (isAddedToFav?.contentID != null) {

                ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_filled_favorite)
                isFav = true

            } else {

                ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_favorite_border)
                isFav = false
            }

            ivFavorite?.setOnClickListener {
                if(isFav.equals(true)){
                    homePatchDetail?.ContentID?.let { it1 -> favViewModel.deleteFavContent(it1,homePatchDetail?.ContentType) }
                    cacheRepository?.deleteFavoriteById(homePatchDetail?.ContentID.toString())
                    Toast.makeText(context,"Removed from favorite", Toast.LENGTH_LONG).show()
                    ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_favorite_border)
                    isFav=false
                    Log.e("TAG","NAME: "+ isFav)
                } else {

                    favViewModel.addFavContent(homePatchDetail?.ContentID.toString(),homePatchDetail?.ContentType.toString())

                    ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_filled_favorite)
                    Log.e("TAG","NAME123: "+ isFav)
                    cacheRepository?.insertFavSingleContent(FavData(homePatchDetail?.ContentID.toString(),homePatchDetail?.AlbumId,homePatchDetail?.image,"",homePatchDetail?.Artist,homePatchDetail?.ArtistId,
                        "","",2,homePatchDetail?.ContentType.toString(),"","","1","",homePatchDetail?.image,"",
                        false,  "",0,"","","",homePatchDetail?.PlayUrl,homePatchDetail?.RootId,
                        homePatchDetail?.RootType,false,"",homePatchDetail?.title,"",""

                    ))
                    isFav = true
                    Toast.makeText(context,"Added to favorite", Toast.LENGTH_LONG).show()
                }
                // favClickCallback.favItemClick(songDetail)

            }
        }
    }

    companion object {
        const val VIEW_TYPE = 1
    }
}