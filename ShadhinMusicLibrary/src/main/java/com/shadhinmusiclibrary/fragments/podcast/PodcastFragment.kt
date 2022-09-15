package com.shadhinmusiclibrary.fragments.podcast

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore

import com.shadhinmusiclibrary.adapter.ReleaseAdapter
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.fragments.amar_tunes.AmartunesWebviewFragment
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.utils.AppConstantUtils
import java.io.Serializable

class PodcastFragment : CommonBaseFragment(), HomeCallBack {

    private lateinit var navController: NavController

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            homePatchItem = it.getSerializable(AppConstantUtils.PatchItem) as HomePatchItem?
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRef = inflater.inflate(R.layout.fragment_podcast, container, false)
        navController = findNavController()

        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = ReleaseAdapter(argHomePatchItem!!, this)
        val title: TextView = view.findViewById(R.id.tvTitle)
        title.text = argHomePatchItem!!.Name
        Log.d("TAG","CLICK ITEM123: "+ argHomePatchItem)
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            if (ShadhinMusicSdkCore.pressCountDecrement() == 0) {
                requireActivity().finish()
            }
        }
//        val button:Button = requireView().findViewById(R.id.btn)
//        button.setOnClickListener {
//            val manager: FragmentManager =
//                (requireContext() as AppCompatActivity).supportFragmentManager
//            manager.beginTransaction()
//                .replace(R.id.parentRelative, AmartunesWebviewFragment.newInstance())
//                .addToBackStack("Fragment")
//                .commit()
//        }
    }

    override fun onClickItemAndAllItem(itemPosition: Int, selectedHomePatchItem: HomePatchItem) {
        ShadhinMusicSdkCore.pressCountIncrement()
        val homePatchDetail = selectedHomePatchItem.Data[itemPosition]
        val homePatchItem =selectedHomePatchItem
        navController.navigate(R.id.action_podcast_list_fragment_to_podcast_details_fragment,
            Bundle().apply {
                putSerializable(
                    AppConstantUtils.PatchItem,
                    homePatchItem as Serializable
                )
                putSerializable(
                    AppConstantUtils.PatchDetail,
                    homePatchDetail as Serializable
                )
            })
    }

    override fun onClickSeeAll(selectedHomePatchItem: HomePatchItem) {
        TODO("Not yet implemented")
    }

    override fun onClickItemPodcastEpisode(itemPosition: Int, selectedEpisode: List<Episode>) {
        TODO("Not yet implemented")
    }



}