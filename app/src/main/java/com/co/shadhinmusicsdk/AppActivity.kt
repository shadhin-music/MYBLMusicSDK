package com.co.shadhinmusicsdk

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.analytics.FirebaseAnalytics
import com.shadhinmusiclibrary.ShadhinMusicSdkCore
import com.shadhinmusiclibrary.ShadhinSDKCallback


class AppActivity : AppCompatActivity(), ShadhinSDKCallback {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    private lateinit var mFirebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        ShadhinMusicSdkCore.initializeSDK(applicationContext, TOKEN, this)

        val data = intent.extras
        data?.getString("mobile_number")
//        val buttonHome: Button = findViewById(R.id.btn_click)
//        buttonHome.setOnClickListener {
//            ShadhinMusicSdkCore.openMusic(this)
//        }
//        val buttonHome:Button = findViewById(R.id.buttonHome)
//        val buttonAPI: Button = findViewById(R.id.buttonAPI)
//        buttonHome.setOnClickListener {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.app_home_fragment, ShadhinMusicSdkCore.getMusicFragment())
//        transaction.commit()
//        }
//        Handler().postDelayed({
//            startActivity(Intent(this, com.shadhinmusiclibrary.activities.MainActivity::class.java))
//            finish()
//        }, 1000)

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

        viewPager.offscreenPageLimit = 2
        val selectedTabIndex = 0
        viewPager.setCurrentItem(selectedTabIndex, false)
    }

    override fun onDestroy() {
        ShadhinMusicSdkCore.destroySDK(applicationContext)
        super.onDestroy()
    }

    override fun tokenStatus(isTokenValid: Boolean, error: String) {
        Log.i("TAG", "isTokenValid: $isTokenValid $error ")
    }

    companion object {
        const val TOKEN: String ="eyJhbGciOiJIUzUxMiIsInR5cCI6IkJlYXJlciJ9.eyJjbGllbnQiOiJNWUJMIiwiZnVsbE5hbWUiOiIiLCJtc2lzZG4iOiI4ODAxOTAwMDAwMDAwIiwiaW1hZ2VVUkwiOiIiLCJnZW5kZXIiOiIiLCJkZXZpY2VUb2tlbiI6IiIsIm5iZiI6MTY2OTI4MzI3OCwiZXhwIjoxNjY5MzI3MjI1LCJpYXQiOjE2NjkyODMyNzgsImlzcyI6IkJMTVVTSUMgIiwiYXVkIjoiU2hhZGhpbiAifQ.wsb3EgB5RqQE5Klw_97eTC7rV5QAFEMpf9CcQKMlxo85UeBz6dFcTIzquuf9eMGsfs3K8lpak76zGEpyelMDvg"
    }
}