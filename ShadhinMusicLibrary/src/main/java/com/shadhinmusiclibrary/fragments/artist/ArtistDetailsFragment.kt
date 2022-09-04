package com.shadhinmusiclibrary.fragments.artist

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.*
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.lastfm.LastFmResult
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.home.HomeViewModel
import com.shadhinmusiclibrary.player.utils.CharParser
import com.shadhinmusiclibrary.utils.AppConstantUtils
import com.shadhinmusiclibrary.utils.ExpandableTextView


class ArtistDetailsFragment : Fragment(), FragmentEntryPoint {
    var homePatchItem: HomePatchItem? = null
    var homePatchDetail: HomePatchDetail? = null
    private lateinit var viewModel: ArtistViewModel
    private lateinit var parentAdapter: ConcatAdapter
    private lateinit var artistHeaderAdapter:ArtistHeaderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            homePatchItem = it.getSerializable(AppConstantUtils.PatchItem) as HomePatchItem?
            homePatchDetail = it.getSerializable(AppConstantUtils.PatchDetail) as HomePatchDetail?
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
        setupAdapters()
        setupViewModel()
        observeData()
//        val dataAdapter = ArtistDetailsAdapter(homePatchItem)
//        dataAdapter.setData(homePatchDetail)
//        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
//        recyclerView.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        recyclerView.adapter = dataAdapter
//        val back: ImageView? = view.findViewById(R.id.imageBack)
//
//        val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
//        back?.setOnClickListener {
//            manager.popBackStack("Artist Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
//
//        }
    }
    private fun setupAdapters() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val config = ConcatAdapter.Config.Builder().apply { setIsolateViewTypes(false) }.build()

        val parentRecycler: RecyclerView = requireView().findViewById(R.id.recyclerView)

        artistHeaderAdapter = ArtistHeaderAdapter(homePatchDetail)
        parentAdapter = ConcatAdapter(
            config,
            artistHeaderAdapter,
            HeaderAdapter(),
            PodcastEpisodesAdapter(),
            PodcastMoreEpisodesAdapter()
        )
        parentRecycler.setLayoutManager(layoutManager)
        parentRecycler.setAdapter(parentAdapter)
    }
    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, injector.artistViewModelFactory)[ArtistViewModel::class.java]
    }

    private fun observeData() {
        homePatchDetail?.let { viewModel.fetchArtistBioData(it.Artist) }
        viewModel.artistBioContent.observe(viewLifecycleOwner) {
            artistHeaderAdapter.artistBio(it)
//            ArtistHeaderAdapter(it)
           // viewDataInRecyclerView(it)
            //Log.e("TAG","DATA: "+ it.artist)
        }
    }
    private fun prepareBiographyView() {
        val biographyMain:ExpandableTextView = requireView().findViewById(R.id.tvDescription)
        biographyMain.setInterpolator(OvershootInterpolator())
        biographyMain.setExpandInterpolator(OvershootInterpolator())
        biographyMain.setCollapseInterpolator(OvershootInterpolator())
    }

    private fun setBiographyData(lastFmResult: LastFmResult?) {
        val biographyMain:ExpandableTextView = requireView().findViewById(R.id.tvDescription)
        if (lastFmResult != null && lastFmResult.getArtist() != null && lastFmResult.getArtist()
                .getBio() != null && lastFmResult.getArtist().getBio()
                .getSummary() != null && lastFmResult.getArtist().getBio().getSummary()
                .length > AppConstantUtils.LAST_FM_MIN_BIO_CHAR
        ) {
            val bio: String = lastFmResult.getArtist().getBio().getSummary()
            biographyMain.setText(Html.fromHtml(CharParser.replaceMultipleSpaces(bio)))
            biographyMain.setClickable(true)
            biographyMain.setMovementMethod(LinkMovementMethod.getInstance())
            //showArtistDes()
        }
    }

//    private fun showArtistDes() {
//        val descriptionLayout:RelativeLayout = requireView().findViewById(R.id.descriptionLayout)
//        val anim = AnimationUtils.loadAnimation(activity, R.anim.fade_in_frag)
//        if (descriptionLayout.getVisibility() !== View.VISIBLE) {
//            descriptionLayout.setVisibility(View.VISIBLE)
//            biographyHeader.setVisibility(View.VISIBLE)
//            descriptionLayout.startAnimation(anim)
//            biographyHeader.startAnimation(anim)
//        }
//    }

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
        fun newInstance(homePatchItem: HomePatchItem, homePatchDetail: HomePatchDetail) =
            ArtistDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("data", homePatchItem)
                    putSerializable("dataX", homePatchDetail)
                }
            }
    }
}