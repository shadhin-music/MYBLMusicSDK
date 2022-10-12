package com.shadhinmusiclibrary.fragments.search

import android.app.SearchManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.activities.video.VideoActivity
import com.shadhinmusiclibrary.adapter.*
import com.shadhinmusiclibrary.callBackService.SearchItemCallBack
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.Video
import com.shadhinmusiclibrary.data.model.search.SearchData
import com.shadhinmusiclibrary.data.model.search.TopTrendingdata
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Serializable

class SearchFragment : CommonBaseFragment(), FragmentEntryPoint, SearchItemCallBack {
    private lateinit var navController: NavController
    private lateinit var viewModel: SearchViewModel

    private lateinit var searchText: String
    private var queryTextChangedJob: Job? = null
    private lateinit var tvTrending: TextView
    private lateinit var tvTrendingItems: TextView
    private lateinit var tvTrendingVideo: TextView
    private lateinit var tvArtist: TextView
    private lateinit var tvAlbums: TextView
    private lateinit var tvTracks: TextView
    private lateinit var tvVideos: TextView
    private lateinit var tvShows: TextView
    private lateinit var tvEpisodes: TextView
    private lateinit var tvPodcastTracks: TextView

    private lateinit var recyclerViewAlbums: RecyclerView
    private lateinit var recyclerViewVideos: RecyclerView
    private lateinit var recyclerViewTracks: RecyclerView
    private lateinit var recyclerViewShows: RecyclerView
    private lateinit var recyclerViewEpisodes: RecyclerView
    private lateinit var recyclerViewPodcastTracks: RecyclerView
    private lateinit var cardView: CardView
    private lateinit var recyclerViewTrending: RecyclerView
    private lateinit var recyclerViewTrendingVideos: RecyclerView
    private lateinit var recyclerViewArtist: RecyclerView
    var mSuggestionAdapter: SearchSuggestionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewRef = inflater.inflate(R.layout.fragment_search, container, false)
        navController = findNavController()
        return viewRef

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            if (ShadhinMusicSdkCore.pressCountDecrement() == 0) {
                requireActivity().finish()
            } else {
                navController.popBackStack()
            }
        }
        cardView = requireView().findViewById(R.id.cardView)
        tvTrendingItems = requireView().findViewById(R.id.tvTrendingItems)
        recyclerViewTrending = requireView().findViewById(R.id.recyclerViewTrending)
        recyclerViewTrendingVideos = requireView().findViewById(R.id.recyclerViewTrendingVideos)
        recyclerViewArtist = requireView().findViewById(R.id.recyclerViewArtist)

        tvTrending = requireView().findViewById(R.id.tvTrending)
        tvTrendingVideo = requireView().findViewById(R.id.tvTrendingVideo)

        viewModel.getTopTrendingItems("s")
        viewModel.topTrendingContent.observe(viewLifecycleOwner) { response ->
            if (response !=null && response.status == Status.SUCCESS) {
                response.data?.data?.let {
                    recyclerViewTrending.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    recyclerViewTrending.adapter = TopTenItemAdapter(it, this)
                }
            }
        }
        val chipArtist: Chip = requireView().findViewById(R.id.chip_1)
        val chipHabib: Chip = requireView().findViewById(R.id.chip_2)
        val chipVideo: Chip = requireView().findViewById(R.id.chip_3)
        val chipTahsan: Chip = requireView().findViewById(R.id.chip_4)
        val chipKona: Chip = requireView().findViewById(R.id.chip_5)

        chipArtist.setOnClickListener {
            Log.e("TAG", "setOnClickListener: chipArtist")
//            navController.navigate(R.id.action_search_fragment_to_popular_artists_fragment,
//                Bundle().apply {
//                    putSerializable(
//                        AppConstantUtils.PatchItem,
//                        HomePatchDetail() as Serializable
//                    )
//                })
        }

//        viewModel.getTopTrendingVideos("v")
//        viewModel.topTrendingVideoContent.observe(viewLifecycleOwner) { response ->
//            if (response.status == Status.SUCCESS) {
//
//                recyclerViewTrendingVideos.layoutManager =
//                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//                recyclerViewTrendingVideos.adapter = TrendingItemsAdapter(response?.data?.data!!)
//                Log.e("TAG", "DATA123: " + response.data?.data)
//            }
//        }
//        observeData(searchText)
        val search: android.widget.SearchView = view.findViewById(R.id.searchInput)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager?
        search.setSearchableInfo(searchManager?.getSearchableInfo(activity?.getComponentName()))

        mSuggestionAdapter = SearchSuggestionAdapter(context, null, 0)
        search.suggestionsAdapter = mSuggestionAdapter!!
        // search.setFocusable(true)
        search.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                val cursor: Cursor = getRecentSuggestions(newText)!!
                val text = newText ?: return false
                searchText = text
                // mSuggestionAdapter?.swapCursor(cursor)
                if (newText.length > 1) {
//                val text = newText ?: return false
//                searchText = text
                    queryTextChangedJob?.cancel()
                    queryTextChangedJob = lifecycleScope.launch(Dispatchers.Main) {
                        Log.e("SearchFragment", "async work started...")
                        delay(2000)
                        observeData(searchText)
                    }
                } else if (searchText.isEmpty()) {
                    queryTextChangedJob?.cancel()
                    queryTextChangedJob = lifecycleScope.launch(Dispatchers.Main) {
                        // Log.e("SearchFragment", "async work started...")
                        delay(1000)
                        //  doSearch(searchText)
//                        rvTrending = requireView().findViewById(R.id.rvTrending)
//                        rvTrending.visibility = VISIBLE
//                        recyclerViewSearch.visibility = GONE
                        //searchAlbumsAdapter =null
                        mSuggestionAdapter?.swapCursor(cursor)
                        //getData()
                        tvTrendingItems.visibility = GONE
                        cardView.visibility = GONE
                        recyclerViewTrending.visibility = GONE
                        recyclerViewTrendingVideos.visibility = GONE
                        tvTrending.visibility = GONE
                        tvTrendingVideo.visibility = GONE
                    }
                    //search.clearFocus()
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // val query: String = intent.getStringExtra(SearchManager.QUERY);
                val suggestions: SearchRecentSuggestions = SearchRecentSuggestions(
                    context,
                    MySuggestionProvider.AUTHORITY,
                    MySuggestionProvider.MODE
                )
                suggestions.saveRecentQuery(query, null)
                search.clearFocus()
                tvTrendingItems.visibility = GONE
                recyclerViewTrending.visibility = GONE
                recyclerViewTrendingVideos.visibility = GONE
                tvTrending.visibility = GONE
                tvTrendingVideo.visibility = GONE
                // doSearch(searchText)
                return true
            }
        })
        search.setOnSuggestionListener(object : android.widget.SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                search.setQuery(mSuggestionAdapter!!.getSuggestionText(position), true)
                return true
            }
        })
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, injector.searchViewModelFactory)[SearchViewModel::class.java]
    }

    private fun observeData(searchText: String) {
        viewModel.getSearchArtist(searchText)
        viewModel.getSearchAlbum(searchText)
        viewModel.getSearchTracks(searchText)
        viewModel.getSearchVideo(searchText)
        viewModel.getSearchPodcastEpisode(searchText)
        viewModel.getSearchPodcastShow(searchText)
        viewModel.getSearchPodcastTrack(searchText)

        viewModel.searchArtistContent.observe(viewLifecycleOwner) { response ->
            if (response != null && response.status == Status.SUCCESS) {
                recyclerViewArtist = requireView().findViewById(R.id.recyclerViewArtist)
                tvArtist = requireView().findViewById(R.id.tvArtist)
                if (response.data?.data?.Artist?.data?.isNotEmpty() == true) {
                    recyclerViewArtist.visibility = VISIBLE
                    tvArtist.visibility = VISIBLE
                    recyclerViewArtist.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    recyclerViewArtist.adapter =
                        SearchArtistAdapter(response.data.data.Artist.data, this)
                    Log.e("TAG", "DATASearch: " + response.data.data)
                } else {
                    recyclerViewArtist.visibility = GONE
                    tvArtist.visibility = GONE
                }
            }
        }
        viewModel.searchAlbumContent.observe(viewLifecycleOwner) { response ->
            if (response != null && response.status == Status.SUCCESS) {
                recyclerViewAlbums = requireView().findViewById(R.id.recyclerViewAlbums)
                tvAlbums = requireView().findViewById(R.id.tvAlbums)
                if (response.data?.data?.Album?.data?.isNotEmpty() == true) {
                    recyclerViewAlbums.visibility = VISIBLE
                    tvAlbums.visibility = VISIBLE
                    recyclerViewAlbums.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    recyclerViewAlbums.adapter =
                        SearchAlbumsAdapter(response.data.data.Album.data, this)
                } else {
                    recyclerViewAlbums.visibility = GONE
                    tvAlbums.visibility = GONE
                }
            }
        }
        viewModel.searchTracksContent.observe(viewLifecycleOwner) { response ->
            if (response !=null && response.status == Status.SUCCESS) {
                recyclerViewTracks = requireView().findViewById(R.id.recyclerViewTracks)
                tvTracks = requireView().findViewById(R.id.tvTracks)
                if (response.data?.data?.Track?.data?.isNotEmpty() == true) {
                    tvTracks.visibility = VISIBLE
                    recyclerViewTracks.visibility = VISIBLE
                    recyclerViewTracks.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    recyclerViewTracks.adapter =
                        SearchTracksAdapter(response.data.data.Track.data, this)
                } else {
                    recyclerViewTracks.visibility = GONE
                    tvTracks.visibility = GONE
                }
            }
        }
        viewModel.searchVideoContent.observe(viewLifecycleOwner) { response ->
            if (response !=null && response.status == Status.SUCCESS) {
                recyclerViewVideos = requireView().findViewById(R.id.recyclerViewVideos)
                tvVideos = requireView().findViewById(R.id.tvVideos)
                if (response.data?.data?.Video?.data?.isNotEmpty() == true) {
                    tvVideos.visibility = VISIBLE
                    recyclerViewVideos.visibility = VISIBLE
                    recyclerViewVideos.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    recyclerViewVideos.adapter =
                        SearchVideoAdapter(response.data.data.Video.data, this)
                    Log.e("TAG", "DATA: " + response.data.data.Track.data)
                } else {
                    recyclerViewVideos.visibility = GONE
                    tvVideos.visibility = GONE
                }
            }
        }
        viewModel.searchPodcastShowContent.observe(viewLifecycleOwner) { response ->
            if (response !=null && response.status == Status.SUCCESS) {
                recyclerViewShows = requireView().findViewById(R.id.recyclerViewShows)
                tvShows = requireView().findViewById(R.id.tvShows)
                if (response.data?.data?.PodcastShow?.data?.isNotEmpty() == true) {
                    recyclerViewShows.visibility = VISIBLE
                    tvShows.visibility = VISIBLE
                    recyclerViewShows.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    recyclerViewShows.adapter =
                        SearchShowAdapter(response.data.data.PodcastShow.data, this)
                    Log.e("TAG", "DATA123: " + response.data.data.PodcastShow.data)
                } else {
                    recyclerViewShows.visibility = GONE
                    tvShows.visibility = GONE

                }
            }
        }
        viewModel.searchPodcastEpisodeContent.observe(viewLifecycleOwner) { response ->
            if (response !=null && response.status == Status.SUCCESS) {
                recyclerViewEpisodes = requireView().findViewById(R.id.recyclerViewEpisodes)
                tvEpisodes = requireView().findViewById(R.id.tvEpisodes)
                if (response.data?.data?.PodcastEpisode?.data?.isNotEmpty() == true) {
                    recyclerViewEpisodes.visibility = VISIBLE
                    tvEpisodes.visibility = VISIBLE
                    recyclerViewEpisodes.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    recyclerViewEpisodes.adapter =
                        SearchEpisodeAdapter(response.data.data.PodcastEpisode.data, this)
                    Log.e("TAG", "DATA123: " + response.data.data)
                } else {
                    recyclerViewEpisodes.visibility = GONE
                    tvEpisodes.visibility = GONE
                }
            }
        }
        viewModel.searchPodcastTrackContent.observe(viewLifecycleOwner) { response ->
            if (response.status == Status.SUCCESS) {
                recyclerViewPodcastTracks =
                    requireView().findViewById(R.id.recyclerViewPodcastTracks)
                tvPodcastTracks = requireView().findViewById(R.id.tvPodcastTracks)
                if (response?.data?.data?.PodcastTrack?.data?.isNotEmpty() == true) {
                    recyclerViewPodcastTracks.visibility = VISIBLE
                    tvPodcastTracks.visibility = VISIBLE
                    recyclerViewPodcastTracks.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    recyclerViewPodcastTracks.adapter =
                        SearchPodcastTracksAdapter(response.data.data.PodcastTrack.data, this)
                    Log.e("TAG", "DATA123: " + response.data.data)
                } else {
                    recyclerViewPodcastTracks.visibility = GONE
                    tvPodcastTracks.visibility = GONE
                }
            }
        }
    }

    fun getRecentSuggestions(query: String): Cursor? {
        val uriBuilder: Uri.Builder = Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(MySuggestionProvider.AUTHORITY)
        uriBuilder.appendPath(SearchManager.SUGGEST_URI_PATH_QUERY)
        val selection = "?"
        val selArgs = arrayOf(query)
        val uri: Uri = uriBuilder.build()
        return requireActivity().contentResolver.query(uri, null, selection, selArgs, null)
    }

    override fun onClickSearchItem(searchData: SearchData) {
        ShadhinMusicSdkCore.pressCountIncrement()
        val patchItem = Bundle().apply {
            putSerializable(
                AppConstantUtils.PatchItem,
                HomePatchItem("", "A", listOf(), "", "", 0, 0)
            )
            putSerializable(
                AppConstantUtils.PatchDetail,
                HomePatchDetail(
                    AlbumId = searchData.AlbumId,
                    ArtistId = searchData.ContentID,
                    ContentID = searchData.ContentID,
                    ContentType = searchData.ContentType,
                    PlayUrl = searchData.PlayUrl ?: "",
                    AlbumName = searchData.title,
                    AlbumImage = "",
                    fav = "",
                    Banner = "",
                    Duration = searchData.Duration,
                    TrackType = "",
                    image = searchData.image,
                    ArtistImage = "",
                    Artist = searchData.Artist,
                    CreateDate = "",
                    Follower = "",
                    imageWeb = "",
                    IsPaid = false,
                    NewBanner = "",
                    PlayCount = 0,
                    PlayListId = "",
                    PlayListImage = "",
                    PlayListName = "",
                    RootId = "",
                    RootType = "",
                    Seekable = false,
                    TeaserUrl = "",
                    title = searchData.title,
                    Type = ""
                ) as Serializable
            )
        }
        Log.e("TAG", "onClickSearchItem: " + searchData.ContentType + " " + searchData.ContentID)
        when (searchData.ContentType.toUpperCase()) {
            DataContentType.CONTENT_TYPE_A -> {
                //open artist details
                setupNavGraphAndArg(
                    R.id.to_artist_details,
                    patchItem
                )
            }
            DataContentType.CONTENT_TYPE_R -> {
                //open album details
                setupNavGraphAndArg(
                    R.id.to_album_details,
                    patchItem
                )
            }
            DataContentType.CONTENT_TYPE_PD_BC -> {
                //open playlist
                setupNavGraphAndArg(
                    R.id.to_podcast_details,
                    patchItem
                )
            }
//            DataContentType.CONTENT_TYPE_S -> {
//                //open songs
//                setupNavGraphAndArg(
//                    R.navigation.nav_graph_s_type_details,
//                    patchItem
//                )
//            }
//            DataContentType.CONTENT_TYPE_PD -> {
//                //open podcast
//                setupNavGraphAndArg(
//                    R.navigation.nav_graph_podcast_details,
//                    patchItem
//                )
//            }
        }
    }

    //Top Tend play. whene fast search fragment came
    override fun onClickPlayItem(songItem: List<TopTrendingdata>, clickItemPosition: Int) {
        if (playerViewModel.currentMusic != null) {
            if ((songItem[clickItemPosition].ContentID == playerViewModel.currentMusic?.rootId)) {
                if ((songItem[clickItemPosition].ContentID != playerViewModel.currentMusic?.mediaId)) {
                    playerViewModel.skipToQueueItem(clickItemPosition)
                } else {
                    playerViewModel.togglePlayPause()
                }
            } else {
                playItem(UtilHelper.getSongDetailToTopTrendingDataList(songItem), clickItemPosition)
            }
        } else {
            playItem(UtilHelper.getSongDetailToTopTrendingDataList(songItem), clickItemPosition)
        }
    }

    //after search play item
    //TODO need delay
    override fun onClickPlaySearchItem(songItem: List<SearchData>, clickItemPosition: Int) {
        Log.e("SF", "onClickPlaySearchItem: " + songItem[clickItemPosition].ContentType)
        when (songItem[clickItemPosition].ContentType) {
            DataContentType.CONTENT_TYPE_V -> {
                //open playlist
                val intent = Intent(context, VideoActivity::class.java)
                val videoArray = ArrayList<Video>()
                for (item in songItem) {
//                    val video = Video()
                    videoArray.add(UtilHelper.getVideoToSearchData(item))
                }
                val videos: ArrayList<Video> = videoArray
                intent.putExtra(VideoActivity.INTENT_KEY_POSITION, clickItemPosition)
                intent.putExtra(VideoActivity.INTENT_KEY_DATA_LIST, videos)
                startActivity(intent)
            }
            DataContentType.CONTENT_TYPE_S -> {
                if (playerViewModel.currentMusic != null) {
                    Log.e(
                        "SF",
                        "currentMusic: " + songItem[clickItemPosition].ContentID + " "
                                + playerViewModel.currentMusic?.rootId
                    )
                    if ((songItem[clickItemPosition].ContentID == playerViewModel.currentMusic?.rootId)) {
                        if ((songItem[clickItemPosition].ContentID != playerViewModel.currentMusic?.mediaId)) {
                            playerViewModel.skipToQueueItem(clickItemPosition)
                            Log.e("ADF", "skipToQueueItem:")
                        } else {
                            Log.e("ADF", "togglePlayPause:")
                            playerViewModel.togglePlayPause()
                        }
                    } else {
                        playItem(
                            UtilHelper.getSongDetailToSearchDataList(songItem),
                            clickItemPosition
                        )
                    }
                } else {
                    playItem(UtilHelper.getSongDetailToSearchDataList(songItem), clickItemPosition)
                }
            }
            DataContentType.CONTENT_TYPE_PD_CB -> {
                //TODO need delay
                if (playerViewModel.currentMusic != null) {
                    if ((songItem[clickItemPosition].ContentID == playerViewModel.currentMusic?.rootId)) {
                        if ((songItem[clickItemPosition].ContentID != playerViewModel.currentMusic?.mediaId)) {
                            playerViewModel.skipToQueueItem(clickItemPosition)
                        } else {
                            playerViewModel.togglePlayPause()
                        }
                    } else {
                        playItem(
                            UtilHelper.getSongDetailToSearchDataList(songItem),
                            clickItemPosition
                        )
                    }
                } else {
                    playItem(UtilHelper.getSongDetailToSearchDataList(songItem), clickItemPosition)
                }
            }
        }
    }

    private fun setupNavGraphAndArg(graphResId: Int, bundleData: Bundle) {
        navController.navigate(graphResId, bundleData)
    }

//    override fun onClickAlbumItem(albumModelData: SearchAlbumdata) {
//        Log.e("TAG", "albumModelData: " + albumModelData)
//        ShadhinMusicSdkCore.pressCountIncrement()
//        val data2 = Bundle()
//        data2.putSerializable(
//            AppConstantUtils.Album,
//            albumModelData as Serializable
//        )
////        navController.navigate(R.id.action_search_fragment_to_album_details_fragment,
//            Bundle().apply {
//                putSerializable(
//                    AppConstantUtils.PatchItem,
//                    HomePatchItem("", "", listOf(), "", "", 0, 0)
//                )
//                putSerializable(
//                    AppConstantUtils.PatchDetail,
//                    HomePatchDetail(
//                        AlbumId = albumModelData.AlbumId,
//                        ArtistId = albumModelData.ContentID,
//                        ContentID = albumModelData.ContentID,
//                        ContentType = albumModelData.ContentType,
//                        PlayUrl = albumModelData.PlayUrl,
//                        AlbumName = albumModelData.title,
//                        AlbumImage = albumModelData.image,
//                        fav = "",
//                        Banner = "",
//                        Duration = albumModelData.Duration,
//                        TrackType = "",
//                        image = albumModelData.image,
//                        ArtistImage = "",
//                        Artist = albumModelData.Artist,
//                        CreateDate = "",
//                        Follower = "",
//                        imageWeb = "",
//                        IsPaid = false,
//                        NewBanner = "",
//                        PlayCount = 0,
//                        PlayListId = "",
//                        PlayListImage = "",
//                        PlayListName = "",
//                        RootId = "",
//                        RootType = "",
//                        Seekable = false,
//                        TeaserUrl = "",
//                        title = albumModelData.title,
//                        Type = ""
//
//                    ) as Serializable
//                )
//            })
//    }
}