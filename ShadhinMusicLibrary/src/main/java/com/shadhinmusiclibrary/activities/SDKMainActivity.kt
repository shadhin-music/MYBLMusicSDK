package com.shadhinmusiclibrary.activities

import android.app.UiModeManager.MODE_NIGHT_NO
import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.shadhinmusiclibrary.R


internal class SDKMainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setContentView(R.layout.activity_sdk_main)

        //Will recived data from Home Fragment from MYBLL App

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fcv_navigation_host) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setupNavGraph(@NavigationRes graphResId: Int) {
        val inflater = navHostFragment.navController.navInflater
        val navGraph = inflater.inflate(graphResId)
        navController.graph = navGraph
    }
}