package com.shadhinmusiclibrary.adapter

import android.content.Context
import android.nfc.cardemulation.CardEmulation
import android.provider.Settings.Global.getString
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.model.lastfm.LastFmResult
import com.shadhinmusiclibrary.fragments.artist.ArtistBanner
import com.shadhinmusiclibrary.fragments.artist.ArtistContent
import com.shadhinmusiclibrary.fragments.artist.ArtistContentData
import com.shadhinmusiclibrary.utils.ExpandableTextView

class BottomSheetArtistHeaderAdapter(var songDetail: SongDetail?) :
    RecyclerView.Adapter<BottomSheetArtistHeaderAdapter.HeaderViewHolder>() {
    var data:ArtistContent?= null
    var bio: LastFmResult? = null
    var banner: ArtistBanner? = null
    private var parentView: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        parentView = LayoutInflater.from(parent.context)
            .inflate(R.layout.artist_details_header, parent, false)
        return HeaderViewHolder(parentView!!)
    }


    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bindItems(songDetail)


    }

    override fun getItemViewType(position: Int) = VIEW_TYPE
    override fun getItemCount(): Int {
        return 1
    }

    fun setData(songDetail: SongDetail) {
        this.songDetail = songDetail
        notifyDataSetChanged()
    }

    fun artistBio(bio: LastFmResult?) {
        this.bio = bio


//        val moreText:TextView?= parentView?.findViewById(R.id.tvReadMore)
//        if (moreText != null) {
//            moreText.setOnClickListener { e ->
//                if (textView?.isExpanded() == true) {
//                    textView.collapse()
//                    moreText.text ="Read More"
//                } else {
//                    textView?.isExpanded()
//                   moreText.text ="Show less"
//                }
//            }
//        }
    }

    fun artistBanner(banner: ArtistBanner?) {
        this.banner = banner
    }

    fun setHeaderImage(data: ArtistContent?) {
         this.data = data
        notifyDataSetChanged()
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.getContext()
        fun bindItems(songDetail: SongDetail?) {
            val imageView: ImageView = itemView.findViewById(R.id.thumb)

            var url: String = data?.getImageUrl300Size().toString()
            Log.d("TAGG","Image: "+ url)
            val textArtist: TextView = itemView.findViewById(R.id.name)
            textArtist.setText(songDetail?.artist)
            val textView: ExpandableTextView? = itemView?.findViewById(R.id.tvDescription)
            val bio: String = bio?.artist?.bio?.summary.toString()
            val updatedbio = Html.fromHtml(bio).toString()
             val cardBiography: CardView = itemView.findViewById(R.id.cardBiography)
            if(updatedbio.length>25){
                cardBiography.visibility= VISIBLE
            }
            // textView?.text = bio?.artist?.bio?.summary
            textView?.setText(updatedbio)
            val tvName: TextView = itemView?.findViewById(R.id.tvName)!!
            tvName.text = songDetail?.artist + "'s"
            val imageArtist: ImageView = itemView!!.findViewById(R.id.imageArtist)
            if (banner?.image?.isEmpty() == false) {
                val cardListen: CardView = parentView!!.findViewById(R.id.cardListen)
                cardListen.visibility = VISIBLE
                if (context != null) {
                    Glide.with(context)
                        .load(banner?.image)
                        .into(imageArtist)
                }
            }
            //  textView?.setText(Html.fromHtml(CharParser.replaceMultipleSpaces(bio?.artist?.bio?.summary)))
            val moreText: TextView? = itemView?.findViewById(R.id.tvReadMore)

            moreText?.setOnClickListener {
                if (textView!!.isExpanded) {
                    textView.collapse()
                    moreText.text = "Read More"
                } else {
                    textView.expand()
                    moreText.text = "Less"
                }
            }
            // textView.setText(data.Data[absoluteAdapterPosition].title)
            Glide.with(context)
                .load(url)
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