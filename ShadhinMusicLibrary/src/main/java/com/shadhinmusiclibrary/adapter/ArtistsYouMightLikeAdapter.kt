package com.shadhinmusiclibra

import com.shadhinmusiclibrary.adapter.ArtistAdapter
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.fragments.artist.ArtistDetailsFragment




import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.HomePatchItem


class ArtistsYouMightLikeAdapter(
     var homePatchItem: HomePatchItem?,
     val homeCallBack: HomeCallBack,
     var artistIDToSkip: String? = null) : RecyclerView.Adapter<ArtistsYouMightLikeAdapter.ViewHolder>() {

     var adapter: ArtistAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_you_might_like, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItems(homePatchItem)


    }
    override fun getItemViewType(position: Int) = VIEW_TYPE
    override fun getItemCount(): Int {
        return 1
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  context = itemView.getContext()
        fun bindItems(homePatchItem: HomePatchItem?) {
           val textView:TextView = itemView.findViewById(R.id.tvTitle)
            textView.text= "You might like also"
            if(homePatchItem?.Data?.isEmpty() == true){
                textView.visibility = View.GONE
            }
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ArtistAdapter(homePatchItem, homeCallBack = homeCallBack,artistIDToSkip)
            recyclerView.adapter = adapter

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
        const val VIEW_TYPE = 4
    }
}






