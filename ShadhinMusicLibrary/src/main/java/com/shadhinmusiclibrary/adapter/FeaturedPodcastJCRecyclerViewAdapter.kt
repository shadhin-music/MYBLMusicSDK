package com.shadhinmusiclibrary.adapter

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.R.layout.item_release_patch
import com.shadhinmusiclibrary.data.model.FeaturedPodcastDetails

class FeaturedPodcastJCRecyclerViewAdapter : RecyclerView.Adapter<FeaturedPodcastJCRecyclerViewAdapter.ViewHolder>(){

    var data: MutableList<FeaturedPodcastDetails> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(item_release_patch, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.bindItems()


    }

    override fun getItemCount(): Int {
        return 1
    }

    @JvmName("setData1")
    fun setData(data: List<FeaturedPodcastDetails>?) {
        this.data= data as MutableList<FeaturedPodcastDetails>
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  context = itemView.getContext()
        fun bindItems() {
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager = GridLayoutManager( context,
                2,
                RecyclerView.HORIZONTAL,
                false)
            recyclerView.adapter = FeaturedPodcastJCAdapter(data)
//            val textViewName = itemView.findViewById(R.id.txt_title) as TextView
//            val textViewArtist = itemView.findViewById(R.id.txt_name) as TextView
//            val imageView = itemView.findViewById(R.id.image) as ImageView
//           val url: String? = data?.get(absoluteAdapterPosition)?.getImageUrl300Size()
//
//
//            textViewArtist.text = data?.get(absoluteAdapterPosition)?.TrackName
//            Glide.with(context)
//                .load(url)
//                .into(imageView)
//
//
//            textViewName.text= data?.get(absoluteAdapterPosition)?.EpisodeName
           // textViewArtist.text = data?.get(absoluteAdapterPosition)?.EpisodeName
//            val textViewName = itemView.findViewById(R.id.tv_person_name) as TextView
//            val imageView2 = itemView.findViewById(R.id.civ_person_image) as CircleImageView
//            itemView.setOnClickListener {
//                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
//                manager.beginTransaction()
//                    .replace(R.id.container ,PodcastDetailsFragment.newInstance())
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



}