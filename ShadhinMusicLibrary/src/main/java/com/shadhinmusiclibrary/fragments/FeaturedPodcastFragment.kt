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
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.FeaturedPodcastJCAdapter
import com.shadhinmusiclibrary.adapter.FeaturedPodcastJCRecyclerViewAdapter
import com.shadhinmusiclibrary.adapter.FeaturedPodcastRecyclerViewAdapter
import com.shadhinmusiclibrary.data.model.FeaturedPodcastDetails
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.fragments.podcast.FeaturedPodcastViewModel
import com.shadhinmusiclibrary.utils.Status


class FeaturedPodcastFragment : CommonBaseFragment(){

    private lateinit var navController: NavController
    private var homePatchitem: HomePatchItem? = null
    lateinit var viewModel: FeaturedPodcastViewModel
    private lateinit var  data: List<FeaturedPodcastDetails>
    private lateinit var  dataJc: List<FeaturedPodcastDetails>
    private lateinit var podcastJBAdapter: FeaturedPodcastRecyclerViewAdapter
    private lateinit var  podcastJCAdapter: FeaturedPodcastJCRecyclerViewAdapter
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
        val viewRef = inflater.inflate(R.layout.fragment_podcast, container1, false)
       // navController = findNavController()

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
          podcastJBAdapter = FeaturedPodcastRecyclerViewAdapter()
          podcastJCAdapter =FeaturedPodcastJCRecyclerViewAdapter()
         val parentRecycler: RecyclerView = requireView().findViewById(R.id.recyclerView)

         parentAdapter = ConcatAdapter(
            config,
            podcastJBAdapter,
            podcastJCAdapter,


        )
        parentRecycler.setLayoutManager(layoutManager)
        parentRecycler.setAdapter(parentAdapter)
     }
    fun observeData() {

        viewModel.fetchFeaturedPodcast(false)
        viewModel.fetchFeaturedPodcast(false)
        viewModel.featuredpodcastContent.observe(viewLifecycleOwner) { response ->
            if (response.status == Status.SUCCESS) {
                podcastJBAdapter.setData(response?.data?.data?.get(0)?.Data)

            } else {
//                progressBar.visibility = View.GONE
//                Toast.makeText(requireContext(),"Error happened!", Toast.LENGTH_SHORT).show()
//                showDialog()
            }
        }
            viewModel.featuredpodcastContentJC.observe(viewLifecycleOwner) { response ->
                if (response.status == Status.SUCCESS) {

                    podcastJCAdapter.setData(response?.data?.data?.get(1)?.Data)
                } else {
//                progressBar.visibility = View.GONE
//                Toast.makeText(requireContext(),"Error happened!", Toast.LENGTH_SHORT).show()
//                showDialog()
                }
            }

    }

//    override fun onClickItemAndAllItem(itemPosition: Int, selectedHomePatchItem: HomePatchItem) {
//        ShadhinMusicSdkCore.pressCountIncrement()
//        val homePatchDetail = selectedHomePatchItem.Data[itemPosition]
//        navController.navigate(
//            R.id.action_featured_popular_artist_fragment_to_artist_details_fragment,
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
//    }
//
//    override fun onClickSeeAll(selectedHomePatchItem: HomePatchItem) {
//
//    }
//
//    override fun onClickItemPodcastEpisode(itemPosition: Int, selectedEpisode: List<Episode>) {
//    }
}