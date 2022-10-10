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
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.callBackService.SearchItemCallBack
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.model.search.SearchArtistdata
import com.shadhinmusiclibrary.fragments.search.SearchFragment
import com.shadhinmusiclibrary.utils.CircleImageView
import com.shadhinmusiclibrary.utils.TimeParser


class SearchArtistAdapter(
    val searchArtistdata: List<SearchArtistdata>,
       val homeCallBack: SearchItemCallBack
) :
    RecyclerView.Adapter<SearchArtistAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_artist_layout, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(searchArtistdata[position])



    }

    override fun getItemCount(): Int {
        return  searchArtistdata.size

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



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.getContext()
        fun bindItems(artistDetails: SearchArtistdata) {
            val imageView:CircleImageView = itemView.findViewById(R.id.artist_img)
            val url: String = artistDetails.image
            Log.d("TAG", "ImageUrl: " + url)
            Glide.with(context)
                .load(url.replace("<\$size\$>", "300"))
                .into(imageView)
            val textArtist: TextView = itemView.findViewById(R.id.artist_name)

            textArtist.text = artistDetails.Artist

            itemView.setOnClickListener {
                homeCallBack.onClickArtistItem(artistDetails)
                 Log.d("TAG", "artistDetails: " + artistDetails)
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


}






