package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopTrendingPlaylistFragment : Fragment() {
    private lateinit var playListAdapter: AlbumAdapter
    private lateinit var navController: NavController
    var homePatchItem: HomePatchItem? = null
    var homePatchDetail: HomePatchDetail? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_album, container, false)
        navController = findNavController()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchOnlineData()

        //dataAdapter.setData(getMockData())

        val button: AppCompatImageView = view.findViewById(R.id.imageBack)
        val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
        button.setOnClickListener {
            manager.popBackStack("Trending", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            // Toast.makeText(requireActivity(),"click",Toast.LENGTH_LONG).show()
        }
    }

    private fun fetchOnlineData() {
        val api: ApiService = RetroClient.getApiShadhinMusicService()
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

    //    private fun getMockData(): List<GenreDataModel> = listOf(
//
//        GenreDataModel.Artist(
//            name = "Artist"
//
//        ),
//
//        GenreDataModel.Artist2(
//            name = "Ad"
//        ),
//
//    )
    companion object {


        @JvmStatic
        fun newInstance() =
            TopTrendingPlaylistFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}