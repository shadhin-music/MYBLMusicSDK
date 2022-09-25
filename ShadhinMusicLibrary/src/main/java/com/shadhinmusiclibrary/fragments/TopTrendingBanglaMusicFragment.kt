package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.adapter.TopTrendingBanglaMusicAdapter
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment


class TopTrendingBanglaMusicFragment : CommonBaseFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_top_trending_bangla_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        tvTitle.text = argHomePatchItem!!.Name
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = TopTrendingBanglaMusicAdapter(argHomePatchItem!!)
//        val textTitle: TextView = requireView().findViewById(R.id.tvTitle)
//        textTitle.text= homePatchItem!!.Name
        imageBackBtn.setOnClickListener {
            if (ShadhinMusicSdkCore.pressCountDecrement() == 0) {
                requireActivity().finish()
            }
        }
    }
    companion object {

        @JvmStatic
        fun newInstance() =
            TopTrendingBanglaMusicFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}