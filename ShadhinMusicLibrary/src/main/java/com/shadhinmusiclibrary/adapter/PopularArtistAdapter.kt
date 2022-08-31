package com.shadhinmusiclibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.Data
import com.shadhinmusiclibrary.fragments.ArtistDetailsFragment
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
        return 10
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  context = itemView.getContext()
        fun bindItems(size: Int) {

            val textViewName = itemView.findViewById(R.id.tv_person_name) as TextView
            val imageView2 = itemView.findViewById(R.id.civ_person_image) as CircleImageView
            textViewName.setText(data1.Data[0].Artist)
            itemView.setOnClickListener {
                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
                manager.beginTransaction()
                    .replace(R.id.container , ArtistDetailsFragment.newInstance())
                    .addToBackStack("Artist Fragment")
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