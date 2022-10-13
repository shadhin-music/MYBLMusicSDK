package com.shadhinmusiclibrary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.adapter.DownlodViewPagerAdapter


internal class DownloadFragment : Fragment() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_download, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayout = view.findViewById(R.id.tab)
        viewPager = view.findViewById(R.id.viewpager)



        val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager


        val adapter = DownlodViewPagerAdapter(
            requireContext(), manager,
            tabLayout.tabCount
        )
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position



            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })


//       scope?.launch(Dispatchers.Main) {
       // viewPager.offscreenPageLimit = 3
//        val selectedTabIndex = 0
//        viewPager.setCurrentItem(selectedTabIndex, false)
    }
    companion object {

        @JvmStatic
        fun newInstance() =
            DownloadFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}