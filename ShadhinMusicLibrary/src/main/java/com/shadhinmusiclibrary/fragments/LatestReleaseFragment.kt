package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.FeaturedLatestTracksAdapter
import com.shadhinmusiclibrary.callBackService.LatestReleaseOnCallBack
import com.shadhinmusiclibrary.data.model.FeaturedSongDetail
import com.shadhinmusiclibrary.fragments.artist.FeaturedTracklistViewModel
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.utils.Status
import com.shadhinmusiclibrary.utils.UtilHelper

class LatestReleaseFragment : CommonBaseFragment(), LatestReleaseOnCallBack {
    lateinit var viewModel: FeaturedTracklistViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular_artists, container, false)
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                injector.featuredtrackListViewModelFactory
            )[FeaturedTracklistViewModel::class.java]
    }

    fun observeData() {
        viewModel.fetchFeaturedTrackList()
        viewModel.featuredTracklistContent.observe(viewLifecycleOwner) { response ->
            if (response.status == Status.SUCCESS) {
                val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
                recyclerView.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                recyclerView.adapter =
                    response?.data?.data?.let { FeaturedLatestTracksAdapter(it, this) }
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

    override fun onClickItem(
        mSongDetails: MutableList<FeaturedSongDetail>,
        clickItemPosition: Int
    ) {
        if (playerViewModel.currentMusic != null) {
            if ((mSongDetails[clickItemPosition].contentID != playerViewModel.currentMusic?.mediaId)) {
                playerViewModel.skipToQueueItem(clickItemPosition)
            } else {
                playerViewModel.togglePlayPause()
            }
        } else {
            playItem(
                UtilHelper.getSongDetailToFeaturedSongDetailList(mSongDetails),
                clickItemPosition
            )
        }
    }
}