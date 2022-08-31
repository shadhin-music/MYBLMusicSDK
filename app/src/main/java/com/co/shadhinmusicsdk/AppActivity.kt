package com.co.shadhinmusicsdk

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class AppActivity(contentLayoutId: Int) : FragmentActivity(contentLayoutId) {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       setContentView(R.layout.app_activity)
//        Handler().postDelayed({
//            startActivity(Intent(this, com.shadhinmusiclibrary.activities.MainActivity::class.java))
//            finish()
//        }, 1000)

        // HomeFragment()
//        tabLayout = findViewById(R.id.tabLayout)
//        viewPager = findViewById(R.id.pager)
//
//        tabLayout.elevation = 16F
//        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
//        val adapter = ViewPagerAdapter(
//            this, supportFragmentManager,
//            tabLayout.tabCount
//        )
//        viewPager.adapter = adapter
//        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
//        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                viewPager.currentItem = tab.position
//
//
//
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab) {
//
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab) {
//
//            }
//        })
//
//
////       scope?.launch(Dispatchers.Main) {
//        viewPager.offscreenPageLimit = 2
//        val selectedTabIndex = 1
//        viewPager.setCurrentItem(selectedTabIndex, false)
//    }

    }
}