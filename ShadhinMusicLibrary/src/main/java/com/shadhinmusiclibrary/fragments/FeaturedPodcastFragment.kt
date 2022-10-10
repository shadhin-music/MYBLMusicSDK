package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.adapter.FeaturedPopularArtistAdapter
import com.shadhinmusiclibrary.callBackService.HomeCallBack
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.data.model.podcast.Episode
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.artist.PopularArtistViewModel
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.utils.AppConstantUtils
import com.shadhinmusiclibrary.utils.Status
import java.io.Serializable


class FeaturedPodcastFragment : CommonBaseFragment(), HomeCallBack {

    private lateinit var navController: NavController
    private var homePatchitem: HomePatchItem? = null
    lateinit var viewModel: PopularArtistViewModel


    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                injector.popularArtistViewModelFactory
            )[PopularArtistViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container1: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewRef = inflater.inflate(R.layout.fragment_featured_popular_artist, container1, false)
        navController = findNavController()

        return viewRef
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TaG", "Message: " + homePatchitem)
        val tvTitle: TextView = requireView().findViewById(R.id.tvTitle)
        //tvTitle.text =  homePatchitem?.Name
        setupViewModel()
        observeData()
        val imageBackBtn: AppCompatImageView = view.findViewById(R.id.imageBack)
        imageBackBtn.setOnClickListener {
            Log.d("TAGGGGGGGY", "MESSAGE: ")
//            val manager: FragmentManager =
//                (requireContext() as AppCompatActivity).supportFragmentManager
//            manager?.popBackStack("Fragment", 0);
            // ShadhinMusicSdkCore.getHomeFragment()
//            val manager: FragmentManager =
//                (requireContext() as AppCompatActivity).supportFragmentManager
//            manager.beginTransaction()
//                .replace(R.id.container1, HomeFragment())
//                .addToBackStack(null)
//                .commit()
//            if (ShadhinMusicSdkCore.pressCountDecrement() == 0) {
//                requireActivity().finish()
//            }
        }
    }

    fun observeData() {
        viewModel.fetchPouplarArtist()
        viewModel.popularArtistContent.observe(viewLifecycleOwner) { response ->
            if (response.status == Status.SUCCESS) {
                val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
                recyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
                recyclerView.adapter =
                    response.data?.let {
                        it?.data?.let { it1 ->
                            FeaturedPopularArtistAdapter(it1, this)
                        }
                    }
            } else {
//                progressBar.visibility = View.GONE
//                Toast.makeText(requireContext(),"Error happened!", Toast.LENGTH_SHORT).show()
//                showDialog()
            }
        }
    }

    override fun onClickItemAndAllItem(itemPosition: Int, selectedHomePatchItem: HomePatchItem) {
        ShadhinMusicSdkCore.pressCountIncrement()
        val homePatchDetail = selectedHomePatchItem.Data[itemPosition]
        navController.navigate(
            R.id.action_featured_popular_artist_fragment_to_artist_details_fragment,
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
    }
}