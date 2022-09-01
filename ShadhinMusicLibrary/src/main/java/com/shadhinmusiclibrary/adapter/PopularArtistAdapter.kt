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
import com.shadhinmusiclibrary.data.model.Data


import com.shadhinmusiclibrary.fragments.artist.ArtistDetailsFragment
import com.shadhinmusiclibrary.utils.CircleImageView

class PopularArtistAdapter(val data1: Data) : RecyclerView.Adapter<PopularArtistAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_circle_image_view, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.bindItems(data1.Data.size)


    }

    override fun getItemCount(): Int {
        return data1.Data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  context = itemView.getContext()
        fun bindItems(size: Int) {

            val textViewName = itemView.findViewById(R.id.tv_person_name) as TextView
            val imageView = itemView.findViewById(R.id.civ_person_image) as CircleImageView
            var url :String = data1!!.Data[absoluteAdapterPosition].image
            Log.d("TAG","ImageUrl: " + url)
            Glide.with(context)
                .load(url.replace("<\$size\$>","300"))
                .into(imageView)

            textViewName.setText(data1.Data[absoluteAdapterPosition].Artist)
            itemView.setOnClickListener {
//                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
//                manager.beginTransaction()
//                    .replace(R.id.container , ArtistDetailsFragment.newInstance(data))
//                    .addToBackStack("Artist Fragment")
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