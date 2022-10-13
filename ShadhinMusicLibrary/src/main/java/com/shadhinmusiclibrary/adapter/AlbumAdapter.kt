package com.shadhinmusiclibrary.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.BottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.OnItemClickCallback
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModelData
import com.shadhinmusiclibrary.fragments.artist.ArtistContentData
import com.shadhinmusiclibrary.utils.TimeParser


internal open class AlbumAdapter(
    private val itemClickCB: OnItemClickCallback,
    private val bottomSheetDialogItemCallback: BottomSheetDialogItemCallback
) :
    RecyclerView.Adapter<AlbumAdapter.AlbumVH>() {
    private var rootDataContent: HomePatchDetail? = null
    private var dataSongDetail: MutableList<SongDetail> = mutableListOf()
    private var artistAlbumModel: MutableList<ArtistAlbumModelData> = mutableListOf()
    private var isPlaying = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumVH {
        val layout = when (viewType) {
            VIEW_ALBUM -> R.layout.playlist_header
            VIEW_TRACK_ITEM -> R.layout.latest_music_view_item
            VIEW_OTHER_ALBUM -> R.layout.item_release_patch
            else -> throw IllegalArgumentException("Invalid view type")
        }
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)
        return AlbumVH(view)
    }

    override fun onBindViewHolder(holder: AlbumVH, position: Int) {
        when (holder.itemViewType) {
            0 -> holder.bindRoot(rootDataContent!!)
            1 -> holder.bindTrackItem(dataSongDetail[position - 1])
            2-> holder.bindAlbum(artistAlbumModel)
        }

        if (dataSongDetail.isNotEmpty()) {
            // val mSongDetItem = dataSongDetail[position-1]
            when (holder.itemViewType) {
                0 -> {
                    itemClickCB.getCurrentVH(holder, dataSongDetail)
                    holder.ivPlayBtn?.setOnClickListener {
                        /*
                        if player.state == playing && player.rootid == this.rootId{
                            player.pause
                        }else if player.state = paused && player.rootid == this.rootId{
                            player.play
                        }else{
                            this.startPlaying(trackArray, index = 0)
                        }
                         */

                        itemClickCB.onRootClickItem(dataSongDetail, position)
                    }
                }
                1 -> {
                    holder.itemView.setOnClickListener {
                        if (holder.itemViewType == VIEW_TRACK_ITEM) {
//                            val mSongDetItem = dataSongDetail[position - 1]
                            itemClickCB.onClickItem(dataSongDetail, (position - 1))
                        }
                    }
//                    holder.menu?.setOnClickListener {
//                        bottomSheetDialogItemCallback.onClickBottomItem(dataSongDetail,(position-1))
//                    }
                }
            }
        }
    }

    fun setCurrentPlayingState(isPlayingState: Boolean) {
        isPlaying = isPlayingState
    }

    override fun getItemCount(): Int {
        var count = dataSongDetail.size
        if (rootDataContent != null) {
            count += 1
        }
        return count
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_ALBUM
            1 -> VIEW_TRACK_ITEM

            else->
                VIEW_OTHER_ALBUM

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSongData(
        data: MutableList<SongDetail>,


        ) {
        this.dataSongDetail = data
//        this.artistAlbumModel = artistAlbumModel
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setRootData(data: HomePatchDetail?) {
        this.rootDataContent = data
        notifyDataSetChanged()
    }

    fun getCurrentItem(): SongDetail {
        return dataSongDetail[0]
    }

    inner class AlbumVH(private val viewItem: View) :
        RecyclerView.ViewHolder(viewItem) {
        val mContext = viewItem.context
        private lateinit var ivThumbCurrentPlayItem: ImageView
        private lateinit var tvCurrentAlbumName: TextView
        private lateinit var tvArtistName: TextView

        //        private lateinit var ivFavorite: ImageView
        var ivPlayBtn: ImageView? = null
        var menu: ImageView? = null

        //        private lateinit var ivShareBtnFab: ImageView
        fun bindRoot(root: HomePatchDetail) {
            ivThumbCurrentPlayItem =
                viewItem.findViewById(R.id.iv_thumb_current_play_item)
            Glide.with(mContext)
                .load(root.getImageUrl300Size())
                .into(ivThumbCurrentPlayItem)
            tvCurrentAlbumName =
                viewItem.findViewById(R.id.tv_current_album_name)
            tvCurrentAlbumName.text = root.title
            if (root.title.isNullOrEmpty()) {
                tvCurrentAlbumName.text = root.AlbumName
            }
//            if(root.Artist.isNullOrEmpty()){
//                tvArtistName.text = rootDataContent?.AlbumName
//            }
            tvArtistName =
                viewItem.findViewById(R.id.tv_artist_name)
            tvArtistName.text = root.Artist

//            ivFavorite = viewItem.findViewById(R.id.iv_favorite)
            ivPlayBtn = viewItem.findViewById(R.id.iv_play_btn)
            menu = viewItem.findViewById(R.id.iv_song_menu_icon)
//            if (isPlaying) {
//                ivPlayBtn!!.setImageResource(R.drawable.ic_pause_circle_filled)
//            } else {
//                ivPlayBtn!!.setImageResource(R.drawable.ic_play_linear)
//            }
//            ivShareBtnFab = viewItem.findViewById(R.id.iv_share_btn_fab)
        }

        fun bindTrackItem(mSongDetail: SongDetail) {
            val sivSongIcon: ImageView = viewItem.findViewById(R.id.siv_song_icon)
            Glide.with(mContext)
                .load(mSongDetail.getImageUrl300Size())
                .into(sivSongIcon)
            val tvSongName: TextView = viewItem.findViewById(R.id.tv_song_name)
            tvSongName.text = mSongDetail.title

            val tvSingerName: TextView = viewItem.findViewById(R.id.tv_singer_name)
            tvSingerName.text = mSongDetail.artist

            val tvSongLength: TextView = viewItem.findViewById(R.id.tv_song_length)
            tvSongLength.text = TimeParser.secToMin(mSongDetail.duration)
            val ivSongMenuIcon: ImageView = viewItem.findViewById(R.id.iv_song_menu_icon)
            ivSongMenuIcon.setOnClickListener {
//                bottomSheetDialogItemCallback.onClickBottomItem(
//                    mSongDetail,
//                    artistDetails = ArtistContentData(
//                        "",
//                        "",
//                        "",
//                        "",
//                        "",
//                        0,
//                        "",
//                        "",
//                        "",
//                        "",
//                        "",
//                        "",
//                        "",
//                        ""
//                    )
//                )
            }

        }

        fun bindAlbum(artistAlbumModelData: MutableList<ArtistAlbumModelData>) {
            val seeAll: TextView = itemView.findViewById(R.id.tvSeeALL)
            val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
           // tvTitle.text = homePatchItem.Name
            seeAll.visibility = GONE

//            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
//            recyclerView.layoutManager =
//                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
//            recyclerView.adapter = ArtistOthersAlbumListAdapter(artistAlbumModelData!!,homeCallBack)
        }


    }

    private companion object {
        const val VIEW_ALBUM = 0
        const val VIEW_TRACK_ITEM = 1
        const val VIEW_OTHER_ALBUM =2
    }
}