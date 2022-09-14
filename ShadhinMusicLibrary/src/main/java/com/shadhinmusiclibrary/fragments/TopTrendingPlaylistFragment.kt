package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment

class TopTrendingPlaylistFragment : CommonBaseFragment() {
    private lateinit var navController: NavController
    var homePatchItem: HomePatchItem? = null
    var homePatchDetail: HomePatchDetail? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewRef = inflater.inflate(R.layout.fragment_album_details, container, false)
        navController = findNavController()

        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        fetchOnlineData()
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            navController.popBackStack()
        }
    }

    private fun fetchOnlineData() {
//        val api: ApiService = RetroClient.getApiShadhinMusicService()
//        val call = api.getAlbumContent(21132)
//        call.enqueue(object : Callback<APIResponse<List<SongDetail>>> {
//            override fun onResponse(
//                call: Call<APIResponse<List<SongDetail>>>,
//                response: Response<APIResponse<List<SongDetail>>>
//            ) {
//                if (response.isSuccessful) {
//                    Log.e("TTPLF", "onResponse: " + response.body()!!.data.toString())
//                    playListAdapter = AlbumAdapter()
//                    playListAdapter.setData(response.body()!!.data)
//
//                    val recyclerView: RecyclerView = view!!.findViewById(R.id.recyclerView)
//                    recyclerView.layoutManager =
//                        LinearLayoutManager(
//                            requireContext(),
//                            LinearLayoutManager.VERTICAL,
//                            false
//                        )
//                    recyclerView.adapter = playListAdapter
//                }
//            }
//
//            override fun onFailure(call: Call<APIResponse<List<SongDetail>>>, t: Throwable) {
//            }
//        })
    }
}