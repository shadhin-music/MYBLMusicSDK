package com.shadhinmusiclibrary.activities

import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
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
    }

    private fun routeData(homePatchItem: HomePatchItem, selectedIndex: Int?) {
        if (selectedIndex != null) {
            //Single Item Click event
            val homePatchDetail = homePatchItem.Data[selectedIndex]
            when (homePatchDetail.ContentType.uppercase()) {
                DataContentType.CONTENT_TYPE_A -> {
                    //open artist details
                    setupNavGraphAndArg(R.navigation.nav_graph_artist_details,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.PatchItem,
                                homePatchItem
                            )
                            putSerializable(
                                AppConstantUtils.PatchDetail,
                                homePatchDetail as Serializable
                            )
                        })

                }
                DataContentType.CONTENT_TYPE_R -> {
                    //open album details
                    setupNavGraphAndArg(R.navigation.nav_graph_album_details,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.PatchItem,
                                homePatchItem
                            )
                            putSerializable(
                                AppConstantUtils.PatchDetail,
                                homePatchDetail as Serializable
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_P -> {
                    //open playlist
                    setupNavGraphAndArg(R.navigation.nav_graph_playlist_details,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.PatchItem,
                                homePatchItem
                            )
                            putSerializable(
                                AppConstantUtils.PatchDetail,
                                homePatchDetail as Serializable
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_S -> {
                    //open songs
                    setupNavGraphAndArg(R.navigation.nav_graph_s_type_details,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.PatchItem,
                                homePatchItem
                            )
                            putSerializable(
                                AppConstantUtils.PatchDetail,
                                homePatchDetail as Serializable
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_PD -> {
                    //open songs
                    setupNavGraphAndArg(R.navigation.nav_graph_podcast_details,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.PatchItem,
                                homePatchItem as Serializable
                            )
                            putSerializable(
                                AppConstantUtils.PatchDetail,
                                homePatchDetail as Serializable
                            )
                        })
                }
            }
        } else {
            //See All Item Click event
            when (homePatchItem.ContentType.uppercase()) {
                DataContentType.CONTENT_TYPE_A -> {
                    //open artist details
                    setupNavGraphAndArg(R.navigation.nav_graph_artist_list_details,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.PatchItem,
                                homePatchItem as Serializable
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_R -> {
                    //open artist details
                    setupNavGraphAndArg(R.navigation.nav_graph_album_list,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.PatchItem,
                                homePatchItem
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_P -> {
                    //open playlist
                    setupNavGraphAndArg(R.navigation.nav_graph_playlist_list,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.PatchItem,
                                homePatchItem as Serializable
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_S -> {
                    //open songs
                    setupNavGraphAndArg(R.navigation.nav_graph_s_type_list_details,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.PatchItem,
                                homePatchItem as Serializable
                            )
                        })
                }
                DataContentType.CONTENT_TYPE_PD -> {
                    //open songs
                    setupNavGraphAndArg(R.navigation.nav_graph_podcast_list_and_details,
                        Bundle().apply {
                            putSerializable(
                                AppConstantUtils.PatchItem,
                                homePatchItem as Serializable
                            )
                        })
                }
                else -> {

                }
            }
        }
    }

    private fun setupNavGraphAndArg(@NavigationRes graphResId: Int, bundleData: Bundle) {
        val inflater = navHostFragment.navController.navInflater
        val navGraph = inflater.inflate(graphResId)
        navController.setGraph(navGraph, bundleData)
    }

    override fun onBackPressed() {
        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }
}