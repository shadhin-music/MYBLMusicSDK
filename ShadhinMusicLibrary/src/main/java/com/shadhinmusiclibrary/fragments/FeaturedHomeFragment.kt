package com.shadhinmusiclibrary.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.di.FragmentEntryPoint
import com.shadhinmusiclibrary.fragments.base.BaseFragment
import com.shadhinmusiclibrary.fragments.home.HomeViewModel
import com.shadhinmusiclibrary.fragments.home.HomeViewModelFactory
import com.shadhinmusiclibrary.utils.Status


class FeaturedHomeFragment : BaseFragment<HomeViewModel, HomeViewModelFactory>(),
    FragmentEntryPoint {
    private var pageNum = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_featured_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel!!.fetchHomeData( pageNum,false)
//        observeData()
        val btnrelease: Button= requireView().findViewById(R.id.btnLatestRelease)
//
//        btnrelease.setOnClickListener {
//            val manager: FragmentManager =
//                (requireContext() as AppCompatActivity).supportFragmentManager
//            manager.beginTransaction()
//                .replace(R.id.container,LatestReleaseFragment() )
//                .addToBackStack("Fragment")
//                .commit()
//        }
//        val recyclerView:RecyclerView = requireView().findViewById(R.id.rv_all_home)
        val btnPopularArtist: Button = requireView().findViewById(com.shadhinmusiclibrary.R.id.btnPopularArtists)
        btnPopularArtist.setOnClickListener {
            val childFragment: Fragment = FeaturedPopularArtistFragment()
            val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
            transaction.add(com.shadhinmusiclibrary.R.id.container1, childFragment).addToBackStack("YourFragmentTag")
                .commit()
//            val manager: FragmentManager =
//                (requireContext() as AppCompatActivity).supportFragmentManager
//            manager.beginTransaction()
//                .add(R.id.container1,FeaturedPopularArtistFragment.newInstance())
//                .addToBackStack(null)
//                .commit()
        }
    }
    private fun observeData() {
      //  val progressBar: ProgressBar = requireView().findViewById(R.id.progress_bar)

        viewModel!!.homeContent.observe(viewLifecycleOwner) { res ->
            if (res.status == Status.SUCCESS) {
                Log.d("TaG","Message: "+ res.data)
                var homePatchItem = res.data?.data!![0]


               // progressBar.visibility = View.GONE
             //   viewDataInRecyclerView(res.data)
            } else {
               // progressBar.visibility = View.VISIBLE
            }
            //isLoading = false
        }
    }
    override fun getViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun getViewModelFactory(): HomeViewModelFactory {
        return injector.factoryHomeVM
    }
}