package com.shadhinmusiclibrary.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.activities.video.VideoActivity
import com.shadhinmusiclibrary.data.fake.FakeData.VideoJOSN
import com.shadhinmusiclibrary.data.model.Video
import com.shadhinmusiclibrary.fragments.amar_tunes.AmartunesWebviewFragment
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment
import com.shadhinmusiclibrary.fragments.home.HomeViewModel
import com.shadhinmusiclibrary.fragments.home.HomeViewModelFactory
import com.shadhinmusiclibrary.utils.Status

class FeaturedHomeFragment : CommonBaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_featured_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnrelease: Button= requireView().findViewById(R.id.btnLatestRelease)
        val btnPopularArtist: Button = requireView().findViewById(R.id.btnPopularArtists)
        val btnAmartunes: Button = requireView().findViewById(R.id.btnWebview)
        btnPopularArtist.setOnClickListener {
            val manager: FragmentManager =
                (requireContext() as AppCompatActivity).supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.container1,FeaturedPopularArtistFragment() )
                .addToBackStack("Fragment")
                .commit()
        }
        btnrelease.setOnClickListener {
            val manager: FragmentManager =
                (requireContext() as AppCompatActivity).supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.container1,LatestReleaseFragment() )
                .addToBackStack("Fragment")
                .commit()
        }
        btnAmartunes.setOnClickListener {
            val manager: FragmentManager =
                (requireContext() as AppCompatActivity).supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.container1, AmartunesWebviewFragment.newInstance())
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
}