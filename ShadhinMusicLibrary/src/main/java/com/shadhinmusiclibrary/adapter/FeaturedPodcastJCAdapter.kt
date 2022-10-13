package com.shadhinmusiclibrary.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.FeaturedPodcastOnItemClickCallback
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.callBackService.PodcastOnItemClickCallback
import com.shadhinmusiclibrary.data.model.FeaturedPodcastDetails
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.data.model.podcast.Track


internal class FeaturedPodcastJCAdapter(
    var data: MutableList<FeaturedPodcastDetails>,var clickCallback: FeaturedPodcastOnItemClickCallback
) : RecyclerView.Adapter<FeaturedPodcastJCAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.featured_podcast_video_list, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.bindItems()
        holder.itemView.setOnClickListener {
            clickCallback.onClickItem(data,position)
            Log.e("TAG","String: "+ data)
//            homeCallBack.onClickItemAndAllItem(position, homePatchitem)
           // homeCallBack.onClickItem(position, Track(data.get()) )
        }
//         homeCallBack.onClickItemPodcastEpisode(position, listOf(Episode(data.get(position).EpisodeId,data.get(position).ContentType,
//            data.get(position).About,
//             1,
//       data.get(position).ImageUrl,
//        false,
//        false,
//        data.get(position).EpisodeName,
//             data.get(position).ShowId,
//        0,
//        mutableListOf(),
//       "")))
//         homeCallBack.onClickItemAndAllItem(position, HomePatchItem(data.get(position).EpisodeId,data.get(position).ContentType,
//             listOf(),"",data.get(position).EpisodeName,0,0))
//        Log.e("TAG","String: "+ HomePatchItem(data.get(position).EpisodeId,data.get(position).ContentType,
//            listOf(),"",data.get(position).EpisodeName,0,0))
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    @JvmName("setData1")
    fun setData(data: List<FeaturedPodcastDetails>?) {
        this.data= data as MutableList<FeaturedPodcastDetails>
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  context = itemView.getContext()
        fun bindItems() {
            val textViewName = itemView.findViewById(R.id.txt_title) as TextView
            val textViewArtist = itemView.findViewById(R.id.txt_name) as TextView
            val imageView = itemView.findViewById(R.id.image) as ImageView
           val url: String? = data?.get(absoluteAdapterPosition)?.getImageUrl300Size()


            //textViewArtist.text = data?.get(absoluteAdapterPosition)?.TrackName
            Glide.with(context)
                .load(url)
                .into(imageView)


            textViewName.text= data.get(absoluteAdapterPosition).EpisodeName
           // textViewArtist.text = data?.get(absoluteAdapterPosition)?.EpisodeName
//            val textViewName = itemView.findViewById(R.id.tv_person_name) as TextView
//            val imageView2 = itemView.findViewById(R.id.civ_person_image) as CircleImageView
//            itemView.setOnClickListener {
////                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
////                manager.beginTransaction()
////                    .replace(R.id.container , PodcastDetailsFragment())
////                    .commit()
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