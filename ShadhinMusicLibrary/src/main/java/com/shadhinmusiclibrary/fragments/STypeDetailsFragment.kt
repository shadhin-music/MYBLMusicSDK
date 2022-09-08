package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.adapter.AlbumAdapter
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment

class STypeDetailsFragment : CommonBaseFragment() {
    private lateinit var navController: NavController
    private lateinit var adapter: AlbumAdapter
    private lateinit var listSongDetail: MutableList<SongDetail>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRef = inflater.inflate(R.layout.fragment_s_type_details, container, false)
        navController = findNavController()

        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listSongDetail = mutableListOf()
        argHomePatchDetail!!.apply {
            listSongDetail.add(
                SongDetail(
                    ContentID,
                    image,
                    title,
                    ContentType,
                    PlayUrl,
                    Artist,
                    Duration,
                    copyright = "",
                    labelname = "",
                    releaseDate = "",
                    fav,
                    ArtistId,
                    albumId = "",
                    userPlayListId = ""
                )
            )
        }

        adapter = AlbumAdapter()
        adapter.setRootData(argHomePatchDetail!!)
        adapter.setData(listSongDetail)
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
}