package com.shadhinmusiclibrary.fragments.home


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.adapter.ParentAdapter
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.DataDetails
import com.shadhinmusiclibrary.data.model.SortDescription
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.utils.AppConstantUtils
import java.io.Serializable

class HomeFragment : Fragment(), FragmentEntryPoint, HomeCallBack {

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        observeData()
        // dataAdapter.setData(getHomeData())
        // getHomeData()

    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, injector.homeViewModelFactory)[HomeViewModel::class.java]
    }

    private fun observeData() {
        viewModel.fetchHomeData(1, false)
        viewModel.homeContent.observe(viewLifecycleOwner) { viewDataInRecyclerView(it) }
    }

    private fun viewDataInRecyclerView(homeData: List<SortDescription>) {
        val dataAdapter = ParentAdapter(this)
        val recyclerView: RecyclerView = view?.findViewById(R.id.recyclerView)!!
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = dataAdapter
        homeData.let { dataAdapter.setData(it) }
    }

    override fun onClickItemAndAllItem(
        dataDetails: DataDetails,
        listDataDetail: List<DataDetails>
    ) {
        val data = Bundle()
        val listData = mutableListOf<DataDetails>().apply {
            add(dataDetails)
        }
        data.putSerializable(
            AppConstantUtils.commonData,
            listData as Serializable
        )
        startActivity(Intent(requireActivity(), SDKMainActivity::class.java)
            .apply {
                putExtra(AppConstantUtils.commonData, data)
            })
    }

    override fun onClickSeeAll(listDataDetail: List<DataDetails>) {
        // got to list page
//        val data = Bundle()
//        data.putSerializable(AppConstantUtils.commonData, listDataDetail as Serializable)
//        startActivity(Intent(requireActivity(), SDKMainActivity::class.java)
//            .apply {
//                putExtra(AppConstantUtils.commonData, data)
//            })
    }


    /*private fun getHomeData() {

        val api: ApiService = RetroClient.getApiService()!!
        val call = api.getHomeData(1,false)
        call.enqueue(object : Callback<HomeData> {
            override fun onResponse(
                call: Call<HomeData>,
                response: Response<HomeData>
            ) {
                if (response.isSuccessful && response.code() == 200) {


                }
            }

            override fun onFailure(call: Call<HomeData>, t: Throwable) {

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
 //        DataModel.Friend(
 //            name = "My Friend one",
 //            gender = "Male"
 //        ),
 //        DataModel.Friend(
 //            name = "My Friend two",
 //            gender = "Female"
 //        ),
 //        DataModel.Friend(
 //            name = "My Friend three",
 //            gender = "Male"
 //        ),
 //        DataModel.Header(
 //            bgColor = resources.getColor(R.color.colleague_bg),
 //            title = "My colleagues"
 //        ),
 //        DataModel.Colleague(
 //            name = "Colleague 1",
 //            organization = "Org 1",
 //            designation = "Manager"
 //        ),
 //        DataModel.Colleague(
 //            name = "Colleague 2",
 //            organization = "Org 2",
 //            designation = "Software Eng"
 //        ),
 //        DataModel.Colleague(
 //            name = "Colleague 3",
 //            organization = "Org 3",
 //            designation = "Software Eng"
 //        ),
 //        DataModel.Colleague(
 //            name = "Colleague 4",
 //            organization = "Org 4",
 //            designation = "Sr Software Eng"
 //        ),
 //        DataModel.Colleague(
 //            name = "Colleague 5",
 //            organization = "Org 5",
 //            designation = "Sr Software Eng"
 //        ),
     )*/
}
