package com.shadhinmusiclibrary.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager


import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModel
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumsDetails2Fragment


class ArtistAlbumListAdapter(
    val homePatchItem: HomePatchItem,
    val artistAlbumModel: ArtistAlbumModel?,
    private val homeCallBack: HomeCallBack
) :
    RecyclerView.Adapter<ArtistAlbumListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.top_trending_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(artistAlbumModel)
        holder.itemView.setOnClickListener {
            homeCallBack.onArtistAlbumClick(position, artistAlbumModel!!.data)
        }
    }

    override fun getItemCount(): Int {
        return artistAlbumModel?.data?.size ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mContext = itemView.context
        fun bindItems(artistAlbumModel: ArtistAlbumModel?) {
//            itemView.setOnClickListener {
//                val manager: FragmentManager = (mContext as AppCompatActivity).supportFragmentManager
//               manager.beginTransaction()
//                    .replace(R.id.container, ArtistAlbumsDetails2Fragment.newInstance(artistAlbumModel,
//                        artistAlbumModel!!.data[absoluteAdapterPosition]))
//                    .addToBackStack("Trending")
//                    .commit()
//            }
            val imageView: ShapeableImageView = itemView.findViewById(R.id.image)
            val textView: TextView = itemView.findViewById(R.id.txt_title)
            val url: String =
                this@ArtistAlbumListAdapter.artistAlbumModel?.data?.get(absoluteAdapterPosition)?.image.toString()
            textView.text =
                this@ArtistAlbumListAdapter.artistAlbumModel?.data!![absoluteAdapterPosition]?.title
            val textViewArtist: TextView = itemView.findViewById(R.id.txt_name)
            textViewArtist.text =
                this@ArtistAlbumListAdapter.artistAlbumModel.data.get(absoluteAdapterPosition).artistname
            //Log.d("TAG","ImageUrl: " + url.replace("<\$size\$>","300"))
            Glide.with(mContext)
                .load(url.replace("<\$size\$>", "300"))
                .into(imageView)
//            val linearLayout: LinearLayout = itemView.findViewById(R.id.linear)
//            entityId = banner.entityId
            //getActorName(entityId!!)
//            //textViewName.setText(banner.name)
//            textViewName.text = LOADING_TXT
//            textViewName.tag = banner.entityId
        }
    }

}






