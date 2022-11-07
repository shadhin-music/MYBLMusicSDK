package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.fragments.album.AlbumViewModel
import com.shadhinmusiclibrary.fragments.artist.ArtistViewModel
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.utils.Status

internal class RadioFragment : CommonBaseFragment() {

    private lateinit var viewModel: AlbumViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_bl_sdk_common_rv_pb_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun fetchOnlineData(contentId: String) {
        val progressBar: ProgressBar = requireView().findViewById(R.id.progress_bar)
        viewModel?.fetchPlaylistContent(contentId)
        viewModel?.albumContent?.observe(viewLifecycleOwner) { res ->
            if (res.data?.data != null && res.status == Status.SUCCESS) {
//                playlistTrackAdapter.setData(
//                    res.data.data,
//                    argHomePatchDetail!!,
//                    playerViewModel.currentMusic?.mediaId
//                )
//                playlistHeaderAdapter.setSongAndData(
//                    res.data.data,
//                    argHomePatchDetail!!
//                )
//                updateAndSetAdapter(res!!.data!!.data)
                progressBar.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
//                updateAndSetAdapter(mutableListOf())
            }
        }
    }
}