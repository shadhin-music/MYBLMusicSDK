package com.shadhinmusiclibrary.fragments.home


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.adapter.HomeFooterAdapter
import com.shadhinmusiclibrary.adapter.ParentAdapter
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.HomeData
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.base.BaseFragment
import com.shadhinmusiclibrary.utils.AppConstantUtils
import com.shadhinmusiclibrary.utils.Status
import java.io.Serializable

internal class HomeFragment : BaseFragment<HomeViewModel, HomeViewModelFactory>(),
    FragmentEntryPoint, HomeCallBack {
    private var dataAdapter: ParentAdapter? = null
    private var pageNum = 1
    //var page = -1
    var isLoading = false
    var isLastPage = false
    private lateinit var rvAllHome: RecyclerView
      private lateinit var footerAdapter: HomeFooterAdapter
    override fun getViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun getViewModelFactory(): HomeViewModelFactory {
        return injector.factoryHomeVM
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("Home", "onViewCreated Message: " + pageNum)
        viewModel!!.fetchHomeData( pageNum,false)
        observeData()
    }

    private fun observeData() {
        val progressBar: ProgressBar = requireView().findViewById(R.id.progress_bar)

        viewModel!!.homeContent.observe(viewLifecycleOwner) { res ->
            if (res.status == Status.SUCCESS) {
                progressBar.visibility = GONE
                viewDataInRecyclerView(res.data)
            } else {
                progressBar.visibility = VISIBLE
            }
            isLoading = false
        }
    }


    private fun viewDataInRecyclerView(homeData: HomeData?) {
        if (dataAdapter == null) {

            footerAdapter = HomeFooterAdapter()

            dataAdapter = ParentAdapter(this)



            val recyclerView: RecyclerView = view?.findViewById(R.id.recyclerView)!!
            val layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.layoutManager = layoutManager
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()


                    if (!isLoading && !isLastPage) {
                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                            //loadMoreItems()
                            Log.e("TAG", "visibleItemCount: " + visibleItemCount)
                            Log.e("TAG", "pastVisibleItem: " + firstVisibleItemPosition)
                            //Log.e("TAG", "pagenumber: " + pageNum++)
                            //pageNum++
                            isLoading = true
                            viewModel!!.fetchHomeData(++pageNum, false)

                            //observeData()
                        }

                    }
                    super.onScrolled(recyclerView, dx, dy)
                }
            })
//            val config = ConcatAdapter.Config.Builder()
//                .setIsolateViewTypes(false)
//                .build()
//            val concatAdapter=  ConcatAdapter(config,dataAdapter)
            recyclerView.adapter = dataAdapter
        }
        homeData.let {
            it?.data?.let { it1 ->
                dataAdapter?.setData(it1)
                dataAdapter?.notifyDataSetChanged()
            }
        }
        if(homeData?.total == pageNum){
            isLastPage = true
            val config = ConcatAdapter.Config.Builder()
                .setIsolateViewTypes(false)
                .build()
            val recyclerView: RecyclerView = view?.findViewById(R.id.recyclerView)!!
            recyclerView.adapter= ConcatAdapter(config,dataAdapter,footerAdapter)
        }

    }

    override fun onClickItemAndAllItem(itemPosition: Int, selectedHomePatchItem: HomePatchItem) {
        ShadhinMusicSdkCore.pressCountIncrement()
        val data = Bundle()
        data.putSerializable(
            AppConstantUtils.PatchItem,
            selectedHomePatchItem as Serializable
        )
        startActivity(Intent(requireActivity(), SDKMainActivity::class.java)
            .apply {
                putExtra(AppConstantUtils.PatchItem, data)
                putExtra(AppConstantUtils.SelectedPatchIndex, itemPosition)
            })
    }

    override fun onClickSeeAll(selectedHomePatchItem: HomePatchItem) {
        ShadhinMusicSdkCore.pressCountIncrement()
        val data = Bundle()
        data.putSerializable(
            AppConstantUtils.PatchItem,
            selectedHomePatchItem as Serializable
        )
        startActivity(Intent(requireActivity(), SDKMainActivity::class.java)
            .apply {
                putExtra(AppConstantUtils.PatchItem, data)
            })
    }

    override fun onClickItemPodcastEpisode(itemPosition: Int, selectedEpisode: List<Episode>) {
        TODO("Not yet implemented")
    }


}
