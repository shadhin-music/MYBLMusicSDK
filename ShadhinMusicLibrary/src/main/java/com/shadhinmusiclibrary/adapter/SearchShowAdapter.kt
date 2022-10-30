package com.shadhinmusiclibrary.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.SearchItemCallBack
import com.shadhinmusiclibrary.data.model.SongDetailModel
import com.shadhinmusiclibrary.data.model.search.SearchDataModel


internal class SearchShowAdapter(
    val searchshowdata: List<SearchDataModel>,
    private val seaItemCallback: SearchItemCallBack
) :
    RecyclerView.Adapter<SearchShowAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bl_sdk_search_album_layout, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(searchshowdata[position])
        val searchData: SearchDataModel = searchshowdata[position]
        holder.itemView.setOnClickListener {
            seaItemCallback.onClickSearchItem(searchData)
        }
    }

    override fun getItemCount(): Int {
        return searchshowdata.size

    }

    fun trackContent(dataSongDetail: SongDetailModel?) {
//        trackContent?.let {
//            this.artistContentList.clear()
//            this.artistContentList.addAll(it)
//            this.notifyDataSetChanged()
//        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.getContext()
        fun bindItems(searchshowdata: SearchDataModel) {
            val imageView: ImageView = itemView.findViewById(R.id.thumb)
            val url: String = searchshowdata.imageUrl!!
            val textTitle: TextView = itemView.findViewById(R.id.title)
            //textArtist.setText(data.Data[absoluteAdapterPosition].Artist)
            //textView.setText(data.Data[absoluteAdapterPosition].title)
            Log.d("TAG", "ImageUrl: " + url)
            Glide.with(context)
                .load(url.replace("<\$size\$>", "300"))
                .into(imageView)
            val textArtist: TextView = itemView.findViewById(R.id.similarArtist)
            //  val textDuration: TextView = itemView.findViewById(R.id.tv_song_length)
            textTitle.text = searchshowdata.titleName
            textArtist.text = searchshowdata.artistName
            // textDuration.text = TimeParser.secToMin(dataSongDetail.duration)
            //Log.e("TAG","DATA123: "+ artistContent?.image)
            itemView.setOnClickListener {
//                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
//                manager.beginTransaction()
//                    .replace(R.id.container , AlbumFragment.newInstance())
//                    .commit()
            }
        }
    }
}






