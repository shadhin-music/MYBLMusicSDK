package com.shadhinmusiclibrary.fragments.artist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
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
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.utils.AppConstantUtils


class ArtistDetailsFragment : Fragment(), FragmentEntryPoint, HomeCallBack {
    private lateinit var navController: NavController
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
        val viewRef= inflater.inflate(R.layout.fragment_artist_details, container, false)
        navController = findNavController()
        return  viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            ViewModelProvider(this, injector.factoryArtistVM)[ArtistViewModel::class.java]
        viewModelArtistBanner = ViewModelProvider(this,injector.factoryArtistBannerVM)[ArtistBannerViewModel::class.java]
        viewModelArtistSong = ViewModelProvider(this,injector.factoryArtistSongVM)[ArtistContentViewModel::class.java]
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

                 artistAlbumsAdapter.setData(it)
                Log.e("TAG","DATAALBUM: "+ it)
            }
        }
        Log.e("TAG","ARTISTID: "+ homePatchDetail?.ArtistId)
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