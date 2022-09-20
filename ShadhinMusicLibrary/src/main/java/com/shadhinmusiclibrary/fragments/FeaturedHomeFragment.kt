package com.shadhinmusiclibrary.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.activities.VideoActivity
import com.shadhinmusiclibrary.data.fake.FakeData.VideoJOSN
import com.shadhinmusiclibrary.data.model.Video
import com.shadhinmusiclibrary.fragments.base.CommonBaseFragment

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



 
        view.findViewById<Button>(R.id.button).setOnClickListener {

            val intent = Intent(requireContext(), VideoActivity::class.java)
            val typeToken = object:TypeToken<ArrayList<Video>>(){}.type
            val videos :ArrayList<Video> = Gson().fromJson(VideoJOSN,typeToken)
            intent.putExtra(VideoActivity.INTENT_KEY_POSITION, 0)
            intent.putExtra(VideoActivity.INTENT_KEY_DATA_LIST, videos)
            startActivity(intent)
        }


//        val btnPopularArtist: Button = requireView().findViewById(R.id.btnPopularArtists)
//        btnPopularArtist.setOnClickListener {
//            val manager: FragmentManager =
//                (requireContext() as AppCompatActivity).supportFragmentManager
//            manager.beginTransaction()
//                .replace(R.id.container,FeaturedPopularArtistsFragment() )
//                .addToBackStack("Fragment")
//                .commit()
//        }
//        btnrelease.setOnClickListener {
//            val manager: FragmentManager =
//                (requireContext() as AppCompatActivity).supportFragmentManager
//            manager.beginTransaction()
//                .replace(R.id.container,LatestReleaseFragment() )
//                .addToBackStack("Fragment")
//                .commit()
//        }
//        val recyclerView:RecyclerView = requireView().findViewById(R.id.rv_all_home)

    }
}