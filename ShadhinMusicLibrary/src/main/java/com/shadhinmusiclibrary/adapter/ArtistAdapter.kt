package com.shadhinmusiclibrary.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.fragments.artist.ArtistDetailsFragment
import com.shadhinmusiclibrary.utils.CircleImageView


class ArtistAdapter(var homePatchItem: HomePatchItem?, private val homeCallBack: HomeCallBack, var artistIDtoSkip: String? = null) :
    RecyclerView.Adapter<ArtistAdapter.ViewHolder>() {

    private var filteredHomePatchItem:HomePatchItem? = null

    init {
        initialize()
    }

    fun initialize(){
        var items = homePatchItem?.Data
        items = items?.filter { it.ArtistId != artistIDtoSkip }
        filteredHomePatchItem = homePatchItem?.copy()
        if (items != null) {
            filteredHomePatchItem?.Data = items
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.artist_list, parent, false)
        return ViewHolder(v)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems()
        holder.itemView.setOnClickListener {
            val artistID = filteredHomePatchItem!!.Data[position].ArtistId
            val index = homePatchItem!!.Data.indexOfFirst { it.ArtistId == artistID }
            homeCallBack.onClickItemAndAllItem(index, homePatchItem!!)
        }
    }

    override fun getItemCount(): Int {
        return filteredHomePatchItem?.Data?.size?:0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mContext = itemView.context
        fun bindItems() {
            val textViewName = itemView.findViewById(R.id.txt_name) as TextView
            val imageView2 = itemView.findViewById(R.id.image) as CircleImageView

            val url: String = filteredHomePatchItem!!.Data[absoluteAdapterPosition].image
            Log.d("TAG", "ImageUrl: $url")
            Glide.with(mContext)
                .load(url.replace("<\$size\$>", "300"))
                .into(imageView2)

            textViewName.setText(filteredHomePatchItem!!.Data[absoluteAdapterPosition].Artist)

        }
    }
}






