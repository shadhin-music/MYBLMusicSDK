package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.*
import com.shadhinmusiclibrary.data.model.GenreDataModel

class TopTrendingPlaylistFragment : Fragment() {
    private lateinit var ccAdapter: ConcatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_playlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataAdapter = PlaylistAdapter()
        dataAdapter.setData(getMockData())
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = dataAdapter

        val button: AppCompatImageView = view.findViewById(R.id.imageBack)
        val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
        button.setOnClickListener {
            manager.popBackStack("Trending", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            // Toast.makeText(requireActivity(),"click",Toast.LENGTH_LONG).show()
        }
    }

    private fun setupAdapters() {
        val config = ConcatAdapter.Config.Builder().apply { setIsolateViewTypes(false) }.build()
        ccAdapter = ConcatAdapter(
            config,
            VideoPodcastHeaderAdapter(),
            HeaderAdapter()
        )
    }

    private fun getMockData(): List<GenreDataModel> = listOf(
        GenreDataModel.Artist(
            name = "Artist"
        ),
        GenreDataModel.Artist2(
            name = "Ad"
        ),
        GenreDataModel.Artist2(
            name = "Ad2"
        ),
    )

    companion object {
        @JvmStatic
        fun newInstance() =
            TopTrendingPlaylistFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}