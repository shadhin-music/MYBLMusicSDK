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
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.Data
import com.shadhinmusiclibrary.data.model.DataX
import com.shadhinmusiclibrary.fragments.PlaylistFragment
import com.shadhinmusiclibrary.utils.CircleImageView


class ArtistAdapter(val data: Data?) : RecyclerView.Adapter<ArtistAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.artist_list, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems()


    }

    override fun getItemCount(): Int {
        return data?.Data!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  context = itemView.getContext()
        fun bindItems() {

            val textViewName = itemView.findViewById(R.id.txt_name) as TextView
            val imageView2 = itemView.findViewById(R.id.image) as CircleImageView


            var url :String = data!!.Data[absoluteAdapterPosition].image
                  //Log.d("TAG","ImageUrl: " + url.replace("<\$size\$>","300"))
            Glide.with(context)
                .load(url.replace("<\$size\$>","300"))
                .into(imageView2)

            textViewName.setText(data.Data[absoluteAdapterPosition].Artist)
            itemView.setOnClickListener {
                val manager: FragmentManager = (context  as AppCompatActivity).supportFragmentManager

                manager.beginTransaction()
                    .replace(R.id.container , PlaylistFragment.newInstance())
                    .addToBackStack("Fragment")
                    .commit()
            }


        }

    }

}





