package com.shadhinmusiclibrary.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.LatestReleaseOnCallBack
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.FeaturedSongDetail
import com.shadhinmusiclibrary.utils.AnyTrackDiffCB
import com.shadhinmusiclibrary.utils.TimeParser
import com.shadhinmusiclibrary.utils.UtilHelper


internal class FeaturedLatestTracksAdapter(
    private val lrOnCallBack: LatestReleaseOnCallBack
) : RecyclerView.Adapter<FeaturedLatestTracksAdapter.ViewHolder>() {
    private var listSongDetail: MutableList<IMusicModel> = mutableListOf()
    private var parentView: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val v = LayoutInflater.from(parent.context)
        parentView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_video_podcast_epi_single_item, parent, false)
        return ViewHolder(parentView!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mFeaturedSongDetail = listSongDetail[position]
        holder.bindItems(mFeaturedSongDetail)
        holder.itemView.setOnClickListener {
            lrOnCallBack.onClickItem(listSongDetail, position)
        }

        if (mFeaturedSongDetail.isPlaying) {
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

    override fun getItemCount(): Int {
        return listSongDetail.size
    }

    fun setData(
        data: MutableList<FeaturedSongDetail>,
        /*   rootPatch: HomePatchDetail,*/
        mediaId: String?
    ) {
        this.listSongDetail = mutableListOf()
        for (songItem in data) {
            listSongDetail.add(
                UtilHelper.getFeaturedSongDetailAndRootData(songItem/*, rootPatch*/)
            )
        }

        if (mediaId != null) {
            setPlayingSong(mediaId)
        }
        notifyDataSetChanged()
    }

    fun setPlayingSong(mediaId: String) {
        val newList: List<IMusicModel> =
            UtilHelper.getCurrentRunningSongToNewSongList(mediaId, listSongDetail)
        val callback = AnyTrackDiffCB(listSongDetail, newList)
        val diffResult = DiffUtil.calculateDiff(callback)
        listSongDetail.clear()
        listSongDetail.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

//    private class AlbumTrackDiffCB() : DiffUtil.Callback() {
//        private lateinit var oldSongDetails: List<IMusicModel>
//        private lateinit var newSongDetails: List<IMusicModel>
//
//        constructor(
//            oldSongDetails: List<IMusicModel>,
//            newSongDetails: List<IMusicModel>
//        ) : this() {
//            this.oldSongDetails = oldSongDetails
//            this.newSongDetails = newSongDetails
//        }
//
//        override fun getOldListSize(): Int = oldSongDetails.size
//
//        override fun getNewListSize(): Int = newSongDetails.size
//
//        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
//            oldSongDetails[oldItemPosition].content_Id == newSongDetails[newItemPosition].content_Id
//
//        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
//            oldSongDetails[oldItemPosition].isPlaying == newSongDetails[newItemPosition].isPlaying
//    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.getContext()
        var tvSongName: TextView? = null
        fun bindItems(mSongDetails: IMusicModel) {
            val imageView: ShapeableImageView? = itemView.findViewById(R.id.siv_song_icon)
            val url: String = mSongDetails.imageUrl!!
            // val textArtist:TextView = itemView.findViewById(R.id.txt_name)
            //textArtist.setText(data.Data[absoluteAdapterPosition].Artist)
            // textView.setText(data.Data[absoluteAdapterPosition].title)

            Glide.with(context)
                .load(UtilHelper.getImageUrlSize300(url))
                .into(imageView!!)
            tvSongName = itemView.findViewById(R.id.tv_song_name)
            val textArtist: TextView = itemView.findViewById(R.id.tv_singer_name)
            val textDuration: TextView = itemView.findViewById(R.id.tv_song_length)
            tvSongName?.text = mSongDetails.titleName
            textArtist.text = mSongDetails.artistName
            textDuration.text = TimeParser.secToMin(mSongDetails.total_duration)
/*            itemView.setOnClickListener {

            }*/
        }
    }
}






