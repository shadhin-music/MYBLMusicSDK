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
import com.shadhinmusiclibrary.R

import com.shadhinmusiclibrary.adapter.PopularPodcastAdapter

class PodcastFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_podcast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataAdapter = PopularPodcastAdapter()
       // dataAdapter.setData(getMockData())

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false )
        recyclerView.adapter = dataAdapter
        val back: ImageView = view.findViewById(R.id.imageBack)

        val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
        back.setOnClickListener {
            manager.popBackStack("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)

        }
    }
//    private fun getMockData(): List<GenreDataModel> = listOf(
//
//
//
//        GenreDataModel.Artist(
//            name = "Artist"
//
//        ),
//        GenreDataModel.Artist2(
//            name = "Artist"
//
//        ),
//    )
    companion object {

        @JvmStatic
        fun newInstance() =
            PodcastFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}