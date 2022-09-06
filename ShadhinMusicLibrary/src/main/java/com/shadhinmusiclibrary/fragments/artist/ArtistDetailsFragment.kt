package com.shadhinmusiclibrary.fragments.artist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibra.ArtistAlbumsAdapter
import com.shadhinmusiclibra.ArtistsYouMightLikeAdapter
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.*
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.utils.AppConstantUtils


class ArtistDetailsFragment : Fragment(), FragmentEntryPoint, HomeCallBack {
    var homePatchItem: HomePatchItem? = null
    var homePatchDetail: HomePatchDetail? = null
    var artistContent:ArtistContent?= null
    private lateinit var viewModel: ArtistViewModel
    private lateinit var viewModelArtistBanner: ArtistBannerViewModel
    private lateinit var viewModelArtistSong: ArtistContentViewModel
    private lateinit var viewModelArtistAlbum: ArtistAlbumsViewModel
    private lateinit var parentAdapter: ConcatAdapter
    private lateinit var artistHeaderAdapter:ArtistHeaderAdapter
    private lateinit var artistsYouMightLikeAdapter: ArtistsYouMightLikeAdapter
    private lateinit var artistSongAdapter:ArtistSongsAdapter
    private lateinit var artistAlbumsAdapter: ArtistAlbumsAdapter
    private lateinit var parentRecycler:RecyclerView
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
        initialize()
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

    private  fun initialize(){
        setupAdapters()
        setupViewModel()
        observeData()
    }
    private fun setupAdapters() {

         parentRecycler = requireView().findViewById(R.id.recyclerView)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val config = ConcatAdapter.Config.Builder().apply { setIsolateViewTypes(false) }.build()
        artistHeaderAdapter = ArtistHeaderAdapter(homePatchDetail)
        artistSongAdapter= ArtistSongsAdapter()
        artistAlbumsAdapter= ArtistAlbumsAdapter(homePatchItem,this)
        artistsYouMightLikeAdapter = ArtistsYouMightLikeAdapter(homePatchItem,this,homePatchDetail?.ArtistId)
        parentAdapter = ConcatAdapter(
            config,
            artistHeaderAdapter,
            HeaderAdapter(),
            artistSongAdapter,
            artistAlbumsAdapter,
            ArtistsYouMightLikeAdapter(homePatchItem,this,homePatchDetail?.ArtistId)
        )
        parentAdapter.notifyDataSetChanged()
        parentRecycler.setLayoutManager(layoutManager)
        parentRecycler.setAdapter(parentAdapter)
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, injector.artistViewModelFactory)[ArtistViewModel::class.java]
        viewModelArtistBanner = ViewModelProvider(this,injector.artistBannerViewModelFactory)[ArtistBannerViewModel::class.java]
        viewModelArtistSong = ViewModelProvider(this,injector.artistSongViewModelFactory)[ArtistContentViewModel::class.java]
        viewModelArtistAlbum = ViewModelProvider(this,injector.artistAlbumViewModelFactory)[ArtistAlbumsViewModel::class.java]
    }

    private fun observeData() {
        homePatchDetail?.let { viewModel.fetchArtistBioData(it.Artist) }
        viewModel.artistBioContent.observe(viewLifecycleOwner) {
            artistHeaderAdapter.artistBio(it)
//            ArtistHeaderAdapter(it)
           // viewDataInRecyclerView(it)
            //Log.e("TAG","DATA: "+ it.artist)
        }
        homePatchDetail.let {
            it?.ArtistId?.let { it1 -> it1?.toInt()
                ?.let { it2 -> viewModelArtistBanner.fetchArtistBannerData(it2) } }
            viewModelArtistBanner.artistBannerContent.observe(viewLifecycleOwner) {
                artistHeaderAdapter.artistBanner(it,context)
                Log.e("TAG","DATA123: "+ it)
            }
        }
        homePatchDetail.let {
            viewModelArtistSong.fetchArtistSongData(it!!.ArtistId.toInt())
            viewModelArtistSong.artistSongContent.observe(viewLifecycleOwner) {
                //artistHeaderAdapter.artistBanner(it,context)
                artistSongAdapter.artistContent(it)
                Log.e("TAG","DATA: "+ it)
            }
        }
        homePatchDetail.let {
            viewModelArtistAlbum.fetchArtistAlbum("r", it?.ArtistId?.toInt()!!)
            viewModelArtistAlbum.artistAlbumContent.observe(viewLifecycleOwner) {
                //artistHeaderAdapter.artistBanner(it,context)
               // artistSongAdapter.artistContent(it)
                 artistAlbumsAdapter.setData(it)
                Log.e("TAG","DATAALBUM: "+ it)
            }
        }
        Log.e("TAG","ARTISTID: "+ homePatchDetail?.ArtistId)
    }
//    private fun prepareBiographyView() {
//        val biographyMain:ExpandableTextView = requireView().findViewById(R.id.tvDescription)
//        biographyMain.setInterpolator(OvershootInterpolator())
//        biographyMain.setExpandInterpolator(OvershootInterpolator())
//        biographyMain.setCollapseInterpolator(OvershootInterpolator())
//    }

//    private fun setBiographyData(lastFmResult: LastFmResult?) {
//        val biographyMain:ExpandableTextView = requireView().findViewById(R.id.tvDescription)
//        if (lastFmResult != null && lastFmResult.getArtist() != null && lastFmResult.getArtist()
//                .getBio() != null && lastFmResult.getArtist().getBio()
//                .getSummary() != null && lastFmResult.getArtist().getBio().getSummary()
//                .length > AppConstantUtils.LAST_FM_MIN_BIO_CHAR
//        ) {
//            val bio: String = lastFmResult.getArtist().getBio().getSummary()
//            biographyMain.setText(Html.fromHtml(CharParser.replaceMultipleSpaces(bio)))
//            biographyMain.setClickable(true)
//            biographyMain.setMovementMethod(LinkMovementMethod.getInstance())
//            //showArtistDes()
//        }
//    }

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

    override fun onClickItemAndAllItem(itemPosition: Int, patch: HomePatchItem) {
        Log.e("TAG","DATA ARtist: "+ patch)
        //  setAdapter(patch)
        homePatchDetail = patch.Data[itemPosition]

        artistHeaderAdapter.setData(homePatchDetail!!)
        artistsYouMightLikeAdapter.adapter!!.artistIDtoSkip = homePatchDetail!!.ArtistId
       // artistsYouMightLikeAdapter.adapter!!.initialize()
        artistsYouMightLikeAdapter.notifyDataSetChanged()
       // artistsYouMightLikeAdapter.adapter!!.notifyDataSetChanged()

        observeData()
        parentAdapter.notifyDataSetChanged()
        parentRecycler.scrollToPosition(0)
    }

    override fun onClickSeeAll(patch: HomePatchItem) {
        TODO("Not yet implemented")
    }
}