package com.shadhinmusiclibrary.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.lastfm.LastFmResult
import com.shadhinmusiclibrary.utils.ExpandableTextView

class ArtistHeaderAdapter( val homePatchDetail: HomePatchDetail?) : RecyclerView.Adapter<ArtistHeaderAdapter.PodcastHeaderViewHolder>() {

    var bio: LastFmResult? = null
    private var parentView:View?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastHeaderViewHolder {
        parentView = LayoutInflater.from(parent.context).inflate(R.layout.artist_details_header, parent, false)
        return PodcastHeaderViewHolder(parentView!!)
    }


    override fun onBindViewHolder(holder:PodcastHeaderViewHolder, position: Int) {
         holder.bindItems(homePatchDetail)


    }
    override fun getItemViewType(position: Int) = VIEW_TYPE
    override fun getItemCount(): Int {
        return 1
    }

    fun artistBio(bio: LastFmResult?) {
        this.bio = bio

        val textView: ExpandableTextView? = parentView?.findViewById(R.id.tvDescription)
        textView?.text = bio?.artist?.bio?.summary
    }

    inner class PodcastHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  context = itemView.getContext()
        fun bindItems(homePatchDetail: HomePatchDetail?) {
            val imageView: ImageView = itemView.findViewById(R.id.thumb)

            var url: String = homePatchDetail!!.image
             val textArtist: TextView = itemView.findViewById(R.id.name)
            textArtist.setText(homePatchDetail.Artist)
            val textView: ExpandableTextView? = itemView?.findViewById(R.id.tvDescription)
            textView?.text = bio?.artist?.bio?.summary

            // textView.setText(data.Data[absoluteAdapterPosition].title)
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