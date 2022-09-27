//package com.shadhinmusiclibrary.fragments.album
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.View.GONE
//import android.view.View.VISIBLE
//import android.view.ViewGroup
//import android.widget.ProgressBar
//import androidx.appcompat.widget.AppCompatImageView
//import androidx.lifecycle.Observer
//import androidx.navigation.NavController
//import androidx.navigation.fragment.findNavController
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.shadhinmusiclibrary.R
//import com.shadhinmusiclibrary.ShadhinMusicSdkCore
//import com.shadhinmusiclibrary.adapter.AlbumAdapter
//import com.shadhinmusiclibrary.callBackService.OnItemClickCallback
//import com.shadhinmusiclibrary.data.model.HomePatchDetail
//import com.shadhinmusiclibrary.data.model.HomePatchItem
//import com.shadhinmusiclibrary.data.model.SongDetail
//import com.shadhinmusiclibrary.fragments.artist.ArtistAlbumModelData
//import com.shadhinmusiclibrary.fragments.base.BaseFragment
//import com.shadhinmusiclibrary.player.utils.isPlaying
//import com.shadhinmusiclibrary.utils.Status
//import com.shadhinmusiclibrary.utils.UtilHelper
//
//class BottomsheetAlbumDetailsFragment :
//    BaseFragment<AlbumViewModel, AlbumViewModelFactory>(), OnItemClickCallback{
//
//    private lateinit var navController: NavController
//    private lateinit var adapter: AlbumAdapter
//    var artistAlbumModelData: ArtistAlbumModelData? = null
//     var homePatchItem:HomePatchItem?= null
//    var homePatchDetail:HomePatchDetail?=null
////    private lateinit var playerViewModel: PlayerViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//          //  homePatchItem = it.getSerializable("argHomePatchItem") as HomePatchItem?
//            artistAlbumModelData = it.getSerializable("artistAlbumModelData") as ArtistAlbumModelData
//            homePatchDetail = it.getSerializable("argHomePatchDetail") as HomePatchDetail
//        }
//
//    }
//    override fun getViewModel(): Class<AlbumViewModel> {
//        return AlbumViewModel::class.java
//    }
//
//    override fun getViewModelFactory(): AlbumViewModelFactory {
//        return injector.factoryAlbumVM
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View? {
//        val viewRef = inflater.inflate(R.layout.fragment_album_details, container, false)
//        navController = findNavController()
//
//        return viewRef
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//////        createPlayerVM()
////        adapter = AlbumAdapter(this,)
//
//        ///read data from online
//        fetchOnlineData(artistAlbumModelData?.ContentID?.toInt() ?: 0)
//        adapter.setRootData(homePatchDetail)
//
//        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
//        recyclerView.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        recyclerView.adapter = adapter
//        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
//        imageBackBtn.setOnClickListener {
//            if (ShadhinMusicSdkCore.pressCountDecrement() == 0) {
//                requireActivity().finish()
//            } else {
//                navController.popBackStack()
//            }
//        }
//    }
//
//    private fun fetchOnlineData(contentId: Int) {
//        val progressBar: ProgressBar = requireView().findViewById(R.id.progress_bar)
//        viewModel!!.fetchAlbumContent(contentId)
//        viewModel!!.albumContent.observe(requireActivity()) { res ->
//            if (res.status == Status.SUCCESS) {
//                progressBar.visibility = GONE
//                updateAndSetAdapter(res.data!!.data)
//            } else {
//                progressBar.visibility = VISIBLE
//                updateAndSetAdapter(mutableListOf())
//            }
//        }
//    }
//}