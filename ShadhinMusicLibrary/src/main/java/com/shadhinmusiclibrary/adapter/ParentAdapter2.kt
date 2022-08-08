package com.shadhinmusiclibrary.adapter


import android.view.LayoutInflater

import android.view.ViewGroup



//class ParentAdapter () : RecyclerView.Adapter<ParentAdapter.ViewHolder>() {
//
////    val VIEW_ARTIST = 0
////    val VIEW_TOP_TRENDING = 1
////    val VIEW_BROWSE_ALL = 2
////    val VIEW_AD = 3
////    val VIEW_DOWNLOAD = 4
////    val VIEW_mPOPULAR_AMAR_TUNES = 5
////    val VIEW_POPULAR_BANDS = 6
////    val VIEW_MADE_FOR_YOU = 7
////    val VIEW_LATEST_RELEASE = 8
////    val VIEW_POPULAR_PODCAST = 9
////    val VIEW_BL_MUSIC_OFFERS =10
////    val VIEW_TRENDING_MUSIC_VIDEO = 11
////    var scope = CoroutineScope(Dispatchers.IO)
////    override fun onCreateViewHolder(
////        parent: ViewGroup,
////        viewType: Int
////    ): ParentAdapter.ViewHolder {
////
////        val binding: ViewDataBinding
////
////        when (viewType) {
////            VIEW_ARTIST -> {
////                binding = DataBindingUtil.inflate(
////                    LayoutInflater.from(parent.context),
////                    R.layout.item_artist,
////                    parent,
////                    false
////                )
////                return ViewHolder(binding as ItemArtistBinding)
////            }
////            VIEW_TOP_TRENDING -> {
////                binding = DataBindingUtil.inflate(
////                    LayoutInflater.from(parent.context),
////                    R.layout.item_top_trending,
////                    parent,
////                    false
////                )
////                return ViewHolder(binding as ItemTopTrendingBinding)
////            }
////            VIEW_BROWSE_ALL -> {
////                binding = DataBindingUtil.inflate(
////                    LayoutInflater.from(parent.context),
////                    R.layout.item_browse_all_genre,
////                    parent,
////                    false
////                )
////                return ViewHolder(binding as ItemBrowseAllGenreBinding)
////            }
////            VIEW_AD -> {
////                binding = DataBindingUtil.inflate(
////                    LayoutInflater.from(parent.context),
////                    R.layout.item_ad,
////                    parent,
////                    false
////                )
////                return ViewHolder(binding as ItemAdBinding)
////            }
////            VIEW_DOWNLOAD -> {
////                binding = DataBindingUtil.inflate(
////                    LayoutInflater.from(parent.context),
////                    R.layout.item_my_fav,
////                    parent,
////                    false
////                )
////                return ViewHolder(binding as ItemMyFavBinding)
////            }
////            VIEW_POPULAR_AMAR_TUNES -> {
////                binding = DataBindingUtil.inflate(
////                    LayoutInflater.from(parent.context),
////                    R.layout.item_popular_amar_tunes,
////                    parent,
////                    false
////                )
////                return ViewHolder(binding as ItemPopularAmarTunesBinding)
////            }
////            VIEW_BL_MUSIC_OFFERS -> {
////                binding = DataBindingUtil.inflate(
////                    LayoutInflater.from(parent.context),
////                    R.layout.item_my_bl_offers,
////                    parent,
////                    false
////                )
////                return ViewHolder(binding as ItemMyBlOffersBinding)
////            }
////            VIEW_TRENDING_MUSIC_VIDEO -> {
////                binding = DataBindingUtil.inflate(
////                    LayoutInflater.from(parent.context),
////                    R.layout.item_trending_music_videos,
////                    parent,
////                    false
////                )
////                return ViewHolder(binding as ItemTrendingMusicVideosBinding)
////            }
////            else -> {
////                throw IllegalStateException("Illegal view type")
////            }
////        }
////
////    }
////
////    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
////        // holder.bindItems(albumsList[position])
////
////        holder.artistBinding?.let { binding ->
////            binding.recyclerView2.layoutManager =
////                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
////            binding.recyclerView2.adapter = ArtistAdapter()
////
////
////        }
////
////
////        holder.topTrendingBinding?.let { binding ->
////
////            binding.recyclerView.layoutManager =
////                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
////            binding.recyclerView.adapter = TopTrendingAdapter()
////
////
////        }
////
////        holder.browseAllGenreBinding.let { binding ->
////
////            binding?.recyclerView?.layoutManager =
////                LinearLayoutManager(binding?.root?.context, LinearLayoutManager.HORIZONTAL, false)
////            binding?.recyclerView?.adapter = BrowseAllGenresAdapter()
////
////
////        }
////        holder.blMusicOffersListBinding.let { binding ->
////
////            binding?.recyclerView?.layoutManager =
////                LinearLayoutManager(binding?.root?.context, LinearLayoutManager.HORIZONTAL, false)
////            binding?.recyclerView?.adapter = MyBLOffersAdapter()
////
////
////        }
////        holder.trendingMusicVideosBinding.let { binding ->
////
////            binding?.recyclerView?.layoutManager =
////                LinearLayoutManager(binding?.root?.context, LinearLayoutManager.HORIZONTAL, false)
////            binding?.recyclerView?.adapter = TopTrendingVideosAdapter()
////
////
////        }
//
//   // }
//
//
//
//
//
//
//
//
//
//
//
////    override fun getItemCount(): Int {
////        return 12
////    }
////
////
////    override fun getItemViewType(position: Int): Int {
////        return when (position) {
////            0 -> VIEW_ARTIST
////            1-> VIEW_TOP_TRENDING
////            2 -> VIEW_BROWSE_ALL
////            3-> VIEW_AD
////            4->VIEW_DOWNLOAD
////            5->VIEW_POPULAR_AMAR_TUNES
////            6->VIEW_ARTIST
////            7-> VIEW_TOP_TRENDING
////            8 ->VIEW_TOP_TRENDING
////            9->VIEW_TOP_TRENDING
////            10->VIEW_BL_MUSIC_OFFERS
////            11->VIEW_TRENDING_MUSIC_VIDEO
////            else->VIEW_ARTIST
////        }
////    }
////
////    inner class ViewHolder : RecyclerView.ViewHolder {
////
////        var artistBinding: ItemArtistBinding? = null
////
////
////        constructor(artistBinding: ItemArtistBinding) : super(artistBinding.root) {
////            this.artistBinding = artistBinding
////        }
////
////        var topTrendingBinding: ItemTopTrendingBinding? = null
////
////        constructor(topTrendingBinding: ItemTopTrendingBinding) : super(topTrendingBinding.root) {
////            this.topTrendingBinding =topTrendingBinding
////
////        }
////
////        var browseAllGenreBinding: ItemBrowseAllGenreBinding? = null
////
////        constructor(browseAllGenreBinding:ItemBrowseAllGenreBinding) : super(browseAllGenreBinding.root) {
////            this.browseAllGenreBinding =browseAllGenreBinding
////
////        }
////
////        var adBinding: ItemAdBinding? = null
////
////        constructor(adBinding: ItemAdBinding) : super(adBinding.root) {
////            this.adBinding =adBinding
////
////        }
////        var myFavBinding: ItemMyFavBinding? = null
////
////        constructor(myFavBinding: ItemMyFavBinding) : super(myFavBinding.root) {
////            this.myFavBinding =myFavBinding
////
////        }
////        var amarTunesBinding: ItemPopularAmarTunesBinding? = null
////
////        constructor(amarTunesBinding: ItemPopularAmarTunesBinding) : super(amarTunesBinding.root) {
////            this.amarTunesBinding =amarTunesBinding
////
////        }
////        var blMusicOffersListBinding: ItemMyBlOffersBinding? = null
////
////        constructor(blMusicOffersListBinding: ItemMyBlOffersBinding) : super(blMusicOffersListBinding.root) {
////            this.blMusicOffersListBinding =blMusicOffersListBinding
////
////        }
////        var trendingMusicVideosBinding: ItemTrendingMusicVideosBinding? = null
////
////        constructor(trendingMusicVideosBinding: ItemTrendingMusicVideosBinding) : super(trendingMusicVideosBinding.root) {
////            this.trendingMusicVideosBinding =trendingMusicVideosBinding
////
////        }
////
////    }
////
//
//
//
//
//}









