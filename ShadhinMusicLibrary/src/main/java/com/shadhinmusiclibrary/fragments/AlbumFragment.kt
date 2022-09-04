package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.AlbumAdapter
import com.shadhinmusiclibrary.data.model.APIResponse
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.rest.RetroClient
import com.shadhinmusiclibrary.utils.AppConstantUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumFragment : Fragment() {
    private lateinit var adapter: AlbumAdapter
    private var listData: MutableList<SongDetail>? = null
    var homePatchItem: HomePatchItem? = null
    var homePatchDetail: HomePatchDetail? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewRef = inflater.inflate(R.layout.fragment_album, container, false)
        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val singleDetails =
            arguments?.getSerializable(AppConstantUtils.SingleDataItem) as HomePatchDetail
        listData = mutableListOf()
        adapter = AlbumAdapter()
        ///read data from online
       // fetchOnlineData(singleDetails.ContentID.toInt())
        adapter.setRootData(singleDetails)

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

//    private fun fetchOnlineData(contentId: Int) {
//        val api: ApiService = RetroClient.getApiShadhinMusicService()
//        val call = api.getAlbumContent(contentId)
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
//    }
}