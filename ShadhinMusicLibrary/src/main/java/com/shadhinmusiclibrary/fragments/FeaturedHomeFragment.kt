package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment

class FeaturedHomeFragment : CommonBaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_featured_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnrelease: Button= requireView().findViewById(R.id.btnLatestRelease)
//        val btnPopularArtist: Button = requireView().findViewById(R.id.btnPopularArtists)
//        btnPopularArtist.setOnClickListener {
//            val manager: FragmentManager =
//                (requireContext() as AppCompatActivity).supportFragmentManager
//            manager.beginTransaction()
//                .replace(R.id.container,FeaturedPopularArtistsFragment() )
//                .addToBackStack("Fragment")
//                .commit()
//        }
//        btnrelease.setOnClickListener {
//            val manager: FragmentManager =
//                (requireContext() as AppCompatActivity).supportFragmentManager
//            manager.beginTransaction()
//                .replace(R.id.container,LatestReleaseFragment() )
//                .addToBackStack("Fragment")
//                .commit()
//        }
//        val recyclerView:RecyclerView = requireView().findViewById(R.id.rv_all_home)

    }
}