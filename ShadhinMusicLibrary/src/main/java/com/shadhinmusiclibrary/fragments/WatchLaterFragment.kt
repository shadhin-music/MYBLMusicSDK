package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.WatchlaterAdapter
import com.shadhinmusiclibrary.callBackService.WatchlaterOnCallBack
import com.shadhinmusiclibrary.download.room.WatchLaterContent
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.player.utils.CacheRepository


internal class WatchLaterFragment : CommonBaseFragment(), WatchlaterOnCallBack {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.my_bl_sdk_fragment_watch_later, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {

            requireActivity().onBackPressed()
        }
    }
    fun loadData(){
        val cacheRepository= CacheRepository(requireContext())
        val dataAdapter = WatchlaterAdapter(cacheRepository.getAllWatchlater()!!)
        // Log.e("TAG", "Track123: " + cacheRepository.getDownloadedContent())
        val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false )
        recyclerView.adapter = dataAdapter


    }

     companion object {

        @JvmStatic
        fun newInstance() =
            WatchLaterFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onClickItem(mSongDetails: MutableList<WatchLaterContent>, clickItemPosition: Int) {
        TODO("Not yet implemented")
    }
}