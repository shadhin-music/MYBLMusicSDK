package com.shadhinmusiclibrary.adapter


import android.util.Log
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


class HomeContentPlaylistAdapter(val homePatchItem: HomePatchItem) : RecyclerView.Adapter<HomeContentPlaylistAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.browse_all_genre, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bindItems()


    }

    override fun getItemCount(): Int {
        return homePatchItem.Data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context
        fun bindItems() {
            itemView.setOnClickListener {
                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
                manager.beginTransaction()
                    .replace(R.id.container , GenrePlaylistFragment.newInstance())
                    .addToBackStack("GenrePlaylistFragment")
                    .commit()
            }
            val imageView: ImageView = itemView.findViewById(R.id.image)
           // val textView:TextView = itemView.findViewById(R.id.txt_title)
            var url :String = homePatchItem!!.Data[absoluteAdapterPosition].image
           // val textArtist:TextView = itemView.findViewById(R.id.txt_name)
            //textArtist.setText(data.Data[absoluteAdapterPosition].Artist)
           // textView.setText(data.Data[absoluteAdapterPosition].title)
            Log.d("TAG","ImageUrl: " + url.replace("<\$size\$>","300"))
            Glide.with(context)
                .load( url.replace("<\$size\$>","300"))
                .into(imageView)

//
//            val textViewName = itemView.findViewById(R.id.txt_name) as TextView
//            val imageView2 = itemView.findViewById(R.id.image) as ImageView
//            val linearLayout: LinearLayout = itemView.findViewById(R.id.linear)
//            entityId = banner.entityId
            //getActorName(entityId!!)

//            //textViewName.setText(banner.name)
//            textViewName.text = LOADING_TXT
//            textViewName.tag = banner.entityId


        }

    }

}





