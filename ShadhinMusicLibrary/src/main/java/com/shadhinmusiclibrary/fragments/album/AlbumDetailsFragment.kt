package com.shadhinmusiclibrary.fragments.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.adapter.AlbumAdapter
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.base.BaseFragment

class AlbumDetailsFragment :
    BaseFragment<AlbumViewModel, AlbumViewModelFactory>(),
    FragmentEntryPoint {

    private lateinit var navController: NavController
    private lateinit var adapter: AlbumAdapter

    override fun getViewModel(): Class<AlbumViewModel> {
        return AlbumViewModel::class.java
    }

    override fun getViewModelFactory(): AlbumViewModelFactory {
        return injector.factoryAlbumVM
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewRef = inflater.inflate(R.layout.fragment_album_details, container, false)
        val btnCheck: Button = viewRef.findViewById(R.id.btnCheck)
        navController = findNavController()

        btnCheck.setOnClickListener {
            navController.navigate(R.id.action_album_details_fragment_to_music_play_bs)
        }

        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AlbumAdapter()

        ///read data from online
        fetchOnlineData(argHomePatchDetail!!.ContentID.toInt())
        adapter.setRootData(argHomePatchDetail!!)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            if (ShadhinMusicSdkCore.pressCountDecrement() == 0) {
                requireActivity().finish()
            } else {
                navController.popBackStack()
            }
        }
    }

    private fun fetchOnlineData(contentId: Int) {
        viewModel!!.fetchAlbumContent(contentId)
        viewModel!!.albumContent.observe(requireActivity()) {
            adapter.setData(it)
        }
    }
}