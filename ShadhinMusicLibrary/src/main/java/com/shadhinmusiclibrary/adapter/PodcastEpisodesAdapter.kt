package com.shadhinmusiclibrary.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R


class PodcastEpisodesAdapter :
    RecyclerView.Adapter<PodcastEpisodesAdapter.PodcastEpisodesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastEpisodesViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.video_podcast_epi_single_item, parent, false)
        return PodcastEpisodesViewHolder(v)
    }


    override fun onBindViewHolder(holder: PodcastEpisodesViewHolder, position: Int) {
        holder.bindItems()


    }

    override fun getItemViewType(position: Int) = VIEW_TYPE
    override fun getItemCount(): Int {
        return 10
    }

    inner class PodcastEpisodesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.getContext()
        fun bindItems() {


            itemView.setOnClickListener {
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

    companion object {
        const val VIEW_TYPE = 2
    }
}






