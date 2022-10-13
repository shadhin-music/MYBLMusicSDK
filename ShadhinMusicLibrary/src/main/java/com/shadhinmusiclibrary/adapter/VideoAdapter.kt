package com.shadhinmusiclibrary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.Video
import com.shadhinmusiclibrary.utils.createTimeLabel


internal typealias VideoItemClickFunc = (Video, isMenuClick:Boolean)-> Unit
internal class VideoAdapter(private val context:Context): ListAdapter<Video,RecyclerView.ViewHolder>(
    VideoDiffCallBack()
){
    val layoutManager: GridLayoutManager = GridLayoutManager(context,1)
    val isList:Boolean
        get() = layoutManager.spanCount == 1
    val isGrid:Boolean
        get() = layoutManager.spanCount == 2
    private var videoItemClickFunc: VideoItemClickFunc? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ViewType.LIST.ordinal ->    crateListViewHolder(parent)
            ViewType.GRID.ordinal ->    crateGridViewHolder(parent)
            else                  ->    crateListViewHolder(parent)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ListViewHolder ->{
                holder.bind(getItem(position))
            }
            is GridViewHolder ->{
                holder.bind(getItem(position))
            }
        }

    }
    fun changeToList(){
        if(isGrid){
            layoutManager.spanCount = 1
            notifyItemRangeChanged(0,itemCount)
        }
    }
    fun changeToGrid(){
        if(isList){
            layoutManager.spanCount = 2
            notifyItemRangeChanged(0,itemCount)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when{
            isList -> ViewType.LIST.ordinal
            isGrid -> ViewType.GRID.ordinal
            else   -> ViewType.LIST.ordinal
        }
    }

    fun onItemClickListeners(videoItemClickFunc: VideoItemClickFunc){
        this.videoItemClickFunc = videoItemClickFunc
    }
    private fun crateListViewHolder(parent:ViewGroup): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.my_bl_sdk_row_video_li,parent,false)
        return ListViewHolder(view)
    }
    private fun crateGridViewHolder(parent:ViewGroup): GridViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.my_bl_sdk_row_video_gr,parent,false)
        return GridViewHolder(view)
    }


    fun currentItem(playing: Boolean, mediaId: String?) {
       val newItem =  currentList.map {
           val tItem = it.copy()
           if(it.contentID == mediaId){
               tItem.isPlaying = true
               tItem.isPlaystate = playing
           }else{
               tItem.isPlaying = false
               tItem.isPlaystate = false
           }
           tItem
        }
        submitList(newItem)
    }

    inner class ListViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView){
        private var titleTextView: TextView = itemView.findViewById(R.id.videoTitle)
        private var subTitleTextView:TextView = itemView.findViewById(R.id.videoDesc)
        private var durationTextView:TextView = itemView.findViewById(R.id.video_duration)
        private var playPauseImage:ImageView = itemView.findViewById(R.id.play_pause)
        private var videoImage:ImageView = itemView.findViewById(R.id.videoImage)


        fun bind(item: Video){


            titleTextView.text = item.title
            durationTextView.text = createTimeLabel(item.duration?.toLong()?:0L)
            if (item.isPlaying) {
                titleTextView.setTextColor( ContextCompat.getColor(itemView.context,R.color.my_sdk_color_primary))
            } else {
                titleTextView.setTextColor(ContextCompat.getColor(itemView.context,R.color.my_sdk_down_title))
            }
            subTitleTextView.text = item.artist

            Glide.with(itemView.context)
                .load(item.image)
                .placeholder(R.drawable.my_bl_sdk_default_video)
                .into(videoImage)


                if (item.isPlaystate) {
                    playPauseImage.setImageResource(R.drawable.my_bl_sdk_ic_pause_n)
                } else {
                    playPauseImage.setImageResource(R.drawable.my_bl_sdk_ic_play_n)
                }
            itemView.setOnClickListener {
                videoItemClickFunc?.invoke(getItem(absoluteAdapterPosition),false)
            }

        }

    }
    inner class GridViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView){
        private var titleTextView: TextView = itemView.findViewById(R.id.videoTitle)
        private var subTitleTextView:TextView = itemView.findViewById(R.id.videoDesc)
        private var playPauseImage:ImageView = itemView.findViewById(R.id.play_pause)
        private var videoImage:ImageView = itemView.findViewById(R.id.videoImage)

        fun bind(item: Video){

            titleTextView.text = item.title
            if (item.isPlaying) {
                titleTextView.setTextColor( ContextCompat.getColor(itemView.context,R.color.my_sdk_color_primary))
            } else {
                titleTextView.setTextColor(ContextCompat.getColor(itemView.context,R.color.my_sdk_down_title))
            }
            subTitleTextView.text = item.artist

            Glide.with(itemView.context)
                .load(item.image)
                .placeholder(R.drawable.my_bl_sdk_default_video)
                .into(videoImage)


            if (item.isPlaystate) {
                playPauseImage.setImageResource(R.drawable.my_bl_sdk_ic_pause_n)
            } else {
                playPauseImage.setImageResource(R.drawable.my_bl_sdk_ic_play_n)
            }
            itemView.setOnClickListener {
                videoItemClickFunc?.invoke(getItem(absoluteAdapterPosition),false)
            }
        }
    }

    enum class ViewType{
        LIST,
        GRID
    }
}
internal class  VideoDiffCallBack: DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem.contentID == newItem.contentID
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem == newItem
    }

}