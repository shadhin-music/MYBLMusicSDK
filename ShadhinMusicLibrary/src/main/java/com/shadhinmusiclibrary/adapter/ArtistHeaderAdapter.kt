package com.shadhinmusiclibrary.adapter

import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.ArtistOnItemClickCallback
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.HomePatchDetailModel
import com.shadhinmusiclibrary.data.model.fav.FavData
import com.shadhinmusiclibrary.data.model.lastfm.LastFmResult
import com.shadhinmusiclibrary.data.model.ArtistBannerModel
import com.shadhinmusiclibrary.data.model.ArtistContentDataModel
import com.shadhinmusiclibrary.fragments.fav.FavViewModel
import com.shadhinmusiclibrary.library.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.ExpandableTextView
import com.shadhinmusiclibrary.utils.UtilHelper

internal class ArtistHeaderAdapter(
    var homePatchDetail: HomePatchDetailModel?,
    private val itemClickCB: ArtistOnItemClickCallback,
    private val cacheRepository: CacheRepository?
) : RecyclerView.Adapter<ArtistHeaderAdapter.ArtistHeaderVH>() {
    private var dataSongDetail: MutableList<IMusicModel> = mutableListOf()
    var bio: LastFmResult? = null
    var banner: ArtistBannerModel? = null
    private var parentView: View? = null

    private var favViewModel: FavViewModel? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistHeaderVH {
        parentView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_artist_details_header, parent, false)
        return ArtistHeaderVH(parentView!!)
    }


    override fun onBindViewHolder(holder: ArtistHeaderVH, position: Int) {
        holder.bindItems(homePatchDetail)
        itemClickCB.getCurrentVH(holder, dataSongDetail)
        holder.ivPlayBtn?.setOnClickListener {
            itemClickCB.onRootClickItem(dataSongDetail, position)
        }
    }

    override fun getItemViewType(position: Int) = VIEW_TYPE

    override fun getItemCount(): Int {
        return 1
    }

    fun setSongAndData(data: MutableList<ArtistContentDataModel>, rootPatch: HomePatchDetailModel) {
        this.dataSongDetail = mutableListOf()
        for (songItem in data) {
            dataSongDetail.add(
                UtilHelper.getArtistContentDataToRootData(songItem, rootPatch)
            )
        }
        this.homePatchDetail = homePatchDetail
        notifyDataSetChanged()
    }

    fun setData(homePatchDetail: HomePatchDetailModel) {
        this.homePatchDetail = homePatchDetail
        notifyDataSetChanged()
    }

    fun artistBio(bio: LastFmResult?, favViewModel: FavViewModel) {
        this.bio = bio
        this.favViewModel = favViewModel
        notifyDataSetChanged()
//        val moreText:TextView?= parentView?.findViewById(R.id.tvReadMore)
//        if (moreText != null) {
//            moreText.setOnClickListener { e ->
//                if (textView?.isExpanded() == true) {
//                    textView.collapse()
//                    moreText.text ="Read More"
//                } else {
//                    textView?.isExpanded()
//                   moreText.text ="Show less"
//                }
//            }
//        }
    }

    fun artistBanner(banner: ArtistBannerModel?) {
        this.banner = banner
        notifyDataSetChanged()
    }

    inner class ArtistHeaderVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.getContext()
        var ivPlayBtn: ImageView? = null
        var ivFavorite: ImageView? = null

        fun bindItems(homePatchDetail: HomePatchDetailModel?) {
            val imageView: ImageView = itemView.findViewById(R.id.thumb)
            ivPlayBtn = itemView.findViewById(R.id.iv_play_btn)
            ivFavorite = itemView.findViewById(R.id.favorite)
            val url: String = homePatchDetail!!.getImageUrl300Size()
            val textArtist: TextView = itemView.findViewById(R.id.name)
            textArtist.text = homePatchDetail.Artist
            val textView: ExpandableTextView? = itemView?.findViewById(R.id.tvDescription)
            val bio: String = bio?.artist?.bio?.summary.toString()
            if (homePatchDetail.Artist.equals("Elita", true)) {

                val elitaBio =
                    "Elita Karim is a Bangladeshi popular pop singer. She is one of the heart-touching female singers in the country. Her full name Dilshan Karim Elita but she is best known as Elita around the country.Elita Karim started her career as a journalist in the country’s leading English daily the Daily Star. Then she connected with the Black Band but her first mixed album was released in 2009 with the title ‘Amar Prithibi’.In 2009, her mixed album ‘Antohin’ was released and is well discussed in the market. Elita Karim couldn’t release her single music album even after lots of time since her career began, finally in 2015; she released her first single album Elita 2015."
                // val textView: ExpandableTextView? = itemView?.findViewById(R.id.tvDescription)
                Log.e("TAG", "ARTIST: " + elitaBio)
                textView?.setText(elitaBio)
                val cardBiography: CardView = itemView.findViewById(R.id.cardBiography)
                cardBiography.visibility = VISIBLE
            } else {
                val cardBiography: CardView = itemView.findViewById(R.id.cardBiography)
                val updatedbio = Html.fromHtml(bio).toString()
                if (updatedbio.length > 25) {
                    cardBiography.visibility = VISIBLE
                }
                textView?.setText(updatedbio)
                if (homePatchDetail.Artist.equals(
                        "Scarecrow",
                        true
                    ) || homePatchDetail.Artist.equals(
                        "Mizan",
                        true
                    ) || homePatchDetail.Artist.equals(
                        "Feedback",
                        true
                    ) || homePatchDetail.Artist.equals(
                        "Vibe",
                        true
                    ) || homePatchDetail.Artist.equals("Kona", true)
                    || homePatchDetail.Artist.equals(
                        "Balam",
                        true
                    ) || homePatchDetail.Artist.equals(
                        "Nancy",
                        true
                    ) || homePatchDetail.Artist.equals("Liza", true)
                ) {
                    cardBiography.visibility = GONE
                }
            }
//            val updatedbio = Html.fromHtml(bio).toString()
//            val cardBiography: CardView = itemView.findViewById(R.id.cardBiography)
//            if (updatedbio.length > 25) {
//                cardBiography.visibility = VISIBLE
//            }
//            // textView?.text = bio?.artist?.bio?.summary
//            textView?.text = updatedbio
            val tvName: TextView = itemView?.findViewById(R.id.tvName)!!
            tvName.text = homePatchDetail.Artist + "'s"
            val imageArtist: ImageView = itemView!!.findViewById(R.id.imageArtist)
            if (banner?.image?.isEmpty() == false) {
                val cardListen: CardView = parentView!!.findViewById(R.id.cardListen)
                cardListen.visibility = VISIBLE
                if (itemView.context != null) {
                    Glide.with(itemView.context)
                        .load(banner?.image)
                        .into(imageArtist)
                }
            }
            //  textView?.setText(Html.fromHtml(CharParser.replaceMultipleSpaces(bio?.artist?.bio?.summary)))
            val moreText: TextView? = itemView.findViewById(R.id.tvReadMore)

            moreText?.setOnClickListener {
                if (textView!!.isExpanded) {
                    textView.collapse()
                    moreText.text = "Read More"
                } else {
                    textView.expand()
                    moreText.text = "Less"
                }
            }
            Glide.with(context)
                .load(url)
                .into(imageView)
            var isFav = false
            val isAddedToFav = cacheRepository?.getFavoriteById(homePatchDetail.ContentID)
            if (isAddedToFav?.content_Id != null) {
                ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_filled_favorite)
                isFav = true
            } else {
                ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_favorite_border)
                isFav = false
            }

            ivFavorite?.setOnClickListener {
                if (isFav.equals(true)) {
                    favViewModel?.deleteFavContent(
                        homePatchDetail.ContentID,
                        homePatchDetail.ContentType
                    )
                    cacheRepository?.deleteFavoriteById(homePatchDetail.ContentID)
                    Toast.makeText(context, "Removed from favorite", Toast.LENGTH_LONG).show()
                    ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_favorite_border)
                    isFav = false
                    Log.e("TAG", "NAME: " + isFav)
                } else {
                    favViewModel?.addFavContent(
                        homePatchDetail.ContentID,
                        homePatchDetail.ContentType
                    )
                    ivFavorite?.setImageResource(R.drawable.my_bl_sdk_ic_filled_favorite)
                    Log.e("TAG", "NAME123: " + isFav)
                    cacheRepository?.insertFavSingleContent(
                        FavData().apply {
                            content_Id = homePatchDetail.ContentID
                            album_Id = homePatchDetail.AlbumId
                            albumImage = homePatchDetail.image
                            artistName = homePatchDetail.Artist
                            artist_Id = homePatchDetail.ArtistId
                            clientValue = 2
                            content_Type = homePatchDetail.ContentType
                            fav = "1"
                            imageUrl = homePatchDetail.image
                            playingUrl = homePatchDetail.PlayUrl
                            rootContentId = homePatchDetail.RootId
                            rootContentId = homePatchDetail.RootType
                            titleName = homePatchDetail.title
                        }
                    )
                    isFav = true
                    Toast.makeText(context, "Added to favorite", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        const val VIEW_TYPE = 1
    }
}