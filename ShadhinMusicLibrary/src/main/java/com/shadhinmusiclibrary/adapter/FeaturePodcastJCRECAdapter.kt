package com.shadhinmusiclibra


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.FeaturedPodcastJCAdapter
import com.shadhinmusiclibrary.callBackService.FeaturedPodcastOnItemClickCallback
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.FeaturedPodcastDetails
import com.shadhinmusiclibrary.data.model.HomePatchItem


class FeaturePodcastJCRECAdapter(var cilckCallBack: FeaturedPodcastOnItemClickCallback) : RecyclerView.Adapter<FeaturePodcastJCRECAdapter.ViewHolder>() {
    var data: MutableList<FeaturedPodcastDetails> = mutableListOf()
    var showName:String ?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_you_might_like, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems()
//         holder.itemView.setOnClickListener {
//
//         }

    }

    override fun getItemViewType(position: Int) = VIEW_TYPE
    override fun getItemCount(): Int {
        return 1
    }

    @JvmName("setData1")
    fun setData(data: List<FeaturedPodcastDetails>?,showName:String) {
        this.data= data as MutableList<FeaturedPodcastDetails>
        this.showName = showName
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  context = itemView.getContext()
        fun bindItems() {
            val textViewName = itemView.findViewById(R.id.tvTitle) as TextView
            textViewName.text = showName
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager = GridLayoutManager( context,
                2,
                RecyclerView.HORIZONTAL,
                false)
            recyclerView.adapter = FeaturedPodcastJCAdapter(data,cilckCallBack)
                //ArtistAlbumListAdapter(homePatchItem!!, artistAlbumModel, homeCallBack)

//            val textViewName = itemView.findViewById(R.id.tvTitle) as TextView
//            textViewName.text = data[1].ShowName
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
        const val VIEW_TYPE = 15
    }
}






