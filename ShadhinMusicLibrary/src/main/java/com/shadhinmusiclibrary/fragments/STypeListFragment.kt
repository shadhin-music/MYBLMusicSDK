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
import com.shadhinmusiclibrary.adapter.ReleaseAdapter
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.utils.AppConstantUtils
import java.io.Serializable

class STypeListFragment : CommonBaseFragment(), HomeCallBack {
    private lateinit var navController: NavController
   private  lateinit var releaseAdapter:ReleaseAdapter
   private lateinit var footerAdapter:HomeFooterAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRef = inflater.inflate(R.layout.fragment_s_type_list, container, false)
        navController = findNavController()

        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val verticalSpanCount = 1
        val horizontalSpanCount = 3

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        //  recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        releaseAdapter = ReleaseAdapter(argHomePatchItem!!, this)
        footerAdapter = HomeFooterAdapter()
        val config = ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .build()
        val concatAdapter=  ConcatAdapter(config,releaseAdapter,footerAdapter)
        val layoutManager = GridLayoutManager(context, horizontalSpanCount)
        val onSpanSizeLookup: GridLayoutManager.SpanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (concatAdapter.getItemViewType(position) == HomeFooterAdapter.VIEW_TYPE) horizontalSpanCount else verticalSpanCount
                }
            }
        recyclerView.layoutManager = layoutManager
        layoutManager.setSpanSizeLookup(onSpanSizeLookup)
        recyclerView.adapter = concatAdapter

//        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
//        recyclerView.adapter = ReleaseAdapter(argHomePatchItem!!, this)
        val title: TextView = view.findViewById(R.id.tvTitle)
        title.text = argHomePatchItem!!.Name
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
            R.id.action_s_type_list_fragment_to_S_type_details_fragment,
            Bundle().apply {
                putSerializable(
                    AppConstantUtils.PatchItem,
                    selectedHomePatchItem
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