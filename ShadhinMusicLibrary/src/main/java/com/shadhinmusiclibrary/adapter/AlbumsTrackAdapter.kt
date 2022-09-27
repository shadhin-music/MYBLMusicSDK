package com.shadhinmusiclibrary.adapter


import android.util.Log
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
import com.shadhinmusiclibrary.fragments.artist.ArtistContentData
import com.shadhinmusiclibrary.utils.TimeParser
import com.shadhinmusiclibrary.utils.UtilHelper


class AlbumsTrackAdapter( private val itemClickCB: OnItemClickCallback, val bottomSheetDialogItemCallback: BottomSheetDialogItemCallback) :
    RecyclerView.Adapter<AlbumsTrackAdapter.ViewHolder>() {
    //   private var artistContent: ArtistContent? = null
    var dataSongDetail: MutableList<SongDetail> = mutableListOf()
   // private var artistContentList: MutableList<ArtistContentData> = ArrayList()
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
        val ivSongMenuIcon: ImageView =  holder.itemView.findViewById(R.id.iv_song_menu_icon)
        ivSongMenuIcon.setOnClickListener {
             val songDetail = dataSongDetail[position]
            Log.e("TAG","ONCLICK: "+ songDetail)
            //val artistContent = artistContentList[position]
            bottomSheetDialogItemCallback.onClickBottomItem(songDetail)

        }
    }

    override fun getItemViewType(position: Int) = VIEW_TYPE
    override fun getItemCount(): Int {
        return  dataSongDetail.size

    }

    fun trackContent(dataSongDetail: SongDetail?) {

//        trackContent?.let {
//
//            this.artistContentList.clear()
//            this.artistContentList.addAll(it)
//            this.notifyDataSetChanged()
//
//        }

    }

    fun setData(data: MutableList<SongDetail>,  rootPatch: HomePatchDetail) {
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
        fun bindItems( dataSongDetail: SongDetail) {
            val imageView: ShapeableImageView? = itemView.findViewById(R.id.siv_song_icon)
            val url: String = dataSongDetail.getImageUrl300Size()
            // val textArtist:TextView = itemView.findViewById(R.id.txt_name)
            //textArtist.setText(data.Data[absoluteAdapterPosition].Artist)
            // textView.setText(data.Data[absoluteAdapterPosition].title)
            Log.d("TAG", "ImageUrl: " + url.replace("<\$size\$>", "300"))
            Glide.with(context)
                .load(url)
                .into(imageView!!)
            val textTitle: TextView = itemView.findViewById(R.id.tv_song_name)
            val textArtist: TextView = itemView.findViewById(R.id.tv_singer_name)
            val textDuration: TextView = itemView.findViewById(R.id.tv_song_length)
            textTitle.text = dataSongDetail.title
            textArtist.text = dataSongDetail.artist
            textDuration.text = TimeParser.secToMin(dataSongDetail.duration)
            //Log.e("TAG","DATA123: "+ artistContent?.image)
            itemView.setOnClickListener {
//                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
//                manager.beginTransaction()
//                    .replace(R.id.container , AlbumFragment.newInstance())
//                    .commit()
            }
//            val linearLayout: LinearLayout = itemView.findViewById(R.id.linear)
//            entityId = banner.entityId
            //getActorName(entityId!!)

//            //textViewName.setText(banner.name)
//            textViewName.text = LOADING_TXT
//            textViewName.tag = banner.entityId


        }

    }

    companion object {
        const val VIEW_TYPE = 2
    }
}






