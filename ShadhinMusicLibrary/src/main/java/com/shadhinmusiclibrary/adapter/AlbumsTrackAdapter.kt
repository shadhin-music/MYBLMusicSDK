package com.shadhinmusiclibrary.adapter


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.BottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.OnItemClickCallback
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.fragments.album.AlbumDetailsFragment
import com.shadhinmusiclibrary.utils.TimeParser
import com.shadhinmusiclibrary.utils.UtilHelper
import com.shadhinmusiclibrary.utils.get


internal class AlbumsTrackAdapter(
    private val itemClickCB: OnItemClickCallback,
    private val bsDialogItemCallback: BottomSheetDialogItemCallback,
   val myBroadcastReceiver: AlbumDetailsFragment.MyBroadcastReceiver,
) : RecyclerView.Adapter<AlbumsTrackAdapter.ViewHolder>() {
    var dataSongDetail: MutableList<SongDetail> = mutableListOf()
    private var parentView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
        parentView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_video_podcast_epi_single_item, parent, false)
        return ViewHolder(parentView!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(dataSongDetail[position])

        holder.itemView.setOnClickListener {
            itemClickCB.onClickItem(dataSongDetail, position)
        }
//        DownloadProgressObserver.addViewHolder(holder, dataSongDetail[position].rootContentID)
//        DownloadProgressObserver.updateProgress(holder)
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

    fun setData(
        data: MutableList<SongDetail>,
        rootPatch: HomePatchDetail,
        myBroadcastReceiver: AlbumDetailsFragment.MyBroadcastReceiver
    ) {
        this.dataSongDetail = mutableListOf()
        for (songItem in data) {
            dataSongDetail.add(
                UtilHelper.getSongDetailAndRootData(songItem, rootPatch)
            )
        }
        notifyDataSetChanged()
    }

    fun setPlayingSong(mediaId: String, newSongDetails: List<SongDetail>) {
        val callback = AlbumTrackDiffCB(dataSongDetail, newSongDetails)
        val diffResult = DiffUtil.calculateDiff(callback)
        dataSongDetail.clear()
        dataSongDetail.addAll(newSongDetails)
        diffResult.dispatchUpdatesTo(this)
    }

    private class AlbumTrackDiffCB() : DiffUtil.Callback() {
        private lateinit var oldSongDetails: List<SongDetail>
        private lateinit var newSongDetails: List<SongDetail>

        constructor(oldSongDetails: List<SongDetail>, newSongDetails: List<SongDetail>) : this() {
            this.oldSongDetails = oldSongDetails
            this.newSongDetails = newSongDetails
        }

        override fun getOldListSize(): Int = oldSongDetails.size

        override fun getNewListSize(): Int = newSongDetails.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldSongDetails[oldItemPosition].ContentID == newSongDetails[newItemPosition].ContentID

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldSongDetails[oldItemPosition].ContentID == newSongDetails[newItemPosition].ContentID

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return super.getChangePayload(oldItemPosition, newItemPosition)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tag:String?=null
        val context = itemView.getContext()
        var tvSongName: TextView? = null
        fun bindItems(dataSongDetail: SongDetail) {
            itemView.tag = dataSongDetail.rootContentID
            val imageView: ShapeableImageView? = itemView.findViewById(R.id.siv_song_icon)
//            imageView?.tag = dataSongDetail.albumId
//            Log.e("TAGDOWNLOADED","TAGDOWNLOADEDAdapter: " + dataSongDetail.albumId)
        //    Log.e("TAGDOWNLOADED","TAGDOWNLOADEDAdapter: " + itemView.tag)
//
//            LocalBroadcastManager.getInstance(context).registerReceiver(MyBroadcastReceiver(),
//                IntentFilter())
//           //val listener = MyBroadcastReceiver()
           // Log.e("TAGDOWNLOADED","TAGDOWNLOADEDAdapter: " + myBroadcastReceiver.toString())
             //myBroadcastReceiver.resultData
            Glide.with(context)
                .load(dataSongDetail.getImageUrl300Size())
                .into(imageView!!)
            tvSongName = itemView.findViewById(R.id.tv_song_name)
            val textArtist: TextView = itemView.findViewById(R.id.tv_singer_name)
            val tvSongLength: TextView = itemView.findViewById(R.id.tv_song_length)
            tvSongName!!.text = dataSongDetail.title
            textArtist.text = dataSongDetail.artist
            tvSongLength.text = TimeParser.secToMin(dataSongDetail.duration)
            itemView.setOnClickListener {

            }
        }



    }

    companion object {
        fun sendData(data: Int) {

        }

        const val VIEW_TYPE = 2
    }
}






