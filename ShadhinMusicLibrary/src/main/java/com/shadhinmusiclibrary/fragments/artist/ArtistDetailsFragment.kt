package com.shadhinmusiclibrary.fragments.artist

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibra.ArtistAlbumsAdapter
import com.shadhinmusiclibra.ArtistsYouMightLikeAdapter
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.adapter.*
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment

import com.shadhinmusiclibrary.utils.AppConstantUtils
import com.shadhinmusiclibrary.utils.Status
import java.io.Serializable


class ArtistDetailsFragment : CommonBaseFragment(), FragmentEntryPoint, HomeCallBack {
    private lateinit var navController: NavController
//    var homePatchItem: HomePatchItem? = null
//    var homePatchDetail: HomePatchDetail? = null
    var artistContent: ArtistContent? = null
    private lateinit var viewModel: ArtistViewModel
    private lateinit var viewModelArtistBanner: ArtistBannerViewModel
    private lateinit var viewModelArtistSong: ArtistContentViewModel
    private lateinit var viewModelArtistAlbum: ArtistAlbumsViewModel
    //private lateinit var playerViewModel: PlayerViewModel

    private lateinit var parentAdapter: ConcatAdapter
    private lateinit var artistHeaderAdapter: ArtistHeaderAdapter
    private lateinit var artistsYouMightLikeAdapter: ArtistsYouMightLikeAdapter
    private lateinit var artistSongAdapter: ArtistSongsAdapter
    private lateinit var artistAlbumsAdapter: ArtistAlbumsAdapter
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
        Log.e("Check", "yes iam called")
        initialize()
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            if (ShadhinMusicSdkCore.pressCountDecrement() == 0) {
                requireActivity().finish()
            } else {
                navController.popBackStack()
            }
        }
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
        artistHeaderAdapter = ArtistHeaderAdapter(argHomePatchDetail)
        artistSongAdapter = ArtistSongsAdapter()
        artistAlbumsAdapter = ArtistAlbumsAdapter(argHomePatchItem, this)
        artistsYouMightLikeAdapter =
            ArtistsYouMightLikeAdapter(argHomePatchItem, this, argHomePatchDetail?.ArtistId)
        parentAdapter = ConcatAdapter(
            config,
            artistHeaderAdapter,
            HeaderAdapter(),
            artistSongAdapter,
            artistAlbumsAdapter,
            artistsYouMightLikeAdapter
        )
        parentAdapter.notifyDataSetChanged()
        parentRecycler.setLayoutManager(layoutManager)
        parentRecycler.setAdapter(parentAdapter)
    }

    private fun setupViewModel() {

        viewModel =
            ViewModelProvider(this, injector.factoryArtistVM)[ArtistViewModel::class.java]
        viewModelArtistBanner = ViewModelProvider(
            this,
            injector.factoryArtistBannerVM
        )[ArtistBannerViewModel::class.java]
        viewModelArtistSong = ViewModelProvider(
            this,
            injector.factoryArtistSongVM
        )[ArtistContentViewModel::class.java]
        viewModelArtistAlbum = ViewModelProvider(
            this,
            injector.artistAlbumViewModelFactory
        )[ArtistAlbumsViewModel::class.java]
    }

    private fun observeData() {
        argHomePatchDetail?.let { viewModel.fetchArtistBioData(it.Artist) }
        val progressBar: ProgressBar = requireView().findViewById(R.id.progress_bar)
        viewModel.artistBioContent.observe(viewLifecycleOwner) { response ->
            if (response.status == Status.SUCCESS) {
                artistHeaderAdapter.artistBio(response.data)
                progressBar.visibility = GONE
            } else {
                progressBar.visibility = GONE
                Toast.makeText(requireContext(),"Error happened!", Toast.LENGTH_SHORT).show()
                showDialog()
            }

//            ArtistHeaderAdapter(it)
            // viewDataInRecyclerView(it)
            //Log.e("TAG","DATA: "+ it.artist)
        }
        argHomePatchDetail.let {
            it?.ArtistId?.let { it1 ->
                it1?.toInt()
                    ?.let { it2 -> viewModelArtistBanner.fetchArtistBannerData(it2) }
            }
            viewModelArtistBanner.artistBannerContent.observe(viewLifecycleOwner) { response ->
                if (response.status == Status.SUCCESS) {
                    progressBar.visibility = GONE
                    artistHeaderAdapter.artistBanner(response.data)
                } else {
                    progressBar.visibility = VISIBLE
                }

                Log.e("TAG", "DATA123: " + it)
            }
        }
        argHomePatchDetail.let {
            viewModelArtistSong.fetchArtistSongData(it!!.ArtistId.toInt())
            viewModelArtistSong.artistSongContent.observe(viewLifecycleOwner) { res ->
                if (res.status == Status.SUCCESS) {
                    artistSongAdapter.artistContent(res.data)
                } else {
                    showDialog()
                }
//                progressBar.visibility= GONE
//                artistSongAdapter.artistContent(it)
//                Log.e("TAG","DATA: "+ it)
            }
        }
        argHomePatchDetail.let {
            viewModelArtistAlbum.fetchArtistAlbum("r", it!!.ArtistId?.toInt()!!)
            viewModelArtistAlbum.artistAlbumContent.observe(viewLifecycleOwner) { res ->

                if (res.status == Status.SUCCESS) {
                    artistAlbumsAdapter.setData(res.data)
                } else {
                    showDialog()
                }


            }
        }
        Log.e("TAG", "ARTISTID: " + argHomePatchDetail?.ArtistId)
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
            ArtistDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("data", homePatchItem)
                    putSerializable("dataX", homePatchDetail)
                }
            }
    }

    override fun onClickItemAndAllItem(itemPosition: Int, selectedHomePatchItem: HomePatchItem) {
        Log.e("TAG", "DATA ARtist: " + selectedHomePatchItem)
        //  setAdapter(patch)
        argHomePatchDetail = selectedHomePatchItem.Data[itemPosition]
        artistHeaderAdapter.setData(argHomePatchDetail!!)
        observeData()
        artistsYouMightLikeAdapter.artistIDToSkip = argHomePatchDetail!!.ArtistId
        parentAdapter.notifyDataSetChanged()
        parentRecycler.scrollToPosition(0)

    }

    fun loadNewArtist(patchDetails: HomePatchDetail) {
        Log.e("Check", "loadNewArtist")
    }


    override fun onArtistAlbumClick(
        itemPosition: Int,
        artistAlbumModelData: List<ArtistAlbumModelData>,
    ) {
        ShadhinMusicSdkCore.pressCountIncrement()
        val mArtAlbumMod = artistAlbumModelData[itemPosition]
        navController.navigate(R.id.action_artist_details_fragment_to_album_details_fragment,
            Bundle().apply {
                putSerializable(
                    AppConstantUtils.PatchItem,
                    HomePatchItem("", "", listOf(), "", "", 0, 0)
                )
                putSerializable(
                    AppConstantUtils.PatchDetail,
                    HomePatchDetail(
                        AlbumId = mArtAlbumMod.AlbumId,
                        ArtistId = mArtAlbumMod.ArtistId,
                        ContentID = mArtAlbumMod.ContentID,
                        ContentType = mArtAlbumMod.ContentType,
                        PlayUrl = mArtAlbumMod.PlayUrl,
                        AlbumName = "",
                        AlbumImage = "",
                        fav = mArtAlbumMod.fav,
                        Banner = "",
                        Duration = mArtAlbumMod.duration,
                        TrackType = "",
                        image = mArtAlbumMod.image,
                        ArtistImage = "",
                        Artist = mArtAlbumMod.artistname,
                        CreateDate = "",
                        Follower = "",
                        imageWeb = "",
                        IsPaid = false,
                        NewBanner = "",
                        PlayCount = 0,
                        PlayListId = "",
                        PlayListImage = "",
                        PlayListName = "",
                        RootId = "",
                        RootType = "",
                        Seekable = false,
                        TeaserUrl = "",
                        title = "",
                        Type = ""

                    ) as Serializable
                )
            })
    }

    override fun onClickItemPodcastEpisode(itemPosition: Int, selectedEpisode: List<Episode>) {
        TODO("Not yet implemented")
    }

//    fun NavController.safelyNavigate(@IdRes resId: Int, args: Bundle? = null) =
//        try { navigate(resId, args) }
//        catch (e: Exception) {
//            Log.e("TAG", "Message: "+ e)
//        }

    override fun onClickSeeAll(selectedHomePatchItem: HomePatchItem) {
    }
}