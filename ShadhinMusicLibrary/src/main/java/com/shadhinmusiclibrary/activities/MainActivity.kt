package com.shadhinmusiclibrary.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.annotation.NavigationRes
import androidx.appcompat.app.ActionBar
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment

import com.shadhinmusiclibrary.R


class MainActivity : AppCompatActivity() {
    companion object {
        internal var backPressCount = 0
    }
    private lateinit var toolbar: Toolbar
    private lateinit var actionBar: ActionBar
    private lateinit var pbActivityLoader: ProgressBar
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // iniUI()


        /*navHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment*/
       // navController =  findNavController(R.id.my_nav_host_fragment)
      //  setupNavGraph(R.navigation.nav_graph)


    }
    private fun iniUI() {
       // pbActivityLoader = findViewById(R.id.pb_activity_loader)
//        toolbar = findViewById(R.id.ic_toolbar_library)
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
//        setSupportActionBar(toolbar)
//        actionBar = supportActionBar!!
//        actionBar.setDisplayHomeAsUpEnabled(true)
//        actionBar.setDisplayShowHomeEnabled(true)
//        actionBar.setHomeButtonEnabled(true)
//        actionBar.setDisplayShowTitleEnabled(true)
    }

    /*private fun setupNavGraph(@NavigationRes graphResId: Int) {
        val inflater = navHostFragment.navController.navInflater
        val navGraph = inflater.inflate(graphResId)
        navController.graph = navGraph
    }*/

   /* override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }*/

}