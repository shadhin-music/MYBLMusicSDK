package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.adapter.FeaturePodcastJCRECAdapter
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.adapter.FeaturedPodcastRecyclerViewAdapter
import com.shadhinmusiclibrary.callBackService.FeaturedPodcastOnItemClickCallback
import com.shadhinmusiclibrary.data.model.FeaturedPodcastDetailsModel
import com.shadhinmusiclibrary.data.model.HomePatchItemModel
import com.shadhinmusiclibrary.fragments.base.BaseFragment
import com.shadhinmusiclibrary.fragments.podcast.FeaturedPodcastViewModel
import com.shadhinmusiclibrary.utils.AppConstantUtils
import com.shadhinmusiclibrary.utils.DataContentType
import com.shadhinmusiclibrary.utils.Status
import com.shadhinmusiclibrary.utils.UtilHelper
import java.io.Serializable


internal class FeaturedPodcastFragment : BaseFragment(), FeaturedPodcastOnItemClickCallback {
    private lateinit var navController: NavController
    private var homePatchitem: HomePatchItemModel? = null
    lateinit var viewModel: FeaturedPodcastViewModel
    private lateinit var data: List<FeaturedPodcastDetailsModel>
    private lateinit var dataJc: List<FeaturedPodcastDetailsModel>
    private lateinit var podcastJBAdapter: FeaturedPodcastRecyclerViewAdapter
    private lateinit var podcastJCAdapter: FeaturePodcastJCRECAdapter
    private lateinit var parentAdapter: ConcatAdapter
    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                injector.featuredpodcastViewModelFactory
            )[FeaturedPodcastViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container1: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewRef = inflater.inflate(R.layout.my_bl_sdk_common_rv_pb_layout, container1, false)
        navController = findNavController()

        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvTitle: TextView = requireView().findViewById(R.id.tvTitle)

        kotlin.runCatching {
            val title = arguments?.getString(DataContentType.TITLE)
            view.findViewById<TextView>(R.id.tvTitle)?.text = title
        }

        //tvTitle.text =  homePatchitem?.Name
        setupViewModel()
        setAdapter()
        observeData()
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            //requireActivity().finish()
            requireActivity().onBackPressed()
        }
    }

    fun setAdapter() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val config = ConcatAdapter.Config.Builder().apply { setIsolateViewTypes(false) }.build()
        podcastJBAdapter = FeaturedPodcastRecyclerViewAdapter(this)
        podcastJCAdapter = FeaturePodcastJCRECAdapter(this)
        val parentRecycler: RecyclerView = requireView().findViewById(R.id.recyclerView)

        parentAdapter = ConcatAdapter(
            config,
            podcastJBAdapter, podcastJCAdapter


        )
        parentRecycler.layoutManager = layoutManager
        parentRecycler.adapter = parentAdapter
    }

    fun observeData() {
        val progressBar: ProgressBar = requireView().findViewById(R.id.progress_bar)
        viewModel.fetchFeaturedPodcast(false)
        viewModel.featuredpodcastContent.observe(viewLifecycleOwner) { response ->
            if (response != null && response.status == Status.SUCCESS) {
                if (response.data?.data?.get(0)?.Data != null) {
                    podcastJBAdapter.setData(
                        response.data.data[0].Data,
                        response.data.data[0].Data[0].ShowName
                    )
                }
                progressBar.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
            }
        }
        viewModel.fetchFeaturedPodcastJC(false)
        viewModel.featuredpodcastContentJC.observe(viewLifecycleOwner) { response ->
            if (response.status == Status.SUCCESS) {
                podcastJCAdapter.setData(
                    response?.data?.data?.get(1)?.Data,
                    response?.data?.data?.get(1)?.Data?.get(0)?.ShowName.toString()
                )
                progressBar.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
            }

            if (response.data?.data?.get(1)?.Data != null) {
                podcastJCAdapter.setData(response?.data?.data?.get(1)?.Data,
                    response?.data?.data?.get(1)?.Data?.get(0)?.ShowName.toString())
            }
        }
          //  else {
//                progressBar.visibility = View.GONE
//                Toast.makeText(requireContext(),"Error happened!", Toast.LENGTH_SHORT).show()
//                showDialog()
          //  }
        }
//        viewModel.fetchFeaturedPodcastJC(false)
//            viewModel.featuredpodcastContentJC.observe(viewLifecycleOwner) { response ->
//                if (response.status == Status.SUCCESS) {
//                    Log.e("TAGGGGGGGY", "MESSAGE: "+response?.data?.data?.get(1)?.Data)
//
//                } else {
////                progressBar.visibility = View.GONE
////                Toast.makeText(requireContext(),"Error happened!", Toast.LENGTH_SHORT).show()
////                showDialog()
//                }
//            }



    override fun onRootClickItem(
        episode: MutableList<FeaturedPodcastDetailsModel>,
        clickItemPosition: Int,
    ) {
    }

    override fun onClickItem(
        episode: MutableList<FeaturedPodcastDetailsModel>,
        clickItemPosition: Int
    ) {
//        ShadhinMusicSdkCore.pressCountIncrement()
        val homePatchItem = argHomePatchItem
        val mEpisod = episode[clickItemPosition]
        navController.navigate(
            R.id.to_podcast_details,
            Bundle().apply {
                putSerializable(
                    AppConstantUtils.PatchItem,
                    homePatchItem
                )
                putSerializable(
                    AppConstantUtils.PatchDetail,
                    UtilHelper.getHomePatchDetailToFeaturedPodcastDetails(mEpisod) as Serializable
                )
            })
    }

    override fun getCurrentVH(
        currentVH: RecyclerView.ViewHolder,
        episode: MutableList<FeaturedPodcastDetailsModel>,
    ) {
    }
}