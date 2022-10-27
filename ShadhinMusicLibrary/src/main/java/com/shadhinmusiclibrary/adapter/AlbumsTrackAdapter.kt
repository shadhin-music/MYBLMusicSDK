package com.shadhinmusiclibrary.adapter


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
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.BottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.OnItemClickCallback
import com.shadhinmusiclibrary.data.IMusicModel
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.utils.AnyTrackDiffCB
import com.shadhinmusiclibrary.utils.TimeParser
import com.shadhinmusiclibrary.utils.UtilHelper


internal class AlbumsTrackAdapter(
    private val itemClickCB: OnItemClickCallback,
    private val bsDialogItemCallback: BottomSheetDialogItemCallback
) : RecyclerView.Adapter<AlbumsTrackAdapter.ViewHolder>() {
    var dataSongDetail: MutableList<IMusicModel> = mutableListOf()
    private var parentView: View? = null
    private var contentId: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val v = LayoutInflater.from(parent.context)
        parentView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_video_podcast_epi_single_item, parent, false)
        return ViewHolder(parentView!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mSongDetails = dataSongDetail[position]
        holder.bindItems(mSongDetails)
        holder.itemView.setOnClickListener {
            itemClickCB.onClickItem(dataSongDetail, position)
        }

        val ivSongMenuIcon: ImageView = holder.itemView.findViewById(R.id.iv_song_menu_icon)
        ivSongMenuIcon.setOnClickListener {
            val songDetail = dataSongDetail[position]
            bsDialogItemCallback.onClickBottomItem(songDetail)
        }

        if (mSongDetails.isPlaying) {
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
        return dataSongDetail.size
    }

    fun setData(data: MutableList<SongDetail>, rootPatch: HomePatchDetail, mediaId: String?) {
        this.dataSongDetail = mutableListOf()
        for (songItem in data) {
            dataSongDetail.add(
                UtilHelper.getSongDetailAndRootData(songItem, rootPatch)
            )
        }
        notifyDataSetChanged()

        if (mediaId != null) {
            setPlayingSong(mediaId)
        }
    }

    fun setPlayingSong(mediaId: String) {
        contentId = mediaId
        val newList: List<IMusicModel> =
            UtilHelper.getCurrentRunningSongToNewSongList(mediaId, dataSongDetail)
        val callback = AnyTrackDiffCB(dataSongDetail, newList)
        val diffResult = DiffUtil.calculateDiff(callback)
        dataSongDetail.clear()
        dataSongDetail.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

//    private class AlbumTrackDiffCB() : DiffUtil.Callback() {
//        private lateinit var oldSongDetails: List<IMusicModel>
//        private lateinit var newSongDetails: List<IMusicModel>
//
//        constructor(oldSongDetails: List<IMusicModel>, newSongDetails: List<IMusicModel>) : this() {
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
        fun bindItems(dataSongDetail: IMusicModel) {
            val imageView: ShapeableImageView? = itemView.findViewById(R.id.siv_song_icon)
            Glide.with(context)
                .load(UtilHelper.getImageUrlSize300(dataSongDetail.imageUrl!!))
                .into(imageView!!)
            tvSongName = itemView.findViewById(R.id.tv_song_name)
            val textArtist: TextView = itemView.findViewById(R.id.tv_singer_name)
            val tvSongLength: TextView = itemView.findViewById(R.id.tv_song_length)
            tvSongName!!.text = dataSongDetail.titleName
            textArtist.text = dataSongDetail.artistName
            tvSongLength.text = TimeParser.secToMin(dataSongDetail.total_duration)
        }
    }

    companion object {
        const val VIEW_TYPE = 2
    }
}






