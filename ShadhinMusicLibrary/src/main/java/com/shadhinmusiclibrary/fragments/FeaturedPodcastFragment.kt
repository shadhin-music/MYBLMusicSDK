package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.shadhinmusiclibrary.data.model.FeaturedPodcastDetails
import com.shadhinmusiclibrary.data.model.HomePatchDetail
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.fragments.podcast.FeaturedPodcastViewModel
import com.shadhinmusiclibrary.utils.AppConstantUtils
import com.shadhinmusiclibrary.utils.Status
import java.io.Serializable


internal class FeaturedPodcastFragment : CommonBaseFragment(),FeaturedPodcastOnItemClickCallback{

    private lateinit var navController: NavController
    private var homePatchitem: HomePatchItem? = null
    lateinit var viewModel: FeaturedPodcastViewModel
    private lateinit var  data: List<FeaturedPodcastDetails>
    private lateinit var  dataJc: List<FeaturedPodcastDetails>
    private lateinit var podcastJBAdapter: FeaturedPodcastRecyclerViewAdapter
    private lateinit var  podcastJCAdapter: FeaturePodcastJCRECAdapter
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
        val viewRef = inflater.inflate(R.layout.my_bl_sdk_fragment_podcast, container1, false)
        navController = findNavController()

        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TaG", "Message: " + homePatchitem)
        val tvTitle: TextView = requireView().findViewById(R.id.tvTitle)
        //tvTitle.text =  homePatchitem?.Name
        setupViewModel()
        setAdapter()
        observeData()
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            Log.d("TAGGGGGGGY", "MESSAGE: ")

        }
    }
     fun setAdapter(){
         val layoutManager =
             LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
         val config = ConcatAdapter.Config.Builder().apply { setIsolateViewTypes(false) }.build()
          podcastJBAdapter = FeaturedPodcastRecyclerViewAdapter(this)
          podcastJCAdapter =  FeaturePodcastJCRECAdapter(this)
         val parentRecycler: RecyclerView = requireView().findViewById(R.id.recyclerView)

         parentAdapter = ConcatAdapter(
            config,
            podcastJBAdapter,podcastJCAdapter


        )
        parentRecycler.setLayoutManager(layoutManager)
        parentRecycler.setAdapter(parentAdapter)
     }
    fun observeData() {

        viewModel.fetchFeaturedPodcast(false)

        viewModel.featuredpodcastContent.observe(viewLifecycleOwner) { response ->
            if (response !=null && response.status == Status.SUCCESS) {
                if(response.data?.data?.get(0)?.Data !=null) {
                    podcastJBAdapter.setData(
                        response.data.data.get(0).Data,
                        response.data.data.get(0).Data.get(0).ShowName
                    )

                }
            }
          //  else {
//                progressBar.visibility = View.GONE
//                Toast.makeText(requireContext(),"Error happened!", Toast.LENGTH_SHORT).show()
//                showDialog()
          //  }
        }
        viewModel.fetchFeaturedPodcastJC(false)
            viewModel.featuredpodcastContentJC.observe(viewLifecycleOwner) { response ->
                if (response.status == Status.SUCCESS) {
                    Log.e("TAGGGGGGGY", "MESSAGE: "+response?.data?.data?.get(1)?.Data)
                    podcastJCAdapter.setData(response?.data?.data?.get(1)?.Data,
                        response?.data?.data?.get(1)?.Data?.get(0)?.ShowName.toString())
                } else {
//                progressBar.visibility = View.GONE
//                Toast.makeText(requireContext(),"Error happened!", Toast.LENGTH_SHORT).show()
//                showDialog()
                }
            }

    }

    override fun onRootClickItem(
        episode: MutableList<FeaturedPodcastDetails>,
        clickItemPosition: Int,
    ) {
        TODO("Not yet implemented")
    }

    override fun onClickItem(episode: MutableList<FeaturedPodcastDetails>, clickItemPosition: Int) {
        ShadhinMusicSdkCore.pressCountIncrement()


        val homePatchItem = argHomePatchItem
        Log.e("Check", ""+navController.graph.displayName)
        navController.navigate(R.id.to_podcast_details,
            Bundle().apply {
                putSerializable(
                    AppConstantUtils.PatchItem,
                    homePatchItem
                )
                putSerializable(
                    AppConstantUtils.PatchDetail,
                    HomePatchDetail(episode.get(clickItemPosition).EpisodeId,
                   "",
               episode.get(clickItemPosition).EpisodeName,
                       "",
                "",
               "",
               "",
                "", episode.get(clickItemPosition).ContentType,
                "",
               "",
                "",
                false,
               "",
                0,
                "",
               "",
                "", episode.get(clickItemPosition).PlayUrl,
                                ""

                ,"",
                        false,
               "","","","",episode.get(clickItemPosition).ImageUrl,"",
                        episode.get(clickItemPosition).TrackName) as Serializable
                )
            })
    }

    override fun getCurrentVH(
        currentVH: RecyclerView.ViewHolder,
        episode: MutableList<FeaturedPodcastDetails>,
    ) {
        TODO("Not yet implemented")
    }


}