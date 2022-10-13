package com.co.shadhinmusicsdk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.shadhinmusiclibrary.ShadhinMusicSdkCore


internal class FeaturedHomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_featured_home,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnrelease: Button = requireView().findViewById(R.id.btnLatestRelease)
        val btnPopularArtist: Button = requireView().findViewById(R.id.btnPopularArtists)
        val btnFeaturedPodcast: Button = requireView().findViewById(R.id.btnFeaturedPodcast)
        val btnMusicVideos: Button = requireView().findViewById(R.id.btnMusicVideos)
        val btnAmartunes: Button = requireView().findViewById(R.id.btnWebview)
        val btnAmartunesAll: Button = requireView().findViewById(R.id.btnWebview2)
        val btnMusic: Button = requireView().findViewById(R.id.btnMusic)
        btnPopularArtist.setOnClickListener {
            ShadhinMusicSdkCore.openPatch(requireContext(), "RC203")
        }
        btnrelease.setOnClickListener {
            ShadhinMusicSdkCore.openPatch(requireContext(), "RC201")
        }
        btnFeaturedPodcast.setOnClickListener {
            ShadhinMusicSdkCore.openPatch(requireContext(), "RC202")
        }

        btnAmartunes.setOnClickListener {
            ShadhinMusicSdkCore.openPatch(requireContext(), "BNMAIN01")
        }
        btnAmartunesAll.setOnClickListener {
            ShadhinMusicSdkCore.openPatch(requireContext(), "BNALL01") // for all
        }
        btnMusicVideos.setOnClickListener {
            ShadhinMusicSdkCore.openPatch(requireContext(), "RC204")
        }

        btnMusic.setOnClickListener {
            ShadhinMusicSdkCore.openMusic(requireContext())
        }
//    private fun observeData() {
//        //  val progressBar: ProgressBar = requireView().findViewById(R.id.progress_bar)
//
//        viewModel!!.homeContent.observe(viewLifecycleOwner) { res ->
//            if (res.status == Status.SUCCESS) {
//                Log.d("TaG","Message: "+ res.data)
//                var homePatchItem = res.data?.data!![0]
//
//
//                // progressBar.visibility = View.GONE
//                //   viewDataInRecyclerView(res.data)
//            } else {
//                // progressBar.visibility = View.VISIBLE
//            }
//            //isLoading = false
//        }
//    }
//    override fun getViewModel(): Class<HomeViewModel> {
//        return HomeViewModel::class.java
//    }
//
//    override fun getViewModelFactory(): HomeViewModelFactory {
//        return injector.factoryHomeVM
//    }
//    }
    }
}