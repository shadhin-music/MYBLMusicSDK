package com.shadhinmusiclibrary.activities

import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.shadhinmusiclibrary.R
import com.shadhinmusiclibrary.data.model.HomePatchItem
import com.shadhinmusiclibrary.utils.AppConstantUtils
import com.shadhinmusiclibrary.utils.DataContentType
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
        val patch = intent.extras!!.getBundle(AppConstantUtils.PatchItem)!!
            .getSerializable(AppConstantUtils.PatchItem) as HomePatchItem
        var selectedPatchIndex: Int? = null
        if (intent.hasExtra(AppConstantUtils.SelectedPatchIndex)) {
            selectedPatchIndex = intent.extras!!.getInt(AppConstantUtils.SelectedPatchIndex)
        }
        routeData(patch, selectedPatchIndex)
//        if (receivedData.size > 1) {
//            val inflater = navHostFragment.navController.navInflater
//            val navGraph = inflater.inflate(R.navigation.nav_graph_latest_release)
//            navController.graph = navGraph
//        }
/*        Log.e("SDKMA", "onCreate: position $selectItemPosition data: $receivedData")
        if (selectItemPosition > 0 && receivedData.isNotEmpty()) {
            setupNavGraphAndArg(R.navigation.nav_graph_album_details,
                Bundle().apply {
                    putSerializable(
                        AppConstantUtils.singleDataItem,
                        receivedData as Serializable
                    )
                })
        }*/
    }

    private fun routeData(patch: HomePatchItem, selectedIndex: Int?) {
        if (selectedIndex != null) {
            val itemToShowDetails = patch.Data[selectedIndex]
            when (itemToShowDetails.ContentType.uppercase()) {
                DataContentType.CONTENT_TYPE_R -> {
                    //open album details
                    setupNavGraphAndArg(R.navigation.nav_graph_album_details,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.SingleDataItem,
                                itemToShowDetails as Serializable
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_A -> {
                    //open artist details
                }
                DataContentType.CONTENT_TYPE_P -> {
                    //open playlist
                }
                DataContentType.CONTENT_TYPE_S -> {
                    //open songs
                }
                else -> {

                }
            }
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