package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.MusicVideoAdapter
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.artist.PopularArtistViewModel
import com.shadhinmusiclibrary.utils.Status

internal class MusicVideoFragment : Fragment(), FragmentEntryPoint {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var viewModel:PopularArtistViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private fun setupViewModel() {

        viewModel =
            ViewModelProvider(this, injector.popularArtistViewModelFactory)[PopularArtistViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_featured_popular_artist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        observeData()
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
        fun observeData() {
            viewModel.fetchLatestVideo()
            viewModel.latestVideoContent.observe(viewLifecycleOwner) { response ->
                if (response.status == Status.SUCCESS) {
                    val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
                    recyclerView.layoutManager =
                        GridLayoutManager(requireContext(), 2)

                    recyclerView.adapter = MusicVideoAdapter(response.data!!.data)

                } else {

                }
            }
        }

    companion object {


        @JvmStatic
        fun newInstance() =
            MusicVideoFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}


