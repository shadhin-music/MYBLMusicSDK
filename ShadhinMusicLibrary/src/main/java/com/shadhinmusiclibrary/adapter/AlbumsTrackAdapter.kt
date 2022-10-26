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
import com.shadhinmusiclibrary.adapter.ArtistHeaderAdapter.Companion.VIEW_TYPE
import com.shadhinmusiclibrary.callBackService.BottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.OnItemClickCallback
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.download.MyBLDownloadService
import com.shadhinmusiclibrary.download.MyBLDownloadService.Companion.currentProgress
import com.shadhinmusiclibrary.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.DownloadOrDeleteObserver
import com.shadhinmusiclibrary.utils.TimeParser
import com.shadhinmusiclibrary.utils.UtilHelper


internal class AlbumsTrackAdapter(
    private val itemClickCB: OnItemClickCallback,
    private val bsDialogItemCallback: BottomSheetDialogItemCallback,
    var cacheRepository: CacheRepository?
) : RecyclerView.Adapter<AlbumsTrackAdapter.ViewHolder>() {
    var dataSongDetail: MutableList<SongDetail> = mutableListOf()
    private var parentView: View? = null
    private var contentId: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
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
            val newList: List<SongDetail> =
                UtilHelper.albumSongDetailsNewList(mediaId, dataSongDetail)
            val callback = AlbumTrackDiffCB(dataSongDetail, newList)
            val diffResult = DiffUtil.calculateDiff(callback)
            dataSongDetail.clear()
            dataSongDetail.addAll(newList)
            diffResult.dispatchUpdatesTo(this)
        }

        private class AlbumTrackDiffCB() : DiffUtil.Callback() {
            private lateinit var oldSongDetails: List<SongDetail>
            private lateinit var newSongDetails: List<SongDetail>

            constructor(
                oldSongDetails: List<SongDetail>,
                newSongDetails: List<SongDetail>
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

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tag: String? = null
            val context = itemView.getContext()
            var tvSongName: TextView? = null
            fun bindItems(dataSongDetail: SongDetail) {
                //itemView.tag = dataSongDetail.rootContentID
                val imageView: ShapeableImageView? = itemView.findViewById(R.id.siv_song_icon)
                val progressIndicator: CircularProgressIndicator =
                    itemView.findViewById(R.id.progress)
                val downloaded: ImageView = itemView.findViewById(R.id.iv_song_type_icon)
                Glide.with(context)
                    .load(dataSongDetail.getImageUrl300Size())
                    .into(imageView!!)
                tvSongName = itemView.findViewById(R.id.tv_song_name)
                val textArtist: TextView = itemView.findViewById(R.id.tv_singer_name)
                val tvSongLength: TextView = itemView.findViewById(R.id.tv_song_length)
                tvSongName!!.text = dataSongDetail.title
                textArtist.text = dataSongDetail.artist
                tvSongLength.text = TimeParser.secToMin(dataSongDetail.duration)

                progressIndicator.tag = dataSongDetail.ContentID
                downloaded.tag = 200
                progressIndicator.visibility = View.GONE
                downloaded.visibility = View.GONE
                val isDownloaded =
                    cacheRepository?.isTrackDownloaded(dataSongDetail.ContentID) ?: false
                if (isDownloaded) {
                    Log.e("TAG", "ISDOWNLOADED: " + isDownloaded)
                    downloaded.visibility = View.VISIBLE
                    progressIndicator.visibility = View.GONE

                }


            }


        }

        companion object {

            const val VIEW_TYPE = 2
        }
    }






