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
import com.shadhinmusiclibrary.data.model.SortDescription
import com.shadhinmusiclibrary.di.FragmentEntryPoint

internal class HomeFragment : Fragment(), FragmentEntryPoint, HomeCallBack {

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

    override fun onClickItem() {

    }

    override fun onClickSeeAll() {
        startActivity(Intent(requireActivity(), SDKMainActivity::class.java))
    }
}
