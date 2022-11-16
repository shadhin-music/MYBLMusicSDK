package com.shadhinmusiclibrary.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.DownloadedSongOnCallBack
import com.shadhinmusiclibrary.callBackService.favItemClickCallback
import com.shadhinmusiclibrary.data.model.fav.FavData
import com.shadhinmusiclibrary.fragments.fav.onFavAlbumClick
import com.shadhinmusiclibrary.fragments.fav.onFavArtistClick
import com.shadhinmusiclibrary.fragments.fav.onFavPlaylistClick
import com.shadhinmusiclibrary.library.player.utils.CacheRepository
import com.shadhinmusiclibrary.utils.TimeParser

internal class FavoritePlaylistAdapter(
    val allDownloads: List<FavData>,
    private val lrOnCallBack: DownloadedSongOnCallBack,
    private val openMenu: favItemClickCallback,
    private val cacheRepository: CacheRepository,private val albumClick: onFavPlaylistClick
) : RecyclerView.Adapter<FavoritePlaylistAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.my_bl_sdk_download_songs_item, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.bindItems()
        val menu = holder.itemView.findViewById<ImageView>(R.id.iv_song_menu_icon)
//
//        if(allDownloads[position].rootType.equals("V")){
//
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
//        if(allDownloads[position].rootType.equals("S")){
//        val position =allDownloads[position]
            holder.itemView.setOnClickListener {
                albumClick.onFavPlaylistClick(position,allDownloads)
            //lrOnCallBack.onClickFavItem(allDownloads as MutableList<FavData>, position)
             Log.e("TAG","ALL Downloads: "+ allDownloads)
        }
        menu.setOnClickListener {
            openMenu.onClickBottomItemSongs(allDownloads[position])
        }
        if(allDownloads[position].contentType.equals("PDJG")){
            menu.setOnClickListener {
                openMenu.onClickBottomItemPodcast(allDownloads[position])
            }
        }


        //}

    }

    override fun getItemCount(): Int {
        return allDownloads.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tag:String?=null
        val  context = itemView.getContext()
        fun bindItems() {

            val sivSongIcon: ImageView = itemView.findViewById(R.id.siv_song_icon)
            Glide.with(context)
                .load(allDownloads[absoluteAdapterPosition].getImageUrl300Size())
                .into(sivSongIcon)
            val tvSongName: TextView = itemView.findViewById(R.id.tv_song_name)
            tvSongName.text = allDownloads[absoluteAdapterPosition].title

            val tvSingerName: TextView = itemView.findViewById(R.id.tv_singer_name)
            tvSingerName.text = allDownloads[absoluteAdapterPosition].artist
              tvSingerName.visibility = GONE
            val tvSongLength: TextView =itemView.findViewById(R.id.tv_song_length)
            tvSongLength.visibility = GONE
            tvSongLength.text = TimeParser.secToMin(allDownloads[absoluteAdapterPosition].duration)
            val progressIndicator: CircularProgressIndicator = itemView.findViewById(R.id.progress)
            val downloaded:ImageView = itemView.findViewById(R.id.iv_song_type_icon)
            progressIndicator.tag = allDownloads[absoluteAdapterPosition].contentID
            progressIndicator.visibility = View.GONE
            downloaded.visibility = View.GONE
            downloaded.tag = 220
            val isDownloaded = cacheRepository.isTrackDownloaded(allDownloads[absoluteAdapterPosition].contentID) ?: false

            if(isDownloaded){
                Log.e("TAG","ISDOWNLOADED: "+ isDownloaded)
                downloaded.visibility = View.VISIBLE
                progressIndicator.visibility = View.GONE
            }
        }

    }
}