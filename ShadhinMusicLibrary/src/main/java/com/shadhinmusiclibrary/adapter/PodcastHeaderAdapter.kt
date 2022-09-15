package com.shadhinmusiclibrary.adapter

import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.utils.ExpandableTextView

class PodcastHeaderAdapter(val episodeData: List<Episode>?) : RecyclerView.Adapter<PodcastHeaderAdapter.PodcastHeaderViewHolder>() {
   //var episode: List<Episode> = emptyList()
var episode:List<Episode> ?= null
    private var parentView: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastHeaderViewHolder {
        parentView = LayoutInflater.from(parent.context).inflate(R.layout.podcast_header_layout, parent, false)
        return PodcastHeaderViewHolder(parentView!!)
    }


    override fun onBindViewHolder(holder:PodcastHeaderViewHolder, position: Int) {
         holder.bindItems(position)
       // Log.d("TAG", "Url: " + data!!.EpisodeList[position].Name)

    }
    override fun getItemViewType(position: Int) = VIEW_TYPE
    override fun getItemCount(): Int {
        return 1
    }

    fun setHeader(episode: List<Episode>) {
        this.episode = episode
        notifyDataSetChanged()
//       val textView: ExpandableTextView? = parentView?.findViewById(R.id.tvDescription)
//////         textView?.text = bio?.artist?.bio?.summary
//        textView?.setText(Html.fromHtml(episode.))


    }

    inner class PodcastHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  context = itemView.getContext()
        fun bindItems(position: Int) {
            val imageView: ImageView = itemView.findViewById(R.id.thumb)
            var url: String? = episode?.get(0)?.ImageUrl
            var textArtist: TextView? = itemView.findViewById(R.id.name)
            val details:String=  episode?.get(position)?.Details.toString()
            val result = Html.fromHtml(details).toString()
            textArtist?.text= episode?.get(position)?.Name.toString()
            val textView: ExpandableTextView? =itemView.findViewById(R.id.tvDescription)
            val moreText: TextView? = itemView?.findViewById(R.id.tvReadMore)
            textView?.setText(result)
            moreText?.setOnClickListener {
                if (textView!!.isExpanded) {
                    textView.collapse()
                    moreText.text = "Read More"
                } else {
                    textView.expand()
                    moreText.text = "Less"
                }
            }
//            val tvName: TextView = itemView?.findViewById(R.id.tvName)!!
//            tvName.text = argHomePatchDetail.Artist + "'s"
           // val imageArtist: ImageView = itemView!!.findViewById(R.id.imageArtist)
            Log.d("TAG", "Url: " + episode?.get(0)?.ImageUrl)
            Glide.with(context)
                .load(url?.replace("<\$size\$>", "300"))
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