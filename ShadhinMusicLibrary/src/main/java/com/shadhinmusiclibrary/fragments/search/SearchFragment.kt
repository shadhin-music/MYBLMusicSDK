package com.shadhinmusiclibrary.fragments.search

import android.app.SearchManager
import android.content.ContentResolver
import android.content.Context
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.*
import com.shadhinmusiclibrary.adapter.TopTenItemAdapter
import com.shadhinmusiclibrary.adapter.TrendingItemsAdapter
import com.shadhinmusiclibrary.adapter.SearchTracksAdapter
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.utils.MySuggestionProvider
import com.shadhinmusiclibrary.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SearchFragment : CommonBaseFragment(), FragmentEntryPoint {

   private lateinit var viewModel :SearchViewModel
    private lateinit var searchText: String
    private var queryTextChangedJob: Job? = null
     private lateinit var tvTrending:TextView
     private lateinit var tvTrendingVideo:TextView
    private lateinit var tvArtist:TextView
    private lateinit var tvAlbums:TextView
    private lateinit var tvTracks:TextView
    private lateinit var tvVideos:TextView
    private lateinit var tvShows:TextView
    private lateinit var tvEpisodes:TextView
    private lateinit var tvPodcastTracks:TextView

    private lateinit var recyclerViewAlbums: RecyclerView
    private lateinit var recyclerViewVideos: RecyclerView
    private lateinit var recyclerViewTracks: RecyclerView
    private lateinit var recyclerViewShows: RecyclerView
    private lateinit var recyclerViewEpisodes: RecyclerView
    private lateinit var recyclerViewPodcastTracks: RecyclerView

    private lateinit var  recyclerViewTrending: RecyclerView
    private lateinit var recyclerViewTrendingVideos:RecyclerView
    private lateinit var  recyclerViewArtist:RecyclerView
    var mSuggestionAdapter : SearchSuggestionAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        recyclerViewTrending = requireView().findViewById(R.id.recyclerViewTrending)
         recyclerViewTrendingVideos = requireView().findViewById(R.id.recyclerViewTrendingVideos)
         recyclerViewArtist = requireView().findViewById(R.id.recyclerViewArtist)

        tvTrending =requireView().findViewById(R.id.tvTrending)
        tvTrendingVideo = requireView().findViewById(R.id.tvTrendingVideo)
        viewModel.getTopTrendingItems("s")
        viewModel.topTrendingContent.observe(viewLifecycleOwner) { response ->
            if (response.status == Status.SUCCESS) {

                recyclerViewTrending.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                recyclerViewTrending.adapter = TopTenItemAdapter(response?.data?.data!!)
                Log.e("TAG","DATA: "+ response.data?.data)

            }
        }

        viewModel.getTopTrendingVideos("v")
        viewModel.topTrendingVideoContent.observe(viewLifecycleOwner) { response ->
            if (response.status == Status.SUCCESS) {

                recyclerViewTrendingVideos.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                recyclerViewTrendingVideos.adapter = TrendingItemsAdapter(response?.data?.data!!)
                Log.e("TAG","DATA123: "+ response.data?.data)

            }
        }
//        observeData(searchText)
        val search: android.widget.SearchView = view.findViewById(R.id.searchInput)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager?
        search.setSearchableInfo(searchManager?.getSearchableInfo(activity?.getComponentName()))

        mSuggestionAdapter = SearchSuggestionAdapter(context, null, 0)
        search.setSuggestionsAdapter(mSuggestionAdapter!!)
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
                }


                else if(searchText.isEmpty()){
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
                  recyclerViewTrending.visibility= GONE
                  recyclerViewTrendingVideos.visibility = GONE
                  tvTrending.visibility= GONE
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

        viewModel = ViewModelProvider(this, injector.searchViewModelFactory)[SearchViewModel::class.java]
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
            if (response.status == Status.SUCCESS) {
                recyclerViewArtist = requireView().findViewById(R.id.recyclerViewArtist)
                tvArtist = requireView().findViewById(R.id.tvArtist)
                if(response?.data?.data?.Artist?.data?.size!! > 0) {
                    recyclerViewArtist.visibility = VISIBLE
                    tvArtist.visibility = VISIBLE
                    recyclerViewArtist.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    recyclerViewArtist.adapter =
                        SearchArtistAdapter(response?.data?.data?.Artist?.data!!)
                    Log.e("TAG", "DATA123: " + response?.data?.data)
                }
                else{
                    recyclerViewArtist.visibility = GONE
                    tvArtist.visibility = GONE
                }
            }
        }
        viewModel.searchAlbumContent.observe(viewLifecycleOwner) { response ->
            if (response.status == Status.SUCCESS) {
                recyclerViewAlbums = requireView().findViewById(R.id.recyclerViewAlbums)
                tvAlbums = requireView().findViewById(R.id.tvAlbums)
                if(response?.data?.data?.Album?.data?.size!! > 0) {
                    recyclerViewAlbums.visibility = VISIBLE
                    tvAlbums.visibility = VISIBLE
                    recyclerViewAlbums.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    recyclerViewAlbums.adapter =
                        SearchAlbumsAdapter(response?.data?.data?.Album?.data!!)
                    Log.e("TAG", "DATA123: " + response?.data?.data)
                }
                else{
                    recyclerViewAlbums.visibility = GONE
                    tvAlbums.visibility = GONE
                }
            }
        }
        viewModel.searchTracksContent.observe(viewLifecycleOwner) { response ->
            if (response.status == Status.SUCCESS) {
                recyclerViewTracks = requireView().findViewById(R.id.recyclerViewTracks)
                tvTracks = requireView().findViewById(R.id.tvTracks)
                if(response?.data?.data?.Track?.data?.size!! > 0) {
                    tvTracks.visibility = VISIBLE
                    recyclerViewTracks.visibility = VISIBLE
                    recyclerViewTracks.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    recyclerViewTracks.adapter =
                        SearchTracksAdapter(response.data.data.Track.data)
                    Log.e("TAG", "DATA: " + response.data.data.Track.data)
                }
                else{
                    recyclerViewTracks.visibility = GONE
                    tvTracks.visibility =GONE
                }
            }
        }
          viewModel.searchVideoContent.observe(viewLifecycleOwner) { response ->
            if (response.status == Status.SUCCESS) {
                recyclerViewVideos = requireView().findViewById(R.id.recyclerViewVideos)
                tvVideos = requireView().findViewById(R.id.tvVideos)
                if(response?.data?.data?.Video?.data?.size!! > 0) {
                    tvVideos.visibility = VISIBLE
                    recyclerViewVideos.visibility = VISIBLE
                    recyclerViewVideos.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    recyclerViewVideos.adapter =
                        SearchVideoAdapter(response.data.data.Video.data)
                    Log.e("TAG", "DATA: " + response.data.data.Track.data)
                }
                else{
                    recyclerViewVideos.visibility = GONE
                    tvVideos.visibility =GONE
                }
            }
        }
        viewModel.searchPodcastShowContent.observe(viewLifecycleOwner) { response ->
            if (response.status == Status.SUCCESS) {
                recyclerViewShows= requireView().findViewById(R.id.recyclerViewShows)
                tvShows = requireView().findViewById(R.id.tvShows)
                if(response?.data?.data?.PodcastShow?.data?.size!! > 0) {
                    recyclerViewShows.visibility = VISIBLE
                    tvShows.visibility = VISIBLE
                    recyclerViewShows.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    recyclerViewShows.adapter =
                        SearchShowAdapter(response.data.data.PodcastShow.data)
                    Log.e("TAG", "DATA123: " + response.data.data.PodcastShow.data)
                }
                else{
                    recyclerViewShows.visibility = GONE
                    tvShows.visibility = GONE

                }
            }
        }
        viewModel.searchPodcastEpisodeContent.observe(viewLifecycleOwner) { response ->
            if (response.status == Status.SUCCESS) {
                recyclerViewEpisodes = requireView().findViewById(R.id.recyclerViewEpisodes)
                tvEpisodes = requireView().findViewById(R.id.tvEpisodes)
                if(response?.data?.data?.PodcastEpisode?.data?.size!! > 0) {
                    recyclerViewEpisodes.visibility = VISIBLE
                    tvEpisodes.visibility = VISIBLE
                    recyclerViewEpisodes.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    recyclerViewEpisodes.adapter =
                        SearchEpisodeAdapter(response?.data?.data?.PodcastEpisode?.data!!)
                    Log.e("TAG", "DATA123: " + response?.data?.data)
                }
                else{
                    recyclerViewArtist.visibility = GONE
                    tvEpisodes.visibility = GONE
                }
            }
        }
        viewModel.searchPodcastTrackContent.observe(viewLifecycleOwner) { response ->
            if (response.status == Status.SUCCESS) {
                recyclerViewPodcastTracks = requireView().findViewById(R.id. recyclerViewPodcastTracks)
                tvPodcastTracks = requireView().findViewById(R.id.tvPodcastTracks)
                if(response?.data?.data?.PodcastTrack?.data?.size!! > 0) {
                    recyclerViewPodcastTracks.visibility = VISIBLE
                    tvPodcastTracks.visibility = VISIBLE
                    recyclerViewPodcastTracks.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    recyclerViewPodcastTracks.adapter =
                        SearchPodcastTracksAdapter(response?.data?.data?.PodcastTrack?.data!!)
                    Log.e("TAG", "DATA123: " + response?.data?.data)
                }
                else{
                    recyclerViewPodcastTracks.visibility = GONE
                    tvPodcastTracks.visibility =GONE
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
        return  requireActivity().contentResolver.query(uri, null, selection, selArgs, null)
    }
    companion object {

        @JvmStatic
        fun newInstance() =
            SearchFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}