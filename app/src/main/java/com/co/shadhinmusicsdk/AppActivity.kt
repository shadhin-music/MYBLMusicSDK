package com.co.shadhinmusicsdk

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.analytics.FirebaseAnalytics
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.di.ShadhinApp


class AppActivity() : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    private lateinit var mFirebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity)

        if(applicationContext is DemoApp){
            Log.i("NavControllerx", "DemoApp: ")
        }
      //  ShadhinMusicSdkCore.playerDestroy(applicationContext)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//        val buttonHome:Button = findViewById(R.id.buttonHome)
//        val buttonAPI: Button = findViewById(R.id.buttonAPI)
//        buttonHome.setOnClickListener {
                //ShadhinMusicSdkCore.getHomeFragment()
//            val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.app_home_fragment, ShadhinMusicSdkCore.getMusicFragment())
//        transaction.commit()
//        }
//        Handler().postDelayed({
//            startActivity(Intent(this, com.shadhinmusiclibrary.activities.MainActivity::class.java))
//            finish()
//        }, 1000)

        // HomeFragment()
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ViewPagerAdapter(
            this, supportFragmentManager,
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
        viewPager.offscreenPageLimit = 2
        val selectedTabIndex = 0
        viewPager.setCurrentItem(selectedTabIndex, false)
    }

    override fun onDestroy() {
        ShadhinMusicSdkCore.destroySDK(applicationContext)
        super.onDestroy()
    }
}