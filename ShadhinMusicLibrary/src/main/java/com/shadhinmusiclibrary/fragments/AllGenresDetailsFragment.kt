package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.BrowseAllPlaylistAdapter
import com.shadhinmusiclibrary.data.model.HomePatchItem


class AllGenresDetailsFragment : Fragment() {

    var homePatchItem:HomePatchItem?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.item_all_genres, container, false)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            homePatchItem = it.getSerializable("data") as HomePatchItem?
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = homePatchItem?.let { BrowseAllPlaylistAdapter(it) }

//        val dataAdapter = BrowseAllPlaylistAdapter()
//        //dataAdapter.setData()
//        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
//        recyclerView.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        recyclerView.adapter = dataAdapter

        val title:TextView= view.findViewById(R.id.tvTitle)
        title.setText(homePatchItem!!.Name)
        val button: AppCompatImageView = view.findViewById(R.id.imageBack)
        val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
        button.setOnClickListener {
            manager.popBackStack("AllGenresDetailsFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE)
           // Toast.makeText(requireActivity(),"click",Toast.LENGTH_LONG).show()
        }


    }


//    private fun getMockData(): List<GenreDataModel> = listOf(
//
//       GenreDataModel.Artist(
//            name = "Artist"
//
//        ),
//
//        GenreDataModel.Artist(
//            name = "Ad"
//        ),
//        GenreDataModel.Artist(
//            name = "Ad"
//        ),
////        DataModel.Download(
////            name = "Download"
////        ),
////        DataModel.PopularAmarTunes(
////            name = "PopularAmarTunes"
////        ),
////        DataModel.PopularBands(
////            name = "PopularBands"
////        ),
////        DataModel.MadeForYou(
////            name = "Download"
////        ),
////        DataModel.LatestRelease(
////            name = "Download"
////        ),
////        DataModel.PopularPodcast(
////            name = "Download"
////        ),
////        DataModel.BlOffers(
////            name = "Download"
////        ),
////        DataModel.TrendingMusicVideo(
////            name = "Download"
////        )
////        DataModel.Header(
////            bgColor = resources.getColor(R.color.friend_bg),
////            title = "My friends"
////        ),
////        DataModel.Friend(
////            name = "My Friend one",
////            gender = "Male"
////        ),
////        DataModel.Friend(
////            name = "My Friend two",
////            gender = "Female"
////        ),
////        DataModel.Friend(
////            name = "My Friend three",
////            gender = "Male"
////        ),
////        DataModel.Header(
////            bgColor = resources.getColor(R.color.colleague_bg),
////            title = "My colleagues"
////        ),
////        DataModel.Colleague(
////            name = "Colleague 1",
////            organization = "Org 1",
////            designation = "Manager"
////        ),
////        DataModel.Colleague(
////            name = "Colleague 2",
////            organization = "Org 2",
////            designation = "Software Eng"
////        ),
////        DataModel.Colleague(
////            name = "Colleague 3",
////            organization = "Org 3",
////            designation = "Software Eng"
////        ),
////        DataModel.Colleague(
////            name = "Colleague 4",
////            organization = "Org 4",
////            designation = "Sr Software Eng"
////        ),
////        DataModel.Colleague(
////            name = "Colleague 5",
////            organization = "Org 5",
////            designation = "Sr Software Eng"
////        ),
//    )
    companion object {


        @JvmStatic
        fun newInstance(homePatchItem: HomePatchItem) =
            AllGenresDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("data", homePatchItem)
                }
            }
    }
}