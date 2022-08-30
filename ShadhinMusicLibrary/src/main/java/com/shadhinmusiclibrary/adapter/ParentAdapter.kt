package com.shadhinmusiclibrary.adapter



import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.Data
import com.shadhinmusiclibrary.data.model.HomeData
import com.shadhinmusiclibrary.fragments.*


class ParentAdapter() : RecyclerView.Adapter<ParentAdapter.DataAdapterViewHolder>() {
    private var homeData:HomeData?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapterViewHolder {
        val layout = when (viewType) {
//            VIEW_SEARCH-> R.layout.item_search
            VIEW_ARTIST -> R.layout.item_artist
            VIEW_TOP_TRENDING -> R.layout.item_top_trending
            VIEW_BROWSE_ALL -> R.layout.item_browse_all_genre
            VIEW_AD -> R.layout.item_ad
            VIEW_DOWNLOAD -> R.layout.item_my_fav
            VIEW_POPULAR_AMAR_TUNES -> R.layout.item_popular_amar_tunes
            VIEW_POPULAR_BANDS -> R.layout.item_top_trending
            VIEW_MADE_FOR_YOU -> R.layout.item_top_trending
            VIEW_LATEST_RELEASE -> R.layout.item_top_trending
            VIEW_POPULAR_PODCAST -> R.layout.item_top_trending
            VIEW_BL_MUSIC_OFFERS -> R.layout.item_my_bl_offers
            VIEW_TRENDING_MUSIC_VIDEO -> R.layout.item_trending_music_videos
            else -> throw IllegalArgumentException("Invalid view type")
        }

        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)

        return DataAdapterViewHolder(view)

    }

    override fun onBindViewHolder(holder: DataAdapterViewHolder, position: Int) {
        holder.bind(homeData?.data?.get(position))
    }

    override fun getItemCount(): Int = homeData?.data?.size?:0

    override fun getItemViewType(position: Int): Int {

        return when (homeData?.data?.get(position)?.Design) {
               "Artist"-> VIEW_ARTIST
               "Playlist"-> VIEW_TOP_TRENDING
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
                 0
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data:HomeData) {
        this.homeData = data
        notifyDataSetChanged()
    }

    class DataAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      val  context = itemView.getContext()
        private fun bindArtist(data: Data) {
//            val seeAll:TextView = itemView.findViewById(R.id.tvSeeALL)
//            seeAll.setOnClickListener {
//                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
//                manager.beginTransaction()
//                    .replace(R.id.container , PopularArtistsFragment.newInstance())
//                    .addToBackStack("Popular Artist")
//                    .commit()
//
//            }

            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView1)
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = ArtistAdapter(data)

        }


        private fun bindTopTrending(data: Data) {
            val seeAll:TextView = itemView.findViewById(R.id.tvSeeALL)
            seeAll.setOnClickListener {
                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
                manager.beginTransaction()
                    .replace(R.id.container, TopTrendingFragment.newInstance())
                    .addToBackStack("Top Trending")
                    .commit()

            }
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = TopTrendingAdapter(data)
            //Do your view assignment here from the data model
//            itemView.findViewById<AppCompatTextView>(R.id.tvName)?.text = item.name
//            itemView.findViewById<AppCompatTextView>(R.id.tvOrganization)?.text = item.organization
//            itemView.findViewById<AppCompatTextView>(R.id.tvDesignation)?.text = item.designation
        }

        //        private fun bindBrowseAll(item: DataModel.BrowseAll) {
//            //Do your view assignment here from the data model
////            itemView.findViewById<ConstraintLayout>(R.id.clRoot)?.setBackgroundColor(item.bgColor)
////            itemView.findViewById<AppCompatTextView>(R.id.tvNameLabel)?.text = item.title
//        }
        private fun bindBrowseAll() {
            val seeAll:TextView = itemView.findViewById(R.id.tvSeeALL)
            seeAll.setOnClickListener {
                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
                manager.beginTransaction()
                    .add(R.id.container ,AllGenresDetailsFragment.newInstance())
                    .addToBackStack("AllGenresDetailsFragment")
                    .commit()


            }
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = BrowseAllGenresAdapter()

            //Do your view assignment here from the data model
//            itemView.findViewById<ConstraintLayout>(R.id.clRoot)?.setBackgroundColor(item.bgColor)
//            itemView.findViewById<AppCompatTextView>(R.id.tvNameLabel)?.text = item.title
        }

        private fun bindAd() {
            //Do your view assignment here from the data model
//            itemView.findViewById<ConstraintLayout>(R.id.clRoot)?.setBackgroundColor(item.bgColor)
//            itemView.findViewById<AppCompatTextView>(R.id.tvNameLabel)?.text = item.title
        }

        private fun bindDownload() {
              val download: LinearLayout = itemView.findViewById(R.id.Download)
            download.setOnClickListener {
                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
                manager.beginTransaction()
                    .add(R.id.container ,DownloadFragment.newInstance())
                    .addToBackStack("AllGenresDetailsFragment")
                    .commit()

            }
        }

        private fun bindPopularAmarTunes() {

        }

        private fun bindPopularBands() {
            val seeAll:TextView = itemView.findViewById(R.id.tvSeeALL)
            seeAll.setOnClickListener {
                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
                manager.beginTransaction()
                    .replace(R.id.container , PopularBandsFragment.newInstance())
                    .commit()

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

        private fun bindPopularPodcast() {
            val title: TextView = itemView.findViewById(R.id.tvTitle)
            title.text = "Popular Podcast"
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
           // recyclerView.adapter = TopTrendingAdapter(data)
            val seeAll:TextView = itemView.findViewById(R.id.tvSeeALL)
            seeAll.setOnClickListener {
                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
                manager.beginTransaction()
                    .replace(R.id.container, PodcastFragment.newInstance())
                    .addToBackStack("Fragment")
                    .commit()


            }
            itemView.setOnClickListener {
                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
                manager.beginTransaction()
                    .replace(R.id.container, PodcastDetailsFragment.newInstance())
                    .addToBackStack("Fragment")
                    .commit()
            }
        }

        private fun bindBlOffers() {
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = MyBLOffersAdapter()

        }

        private fun bindTrendingMusic() {
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = TopTrendingVideosAdapter()
            val seeAll:TextView = itemView.findViewById(R.id.tvSeeALL)
            seeAll.setOnClickListener {
                val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
                manager.beginTransaction()
                    .replace(R.id.container , MusicFragment.newInstance())
                    .commit()

            }
        }
        private fun bindSearch(){

        }
        fun bind(dataModel: Data?) {
            when(dataModel?.Design){
                "Artist" -> bindArtist(dataModel)
                    "Playlist" -> bindTopTrending(dataModel)
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


    companion object {
       // val VIEW_SEARCH =0
        val VIEW_ARTIST = 0
        val VIEW_TOP_TRENDING = 1
        val VIEW_BROWSE_ALL = 2
        val VIEW_AD = 3
        val VIEW_DOWNLOAD = 4
        val VIEW_POPULAR_AMAR_TUNES = 5
        val VIEW_POPULAR_BANDS = 6
        val VIEW_MADE_FOR_YOU = 7
        val VIEW_LATEST_RELEASE = 8
        val VIEW_POPULAR_PODCAST = 9
        val VIEW_BL_MUSIC_OFFERS = 10
        val VIEW_TRENDING_MUSIC_VIDEO = 11
    }

}









