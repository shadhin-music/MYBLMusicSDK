package com.shadhinmusiclibrary.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.utils.ExpandableTextView

class PodcastHeaderAdapter( val argHomePatchDetail: HomePatchDetail?) : RecyclerView.Adapter<PodcastHeaderAdapter.PodcastHeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastHeaderViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.podcast_header_layout, parent, false)
        return PodcastHeaderViewHolder(v)
    }


    override fun onBindViewHolder(holder:PodcastHeaderViewHolder, position: Int) {
         holder.bindItems()


    }
    override fun getItemViewType(position: Int) = VIEW_TYPE
    override fun getItemCount(): Int {
        return 1
    }

    inner class PodcastHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  context = itemView.getContext()
        fun bindItems() {
            val imageView: ImageView = itemView.findViewById(R.id.thumb)
            var url: String = argHomePatchDetail!!.image
            val textArtist: TextView = itemView.findViewById(R.id.name)
            textArtist.setText(argHomePatchDetail.title)
            val textView: ExpandableTextView? = itemView?.findViewById(R.id.tvDescription)
//            textView?.text = bio?.artist?.bio?.summary
//            val tvName: TextView = itemView?.findViewById(R.id.tvName)!!
//            tvName.text = argHomePatchDetail.Artist + "'s"
           // val imageArtist: ImageView = itemView!!.findViewById(R.id.imageArtist)
            Log.d("TAG", "ImageUrl: " + url.replace("<\$size\$>", "300"))
            Glide.with(context)
                .load(url.replace("<\$size\$>", "300"))
                .into(imageView)

//            val textViewName = itemView.findViewById(R.id.tv_person_name) as TextView
//            val imageView2 = itemView.findViewById(R.id.civ_person_image) as CircleImageView
//            itemView.setOnClickListener {
//                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
//                manager.beginTransaction()
//                    .replace(R.id.container , ArtistDetailsFragment.newInstance())
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
        const val VIEW_TYPE = 1
    }
}