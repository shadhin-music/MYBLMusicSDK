package com.shadhinmusiclibrary.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.fragments.GenrePlaylistFragment


class GenresAdapter(val homePatchItem: HomePatchItem) : RecyclerView.Adapter<GenresAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.big_video_view_item, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems()


    }

    override fun getItemCount(): Int {
        return homePatchItem?.Data!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  context = itemView.getContext()
        fun bindItems() {
            val url: String = homePatchItem.Data[absoluteAdapterPosition].image
            val imageView = itemView.findViewById(R.id.image) as ImageView
            Glide.with(context)
                .load(url.replace("<\$size\$>", "300"))
                .into(imageView)

            itemView.setOnClickListener {
//                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
//                manager.beginTransaction()
//                    .replace(R.id.container , GenrePlaylistFragment.newInstance())
//                    .addToBackStack("GenrePlaylistFragment")
//                    .commit()
            }
//            val textViewName = itemView.findViewById(R.id.txt_name) as TextView


//            val linearLayout: LinearLayout = itemView.findViewById(R.id.linear)
//            entityId = banner.entityId
            //getActorName(entityId!!)

//            //textViewName.setText(banner.name)
//            textViewName.text = LOADING_TXT
//            textViewName.tag = banner.entityId


        }

    }

}






