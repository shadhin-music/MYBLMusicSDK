package com.shadhinmusiclibrary.fragments.artist

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.adapter.ArtistAlbumsAdapter
import com.shadhinmusiclibrary.adapter.ArtistsYouMightLikeAdapter
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.*
import com.shadhinmusiclibrary.callBackService.ArtistOnItemClickCallback
import com.shadhinmusiclibrary.callBackService.BottomSheetDialogItemCallback
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.di.FragmentEntryPoint

import com.shadhinmusiclibrary.utils.AppConstantUtils
import com.shadhinmusiclibrary.utils.Status


internal class BottomSheetArtistDetailsFragment : Fragment(), FragmentEntryPoint, HomeCallBack,
    ArtistOnItemClickCallback, BottomSheetDialogItemCallback {
    private lateinit var navController: NavController
    var homePatchItem: HomePatchItem? = null
    var songDetail: SongDetail? = null
    var homePatchDetail: HomePatchDetail? = null


    var artistContent: ArtistContent? = null
    private lateinit var viewModel: ArtistViewModel
    private lateinit var viewModelArtistBanner: ArtistBannerViewModel
    private lateinit var viewModelArtistSong: ArtistContentViewModel
    private lateinit var viewModelArtistAlbum: ArtistAlbumsViewModel
    private lateinit var parentAdapter: ConcatAdapter
    private lateinit var artistHeaderAdapter: BottomSheetArtistHeaderAdapter
    private lateinit var artistsYouMightLikeAdapter: ArtistsYouMightLikeAdapter
    private lateinit var bsArtistTrackAdapter: BottomSheetArtistTrackAdapter
    private lateinit var artistAlbumsAdapter: ArtistAlbumsAdapter
    private lateinit var parentRecycler: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            homePatchItem = it.getSerializable(AppConstantUtils.PatchItem) as HomePatchItem?
            songDetail = it.getSerializable("songDetail") as SongDetail?
            homePatchDetail = it.getSerializable("argHomePatchDetail") as HomePatchDetail?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewRef = inflater.inflate(R.layout.my_bl_sdk_fragment_artist_details, container, false)
        navController = findNavController()
        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            /*if (ShadhinMusicSdkCore.pressCountDecrement() == 0) {
                requireActivity().finish()
            } else {
                navController.popBackStack()
            }*/
            requireActivity().onBackPressed()
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
        artistHeaderAdapter = BottomSheetArtistHeaderAdapter(songDetail)

        artistAlbumsAdapter = ArtistAlbumsAdapter(homePatchItem, this)
        artistsYouMightLikeAdapter =
            ArtistsYouMightLikeAdapter(homePatchItem, this, songDetail?.ArtistId)
        parentAdapter = ConcatAdapter(
            config,
            artistHeaderAdapter,
            HeaderAdapter(),
            bsArtistTrackAdapter,
            artistAlbumsAdapter,
            // artistsYouMightLikeAdapter
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
        songDetail?.let {
            viewModel.fetchArtistBioData(it.artist)
            Log.e("TAG", "DATA: " + it.artist)
        }
        val progressBar: ProgressBar = requireView().findViewById(R.id.progress_bar)
        viewModel.artistBioContent.observe(viewLifecycleOwner) { response ->

            if (response.status == Status.SUCCESS) {
                artistHeaderAdapter.artistBio(response.data)
                Log.e("TAG", "DATA321: " + response.data?.artist)
//                Log.e("TAG","DATA: "+ response.message)
                progressBar.visibility = GONE
            } else {
                progressBar.visibility = GONE
            }
        }
        songDetail.let {
            it?.ArtistId?.let { it1 ->
                it1
                    ?.let { it2 -> viewModelArtistBanner.fetchArtistBannerData(it2) }
            }
            viewModelArtistBanner.artistBannerContent.observe(viewLifecycleOwner) { response ->
                if (response.status == Status.SUCCESS) {
                    progressBar.visibility = GONE
                    artistHeaderAdapter.artistBanner(response.data)
                } else {
                    progressBar.visibility = VISIBLE
                }
            }
        }
//        songDetail.let {
//            viewModelArtistSong.fetchArtistSongData(it?.ArtistId)
//            viewModelArtistSong.artistSongContent.observe(viewLifecycleOwner) { res ->
//                if (res.status == Status.SUCCESS) {
//                    artistSongAdapter.artistContent(res.data)
//                    artistHeaderAdapter.setHeaderImage(res.data)
//                } else {
//                    showDialog()
//                }
//            }
//        }
//        songDetail.let {
//            viewModelArtistAlbum.fetchArtistAlbum("r", it!!.ArtistId?.toInt()!!)
//            viewModelArtistAlbum.artistAlbumContent.observe(viewLifecycleOwner) { res ->
//                if (res.status == Status.SUCCESS) {
//                    artistAlbumsAdapter.setData(res.data)
//                } else {
//                    showDialog()
//                }
//            }
//        }
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

    override fun onClickItemAndAllItem(itemPosition: Int, selectedHomePatchItem: HomePatchItem) {
        //  setAdapter(patch)
        //songDetail = selectedHomePatchItem.Data[itemPosition]
        // artistHeaderAdapter.setData(argHomePatchDetail!!)
        observeData()
        // artistsYouMightLikeAdapter.artistIDToSkip = songDetail!!.ArtistId
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
        val manager: FragmentManager =
            (requireContext() as AppCompatActivity).supportFragmentManager
//        manager.beginTransaction()
//            .replace(R.id.containerFrame,
//                BottomsheetAlbumDetailsFragment.newInstance(artistAlbumModelData[itemPosition],
//                    homePatchDetail))
//            .addToBackStack("Fragment")
//            .commit()
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
//

    }

    override fun onClickItemPodcastEpisode(itemPosition: Int, selectedEpisode: List<Episode>) {
        TODO("Not yet implemented")
    }

    override fun onClickSeeAll(selectedHomePatchItem: HomePatchItem) {
    }

    override fun onRootClickItem(
        mSongDetails: MutableList<ArtistContentData>,
        clickItemPosition: Int
    ) {

    }

    override fun onClickItem(mSongDetails: MutableList<ArtistContentData>, clickItemPosition: Int) {

    }

    override fun getCurrentVH(
        currentVH: RecyclerView.ViewHolder,
        songDetails: MutableList<ArtistContentData>,
    ) {

    }

    override fun onClickBottomItem(mSongDetails:SongDetail) {
    }
}