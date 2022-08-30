package com.shadhinmusiclibrary.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R


class PodcastMoreEpisodesAdapter : RecyclerView.Adapter<PodcastMoreEpisodesAdapter.PodcastMoreEpisodesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastMoreEpisodesViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_you_might_like, parent, false)
        return PodcastMoreEpisodesViewHolder(v)
    }


    override fun onBindViewHolder(holder: PodcastMoreEpisodesViewHolder, position: Int) {
        holder.bindItems()


    }
    override fun getItemViewType(position: Int) = VIEW_TYPE
    override fun getItemCount(): Int {
        return 1
    }

    inner class PodcastMoreEpisodesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  context = itemView.getContext()
        fun bindItems() {
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
           // recyclerView.adapter = TopTrendingAdapter(data)

//            val textViewName = itemView.findViewById(R.id.txt_name) as TextView
//            val imageView2 = itemView.findViewById(R.id.image) as ImageView
//            itemView.setOnClickListener {
//                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
//                manager.beginTransaction()
//                    .replace(R.id.container , PlaylistFragment.newInstance())
//                    .commit()
//            }
//            val linearLayout: LinearLayout = itemView.findViewById(R.id.linear)
//            entityId = banner.entityId
            //getActorName(entityId!!)

//            //textViewName.setText(banner.name)
//            textViewName.text = LOADING_TXT
//            textViewName.tag = banner.entityId


        }

    }
    companion object {
        const val VIEW_TYPE = 3
    }
}






