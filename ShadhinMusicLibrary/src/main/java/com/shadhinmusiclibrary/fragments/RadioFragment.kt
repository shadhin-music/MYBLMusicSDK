package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.RadioTrackAdapter
import com.shadhinmusiclibrary.fragments.album.AlbumViewModel
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.utils.Status
import com.shadhinmusiclibrary.utils.UtilHelper

internal class RadioFragment : CommonBaseFragment() {

    private lateinit var albumVM: AlbumViewModel
    private lateinit var radioTrackAdapter: RadioTrackAdapter

    private lateinit var parentRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_bl_sdk_common_rv_pb_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        radioTrackAdapter = RadioTrackAdapter()
        initialize()

        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun initialize() {
        setupViewModel()
        fetchOnlineData("20148")
    }

    private fun setupViewModel() {
        albumVM = ViewModelProvider(this, injector.factoryAlbumVM)[AlbumViewModel::class.java]
    }

    private fun fetchOnlineData(playlistId: String) {
        parentRecycler = requireView().findViewById(R.id.recyclerView)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val progressBar: ProgressBar = requireView().findViewById(R.id.progress_bar)
        albumVM.fetchPlaylistContent(playlistId)
        albumVM.albumContent.observe(viewLifecycleOwner) { res ->
            if (res.data?.data != null && res.status == Status.SUCCESS) {
                radioTrackAdapter.setRadioTrackData(
                    res.data.data,
                    UtilHelper.getEmptyHomePatchDetail(),
                    ""
                )
                progressBar.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
            }
        }
        parentRecycler.layoutManager = layoutManager
        parentRecycler.adapter = radioTrackAdapter
    }
}