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
import com.shadhinmusiclibrary.adapter.PlaylistAdapter
import com.shadhinmusiclibrary.data.model.APIResponse
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.model.DataDetails
import com.shadhinmusiclibrary.data.remote.ApiService
import com.shadhinmusiclibrary.rest.RetroClient
import com.shadhinmusiclibrary.utils.AppConstantUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaylistFragment : Fragment() {
    private var listData: MutableList<SongDetail>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewRef = inflater.inflate(R.layout.fragment_playlist, container, false)
        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val singleDetails =
            arguments?.getSerializable(AppConstantUtils.singleDataItem) as DataDetails
        listData = mutableListOf()
        val dataAdapter = PlaylistAdapter()
        ///read data from online
        fetchOnlineData(singleDetails.ContentID.toInt())
/*        listData!!.add(
            Content(
                singleDetails.ContentID,
                singleDetails.image,
                singleDetails.title,
                singleDetails.ContentType,
                singleDetails.PlayUrl,
                singleDetails.Artist,
                singleDetails.Duration,
                "",
                "",
                "",
                singleDetails.fav,
                singleDetails.ArtistId
            )
        )
        dataAdapter.setData(listData!!)*/
        dataAdapter.setRootData(singleDetails)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = dataAdapter
        val button: AppCompatImageView = view.findViewById(R.id.imageBack)
//        val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
        button.setOnClickListener {
//            manager.popBackStack("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            // Toast.makeText(requireActivity(),"click",Toast.LENGTH_LONG).show()
        }
    }

    private fun fetchOnlineData(contentId: Int) {
        val api: ApiService = RetroClient.getApiShadhinMusicService()
        val call = api.getAlbumContent(contentId)
        call.enqueue(object : Callback<APIResponse<List<SongDetail>>> {
            override fun onResponse(
                call: Call<APIResponse<List<SongDetail>>>,
                response: Response<APIResponse<List<SongDetail>>>
            ) {
                if (response.isSuccessful) {
                    listData!!.clear()
                    listData!!.addAll(response.body()!!.data)
                    Log.e("TTPLF", "onResponse: " + response.body()!!.data.toString())
                }
            }

            override fun onFailure(call: Call<APIResponse<List<SongDetail>>>, t: Throwable) {
            }
        })
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
            PlaylistFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}