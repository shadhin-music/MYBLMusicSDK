package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.adapter.HomeFooterAdapter
import com.shadhinmusiclibrary.adapter.PopularArtistAdapter
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.data.model.podcast.Track
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.utils.AppConstantUtils
import java.io.Serializable


internal class PopularArtistsFragment : CommonBaseFragment(), HomeCallBack {

    private lateinit var navController: NavController
    private lateinit var footerAdapter: HomeFooterAdapter
    private lateinit var popularArtistAdapter: PopularArtistAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewR = inflater.inflate(R.layout.fragment_popular_artists, container, false)
        navController = findNavController()

        return viewR;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val verticalSpanCount = 1
        val horizontalSpanCount = 4
        footerAdapter = HomeFooterAdapter()
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        //  recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        popularArtistAdapter = argHomePatchItem.let { PopularArtistAdapter(it!!, this) }
        footerAdapter = HomeFooterAdapter()
        val config = ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .build()
        val concatAdapter = ConcatAdapter(config, popularArtistAdapter, footerAdapter)
        val layoutManager = GridLayoutManager(context, horizontalSpanCount)
        val onSpanSizeLookup: GridLayoutManager.SpanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (concatAdapter.getItemViewType(position) == HomeFooterAdapter.VIEW_TYPE) horizontalSpanCount else verticalSpanCount
                }
            }
        recyclerView.layoutManager = layoutManager
        layoutManager.spanSizeLookup = onSpanSizeLookup
        recyclerView.adapter = concatAdapter
//        val argHomePatchItem =
//            arguments?.getSerializable(AppConstantUtils.PatchItem) as HomePatchItem
        val tvTitle: TextView = requireView().findViewById(R.id.tvTitle)
        tvTitle.text = argHomePatchItem?.Name

//        val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
//        recyclerView.layoutManager =
//            GridLayoutManager(requireContext(), 4)
      //  recyclerView.adapter = concatAdapter
        // recyclerView.adapter =
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            if (ShadhinMusicSdkCore.pressCountDecrement() == 0) {
                requireActivity().finish()
            }
        }
    }

    override fun onClickItemAndAllItem(itemPosition: Int, selectedHomePatchItem: HomePatchItem) {
        ShadhinMusicSdkCore.pressCountIncrement()
        val homePatchDetail = selectedHomePatchItem.Data[itemPosition]
        navController.navigate(
            R.id.to_artist_details,
            Bundle().apply {
                putSerializable(
                    AppConstantUtils.PatchItem,
                    selectedHomePatchItem as Serializable
                )
                putSerializable(
                    AppConstantUtils.PatchDetail,
                    homePatchDetail as Serializable
                )
            })
    }

    override fun onClickSeeAll(selectedHomePatchItem: HomePatchItem) {

    }

    override fun onClickItemPodcastEpisode(itemPosition: Int, selectedEpisode: List<Episode>) {
        TODO("Not yet implemented")
    }


}