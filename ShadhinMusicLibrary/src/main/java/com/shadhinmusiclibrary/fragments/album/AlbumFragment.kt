package com.shadhinmusiclibrary.fragments.album

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.AlbumAdapter
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.base.BaseFragment
import com.shadhinmusiclibrary.fragments.home.AlbumViewModelFactory
import com.shadhinmusiclibrary.utils.AppConstantUtils

class AlbumFragment :
    BaseFragment<AlbumViewModel, AlbumViewModelFactory>(),
    FragmentEntryPoint {

//    var homePatchItem: HomePatchItem? = null
//    var homePatchDetail: HomePatchDetail? = null

    private lateinit var adapter: AlbumAdapter
    private var listData: MutableList<SongDetail>? = null

    override fun getViewModel(): Class<AlbumViewModel> {
        return AlbumViewModel::class.java
    }

    override fun getViewModelFactory(): AlbumViewModelFactory {
        return injector.albumViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewRef = inflater.inflate(R.layout.fragment_album, container, false)
//        arguments?.let {
//            homePatchItem = it.getSerializable(AppConstantUtils.PatchItem) as HomePatchItem?
//            homePatchDetail = it.getSerializable(AppConstantUtils.PatchDetail) as HomePatchDetail?
//        }
        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val singleDetails =
//            arguments?.getSerializable(AppConstantUtils.SingleDataItem) as HomePatchDetail
        listData = mutableListOf()
        adapter = AlbumAdapter()
        ///read data from online
        fetchOnlineData(homePatchDetail!!.ContentID.toInt())
        adapter.setRootData(homePatchDetail!!)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        val button: AppCompatImageView = view.findViewById(R.id.imageBack)
//        val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
        button.setOnClickListener {
//            manager.popBackStack("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            // Toast.makeText(requireActivity(),"click",Toast.LENGTH_LONG).show()
        }
    }

//    private fun setupViewModel() {
//        viewModel =
//            ViewModelProvider(this, injector.albumViewModelFactory)[AlbumViewModel::class.java]
//    }

    private fun fetchOnlineData(contentId: Int) {
//        val api: ApiService = RetroClient.getApiShadhinMusicService()
//        val call = api.getAlbumContent(contentId)
        Log.e("AF", "fetchOnlineData: $contentId")
        viewModel!!.fetchAlbumContent(contentId)
        viewModel!!.albumContent.observe(requireActivity()) {
            adapter.setData(it)
        }
//        call.enqueue(object : Callback<APIResponse<List<SongDetail>>> {
//            override fun onResponse(
//                call: Call<APIResponse<List<SongDetail>>>,
//                response: Response<APIResponse<List<SongDetail>>>
//            ) {
//                if (response.isSuccessful) {
//                    adapter.setData(response.body()!!.data)
//                    Log.e("TTPLF", "onResponse: " + response.body()!!.data.toString())
//                }
//            }
//
//            override fun onFailure(call: Call<APIResponse<List<SongDetail>>>, t: Throwable) {
//            }
//        })
    }
}