package com.shadhinmusiclibrary.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.fragments.GenrePlaylistFragment


class GenresAdapter() : RecyclerView.Adapter<GenresAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.all_genre_list, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems()


    }

    override fun getItemCount(): Int {
        return 4
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  context = itemView.getContext()
        fun bindItems() {
            itemView.setOnClickListener {
                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
                manager.beginTransaction()
                    .replace(R.id.container , GenrePlaylistFragment.newInstance())
                    .addToBackStack("GenrePlaylistFragment")
                    .commit()
            }
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






