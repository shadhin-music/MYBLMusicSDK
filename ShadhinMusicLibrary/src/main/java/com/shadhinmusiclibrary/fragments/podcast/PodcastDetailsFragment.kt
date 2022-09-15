package com.shadhinmusiclibrary.fragments.podcast

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.adapter.*
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.podcast.Data
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.utils.Status


class PodcastDetailsFragment : CommonBaseFragment(), FragmentEntryPoint, HomeCallBack {
    private lateinit var navController: NavController
//    var homePatchItem: HomePatchItem? = null
//    var homePatchDetail: HomePatchDetail? = null
      private lateinit var viewModel:PodcastViewModel
    private lateinit var parentAdapter: ConcatAdapter
    private lateinit var podcastHeaderAdapter: PodcastHeaderAdapter
    private lateinit var podcastEpisodesAdapter: PodcastEpisodesAdapter
    private lateinit var podcastMoreEpisodesAdapter: PodcastMoreEpisodesAdapter
    var data: Data?= null
     var episode:List<Episode>?=null

    var podcastType: String = ""
    var contentType: String = ""
    var selectedEpisodeID: Int = 0

  //  private lateinit var artistAlbumsAdapter: ArtistAlbumsAdapter
    private lateinit var parentRecycler: RecyclerView
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            homePatchItem = it.getSerializable(AppConstantUtils.PatchItem) as HomePatchItem?
//            homePatchDetail = it.getSerializable(AppConstantUtils.PatchDetail) as HomePatchDetail?
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewRef = inflater.inflate(R.layout.fragment_artist_details, container, false)
        navController = findNavController()
        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        argHomePatchDetail?.let {
            val Type:String = it.ContentType
            podcastType=  Type.take(2)
            contentType = Type.takeLast(2)
            selectedEpisodeID = it.AlbumId.toInt()
            //  Log.d("TAG", "PODCAST DATA: " + contentType + podcastType+it.AlbumId+false)
            //viewModel.fetchPodcastContent(podcastType,it.AlbumId.toInt(),contentType,false)
        }
       // Log.e("Check", "yes iam called")
        initialize()
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            if (ShadhinMusicSdkCore.pressCountDecrement() == 0) {
                requireActivity().finish()
            } else {
                navController.popBackStack()
            }
        }
    }

    private fun initialize() {
        setupAdapters()
        setupViewModel()
        observeData()
    }

    private fun setupAdapters() {

        parentRecycler = requireView().findViewById(R.id.recyclerView)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val config = ConcatAdapter.Config.Builder().apply { setIsolateViewTypes(false) }.build()
        podcastHeaderAdapter = PodcastHeaderAdapter(episode)
        podcastEpisodesAdapter = PodcastEpisodesAdapter(data)
        podcastMoreEpisodesAdapter = PodcastMoreEpisodesAdapter(data,this)
//        artistsYouMightLikeAdapter =
//            ArtistsYouMightLikeAdapter(argHomePatchItem, this, argHomePatchDetail?.ArtistId)
        parentAdapter = ConcatAdapter(
            config,
            podcastHeaderAdapter,
            PodcastTrackHeaderAdapter(),
            podcastEpisodesAdapter,
            podcastMoreEpisodesAdapter

        )
        parentAdapter.notifyDataSetChanged()
        parentRecycler.setLayoutManager(layoutManager)
        parentRecycler.setAdapter(parentAdapter)
    }

    private fun setupViewModel() {

        viewModel =
            ViewModelProvider(this, injector.podcastViewModelFactory)[PodcastViewModel::class.java]
    }

    private fun observeData() {
        viewModel.fetchPodcastContent(podcastType,selectedEpisodeID,contentType,false)
        viewModel.podcastDetailsContent.observe(viewLifecycleOwner) { response ->
            if (response.status == Status.SUCCESS) {

                response?.data?.data?.EpisodeList?.let { podcastHeaderAdapter.setHeader(it) }
                response.data?.data?.EpisodeList?.get(0)?.let { podcastEpisodesAdapter.setData(it.TrackList) }
                response.data?.data?.let { it?.EpisodeList?.let { it1 ->
                    podcastMoreEpisodesAdapter.setData(it1 as MutableList<Episode>)
                } }
                Log.e("TAG", "DATA: " + response.data)
            }

            else {
               Log.e("TAG", "DATA: " + response.message)

            }

//            ArtistHeaderAdapter(it)
            // viewDataInRecyclerView(it)
            //Log.e("TAG","DATA: "+ it.artist)
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext()) //set icon
            .setIcon(android.R.drawable.ic_dialog_alert) //set title
            .setTitle("An Error Happend") //set message
            .setMessage("Go back to previous page") //set positive button
            .setPositiveButton("Okay",
                DialogInterface.OnClickListener { dialogInterface, i ->

                })

            .show()
    }

    companion object {

        @JvmStatic
        fun newInstance(homePatchItem: HomePatchItem, homePatchDetail: HomePatchDetail) =
            PodcastDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("homePatchItem", homePatchItem)
                    putSerializable("homePatchDetail", homePatchDetail)
                }
            }
    }

    override fun onClickItemAndAllItem(itemPosition: Int, selectedHomePatchItem: HomePatchItem) {
        Log.e("TAG", "DATA ARtist: " + selectedHomePatchItem)
        //  setAdapter(patch)
//        argHomePatchDetail = selectedHomePatchItem.Data[itemPosition]
//        artistHeaderAdapter.setData(argHomePatchDetail!!)
//        observeData()
//        artistsYouMightLikeAdapter.artistIDToSkip = argHomePatchDetail!!.ArtistId
//        parentAdapter.notifyDataSetChanged()
//        parentRecycler.scrollToPosition(0)

    }



//    override fun onArtistAlbumClick(
//        itemPosition: Int,
//        artistAlbumModelData: List<ArtistAlbumModelData>,
//    ) {
//        ShadhinMusicSdkCore.pressCountIncrement()
//        val mArtAlbumMod = artistAlbumModelData[itemPosition]
//        navController.navigate(R.id.action_artist_details_fragment_to_album_details_fragment,
//            Bundle().apply {
//                putSerializable(
//                    AppConstantUtils.PatchItem,
//                    HomePatchItem("", "", listOf(), "", "", 0, 0)
//                )
//                putSerializable(
//                    AppConstantUtils.PatchDetail,
//                    HomePatchDetail(
//                        AlbumId = mArtAlbumMod.AlbumId,
//                        ArtistId = mArtAlbumMod.ArtistId,
//                        ContentID = mArtAlbumMod.ContentID,
//                        ContentType = mArtAlbumMod.ContentType,
//                        PlayUrl = mArtAlbumMod.PlayUrl,
//                        AlbumName = "",
//                        AlbumImage = "",
//                        fav = mArtAlbumMod.fav,
//                        Banner = "",
//                        Duration = mArtAlbumMod.duration,
//                        TrackType = "",
//                        image = mArtAlbumMod.image,
//                        ArtistImage = "",
//                        Artist = mArtAlbumMod.artistname,
//                        CreateDate = "",
//                        Follower = "",
//                        imageWeb = "",
//                        IsPaid = false,
//                        NewBanner = "",
//                        PlayCount = 0,
//                        PlayListId = "",
//                        PlayListImage = "",
//                        PlayListName = "",
//                        RootId = "",
//                        RootType = "",
//                        Seekable = false,
//                        TeaserUrl = "",
//                        title = "",
//                        Type = ""
//
//                    ) as Serializable
//                )
//            })
//    }

//    fun NavController.safelyNavigate(@IdRes resId: Int, args: Bundle? = null) =
//        try { navigate(resId, args) }
//        catch (e: Exception) {
//            Log.e("TAG", "Message: "+ e)
//        }

    override fun onClickSeeAll(selectedHomePatchItem: HomePatchItem) {
    }

    override fun onClickItemPodcastEpisode(itemPosition: Int, selectedEpisode: List<Episode>) {
        Log.d("TAG", "DATA ARtist: " + selectedEpisode)
        val episode = selectedEpisode[itemPosition]
        selectedEpisodeID = episode.Id
        observeData()


        //podcastHeaderAdapter.setHeader(data)

        // podcastEpisodesAdapter.setData
//        artistHeaderAdapter.setData(argHomePatchDetail!!)
//        observeData()
//        artistsYouMightLikeAdapter.artistIDToSkip = argHomePatchDetail!!.ArtistId
        //    parentAdapter.notifyDataSetChanged()
//        parentRecycler.scrollToPosition(0)
        parentAdapter.notifyDataSetChanged()
        parentRecycler.scrollToPosition(0)
    }
}