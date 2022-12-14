package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.adapter.HomeFooterAdapter
import com.shadhinmusiclibrary.adapter.ReleaseAdapter
import com.shadhinmusiclibrary.adapter.TopTrendingBanglaMusicAdapter
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment


class TopTrendingBanglaMusicFragment : CommonBaseFragment() {

       private lateinit var adapter: TopTrendingBanglaMusicAdapter
       private lateinit var footerAdapter: HomeFooterAdapter
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
        val verticalSpanCount = 1
        val horizontalSpanCount = 2

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        //  recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        adapter = TopTrendingBanglaMusicAdapter(argHomePatchItem!!)
        footerAdapter = HomeFooterAdapter()
        val config = ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .build()
        val concatAdapter=  ConcatAdapter(config,adapter,footerAdapter)
        val layoutManager = GridLayoutManager(context, horizontalSpanCount)
        val onSpanSizeLookup: GridLayoutManager.SpanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (concatAdapter.getItemViewType(position) == HomeFooterAdapter.VIEW_TYPE) horizontalSpanCount else verticalSpanCount
                }
            }
        recyclerView.layoutManager = layoutManager
        layoutManager.setSpanSizeLookup(onSpanSizeLookup)
        recyclerView.adapter = concatAdapter
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        tvTitle.text = argHomePatchItem!!.Name
//        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
//        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = concatAdapter
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