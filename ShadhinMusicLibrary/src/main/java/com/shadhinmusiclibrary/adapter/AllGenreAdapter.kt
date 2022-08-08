package com.shadhinmusiclibrary.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.DataModel

class AllGenreAdapter() : RecyclerView.Adapter<AllGenreAdapter.DataAdapterViewHolder>() {
    private val adapterData = mutableListOf<DataModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapterViewHolder {
        val layout = when (viewType) {
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
        holder.bind(adapterData[position])
    }

    override fun getItemCount(): Int = adapterData.size

    override fun getItemViewType(position: Int): Int {
        return when (adapterData[position]) {
            is DataModel.Artist -> VIEW_ARTIST
            is DataModel.TopTrending -> VIEW_TOP_TRENDING
            is DataModel.BrowseAll -> VIEW_BROWSE_ALL
            is DataModel.Ad -> VIEW_AD
            is DataModel.Download -> VIEW_DOWNLOAD
            is DataModel.PopularAmarTunes -> VIEW_POPULAR_AMAR_TUNES
            is DataModel.PopularBands -> VIEW_POPULAR_BANDS
            is DataModel.MadeForYou -> VIEW_MADE_FOR_YOU
            is DataModel.LatestRelease -> VIEW_LATEST_RELEASE
            is DataModel.PopularPodcast -> VIEW_POPULAR_PODCAST
            is DataModel.BlOffers -> VIEW_BL_MUSIC_OFFERS
            is DataModel.TrendingMusicVideo -> VIEW_TRENDING_MUSIC_VIDEO

            else -> {
                throw IllegalArgumentException("Invalid view type")

            }
        }
    }

    fun setData(data: List<DataModel>) {
        adapterData.apply {
            clear()
            addAll(data)
        }
    }

    class DataAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private fun bindArtist() {
            Log.d("Hello", "Loading")
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView2)
            recyclerView.layoutManager =
                GridLayoutManager(itemView.context,2)
            recyclerView.adapter = BrowseAllGenresAdapter()

        }


        private fun bindTopTrending() {
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = TopTrendingAdapter()
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

        }

        private fun bindPopularAmarTunes() {

        }

        private fun bindPopularBands() {

            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = ArtistAdapter()

        }

        private fun bindMadeForYou() {
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = TopTrendingAdapter()

        }

        private fun bindLatestRelease() {
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = TopTrendingAdapter()

        }

        private fun bindPopularPodcast() {
            val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = TopTrendingAdapter()

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
        }

        fun bind(dataModel: DataModel) {
            when (dataModel) {
                is DataModel.Artist -> bindArtist()
                is DataModel.TopTrending -> bindTopTrending()
                is DataModel.BrowseAll -> bindBrowseAll()
                is DataModel.Ad -> bindAd()
                is DataModel.Download -> bindDownload()
                is DataModel.PopularAmarTunes -> bindPopularAmarTunes()
                is DataModel.PopularBands -> bindPopularBands()
                is DataModel.MadeForYou -> bindMadeForYou()
                is DataModel.LatestRelease -> bindLatestRelease()
                is DataModel.PopularPodcast -> bindPopularPodcast()
                is DataModel.BlOffers -> bindBlOffers()
                is DataModel.TrendingMusicVideo -> bindTrendingMusic()
//                is DataModel.BlOffers -> bindBlOffers(dataModel)

                else -> {}
            }
        }
    }


    companion object {
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