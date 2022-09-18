package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.adapter.PopularArtistAdapter
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.utils.AppConstantUtils
import java.io.Serializable


class FeaturedPopularArtistsFragment : CommonBaseFragment(), HomeCallBack {
//        override var argHomePatchItem: HomePatchItem? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//                super.onCreate(savedInstanceState)
//        arguments?.let {
//            homePatchItem = it.getSerializable(AppConstantUtils.PatchItem) as HomePatchItem?
//            homePatchDetail = it.getSerializable(AppConstantUtils.PatchDetail) as HomePatchDetail?
//        }
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewR = inflater.inflate(R.layout.fragment_popular_artists, container, false)


        return viewR;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val argHomePatchItem =
//            arguments?.getSerializable(AppConstantUtils.PatchItem) as HomePatchItem
       val tvTitle:TextView = requireView().findViewById(R.id.tvTitle)
        tvTitle.text = argHomePatchItem?.Name
        val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 4)
          Log.e("TAG","ID: "+ argHomePatchItem)
        recyclerView.adapter = argHomePatchItem.let { PopularArtistAdapter(it!!, this) }
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            if (ShadhinMusicSdkCore.pressCountDecrement() == 0) {
                requireActivity().finish()
            }
        }
    }

    override fun onClickItemAndAllItem(itemPosition: Int, selectedHomePatchItem: HomePatchItem) {
//        ShadhinMusicSdkCore.pressCountIncrement()
//        val homePatchDetail = selectedHomePatchItem.Data[itemPosition]
//        navController.navigate(
//            R.id.action_popular_artist_fragment_to_artist_details_fragment,
//            Bundle().apply {
//                putSerializable(
//                    AppConstantUtils.PatchItem,
//                    selectedHomePatchItem as Serializable
//                )
//                putSerializable(
//                    AppConstantUtils.PatchDetail,
//                    homePatchDetail as Serializable
//                )
//            })
    }

    override fun onClickSeeAll(selectedHomePatchItem: HomePatchItem) {

    }

    override fun onClickItemPodcastEpisode(itemPosition: Int, selectedEpisode: List<Episode>) {
        TODO("Not yet implemented")
    }
}