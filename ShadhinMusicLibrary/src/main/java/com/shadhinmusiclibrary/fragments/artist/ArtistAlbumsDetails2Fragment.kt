package com.shadhinmusiclibrary.fragments.artist

import android.os.Bundle
import androidx.fragment.app.Fragment
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
import com.shadhinmusiclibrary.adapter.ArtistSpecificAlbumAdapter
import com.shadhinmusiclibrary.adapter.HomeFooterAdapter
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.album.AlbumViewModel


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

internal class ArtistAlbumsDetails2Fragment : Fragment(), FragmentEntryPoint {
    private lateinit var navController: NavController
    private lateinit var adapter: ArtistSpecificAlbumAdapter
    private var param1: ArtistAlbumModel? = null
    private var param2: ArtistAlbumModelData? = null
    private lateinit var viewModel: AlbumViewModel
    private lateinit var footerAdapter: HomeFooterAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.get(ARG_PARAM1) as ArtistAlbumModel?
            param2 = it.get(ARG_PARAM2) as ArtistAlbumModelData?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewRef = inflater.inflate(R.layout.fragment_album_details, container, false)
        navController = findNavController()

        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ArtistSpecificAlbumAdapter()
        footerAdapter = HomeFooterAdapter()
        setupViewModel()
        ///read data from online
        fetchOnlineData(param2!!.ContentID)
        adapter.setRootData(param2)
        val config = ConcatAdapter.Config.Builder()
                .setIsolateViewTypes(false)
                .build()
          val concatAdapter=  ConcatAdapter(config,adapter,footerAdapter)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = concatAdapter
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            if (ShadhinMusicSdkCore.pressCountDecrement() == 0) {
                requireActivity().finish()
            } else {
                navController.popBackStack()
            }
        }
    }

    private fun setupViewModel() {


        viewModel = ViewModelProvider(
            this,
            injector.factoryAlbumVM
        )[AlbumViewModel::class.java]
    }

    private fun fetchOnlineData(contentId: String) {
        viewModel.fetchAlbumContent(contentId)
        viewModel.albumContent.observe(viewLifecycleOwner) {
            if(it.data?.data !=null){
                adapter.setData(it.data.data)
            }

        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: ArtistAlbumModel?, param2: ArtistAlbumModelData) =
            ArtistAlbumsDetails2Fragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                    putSerializable(ARG_PARAM2, param2)
                }
            }
    }
}