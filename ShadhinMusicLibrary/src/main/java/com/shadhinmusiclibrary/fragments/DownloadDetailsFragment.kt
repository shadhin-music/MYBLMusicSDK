package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.DownlodViewPagerAdapter
import com.shadhinmusiclibrary.adapter.PodcastEpisodesAdapter
import com.shadhinmusiclibrary.adapter.PopularPodcastAdapter


class DownloadDetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_download_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataAdapter = PodcastEpisodesAdapter()

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false )
        recyclerView.adapter = dataAdapter
       // val back: ImageView = view.findViewById(R.id.imageBack)


    }


    companion object {

        @JvmStatic
        fun newInstance() =
            DownloadDetailsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}