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
import android.widget.LinearLayout
import android.widget.SearchView
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
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Serializable

internal class SearchFragment : CommonBaseFragment(), SearchItemCallBack {
    private lateinit var navController: NavController
    private lateinit var viewModel: SearchViewModel

    private var searchText: String = ""
    private var queryTextChangedJob: Job? = null

    private lateinit var svSearchInput: SearchView
    private lateinit var tvNoDataFound: TextView


    private lateinit var llTrendingSearchItem: LinearLayout
    private lateinit var tvTrendingSearchItem: TextView
    private lateinit var cvTrendingSearchItem: CardView

    private lateinit var llWeeklyTrending: LinearLayout
    private lateinit var tvWeeklyTrending: TextView
    private lateinit var rvWeeklyTrending: RecyclerView

    private lateinit var llTrendingVideo: LinearLayout
    private lateinit var tvTrendingVideo: TextView
    private lateinit var rvTrendingVideos: RecyclerView

    private lateinit var llArtist: LinearLayout
    private lateinit var tvArtist: TextView
    private lateinit var rvArtist: RecyclerView

    private lateinit var llAlbum: LinearLayout
    private lateinit var tvAlbums: TextView
    private lateinit var rvAlbums: RecyclerView

    private lateinit var llTracks: LinearLayout
    private lateinit var tvTracks: TextView
    private lateinit var rvTracks: RecyclerView

    private lateinit var llVideos: LinearLayout
    private lateinit var tvVideos: TextView
    private lateinit var rvVideos: RecyclerView

    private lateinit var llShows: LinearLayout
    private lateinit var tvShows: TextView
    private lateinit var rvShows: RecyclerView

    private lateinit var llEpisode: LinearLayout
    private lateinit var tvEpisodes: TextView
    private lateinit var rvEpisodes: RecyclerView

    private lateinit var llPodcastTracks: LinearLayout
    private lateinit var tvPodcastTracks: TextView
    private lateinit var rvPodcastTracks: RecyclerView

    var mSuggestionAdapter: SearchSuggestionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewRef = inflater.inflate(R.layout.my_bl_sdk_fragment_search, container, false)
        navController = findNavController()
        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        setupViewModel()
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            if (ShadhinMusicSdkCore.pressCountDecrement() == 0) {
                requireActivity().finish()
            } else {
                navController.popBackStack()
            }
        }

        observeDataTrendingItems("S")

        svSearchInput = view.findViewById(R.id.sv_search_input)
        val chipArtist: Chip = requireView().findViewById(R.id.chip_1)
        val chipHabib: Chip = requireView().findViewById(R.id.chip_2)
        val chipVideo: Chip = requireView().findViewById(R.id.chip_3)
        val chipTahsan: Chip = requireView().findViewById(R.id.chip_4)
        val chipKona: Chip = requireView().findViewById(R.id.chip_5)

        setTextOnSearchBar(chipArtist)
        setTextOnSearchBar(chipHabib)
        setTextOnSearchBar(chipVideo)
        setTextOnSearchBar(chipTahsan)
        setTextOnSearchBar(chipKona)

//        viewModel.getTopTrendingVideos("v")
//        viewModel.topTrendingVideoContent.observe(viewLifecycleOwner) { response ->
//            if (response.status == Status.SUCCESS) {
//                recyclerViewTrendingVideos.layoutManager =
//                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//                recyclerViewTrendingVideos.adapter = TrendingItemsAdapter(response?.data?.data!!)
//                Log.e("TAG", "DATA123: " + response.data?.data)
//            }
//        }
//        observeData(searchText)
//        search.setOnClickListener {
//            search.focusable =
//        }
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager?
        svSearchInput.setSearchableInfo(searchManager?.getSearchableInfo(activity?.componentName))

        mSuggestionAdapter = SearchSuggestionAdapter(requireContext(), null, 0)
        svSearchInput.suggestionsAdapter = mSuggestionAdapter
        // search.setFocusable(true)
        svSearchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                val cursor: Cursor = getRecentSuggestions(newText)!!
                Log.e("TAG", "After: $searchText")
                if (newText.length > 1) {
                    queryTextChangedJob?.cancel()
                    queryTextChangedJob = lifecycleScope.launch(Dispatchers.Main) {
                        delay(2000)
                        observeDataTrendingItems("\"\"")
                        observeData(searchText)
                    }
                    searchText = newText
                    Log.e("TAG", "After: $searchText")
                } else if (newText.isEmpty()) {
                    queryTextChangedJob?.cancel()
                    queryTextChangedJob = lifecycleScope.launch(Dispatchers.Main) {
                        delay(1000)
//                        observeData(searchText)
                        observeDataTrendingItems("S")
                        observeData("\"\"")
                        mSuggestionAdapter?.swapCursor(cursor)
//                        tvTrendingSearchItem.visibility = GONE
//                        cvTrendingSearchItem.visibility = GONE
//                        rvWeeklyTrending.visibility = GONE
//                        rvTrendingVideos.visibility = GONE
//                        tvWeeklyTrending.visibility = GONE
//                        tvTrendingVideo.visibility = GONE
                    }
                    Log.e("TAG", "After: $searchText")
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                Log.e("TAG", "onQueryTextSubmit: $query")
                // val query: String = intent.getStringExtra(SearchManager.QUERY);
                searchText = query
                val suggestions = SearchRecentSuggestions(
                    context,
                    MySuggestionProvider.AUTHORITY,
                    MySuggestionProvider.MODE
                )
                suggestions.saveRecentQuery(query, null)
                svSearchInput.clearFocus()
                observeData(searchText)
//                tvTrendingSearchItem.visibility = GONE
//                rvWeeklyTrending.visibility = GONE
//                rvTrendingVideos.visibility = GONE
//                tvWeeklyTrending.visibility = GONE
//                tvTrendingVideo.visibility = GONE
                return true
            }
        })

        svSearchInput.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                svSearchInput.setQuery(mSuggestionAdapter!!.getSuggestionText(position), true)
                return true
            }
        })
    }

    private fun initUI() {
        tvNoDataFound = requireView().findViewById(R.id.tvNoDataFound)
        llTrendingSearchItem = requireView().findViewById(R.id.llTrendingSearchItem)
        llWeeklyTrending = requireView().findViewById(R.id.llWeeklyTrending)
        llTrendingVideo = requireView().findViewById(R.id.llTrendingVideo)
        llArtist = requireView().findViewById(R.id.llArtist)
        llAlbum = requireView().findViewById(R.id.llAlbum)
        llTracks = requireView().findViewById(R.id.llTracks)
        llVideos = requireView().findViewById(R.id.llVideos)
        llShows = requireView().findViewById(R.id.llShows)
        llEpisode = requireView().findViewById(R.id.llEpisode)
        llPodcastTracks = requireView().findViewById(R.id.llPodcastTracks)
    }

    private fun setTextOnSearchBar(chipCommon: Chip) {
        chipCommon.setOnClickListener {
            svSearchInput.setQuery(chipCommon.text, true)
        }
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, injector.searchViewModelFactory)[SearchViewModel::class.java]
    }

    private fun observeDataTrendingItems(type: String) {
        viewModel.getTopTrendingItems(type)
        viewModel.topTrendingContent.observe(viewLifecycleOwner) { response ->
            if (response != null && response.status == Status.SUCCESS) {
                if (response.data?.data?.isNotEmpty() == true) {
                    tvTrendingSearchItem = requireView().findViewById(R.id.tvTrendingSearchItem)
                    cvTrendingSearchItem = requireView().findViewById(R.id.cvTrendingSearchItem)
                    llTrendingSearchItem.visibility = VISIBLE

                    tvWeeklyTrending = requireView().findViewById(R.id.tvWeeklyTrending)
                    rvWeeklyTrending = requireView().findViewById(R.id.rvWeeklyTrending)

                    llWeeklyTrending.visibility = VISIBLE
//                tvTrendingVideo = requireView().findViewById(R.id.tvTrendingVideo)
//                rvTrendingVideos = requireView().findViewById(R.id.rvTrendingVideos)
//                llTrendingVideo.visibility = VISIBLE

                    tvNoDataFound.visibility = GONE
                    response.data.data.let {
                        rvWeeklyTrending.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        rvWeeklyTrending.adapter = TopTenItemAdapter(it, this)
                    }
                } else {
                    llTrendingSearchItem.visibility = GONE
                    llWeeklyTrending.visibility = GONE

                    tvNoDataFound.visibility = VISIBLE
//                llTrendingVideo.visibility = GONE
                }
            }
        }
    }

    private fun observeData(searchText: String) {
//        llTrendingSearchItem.visibility = GONE
//        llWeeklyTrending.visibility = GONE

        viewModel.getSearchArtist(searchText)
        viewModel.getSearchAlbum(searchText)
        viewModel.getSearchTracks(searchText)
        viewModel.getSearchVideo(searchText)
        viewModel.getSearchPodcastEpisode(searchText)
        viewModel.getSearchPodcastShow(searchText)
        viewModel.getSearchPodcastTrack(searchText)

        viewModel.searchArtistContent.observe(viewLifecycleOwner) { response ->
            if (response != null && response.status == Status.SUCCESS) {
                if (response.data?.data?.Artist?.data?.isNotEmpty() == true) {
                    tvArtist = requireView().findViewById(R.id.tvArtist)
                    rvArtist = requireView().findViewById(R.id.rvArtist)

                    tvNoDataFound.visibility = GONE
                    llArtist.visibility = VISIBLE
//                    rvArtist.visibility = VISIBLE
//                    tvArtist.visibility = VISIBLE
                    rvArtist.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    rvArtist.adapter =
                        SearchArtistAdapter(response.data.data.Artist.data, this)
                } else {
                    llArtist.visibility = GONE
//                    rvArtist.visibility = GONE
//                    tvArtist.visibility = GONE
                    tvNoDataFound.visibility = VISIBLE
                }
            }
        }
        viewModel.searchAlbumContent.observe(viewLifecycleOwner) { response ->
            if (response != null && response.status == Status.SUCCESS) {
                if (response.data?.data?.Album?.data?.isNotEmpty() == true) {
                    rvAlbums = requireView().findViewById(R.id.rvAlbums)
                    tvAlbums = requireView().findViewById(R.id.tvAlbums)

                    tvNoDataFound.visibility = GONE
                    llAlbum.visibility = VISIBLE
//                    rvAlbums.visibility = VISIBLE
//                    tvAlbums.visibility = VISIBLE
                    rvAlbums.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    rvAlbums.adapter =
                        SearchAlbumsAdapter(response.data.data.Album.data, this)
                } else {
                    llAlbum.visibility = GONE
//                    rvAlbums.visibility = GONE
//                    tvAlbums.visibility = GONE
                    tvNoDataFound.visibility = VISIBLE
                }
            }
        }
        viewModel.searchTracksContent.observe(viewLifecycleOwner) { response ->
            if (response != null && response.status == Status.SUCCESS) {
                if (response.data?.data?.Track?.data?.isNotEmpty() == true) {
                    rvTracks = requireView().findViewById(R.id.rvTracks)
                    tvTracks = requireView().findViewById(R.id.tvTracks)

                    tvNoDataFound.visibility = GONE
                    llTracks.visibility = VISIBLE
//                    rvTracks.visibility = GONE
//                    tvTracks.visibility = GONE
//                    tvTracks.visibility = VISIBLE
//                    recyclerViewTracks.visibility = VISIBLE
                    rvTracks.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    rvTracks.adapter =
                        SearchTracksAdapter(response.data.data.Track.data, this)
                } else {
                    llTracks.visibility = GONE
//                    rvTracks.visibility = GONE
//                    tvTracks.visibility = GONE
                    tvNoDataFound.visibility = VISIBLE
                }
            }
        }
        viewModel.searchVideoContent.observe(viewLifecycleOwner) { response ->
            if (response != null && response.status == Status.SUCCESS) {
                if (response.data?.data?.Video?.data?.isNotEmpty() == true) {
                    rvVideos = requireView().findViewById(R.id.rvVideos)
                    tvVideos = requireView().findViewById(R.id.tvVideos)

                    tvNoDataFound.visibility = GONE
                    llVideos.visibility = VISIBLE
//                    tvVideos.visibility = VISIBLE
//                    rvVideos.visibility = VISIBLE
                    rvVideos.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    rvVideos.adapter =
                        SearchVideoAdapter(response.data.data.Video.data, this)
                    Log.e("TAG", "DATA: " + response.data.data.Track.data)
                } else {
                    llVideos.visibility = GONE
//                    rvVideos.visibility = GONE
//                    tvVideos.visibility = GONE
                    tvNoDataFound.visibility = VISIBLE
                }
            }
        }
        viewModel.searchPodcastShowContent.observe(viewLifecycleOwner) { response ->
            if (response != null && response.status == Status.SUCCESS) {
                if (response.data?.data?.PodcastShow?.data?.isNotEmpty() == true) {
                    rvShows = requireView().findViewById(R.id.rvShows)
                    tvShows = requireView().findViewById(R.id.tvShows)

                    tvNoDataFound.visibility = GONE
                    llShows.visibility = VISIBLE
//                    rvShows.visibility = GONE
//                    tvShows.visibility = GONE
//                    recyclerViewShows.visibility = VISIBLE
//                    tvShows.visibility = VISIBLE
                    rvShows.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    rvShows.adapter =
                        SearchShowAdapter(response.data.data.PodcastShow.data, this)
                    Log.e("TAG", "DATA123: " + response.data.data.PodcastShow.data)
                } else {
                    llShows.visibility = GONE
//                    rvShows.visibility = GONE
//                    tvShows.visibility = GONE
                    tvNoDataFound.visibility = VISIBLE
                }
            }
        }
        viewModel.searchPodcastEpisodeContent.observe(viewLifecycleOwner) { response ->
            if (response != null && response.status == Status.SUCCESS) {
                if (response.data?.data?.PodcastEpisode?.data?.isNotEmpty() == true) {
                    rvEpisodes = requireView().findViewById(R.id.rvEpisodes)
                    tvEpisodes = requireView().findViewById(R.id.tvEpisodes)

                    tvNoDataFound.visibility = GONE
                    llEpisode.visibility = VISIBLE
//                    rvEpisodes.visibility = GONE
//                    tvEpisodes.visibility = GONE
//                    recyclerViewEpisodes.visibility = VISIBLE
//                    tvEpisodes.visibility = VISIBLE
                    rvEpisodes.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    rvEpisodes.adapter =
                        SearchEpisodeAdapter(response.data.data.PodcastEpisode.data, this)
                    Log.e("TAG", "DATA123: " + response.data.data)
                } else {
                    llEpisode.visibility = GONE
//                    rvEpisodes.visibility = GONE
//                    tvEpisodes.visibility = GONE
                    tvNoDataFound.visibility = VISIBLE
                }
            }
        }
        viewModel.searchPodcastTrackContent.observe(viewLifecycleOwner) { response ->
            if (response != null && response.status == Status.SUCCESS) {
                if (response.data?.data?.PodcastTrack?.data?.isNotEmpty() == true) {
                    rvPodcastTracks = requireView().findViewById(R.id.rvPodcastTracks)
                    tvPodcastTracks = requireView().findViewById(R.id.tvPodcastTracks)

                    tvNoDataFound.visibility = GONE
                    llPodcastTracks.visibility = VISIBLE
//                    rvPodcastTracks.visibility = GONE
//                    tvPodcastTracks.visibility = GONE
//                    recyclerViewPodcastTracks.visibility = VISIBLE
//                    tvPodcastTracks.visibility = VISIBLE
                    rvPodcastTracks.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    rvPodcastTracks.adapter =
                        SearchPodcastTracksAdapter(response.data.data.PodcastTrack.data, this)
                    Log.e("TAG", "DATA123: " + response.data.data)
                } else {
                    llPodcastTracks.visibility = GONE
//                    rvPodcastTracks.visibility = GONE
//                    tvPodcastTracks.visibility = GONE
                    tvNoDataFound.visibility = VISIBLE
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
        /*  if (playerViewModel.currentMusic != null) {
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
          }*/
    }

    //after search play item
    //TODO need delay
    override fun onClickPlaySearchItem(songItem: List<SearchData>, clickItemPosition: Int) {
        Log.e("SF", "onClickPlaySearchItem: " + songItem[clickItemPosition].ContentType)
        /* when (songItem[clickItemPosition].ContentType) {
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
         }*/
    }

    override fun onClickPlayVideoItem(songItem: List<SearchData>, clickItemPosition: Int) {
        when (songItem[clickItemPosition].ContentType) {
            DataContentType.CONTENT_TYPE_V -> {
                val intent = Intent(requireContext(), VideoActivity::class.java)
                val videoArray = ArrayList<Video>()
                for (item in songItem) {
                    videoArray.add(UtilHelper.getVideoToSearchData(item))
                }
                val videos: ArrayList<Video> = videoArray
                intent.putExtra(VideoActivity.INTENT_KEY_POSITION, clickItemPosition)
                intent.putExtra(VideoActivity.INTENT_KEY_DATA_LIST, videos)
                startActivity(intent)
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