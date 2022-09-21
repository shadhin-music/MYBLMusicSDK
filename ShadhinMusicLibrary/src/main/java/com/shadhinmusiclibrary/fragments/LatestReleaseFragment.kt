package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.FeaturedLatestTracksAdapter
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.artist.FeaturedTracklistViewModel
import com.shadhinmusiclibrary.utils.Status

class LatestReleaseFragment : Fragment(), FragmentEntryPoint {
    lateinit var viewModel:FeaturedTracklistViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular_artists, container, false)
    }
    private fun setupViewModel() {

        viewModel =
            ViewModelProvider(this, injector.featuredtrackListViewModelFactory)[FeaturedTracklistViewModel::class.java]
    }
    fun observeData() {
        viewModel.fetchFeaturedTrackList()
        viewModel.featuredTracklistContent.observe(viewLifecycleOwner) { response ->
            if (response.status == Status.SUCCESS) {
                val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
                recyclerView.layoutManager =
                    LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
                recyclerView.adapter = response?.data?.data?.let { FeaturedLatestTracksAdapter(it) }
//          Log.e("TAG","ID: "+ argHomePatchItem)

            } else {
//                progressBar.visibility = View.GONE
//                Toast.makeText(requireContext(),"Error happened!", Toast.LENGTH_SHORT).show()
//                showDialog()
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      setupViewModel()
        observeData()
    }
    companion object {

        @JvmStatic
        fun newInstance() =
            LatestReleaseFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}