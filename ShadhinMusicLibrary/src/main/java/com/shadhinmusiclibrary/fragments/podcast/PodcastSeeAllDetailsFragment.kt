package com.shadhinmusiclibrary.fragments.podcast

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.ParentAdapter
import com.shadhinmusiclibrary.adapter.PodcastSeeAllDetailsAdapter
import com.shadhinmusiclibrary.fragments.base.BaseFragment


internal class PodcastSeeAllDetailsFragment : BaseFragment() {
    lateinit var viewModel: FeaturedPodcastViewModel
    private lateinit var navController: NavController
    private var dataAdapter: PodcastSeeAllDetailsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                injector.featuredpodcastViewModelFactory
            )[FeaturedPodcastViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewRef = inflater.inflate(R.layout.my_bl_sdk_common_rv_layout, container, false)
        navController = findNavController()
        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        viewModel.fetchPodcastSeeAll(false)
       observeData()
    }
      fun observeData(){
          viewModel.podcastSeeAllContent.observe(viewLifecycleOwner){res->
              dataAdapter = PodcastSeeAllDetailsAdapter()

              val recyclerView: RecyclerView = view?.findViewById(R.id.recyclerView)!!
              val layoutManager =
                  LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
              recyclerView.layoutManager = layoutManager
                recyclerView.adapter = dataAdapter
              Log.e("TAG", "PodcastDATA: "+ res.data?.data)
              for (item in res?.data?.data?.indices!!) {
                  if (isValidDesign(res.data.data[item].PatchType) == -1) {
                      res.data.data[item].PatchType = ""
                  }
                  if (res.data.data[item].PatchType.isNotEmpty()) {
                      res.data.data[item].let { it1 ->
                          dataAdapter?.setData(listOf(it1))
                          //dataAdapter?.notifyItemChanged(pageNum)
                          dataAdapter?.notifyDataSetChanged()
                      }
//                  it.data.let {
                      //   it1 ->
                      // }
                  }
              }
          }

      }
    private fun isValidDesign(patchType: String): Int {
        return when (patchType) {
//            "search" -> VIEW_SEARCH
            "PP" -> ParentAdapter.VIEW_ARTIST
            "TN" -> ParentAdapter.VIEW_PLAYLIST
            "SS" -> ParentAdapter.VIEW_RELEASE

            else -> {
                -1
            }
        }
    }
}