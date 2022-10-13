package com.shadhinmusiclibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.ArtistOnItemClickCallback
import com.shadhinmusiclibrary.callBackService.BottomSheetDialogItemCallback
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.fragments.artist.ArtistContent
import com.shadhinmusiclibrary.fragments.artist.ArtistContentData
import com.shadhinmusiclibrary.utils.TimeParser
import com.shadhinmusiclibrary.utils.UtilHelper

internal class SearchArtistTrackAdapter(
    private val itemClickCB: ArtistOnItemClickCallback,
    val bottomSheetDialogItemCallback: BottomSheetDialogItemCallback
) : RecyclerView.Adapter<SearchArtistTrackAdapter.ArtistTrackVH>() {
    //   private var artistContent: ArtistContent? = null
//    private var songDetail: MutableList<SongDetail> = ArrayList()
    var artistSongList: MutableList<ArtistContentData> = mutableListOf()
    private var parentView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistTrackVH {
        val v = LayoutInflater.from(parent.context)
        parentView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_video_podcast_epi_single_item, parent, false)
        return ArtistTrackVH(parentView!!)
    }

    override fun onBindViewHolder(holder: ArtistTrackVH, position: Int) {
        holder.bindItems(artistSongList[position])

        holder.itemView.setOnClickListener {
            itemClickCB.onClickItem(artistSongList, position)
        }
        val ivSongMenuIcon: ImageView = holder.itemView.findViewById(R.id.iv_song_menu_icon)
        ivSongMenuIcon.setOnClickListener {
            val artistContent = artistSongList[position]
            bottomSheetDialogItemCallback.onClickBottomItem(
                SongDetail(
                    artistContent.ContentID,
                    artistContent.image,
                    artistContent.title,
                    "",
                    "",
                    artistContent.artistname,
                    "",
                    "",
                    "",
                    "",
                    "",
                    artistContent.ArtistId,
                    artistContent.AlbumId,
                    "",
                    "",
                    "",
                    ""
                )
            )
        }
    }

    override fun getItemViewType(position: Int) = VIEW_TYPE

    override fun getItemCount(): Int {
        return artistSongList.size
    }

    fun artistContent(artistContent: ArtistContent?) {
        artistContent?.data?.let {
            this.artistSongList.clear()
            this.artistSongList.addAll(it)
            this.notifyDataSetChanged()
        }
    }

    fun setArtistTrack(data: List<ArtistContentData>, rootPatch: HomePatchDetail) {
        this.artistSongList = mutableListOf()
        this.artistSongList.clear()
        for (songItem in data) {
            artistSongList.add(
                UtilHelper.getArtistContentDataToRootData(songItem, rootPatch)
            )
        }
        this.notifyDataSetChanged()
    }

    inner class ArtistTrackVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.getContext()
        fun bindItems(artistContent: ArtistContentData) {
            val imageView: ShapeableImageView? = itemView.findViewById(R.id.siv_song_icon)
            // val textArtist:TextView = itemView.findViewById(R.id.txt_name)
            //textArtist.setText(data.Data[absoluteAdapterPosition].Artist)
            // textView.setText(data.Data[absoluteAdapterPosition].title)
            Glide.with(context)
                .load(artistContent.getImageUrl300Size())
                .into(imageView!!)
            val textTitle: TextView = itemView.findViewById(R.id.tv_song_name)
            val textArtist: TextView = itemView.findViewById(R.id.tv_singer_name)
            val textDuration: TextView = itemView.findViewById(R.id.tv_song_length)
            textTitle.text = artistContent.title
            textArtist.text = artistContent.artistname
            textDuration.text = TimeParser.secToMin(artistContent.duration)

            itemView.setOnClickListener {

            }
//            val linearLayout: LinearLayout = itemView.findViewById(R.id.linear)
//            entityId = banner.entityId
//            getActorName(entityId!!)
//            //textViewName.setText(banner.name)
//            textViewName.text = LOADING_TXT
//            textViewName.tag = banner.entityId
        }
    }

    companion object {
        const val VIEW_TYPE = 2
    }
}