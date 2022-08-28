package com.shadhinmusiclibrary.adapter


import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.shadhinmusiclibrary.fragments.AllFragment
import com.shadhinmusiclibrary.fragments.DownloadDetailsFragment
import com.shadhinmusiclibrary.fragments.HomeFragment


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
                HomeFragment();

                // DownloadDetailsFragment.newInstance()
            }
           2-> {
               DownloadDetailsFragment()
                //DownloadDetailsFragment.newInstance()
//                st artActivity(Intent(this, LoginActivity::class.java))
                //HomeFragment();
            }
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
