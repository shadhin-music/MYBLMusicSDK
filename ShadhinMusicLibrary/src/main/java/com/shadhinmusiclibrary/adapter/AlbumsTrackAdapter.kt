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
import com.shadhinmusiclibrary.callBackService.BottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.OnItemClickCallback
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.utils.TimeParser
import com.shadhinmusiclibrary.utils.UtilHelper


class AlbumsTrackAdapter(
    private val itemClickCB: OnItemClickCallback,
    private val bsDialogItemCallback: BottomSheetDialogItemCallback
) : RecyclerView.Adapter<AlbumsTrackAdapter.ViewHolder>() {
    var dataSongDetail: MutableList<SongDetail> = mutableListOf()
    private var parentView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
        parentView = LayoutInflater.from(parent.context)
            .inflate(R.layout.video_podcast_epi_single_item, parent, false)
        return ViewHolder(parentView!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(dataSongDetail[position])

        holder.itemView.setOnClickListener {
            itemClickCB.onClickItem(dataSongDetail, position)
        }

        val ivSongMenuIcon: ImageView = holder.itemView.findViewById(R.id.iv_song_menu_icon)
        ivSongMenuIcon.setOnClickListener {
            val songDetail = dataSongDetail[position]
            bsDialogItemCallback.onClickBottomItem(songDetail)
        }
    }

    override fun getItemViewType(position: Int) = VIEW_TYPE

    override fun getItemCount(): Int {
        return dataSongDetail.size
    }

    fun setData(data: MutableList<SongDetail>, rootPatch: HomePatchDetail) {
        this.dataSongDetail = mutableListOf()
        for (songItem in data) {
            dataSongDetail.add(
                UtilHelper.getSongDetailAndRootData(songItem, rootPatch)
            )
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.getContext()
        fun bindItems(dataSongDetail: SongDetail) {
            val imageView: ShapeableImageView? = itemView.findViewById(R.id.siv_song_icon)
            Glide.with(context)
                .load(dataSongDetail.getImageUrl300Size())
                .into(imageView!!)
            val textTitle: TextView = itemView.findViewById(R.id.tv_song_name)
            val textArtist: TextView = itemView.findViewById(R.id.tv_singer_name)
            val textDuration: TextView = itemView.findViewById(R.id.tv_song_length)
            textTitle.text = dataSongDetail.title
            textArtist.text = dataSongDetail.artist
            textDuration.text = TimeParser.secToMin(dataSongDetail.duration)
            itemView.setOnClickListener {

            }
        }
    }

    companion object {
        const val VIEW_TYPE = 2
    }
}






