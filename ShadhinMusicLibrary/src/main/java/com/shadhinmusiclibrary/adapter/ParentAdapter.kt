package com.shadhinmusiclibrary.adapter


import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.callBackService.DownloadClickCallBack
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.callBackService.SearchClickCallBack
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.RBTDATA


internal class ParentAdapter(var homeCallBack: HomeCallBack, val searchCb: SearchClickCallBack, val downloadClickCallBack: DownloadClickCallBack) :
    RecyclerView.Adapter<ParentAdapter.DataAdapterViewHolder>() {

    private var homeListData: MutableList<HomePatchItem> = mutableListOf()
    var search: HomePatchItem? = null
    var download: HomePatchItem? = null
    private var rbtData: MutableList<RBTDATA> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapterViewHolder {
        val layout = when (viewType) {
            VIEW_SEARCH -> R.layout.my_bl_sdk_item_search
            VIEW_ARTIST -> R.layout.my_bl_sdk_item_artist
            VIEW_PLAYLIST -> R.layout.my_bl_sdk_item_playlist
            VIEW_RELEASE -> R.layout.my_bl_sdk_item_release_patch
            VIEW_POPULAR_PODCAST -> R.layout.my_bl_sdk_item_release_patch
            VIEW_TRENDING_MUSIC_VIDEO -> R.layout.my_bl_sdk_item_trending_music_videos
            // VIEW_AD -> R.layout.item_ad
            VIEW_DOWNLOAD -> R.layout.my_bl_sdk_item_my_fav
            VIEW_POPULAR_AMAR_TUNES -> R.layout.my_bl_sdk_item_popular_amar_tunes
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

    override fun getItemCount(): Int = homeListData.size

    override fun getItemViewType(position: Int): Int {

        return when (homeListData.get(position).Design) {
            "search" -> VIEW_SEARCH
            "Artist" -> VIEW_ARTIST
            "Playlist" -> VIEW_PLAYLIST
            "Release" -> VIEW_RELEASE
            "Track" -> VIEW_RELEASE
            "Podcast" -> VIEW_POPULAR_PODCAST
            "SmallVideo" -> VIEW_TRENDING_MUSIC_VIDEO
            "amarTune" -> VIEW_POPULAR_AMAR_TUNES
            "download" -> VIEW_DOWNLOAD

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

    var downloadNotAdded = true
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<HomePatchItem>) {
        val size = this.homeListData.size
        if (this.homeListData.isEmpty()) {
            for (item in data.indices) {
                search =
                    HomePatchItem("007", "searchBar", data[item].Data, "search", "search", 0, 0)
              // download = HomePatchItem("002","download",data[item].Data,"download","download",0,0)
            }
            this.homeListData.add(search!!)
            //this.homeListData.add(download!!)


        }
        if (this.homeListData.size >= 3 && downloadNotAdded){
            downloadNotAdded = false
            download = HomePatchItem("002","download", listOf(),"download","download",0,0)
            this.homeListData.add(download!!)
        }
//        var exists :Boolean = false
//        if (this.homeListData.isNotEmpty() && this.homeListData.size >= 3) {
//
//            for (item in data.indices) {
//                Log.e("TaG","Items: "+ data[item].ContentType)
//                download = HomePatchItem("002",
//                    "download",
//                    listOf(),
//                    "download",
//                    "download",
//                    0,
//                    0)
//                if (homeListData[item].ContentType==data[item].ContentType){
//                    exists = true
//                    Log.e("TaG","Items123: "+ exists)
//
//                }
//            }
//
//            if(exists) {
//                Log.e("TaG","Items321: "+ exists)
//                this.homeListData.add(download!!)
//
//            }
//
//        }

        this.homeListData.addAll(data)
        val sizeNew = this.homeListData.size
        notifyItemRangeChanged(size, sizeNew)


    }


    inner class DataAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mContext = itemView.context

        private fun bindSearch(homePatchItemModel: HomePatchItem) {
            val search: TextView = itemView.findViewById(R.id.sv_search_input)
            search.setOnClickListener {
                //call back SearchFragment
                search.isEnabled = false
                searchCb.clickOnSearchBar(homePatchItemModel)
                search.isEnabled = true
            }
        }

        private fun bindArtist(homePatchItem: HomePatchItem, homeCallBack: HomeCallBack) {
            val seeAll: TextView = itemView.findViewById(R.id.tvSeeALL)
            val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
            tvTitle.text = homePatchItem.Name
            seeAll.setOnClickListener {
                //PopularArtistsFragment
                homeCallBack.onClickSeeAll(homePatchItem)
            }

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
                //TopTrendingFragment
                homeCallBack.onClickSeeAll(homePatchItem)
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
            recyclerView.adapter = HomeContentPlaylistAdapter(homePatchItem, homeCallBack)
        }

        private fun bindPopularPodcast(homePatchItem: HomePatchItem) {
            val title: TextView = itemView.findViewById(R.id.tvTitle)
            title.text = homePatchItem.Name
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = HomePodcastAdapter(homePatchItem, homeCallBack)
            val seeAll: TextView = itemView.findViewById(R.id.tvSeeALL)
            seeAll.setOnClickListener {
                //PodcastDetailsFragment
                homeCallBack.onClickSeeAll(homePatchItem)
            }
        }

        private fun bindPopularAmarTunes(
            homePatchItem: HomePatchItem,
        ) {
            val title: TextView = itemView.findViewById(R.id.tvTitle)
            title.text = homePatchItem.Name
            val image: ShapeableImageView = itemView.findViewById(R.id.image)
            Glide.with(itemView.context).load(homePatchItem.Data[0].image).into(image)
            val seeAll: TextView = itemView.findViewById(R.id.tvSeeALL)
            seeAll.setOnClickListener {
                //  homeCallBack.onClickSeeAll(homePatchItem)
                // Log.d("TAG","CLICK ITEM: "+ homePatchItem)
            }
            Log.e("TAG", "URL1233444: " + rbtData)
            itemView.setOnClickListener {
                ShadhinMusicSdkCore.openPatch(itemView.context, "BNMAIN01")
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

        private fun bindDownload(homePatchItemModel: HomePatchItem) {
            val download: LinearLayout = itemView.findViewById(R.id.Download)
            val watchlater:LinearLayout = itemView.findViewById(R.id.WatchLater)
            download.setOnClickListener {
                downloadClickCallBack.clickOnDownload(homePatchItemModel)
            }
            watchlater.setOnClickListener {
                downloadClickCallBack.clickOnWatchlater(homePatchItemModel)
            }
        }


        private fun bindPopularBands(homePatchItemModel: HomePatchItem) {
            val seeAll: TextView = itemView.findViewById(R.id.tvSeeALL)
            val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
            tvTitle.text = homePatchItemModel.Name
            seeAll.setOnClickListener {
                //PopularArtistsFragment
                homeCallBack.onClickSeeAll(homePatchItemModel)
            }

            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
           // recyclerView.adapter = ArtistAdapter(homePatchItemModel, homeCallBack)
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
            recyclerView.adapter = TopTrendingVideosAdapter(
                homePatchItemModel,
                homePatchDetail = homeListData[absoluteAdapterPosition].Data
            )
        }

        fun bind(homePatchItemModel: HomePatchItem?) {
            when (homePatchItemModel?.Design) {
                "search" -> bindSearch(homePatchItemModel)
                "Artist" -> bindArtist(homePatchItemModel, homeCallBack)
                "Playlist" -> bindPlaylist(homePatchItemModel)
                "Release" -> bindRelease(homePatchItemModel)
                "Track" -> bindRelease(homePatchItemModel)
                "Podcast" -> bindPopularPodcast(homePatchItemModel)
                "SmallVideo" -> bindTrendingMusic(homePatchItemModel)
                "amarTune" -> bindPopularAmarTunes(homePatchItemModel)
                "download" -> bindDownload(homePatchItemModel)
                //"Artist"->bindPopularBands(homePatchItemModel)
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


        const val VIEW_TYPE = 10

    }
}









