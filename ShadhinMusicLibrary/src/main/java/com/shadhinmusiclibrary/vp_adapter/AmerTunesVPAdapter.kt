package com.shadhinmusiclibrary.vp_adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.shadhinmusiclibrary.fragments.HomeFragment

internal class AmerTunesVPAdapter(
    fm: FragmentManager,
    var totalTabs: Int
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }
            1 -> {
                HomeFragment();
            }
            2 -> {
                HomeFragment();
            }
            3 -> {
                HomeFragment();
            }
            4 -> {
                HomeFragment();
            }
            else -> getItem(position)
        }
    }
}