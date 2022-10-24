package com.shadhinmusiclibrary.adapter


import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.shadhinmusiclibrary.fragments.DownloadDetailsFragment
import com.shadhinmusiclibrary.fragments.home.HomeFragment


@Suppress("DEPRECATION")
internal class DownlodViewPagerAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int,
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
//        return  SearchFragment()
        return when (position) {
            0 -> {
               DownloadDetailsFragment()
            }
            1 -> {
                DownloadDetailsFragment()

            }
           2-> {
               DownloadDetailsFragment()

            }

            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}
