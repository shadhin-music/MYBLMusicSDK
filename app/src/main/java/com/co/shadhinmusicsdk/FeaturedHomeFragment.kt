package com.co.shadhinmusicsdk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.fragments.FeaturedPodcastFragment
import com.shadhinmusiclibrary.fragments.MusicVideoFragment
import com.shadhinmusiclibrary.fragments.amar_tunes.AmartunesWebviewFragment

class FeaturedHomeFragment : Fragment() {

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
        btnPopularArtist.setOnClickListener {
            ShadhinMusicSdkCore.openPatch(requireContext(), "RC203")
        }
        btnrelease.setOnClickListener {
            ShadhinMusicSdkCore.openPatch(requireContext(), "RC201")
        }
        btnFeaturedPodcast.setOnClickListener {
          //  ShadhinMusicSdkCore.openPatch(requireContext(), "RC202")
            val manager: FragmentManager =
                (requireContext() as AppCompatActivity).supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.container1, FeaturedPodcastFragment() )
                .addToBackStack("Fragment")
                .commit()
        }

        btnAmartunes.setOnClickListener {
            ShadhinMusicSdkCore.openPatch(requireContext(), "BNMAIN01")
        }
        btnAmartunesAll.setOnClickListener {
            ShadhinMusicSdkCore.openPatch(requireContext(), "BNALL01") // for all
        }
        btnMusicVideos.setOnClickListener {

            ShadhinMusicSdkCore.openPatch(requireContext(), "RC204")

            /* val manager: FragmentManager =
                (requireContext() as AppCompatActivity).supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.container1, MusicVideoFragment() )
                .addToBackStack("Fragment")
                .commit()
        }
        btnFeaturedPodcast.setOnClickListener {
            val manager: FragmentManager =
                (requireContext() as AppCompatActivity).supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.container1, FeaturedPodcastFragment() )
                .addToBackStack("Fragment")
                .commit()
        }
//        val recyclerView:RecyclerView = requireView().findViewById(R.id.rv_all_home)
//           view.findViewById<Button>(R.id.button).setOnClickListener {
//            //todo hide button
//            val intent = Intent(requireContext(), VideoActivity::class.java)
//            val typeToken = object: TypeToken<ArrayList<Video>>(){}.type
//            val videos :ArrayList<Video> = Gson().fromJson(VideoJOSN,typeToken)
//            intent.putExtra(VideoActivity.INTENT_KEY_POSITION, 0)
//            intent.putExtra(VideoActivity.INTENT_KEY_DATA_LIST, videos)
//            startActivity(intent)
//        }
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

            */
        }
    }
}