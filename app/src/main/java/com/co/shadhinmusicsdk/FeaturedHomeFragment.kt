package com.co.shadhinmusicsdk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.utils.share.ShareRC


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
        val cvRadioButton: CardView = requireView().findViewById(R.id.include_radio_layout)
        val btnRadioSeeAll: TextView = requireView().findViewById(R.id.btn_radio_see_all)
        val btnShare: Button = requireView().findViewById(R.id.btnshare)

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

        cvRadioButton.setOnClickListener {
            ShadhinMusicSdkCore.openRadio(requireContext(), "20148")
        }

        btnRadioSeeAll.setOnClickListener {
            ShadhinMusicSdkCore.openPatch(requireContext(), "RADIO")
        }

        btnMusic.setOnClickListener {
            ShadhinMusicSdkCore.openMusic(requireContext())
        }
        btnShare.setOnClickListener {
            //val share = ShareRC.generate("70","A")
           // val share = ShareRC.generate(null,"PDBC")
            //val share = ShareRC.generate(null,"PDJC")
            val share = ShareRC.generate(null,"PDJG")
            Log.i("onShare", "onViewCreated: ${share}")
            ShadhinMusicSdkCore.openPatchFromRC(requireContext(),share.code)

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