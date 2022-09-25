package com.shadhinmusiclibrary.adapter


import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.callBackService.HomeCallBack

import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.fragments.amar_tunes.AmartunesWebviewFragment


class ParentAdapter(val homeCallBack: HomeCallBack) :
    RecyclerView.Adapter<ParentAdapter.DataAdapterViewHolder>() {
    private var homeListData: MutableList<HomePatchItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapterViewHolder {
        val layout = when (viewType) {
            VIEW_SEARCH-> R.layout.item_search
            VIEW_ARTIST -> R.layout.item_artist
            VIEW_PLAYLIST -> R.layout.item_playlist
            VIEW_RELEASE -> R.layout.item_release_patch
            VIEW_POPULAR_PODCAST -> R.layout.item_release_patch
            VIEW_TRENDING_MUSIC_VIDEO -> R.layout.item_trending_music_videos
          // VIEW_AD -> R.layout.item_ad
//            VIEW_DOWNLOAD -> R.layout.item_my_fav
            VIEW_POPULAR_AMAR_TUNES -> R.layout.item_popular_amar_tunes
//            VIEW_POPULAR_BANDS -> R.layout.item_top_trending
//            VIEW_MADE_FOR_YOU -> R.layout.item_top_trending
//            VIEW_LATEST_RELEASE -> R.layout.item_top_trending
//            VIEW_POPULAR_PODCAST -> R.layout.item_top_trending
//            VIEW_BL_MUSIC_OFFERS -> R.layout.item_my_bl_offers

            else -> throw IllegalArgumentException("Invalid view type")
        }

        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)

        return DataAdapterViewHolder(view)

    }

    override fun onBindViewHolder(holder: DataAdapterViewHolder, position: Int) {
        holder.bind(homeListData.get(position))
    }

    override fun getItemCount(): Int = homeListData.size ?: 0

    override fun getItemViewType(position: Int): Int {

        return when (homeListData.get(position).Design) {
            "search" -> VIEW_SEARCH
            "Artist" -> VIEW_ARTIST
            "Playlist" -> VIEW_PLAYLIST
            "Release" -> VIEW_RELEASE
            "Track" -> VIEW_RELEASE
            "Podcast"-> VIEW_POPULAR_PODCAST
            "SmallVideo"-> VIEW_TRENDING_MUSIC_VIDEO
            "amarTune" -> VIEW_POPULAR_AMAR_TUNES
//            "Artist" -> VIEW_AD
            //adapterData[0].data[0].Design -> VIEW_ARTIST
            //           is DataModel.Artist -> VIEW_ARTIST
//            is DataModel.Search -> VIEW_SEARCH
//            is DataModel.Artist -> VIEW_ARTIST
//            is DataModel.TopTrending -> VIEW_TOP_TRENDING
//            is DataModel.BrowseAll -> VIEW_BROWSE_ALL
//            is DataModel.Ad -> VIEW_AD
//            is DataModel.Download -> VIEW_DOWNLOAD
//            is DataModel.PopularAmarTunes -> VIEW_POPULAR_AMAR_TUNES
//            is DataModel.PopularBands -> VIEW_POPULAR_BANDS
//            is DataModel.MadeForYou -> VIEW_MADE_FOR_YOU
//            is DataModel.LatestRelease -> VIEW_LATEST_RELEASE
//            is DataModel.PopularPodcast -> VIEW_POPULAR_PODCAST
//            is DataModel.BlOffers -> VIEW_BL_MUSIC_OFFERS
//            is DataModel.TrendingMusicVideo -> VIEW_TRENDING_MUSIC_VIDEO


            else -> {
                -1
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<HomePatchItem>) {
        var size = this.homeListData.size
        if (this.homeListData.isEmpty()) {
            val search = HomePatchItem("007", "searchBar", emptyList(), "search", "search", 0, 0)
            this.homeListData.add(search)
        }
        this.homeListData.addAll(data)
        var sizeNew = this.homeListData.size
        notifyItemRangeChanged(size, sizeNew)
//        this.homeListData.addAll(data)
//        notifyDataSetChanged()
    }

    inner class DataAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mContext = itemView.context
        private fun bindArtist(homePatchItem: HomePatchItem, homeCallBack: HomeCallBack) {
            val seeAll: TextView = itemView.findViewById(R.id.tvSeeALL)
            val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
            tvTitle.text = homePatchItem.Name
            seeAll.setOnClickListener {
                homeCallBack.onClickSeeAll(homePatchItem)
                // val seeAll:TextView = itemView.findViewById(R.id.tvSeeALL)
//            seeAll.setOnClickListener {
//                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
//                manager.beginTransaction()
//                    .replace(R.id.container , PopularArtistsFragment.newInstance(data))
//                    .addToBackStack("Popular Artist")
//                    .commit()

//                Log.e("dataget","$data")
//
            }
            // }

            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = ArtistAdapter(homePatchItem, homeCallBack)


        }

        fun bindRelease(homePatchItem: HomePatchItem) {
            val seeAll: TextView = itemView.findViewById(R.id.tvSeeALL)
            val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
            tvTitle.text = homePatchItem.Name
           seeAll.setOnClickListener {
              homeCallBack.onClickSeeAll(homePatchItem)
//                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
//                manager.beginTransaction()
//                    .replace(R.id.container, TopTrendingFragment.newInstance(data))
//                    .addToBackStack("Top Trending")
//                    .commit()
           }
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = HomeReleaseAdapter(homePatchItem, homeCallBack)

        }


        private fun bindPlaylist(homePatchItem: HomePatchItem) {
            val seeAll: TextView = itemView.findViewById(R.id.tvSeeALL)
            val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
            tvTitle.text = homePatchItem.Name
           seeAll.setOnClickListener {

               homeCallBack.onClickSeeAll(homePatchItem)
           }
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = HomeContentPlaylistAdapter(homePatchItem,homeCallBack)

        }
        private fun bindPopularPodcast(homePatchItem: HomePatchItem) {
            val title: TextView = itemView.findViewById(R.id.tvTitle)
            title.text = homePatchItem.Name
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = HomePodcastAdapter(homePatchItem,homeCallBack)
            val seeAll: TextView = itemView.findViewById(R.id.tvSeeALL)
            seeAll.setOnClickListener {
                homeCallBack.onClickSeeAll(homePatchItem)
               Log.d("TAG","CLICK ITEM: "+ homePatchItem)
            }

//            itemView.setOnClickListener {
//                val manager: FragmentManager =
//                    (mContext as AppCompatActivity).supportFragmentManager
//                manager.beginTransaction()
//                    .replace(R.id.container, PodcastDetailsFragment.newInstance())
//                    .addToBackStack("Fragment")
//                    .commit()
//            }
        }
        private fun bindPopularAmarTunes(homePatchItem: HomePatchItem) {
            val title: TextView = itemView.findViewById(R.id.tvTitle)
            title.text = homePatchItem.Name
            val image:ShapeableImageView = itemView.findViewById(R.id.image)
              Glide.with(itemView.context).load(homePatchItem.Data[0].image).into(image)
            itemView.setOnClickListener {
                val manager: FragmentManager =
                (mContext as AppCompatActivity).supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.container, AmartunesWebviewFragment.newInstance())
                .addToBackStack("Fragment")
                .commit()
            }

        }
        private fun bindAd() {
          //  Do your view assignment here from the data model
//            itemView.findViewById<ConstraintLayout>(R.id.clRoot)?.setBackgroundColor(item.bgColor)
//            itemView.findViewById<AppCompatTextView>(R.id.tvNameLabel)?.text = item.title
//            itemView.setOnClickListener {
//                val manager: FragmentManager =
//                    (mContext as AppCompatActivity).supportFragmentManager
//                manager.beginTransaction()
//                    .replace(R.id.container, AmartunesWebviewFragment.newInstance())
//                    .addToBackStack("Fragment")
//                    .commit()
//            }
        }

        private fun bindDownload() {
            val download: LinearLayout = itemView.findViewById(R.id.Download)
//            download.setOnClickListener {
//                val manager: FragmentManager =
//                    (mContext as AppCompatActivity).supportFragmentManager
//                manager.beginTransaction()
//                    .add(R.id.container, DownloadFragment.newInstance())
//                    .addToBackStack("AllGenresDetailsFragment")
//                    .commit()
//
//            }
        }



        private fun bindPopularBands() {
            val seeAll: TextView = itemView.findViewById(R.id.tvSeeALL)
            seeAll.setOnClickListener {
//                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
//                manager.beginTransaction()
//                    .replace(R.id.container, PopularBandsFragment.newInstance())
//                    .commit()
//               homeCallBack.onClickSeeAll()
            }
            val title: TextView = itemView.findViewById(R.id.tvTitle)
            title.text = "Popular Bands"
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            // recyclerView.adapter = ArtistAdapter(data)

        }

        private fun bindMadeForYou() {
            val title: TextView = itemView.findViewById(R.id.tvTitle)
            title.text = "Made For You"
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            //  recyclerView.adapter = TopTrendingAdapter(data)

        }

        private fun bindLatestRelease() {
            val title: TextView = itemView.findViewById(R.id.tvTitle)
            title.text = "Latest Release"
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            // recyclerView.adapter = TopTrendingAdapter(data)
        }



        private fun bindBlOffers() {
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = MyBLOffersAdapter()
        }

        private fun bindTrendingMusic(homePatchItemModel: HomePatchItem) {
              val title: TextView = itemView.findViewById(R.id.tvTitle)
            title.text = homePatchItemModel.Name

            val seeAll: TextView = itemView.findViewById(R.id.tvSeeALL)
            seeAll.setOnClickListener {
                homeCallBack.onClickSeeAll(homePatchItemModel)

            }
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = TopTrendingVideosAdapter(homePatchItemModel, homePatchDetail = homeListData[absoluteAdapterPosition].Data)
        }


        fun bind(homePatchItemModel: HomePatchItem?) {
            when (homePatchItemModel?.Design) {
                "search"->bindAd()
                "Artist" -> bindArtist(homePatchItemModel, homeCallBack)
                "Playlist" -> bindPlaylist(homePatchItemModel)
                "Release" -> bindRelease(homePatchItemModel)
                "Track" -> bindRelease(homePatchItemModel)
                "Podcast" ->bindPopularPodcast(homePatchItemModel)
                "SmallVideo"-> bindTrendingMusic(homePatchItemModel)
                "amarTune" -> bindPopularAmarTunes(homePatchItemModel)
//                "Artist" ->bindAd()
            }


            /*when (dataModel) {
                     dataModel-> bindArtist(dataModel!!)
 //                is DataModel.Search -> bindSearch()
 //                is DataModel.Artist -> bindArtist()
 //                is DataModel.TopTrending -> bindTopTrending()
 //                is DataModel.BrowseAll -> bindBrowseAll()
 //                is DataModel.Ad -> bindAd()
 //                is DataModel.Download -> bindDownload()
 //                is DataModel.PopularAmarTunes -> bindPopularAmarTunes()
 //                is DataModel.PopularBands -> bindPopularBands()
 //                is DataModel.MadeForYou -> bindMadeForYou()
 //                is DataModel.LatestRelease -> bindLatestRelease()
 //                is DataModel.PopularPodcast -> bindPopularPodcast()
 //                is DataModel.BlOffers -> bindBlOffers()
 //                is DataModel.TrendingMusicVideo -> bindTrendingMusic()
 ////                is DataModel.BlOffers -> bindBlOffers(dataModel)

             }*/
        }


    }

    private companion object {
        val VIEW_SEARCH = 0
        val VIEW_ARTIST = 1
        val VIEW_RELEASE = 2
        val VIEW_PLAYLIST = 3
        val VIEW_AD = 4
        val VIEW_DOWNLOAD = 5
        val VIEW_POPULAR_AMAR_TUNES = 6
        val VIEW_POPULAR_BANDS = 7
        val VIEW_MADE_FOR_YOU = 8
        val VIEW_LATEST_RELEASE = 9
        val VIEW_POPULAR_PODCAST = 10
        val VIEW_BL_MUSIC_OFFERS = 11
        val VIEW_TRENDING_MUSIC_VIDEO = 12
    }
}









