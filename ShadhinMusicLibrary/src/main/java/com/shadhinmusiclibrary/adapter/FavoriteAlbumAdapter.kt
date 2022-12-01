package com.shadhinmusiclibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.CommonPSVCallback
import com.shadhinmusiclibrary.callBackService.DownloadedSongOnCallBack
import com.shadhinmusiclibrary.data.model.fav.FavDataModel
import com.shadhinmusiclibrary.fragments.fav.onFavAlbumClick
import com.shadhinmusiclibrary.library.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.TimeParser
import com.shadhinmusiclibrary.utils.UtilHelper

internal class FavoriteAlbumAdapter(
    private val lrOnCallBack: DownloadedSongOnCallBack,
    private val openMenu: CommonPSVCallback,
    private val cacheRepository: CacheRepository, private val albumClick: onFavAlbumClick
) : RecyclerView.Adapter<FavoriteAlbumAdapter.ViewHolder>() {
    private var allFav: MutableList<FavDataModel> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_download_songs_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems()
        val menu = holder.itemView.findViewById<ImageView>(R.id.iv_song_menu_icon)
//        if(allDownloads[position].rootType.equals("V")){
//             holder.itemView.setOnClickListener {
//                 val intent = Intent(holder.itemView.context, VideoActivity::class.java)
//                 val videoArray = ArrayList<Video>()
//                 for (item in allDownloads) {
//                     val video = Video()
//                     video.setDataDownload(item)
//                     videoArray.add(video)
//                 }
//                 val videos :ArrayList<Video> = videoArray
//                 intent.putExtra(VideoActivity.INTENT_KEY_POSITION, position)
//                 intent.putExtra(VideoActivity.INTENT_KEY_DATA_LIST, videos)
//                 holder.itemView.context.startActivity(intent)
//             }
//        }
        if (allFav[position].content_Type.equals("R")) {

            holder.itemView.setOnClickListener {
                albumClick.onFavAlbumClick2(position, allFav)
                //lrOnCallBack.onClickFavItem(allDownloads as MutableList<FavData>, position)
            }
            menu.setOnClickListener {
                openMenu.onClickBottomItemSongs(allFav[position])
            }
        }
        if (allFav[position].content_Type?.contains("PD") == true) {
            menu.setOnClickListener {
                openMenu.onClickBottomItemPodcast(allFav[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return allFav.size
    }

    fun setData(data: MutableList<FavDataModel>) {
        allFav = data.toMutableList()
        notifyDataSetChanged()

    }
    fun updateData(artistFavoriteContent: List<FavDataModel>?) {
        allFav.clear()
        artistFavoriteContent?.let {allFav.addAll(it) }
        notifyDataSetChanged()

    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tag: String? = null
        val context = itemView.getContext()
        fun bindItems() {
            val sivSongIcon: ImageView = itemView.findViewById(R.id.siv_song_icon)
            Glide.with(context)
                .load(UtilHelper.getImageUrlSize300(allFav[absoluteAdapterPosition].imageUrl!!))
                .into(sivSongIcon)
            val tvSongName: TextView = itemView.findViewById(R.id.tv_song_name)
            tvSongName.text = allFav[absoluteAdapterPosition].titleName

            val tvSingerName: TextView = itemView.findViewById(R.id.tv_singer_name)
            tvSingerName.text = allFav[absoluteAdapterPosition].artistName

            val tvSongLength: TextView = itemView.findViewById(R.id.tv_song_length)
            tvSongLength.text =
                TimeParser.secToMin(allFav[absoluteAdapterPosition].total_duration)
            val progressIndicator: CircularProgressIndicator = itemView.findViewById(R.id.progress)
            val downloaded: ImageView = itemView.findViewById(R.id.iv_song_type_icon)
            progressIndicator.tag = allFav[absoluteAdapterPosition].content_Id
            progressIndicator.visibility = View.GONE
            downloaded.visibility = View.GONE
            downloaded.tag = 220
            val isDownloaded =
                cacheRepository.isTrackDownloaded(allFav[absoluteAdapterPosition].content_Id!!)
                    ?: false
            if (isDownloaded) {
                downloaded.visibility = View.VISIBLE
                progressIndicator.visibility = View.GONE
            }
        }
    }
}