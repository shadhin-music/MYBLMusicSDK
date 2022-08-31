package com.shadhinmusiclibrary.fragments.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.ParentAdapter


import com.shadhinmusiclibrary.data.model.APIResponse
import com.shadhinmusiclibrary.data.model.SortDescription
import com.shadhinmusiclibrary.rest.ApiService
import com.shadhinmusiclibrary.data.model.DataModel
import com.shadhinmusiclibrary.data.model.HomeData
import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.rest.RetroClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() , FragmentEntryPoint {

    private lateinit var rvAllHome: RecyclerView
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        observeData()
        // dataAdapter.setData(getHomeData())
       // getHomeData()

        }
    private fun setupViewModel() {
        viewModel = ViewModelProvider(this,injector.homeViewModelFactory)[HomeViewModel::class.java]
    }

    private fun observeData() {
        viewModel.fetchHomeData(1,false)
        viewModel.homeContent.observe(viewLifecycleOwner){ viewDataInRecyclerView(it)}
    }

    private fun viewDataInRecyclerView(homeData: HomeData?) {
        val dataAdapter = ParentAdapter()
        val recyclerView:RecyclerView = view?.findViewById(R.id.recyclerView)!!
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = dataAdapter
        homeData?.let { dataAdapter.setData(it) }
    }

        getHomeData()

    }

    private fun getHomeData() {

        val api: ApiService = RetroClient.getApiService()!!
        val call = api.getHomeData(1, false)
        call.enqueue(object : Callback<APIResponse<List<SortDescription>>> {
            override fun onResponse(
                call: Call<APIResponse<List<SortDescription>>>,
                response: Response<APIResponse<List<SortDescription>>>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    val dataAdapter = ParentAdapter()
                    val recyclerView: RecyclerView = view?.findViewById(R.id.recyclerView)!!
                    recyclerView.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    recyclerView.adapter = dataAdapter
                    response.body()?.let { dataAdapter.setData(it.data) }

                    Log.d("TAG", "DATA :" + response.body()!!.data[0].Design)

                }
            }

            override fun onFailure(call: Call<APIResponse<List<SortDescription>>>, t: Throwable) {
            }
        })
    }*/
   /* private fun getMockData(): List<DataModel> = listOf(
        DataModel.Search(
            name = "Search"

        ),
        DataModel.Artist(
            name = "Artist"

        ),
        DataModel.TopTrending(
            name = "TopTrending"
        ),
       DataModel.BrowseAll(
            name = "BrowseAll"
        ),
        DataModel.Ad(
            name = "Ad"
        ),
        DataModel.Download(
            name = "Download"
        ),
        DataModel.PopularAmarTunes(
            name = "PopularAmarTunes"
        ),
        DataModel.PopularBands(
            name = "PopularBands"
        ),
        DataModel.MadeForYou(
            name = "Download"
        ),
        DataModel.LatestRelease(
            name = "Download"
        ),
        DataModel.PopularPodcast(
            name = "Download"
        ),
        DataModel.BlOffers(
            name = "Download"
        ),
        DataModel.TrendingMusicVideo(
            name = "Download"
        )
//        DataModel.Header(
//            bgColor = resources.getColor(R.color.friend_bg),
//            title = "My friends"
//        ),
//        DataModel.TopTrending(
//            name = "TopTrending"
//        ),
//       DataModel.BrowseAll(
//            name = "BrowseAll"
//        ),
//        DataModel.Ad(
//            name = "Ad"
//        ),
//        DataModel.Download(
//            name = "Download"
//        ),
//        DataModel.PopularAmarTunes(
//            name = "PopularAmarTunes"
//        ),
//        DataModel.PopularBands(
//            name = "PopularBands"
//        ),
//        DataModel.MadeForYou(
//            name = "Download"
//        ),
//        DataModel.LatestRelease(
//            name = "Download"
//        ),
//        DataModel.PopularPodcast(
//            name = "Download"
//        ),
    )*/
    }
