package com.shadhinmusiclibrary.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.utils.CircleImageView

class PopularArtistAdapter(
    val homePatchItem1: HomePatchItem,
    private val homeCallBack: HomeCallBack
) : RecyclerView.Adapter<PopularArtistAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_circle_image_view, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(homePatchItem1.Data.size)
        holder.itemView.setOnClickListener {
            homeCallBack.onClickItemAndAllItem(position, homePatchItem1)
        }
    }

    override fun getItemCount(): Int {
        return homePatchItem1.Data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context
        fun bindItems(size: Int) {
            val textViewName = itemView.findViewById(R.id.tv_person_name) as TextView
            val imageView = itemView.findViewById(R.id.civ_person_image) as CircleImageView
            val url: String = homePatchItem1.Data[absoluteAdapterPosition].getImageUrl300Size()
            Log.d("TAG", "ImageUrl: " + url)
            Glide.with(context)
                .load(url)
                .into(imageView)

            textViewName.text = homePatchItem1.Data[absoluteAdapterPosition].Artist
            itemView.setOnClickListener {
//                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
//                manager.beginTransaction()
//                    .replace(R.id.container , ArtistDetailsFragment.newInstance(data))
//                    .addToBackStack("Artist Fragment")
//                    .commit()
            }
//            val linearLayout: LinearLayout = itemView.findViewById(R.id.linear)
//            entityId = banner.entityId
//            getActorName(entityId!!)
//            textViewName.setText(banner.name)
//            textViewName.text = LOADING_TXT
//            textViewName.tag = banner.entityId
        }
    }
}