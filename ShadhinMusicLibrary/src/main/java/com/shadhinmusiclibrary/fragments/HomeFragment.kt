package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.ParentAdapter


import com.shadhinmusiclibrary.data.model.HomeData
import com.shadhinmusiclibrary.rest.ApiService
import com.shadhinmusiclibrary.rest.RetroClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var rvAllHome: RecyclerView
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

            // dataAdapter.setData(getHomeData())

          getHomeData()

        }
    private fun getHomeData() {

        val api: ApiService = RetroClient.getApiService()!!
        val call = api.getHomeData(1,false)
        call.enqueue(object : Callback<HomeData> {
            override fun onResponse(
                call: Call<HomeData>,
                response: Response<HomeData>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    val dataAdapter = ParentAdapter()
                    val recyclerView:RecyclerView = view?.findViewById(R.id.recyclerView)!!
                    recyclerView.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    recyclerView.adapter = dataAdapter
                    response.body()?.let { dataAdapter.setData(it) }

                    Log.d("TAG", "DATA :"+ response.body()!!.data[0].Design)

                }
            }

            override fun onFailure(call: Call<HomeData>, t: Throwable) {
            }
        })
    }
//    private fun getMockData(): List<DataModel> = listOf(
//        DataModel.Search(
//            name = "Search"
//
//        ),
//        DataModel.Artist(
//            name = "Artist"
//
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
//        DataModel.BlOffers(
//            name = "Download"
//        ),
//        DataModel.TrendingMusicVideo(
//            name = "Download"
//        )
////        DataModel.Header(
////            bgColor = resources.getColor(R.color.friend_bg),
////            title = "My friends"
////        ),
////        DataModel.Friend(
////            name = "My Friend one",
////            gender = "Male"
////        ),
////        DataModel.Friend(
////            name = "My Friend two",
////            gender = "Female"
////        ),
////        DataModel.Friend(
////            name = "My Friend three",
////            gender = "Male"
////        ),
////        DataModel.Header(
////            bgColor = resources.getColor(R.color.colleague_bg),
////            title = "My colleagues"
////        ),
////        DataModel.Colleague(
////            name = "Colleague 1",
////            organization = "Org 1",
////            designation = "Manager"
////        ),
////        DataModel.Colleague(
////            name = "Colleague 2",
////            organization = "Org 2",
////            designation = "Software Eng"
////        ),
////        DataModel.Colleague(
////            name = "Colleague 3",
////            organization = "Org 3",
////            designation = "Software Eng"
////        ),
////        DataModel.Colleague(
////            name = "Colleague 4",
////            organization = "Org 4",
////            designation = "Sr Software Eng"
////        ),
////        DataModel.Colleague(
////            name = "Colleague 5",
////            organization = "Org 5",
////            designation = "Sr Software Eng"
////        ),
//    )
    }
