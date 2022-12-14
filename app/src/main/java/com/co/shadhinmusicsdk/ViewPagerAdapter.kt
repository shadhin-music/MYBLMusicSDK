package com.co.shadhinmusicsdk


import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.fragments.AllFragment
import com.shadhinmusiclibrary.fragments.FeaturedHomeFragment


@Suppress("DEPRECATION")
internal class ViewPagerAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int,
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
//        return  SearchFragment()

        return when (position) {
            0 -> {
                ShadhinMusicSdkCore.getHomeFragment()
            }
            1 -> {
                FeaturedHomeFragment()
            }
//            2 -> {
//
////                startActivity(Intent(this, LoginActivity::class.java))
//                HomeFragment();
//            }
//            3 -> {
//                HomeFragment();
//            }
//            4 -> {
//                HomeFragment();
//            }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}
