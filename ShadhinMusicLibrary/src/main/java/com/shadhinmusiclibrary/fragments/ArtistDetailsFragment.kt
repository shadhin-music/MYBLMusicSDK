 package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.ArtistDetailsAdapter


 class ArtistDetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_artist_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataAdapter = ArtistDetailsAdapter()
       // dataAdapter.setData()
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = dataAdapter
        val back: ImageView? = view?.findViewById(R.id.imageBack)

        val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
        back?.setOnClickListener {
            manager.popBackStack("Artist Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)

        }
    }
//     private fun getMockData(): List<GenreDataModel> = listOf(
//
//         GenreDataModel.Artist(
//             name = "Artist"
//
//         ),
//
//         GenreDataModel.Artist2(
//             name = "Ad"
//         ),
//         GenreDataModel.Artist3(
//             name = "Ad"
//         ),
//         GenreDataModel.Artist4(
//             name = "Ad"
//         ),
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
//     )
    companion object {

        @JvmStatic
        fun newInstance() =
            ArtistDetailsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}