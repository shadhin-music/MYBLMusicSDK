package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.ParentMusicAdapter

class AllFragment : Fragment() {
    private lateinit var parentMusicAdapter: ParentMusicAdapter
    private lateinit var rvAllHome: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all, container, false)

        rvAllHome = view.findViewById(R.id.rv_all_home)
        parentMusicAdapter = ParentMusicAdapter()
        rvAllHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            rvAllHome.adapter = parentMusicAdapter
        }
        return view
    }
}