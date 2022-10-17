package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.activities.SDKMainActivity
import com.shadhinmusiclibrary.adapter.GenrePlaylistAdapter
import com.shadhinmusiclibrary.adapter.HomeFooterAdapter
import com.shadhinmusiclibrary.callBackService.BottomSheetDialogItemCallback
import com.shadhinmusiclibrary.data.model.SongDetail
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment

internal class STypeDetailsFragment : CommonBaseFragment(), BottomSheetDialogItemCallback {
    private lateinit var navController: NavController
    private lateinit var adapter: GenrePlaylistAdapter
    private lateinit var listSongDetail: MutableList<SongDetail>
    private  lateinit var footerAdapter: HomeFooterAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRef = inflater.inflate(R.layout.my_bl_sdk_fragment_s_type_details, container, false)
        navController = findNavController()

        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        footerAdapter = HomeFooterAdapter()
        listSongDetail = mutableListOf()
        argHomePatchDetail!!.apply {
            listSongDetail.add(
                SongDetail(
                    ContentID,
                    image,
                    title,
                    ContentType,
                    PlayUrl,
                    Artist,
                    Duration,
                    copyright = "",
                    labelname = "",
                    releaseDate = "",
                    fav,
                    ArtistId,
                    albumId = "",
                    userPlayListId = "",
                    rootContentID = "",
                    rootImage = "",
                    rootContentType = ""
                )
            )
        }
        val config = ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .build()
        adapter = GenrePlaylistAdapter(this)
        val concatAdapter=  ConcatAdapter(config,adapter,footerAdapter)

        adapter.setRootData(argHomePatchDetail!!)
        //adapter.setData(listSongDetail)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = concatAdapter
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
    override fun onClickBottomItem(mSongDetails: SongDetail) {
        (activity as? SDKMainActivity)?.showBottomSheetDialogForPlaylist(
            navController,
            context = requireContext(),
            mSongDetails,
            argHomePatchItem,
            argHomePatchDetail
        )
    }
}