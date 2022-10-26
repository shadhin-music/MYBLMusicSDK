package com.shadhinmusiclibrary.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.ArtistOnItemClickCallback
import com.shadhinmusiclibrary.callBackService.BottomSheetDialogItemCallback
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.fragments.artist.ArtistContentData
import com.shadhinmusiclibrary.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.TimeParser
import com.shadhinmusiclibrary.utils.UtilHelper


internal class ArtistTrackAdapter(
    private val itemClickCB: ArtistOnItemClickCallback,
    val bottomSheetDialogItemCallback: BottomSheetDialogItemCallback,
    val cacheRepository: CacheRepository?
) : RecyclerView.Adapter<ArtistTrackAdapter.ArtistTrackVH>() {
    var artistSongList: MutableList<ArtistContentData> = mutableListOf()
    private var parentView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistTrackVH {
//        val v = LayoutInflater.from(parent.context)
        parentView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_video_podcast_epi_single_item, parent, false)
        return ArtistTrackVH(parentView!!)
    }

    override fun onBindViewHolder(holder: ArtistTrackVH, position: Int) {
        val mArtistConData = artistSongList[position]
        holder.bindItems(mArtistConData)

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
                    artistContent.ContentType,
                    artistContent.PlayUrl,
                    artistContent.artistname,
                    artistContent.duration,
                    artistContent.copyright,
                    artistContent.labelname,
                    artistContent.releaseDate,
                    "",
                    artistContent.ArtistId,
                    artistContent.AlbumId,
                    "",
                    artistContent.ArtistId,
                    "",
                    ""
                )
            )
        }

        if (mArtistConData.isPlaying) {
            holder.tvSongName?.setTextColor(
                ContextCompat.getColor(holder.context, R.color.my_sdk_color_primary)
            )
        } else {
            holder.tvSongName?.setTextColor(
                ContextCompat.getColor(
                    holder.context,
                    R.color.my_sdk_black2
                )
            )
        }
    }

    override fun getItemViewType(position: Int) = VIEW_TYPE

    override fun getItemCount(): Int {
        return artistSongList.size
    }

//    fun artistContent(artistContent: ArtistContent?) {
//        artistContent?.data?.let {
//            this.artistSongList.clear()
//            this.artistSongList.addAll(it)
//            this.notifyDataSetChanged()
//        }
//    }

    fun setArtistTrack(
        data: MutableList<ArtistContentData>,
        rootPatch: HomePatchDetail,
        mediaId: String?
    ) {
        this.artistSongList = mutableListOf()
        this.artistSongList.clear()
        for (songItem in data) {
            artistSongList.add(
                UtilHelper.getArtistContentDataToRootData(songItem, rootPatch)
            )
        }
        notifyDataSetChanged()

        if (mediaId != null) {
            setPlayingSong(mediaId)
        }
    }

    fun setPlayingSong(mediaId: String) {
        val newList: List<ArtistContentData> =
            UtilHelper.artistArtistContentDataNewList(mediaId, artistSongList)
        val callback = ArtistTrackDiffCB(artistSongList, newList)
        val diffResult = DiffUtil.calculateDiff(callback)
        artistSongList.clear()
        artistSongList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    private class ArtistTrackDiffCB() : DiffUtil.Callback() {
        private lateinit var oldSongDetails: List<ArtistContentData>
        private lateinit var newSongDetails: List<ArtistContentData>

        constructor(
            oldSongDetails: List<ArtistContentData>,
            newSongDetails: List<ArtistContentData>
        ) : this() {
            this.oldSongDetails = oldSongDetails
            this.newSongDetails = newSongDetails
        }

        override fun getOldListSize(): Int = oldSongDetails.size

        override fun getNewListSize(): Int = newSongDetails.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldSongDetails[oldItemPosition].ContentID == newSongDetails[newItemPosition].ContentID

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldSongDetails[oldItemPosition].isPlaying == newSongDetails[newItemPosition].isPlaying
    }

    inner class ArtistTrackVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.getContext()
        var tvSongName: TextView? = null
        fun bindItems(artistContent: ArtistContentData) {
            val imageView: ShapeableImageView? = itemView.findViewById(R.id.siv_song_icon)
            // val textArtist:TextView = itemView.findViewById(R.id.txt_name)
            //textArtist.setText(data.Data[absoluteAdapterPosition].Artist)
            // textView.setText(data.Data[absoluteAdapterPosition].title)
            Glide.with(context)
                .load(artistContent.getImageUrl300Size())
                .into(imageView!!)
            tvSongName = itemView.findViewById(R.id.tv_song_name)
            val textArtist: TextView = itemView.findViewById(R.id.tv_singer_name)
            val textDuration: TextView = itemView.findViewById(R.id.tv_song_length)
            tvSongName?.text = artistContent.title
            textArtist.text = artistContent.artistname
            textDuration.text = TimeParser.secToMin(artistContent.duration)
            val progressIndicatorArtist: CircularProgressIndicator = itemView.findViewById(R.id.progress)
            val downloaded:ImageView = itemView.findViewById(R.id.iv_song_type_icon)
            progressIndicatorArtist.tag = artistContent.ContentID
            downloaded.tag = 200
            progressIndicatorArtist.visibility = View.GONE
            downloaded.visibility = View.GONE
            val isDownloaded = cacheRepository?.isTrackDownloaded(artistContent.ContentID) ?: false
//            Log.e("Tag","Downloaded: cacheRepository is null"+ (cacheRepository == null))
//            Log.e("Tag","Downloaded: "+ isDownloaded)
//            Log.e("Tag","Downloaded: "+ dataSongDetail.ContentID)
//            Log.e("Tag","Downloaded: "+ cacheRepository?.getAllDownloads())
            if(isDownloaded){
                Log.e("TAG","ISDOWNLOADED: "+ isDownloaded)
                downloaded.visibility = View.VISIBLE
                progressIndicatorArtist.visibility = View.GONE
            }
            itemView.setOnClickListener {

            }

        }
    }

    companion object {
        const val VIEW_TYPE = 2
    }
}






