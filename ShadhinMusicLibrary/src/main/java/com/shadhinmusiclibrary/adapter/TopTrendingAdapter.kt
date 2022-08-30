package com.shadhinmusiclibrary.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.fragments.TopTrendingPlaylistFragment


class TopTrendingAdapter() : RecyclerView.Adapter<TopTrendingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.top_trending_list, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems()


    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context
        fun bindItems() {
            itemView.setOnClickListener {
                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
                manager.beginTransaction()
                    .replace(R.id.container, TopTrendingPlaylistFragment.newInstance())
                    .addToBackStack("Trending")
                    .commit()
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






