package com.shadhinmusiclibrary.fragments.home


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.adapter.ParentAdapter
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.HomeData
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.base.BaseFragment
import com.shadhinmusiclibrary.utils.AppConstantUtils
import com.shadhinmusiclibrary.utils.Status
import java.io.Serializable

internal class HomeFragment : BaseFragment<HomeViewModel, HomeViewModelFactory>(),
    FragmentEntryPoint, HomeCallBack {

    private lateinit var rvAllHome: RecyclerView

    override fun getViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun getViewModelFactory(): HomeViewModelFactory {
        return injector.factoryHomeVM
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        val progressBar:ProgressBar = requireView().findViewById(R.id.progress_bar)
        viewModel!!.fetchHomeData(1, false)
        viewModel!!.homeContent.observe(viewLifecycleOwner) {res->
            if(res.status==Status.SUCCESS){
                progressBar.visibility = GONE
                viewDataInRecyclerView(res.data)
            }
            else{
                progressBar.visibility = VISIBLE
            }
            }
    }

    private fun viewDataInRecyclerView(homeData: HomeData?) {
        val dataAdapter = ParentAdapter(this)
        val recyclerView: RecyclerView = view?.findViewById(R.id.recyclerView)!!
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = dataAdapter
        homeData.let { it?.data?.let { it1 -> dataAdapter.setData(it1) } }
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
}
