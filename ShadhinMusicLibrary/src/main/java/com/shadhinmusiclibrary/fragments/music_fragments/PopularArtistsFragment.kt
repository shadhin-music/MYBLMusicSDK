package com.shadhinmusiclibrary.fragments.music_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.PopularArtistsAdapter

class PopularArtistsFragment : Fragment() {
    private lateinit var rvPopularArtist: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRef = inflater.inflate(R.layout.fragment_popular_artists, container, false)
        rvPopularArtist = viewRef.findViewById(R.id.rv_popular_artist)
        rvPopularArtist.apply {
            layoutManager = StaggeredGridLayoutManager(
                3, StaggeredGridLayoutManager.VERTICAL
            )
            adapter = PopularArtistsAdapter()
        }
        return viewRef;
    }
}