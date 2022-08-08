package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.AllAdapter
/**
 * Rezaul Khan
 * https://github.com/rezaulkhan111
 **/
class AllFragment : Fragment() {
    private lateinit var allAdapter: AllAdapter
    private lateinit var rvAllHome: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all, container, false)

        rvAllHome = view.findViewById(R.id.rv_all_home)
        allAdapter = AllAdapter()
        rvAllHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            rvAllHome.adapter = allAdapter
        }
        return view
    }
}