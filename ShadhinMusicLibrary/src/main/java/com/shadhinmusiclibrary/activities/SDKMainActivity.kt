package com.shadhinmusiclibrary.activities

import android.app.UiModeManager.MODE_NIGHT_NO
import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.DataDetails
import com.shadhinmusiclibrary.utils.AppConstantUtils
import java.io.Serializable


internal class SDKMainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setContentView(R.layout.activity_sdk_main)

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fcv_navigation_host) as NavHostFragment
        navController = navHostFragment.navController
        //Will received data from Home Fragment from MYBLL App
        val receivedData = intent.extras!!.getBundle(AppConstantUtils.commonData)!!
            .getSerializable(AppConstantUtils.commonData) as List<DataDetails>
        if (receivedData.size > 1) {
//            val inflater = navHostFragment.navController.navInflater
//            val navGraph = inflater.inflate(R.navigation.nav_graph_latest_release)
//            navController.graph = navGraph
        } else {
            setupNavGraphAndArg(R.navigation.nav_graph_latest_release,
                Bundle().apply {
                    putSerializable(
                        AppConstantUtils.singleDataItem,
                        receivedData[0] as Serializable
                    )
                })
        }
    }

    private fun setupNavGraph(@NavigationRes graphResId: Int) {
        val inflater = navHostFragment.navController.navInflater
        val navGraph = inflater.inflate(graphResId)
        navController.graph = navGraph
    }

    private fun setupNavGraphAndArg(@NavigationRes graphResId: Int, bundleData: Bundle) {
        val inflater = navHostFragment.navController.navInflater
        val navGraph = inflater.inflate(graphResId)
        navController.setGraph(navGraph, bundleData)
    }
}