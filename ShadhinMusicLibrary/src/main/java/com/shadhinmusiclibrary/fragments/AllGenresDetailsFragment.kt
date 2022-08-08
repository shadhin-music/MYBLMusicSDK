package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.AllGenreAdapter
import com.shadhinmusiclibrary.data.model.DataModel


class AllGenresDetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.item_all_genres, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataAdapter = AllGenreAdapter()
        dataAdapter.setData(getMockData())
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = dataAdapter
    }
    private fun getMockData(): List<DataModel> = listOf(

        DataModel.Artist(
            name = "Artist"

        ),
//       v
//        DataModel.Ad(
//            name = "Ad"
//        ),
//        DataModel.Download(
//            name = "Download"
//        ),
//        DataModel.PopularAmarTunes(
//            name = "PopularAmarTunes"
//        ),
//        DataModel.PopularBands(
//            name = "PopularBands"
//        ),
//        DataModel.MadeForYou(
//            name = "Download"
//        ),
//        DataModel.LatestRelease(
//            name = "Download"
//        ),
//        DataModel.PopularPodcast(
//            name = "Download"
//        ),
//        DataModel.BlOffers(
//            name = "Download"
//        ),
//        DataModel.TrendingMusicVideo(
//            name = "Download"
//        )
//        DataModel.Header(
//            bgColor = resources.getColor(R.color.friend_bg),
//            title = "My friends"
//        ),
//        DataModel.Friend(
//            name = "My Friend one",
//            gender = "Male"
//        ),
//        DataModel.Friend(
//            name = "My Friend two",
//            gender = "Female"
//        ),
//        DataModel.Friend(
//            name = "My Friend three",
//            gender = "Male"
//        ),
//        DataModel.Header(
//            bgColor = resources.getColor(R.color.colleague_bg),
//            title = "My colleagues"
//        ),
//        DataModel.Colleague(
//            name = "Colleague 1",
//            organization = "Org 1",
//            designation = "Manager"
//        ),
//        DataModel.Colleague(
//            name = "Colleague 2",
//            organization = "Org 2",
//            designation = "Software Eng"
//        ),
//        DataModel.Colleague(
//            name = "Colleague 3",
//            organization = "Org 3",
//            designation = "Software Eng"
//        ),
//        DataModel.Colleague(
//            name = "Colleague 4",
//            organization = "Org 4",
//            designation = "Sr Software Eng"
//        ),
//        DataModel.Colleague(
//            name = "Colleague 5",
//            organization = "Org 5",
//            designation = "Sr Software Eng"
//        ),
    )
    companion object {


        @JvmStatic
        fun newInstance() =
            AllGenresDetailsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}