package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.PopularArtistAdapter
import com.shadhinmusiclibrary.data.model.HomePatchItem


class PopularArtistsFragment : Fragment() {
  var homePatchItem:HomePatchItem?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular_artists, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         arguments?.let {
             homePatchItem = it.getSerializable("data") as HomePatchItem?
             Log.d("TAG","DATA: "+ homePatchItem)
         }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 4)
        recyclerView.adapter = homePatchItem?.let { PopularArtistAdapter(it) }
        val back:ImageView = requireView().findViewById(R.id.imageBack)
        val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
        back.setOnClickListener {
            manager.popBackStack("Popular Artist", FragmentManager.POP_BACK_STACK_INCLUSIVE)

        }
    }
    companion object {

        @JvmStatic
        fun newInstance(homePatchItem: HomePatchItem) =
            PopularArtistsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("data", homePatchItem)

                   // data1 = arguments.(data.toString())
                }
            }
    }
}