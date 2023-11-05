package com.example.tp3_grupo7_be.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.tp3_grupo7_be.R
import com.google.android.material.navigation.NavigationView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.tp3_grupo7_be.FragmentTitles
import com.example.tp3_grupo7_be.HomeFragment
import com.example.tp3_grupo7_be.fragments.TestFragment1
import com.example.tp3_grupo7_be.fragments.TestFragment2

class MainActivity : AppCompatActivity() {
    private lateinit var drawer : DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(this, drawer, R.string.nav_drawer_open, R.string.nav_drawer_close)

        drawer.addDrawerListener(toggle)
        toggle.syncState()


        val drawerIcon = ContextCompat.getDrawable(this, R.drawable.drawer_icon)

        toggle.isDrawerIndicatorEnabled = false
        toggle.setHomeAsUpIndicator(drawerIcon)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            if (drawer.isDrawerVisible(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START)
            } else {
                val currentFragment = getCurrentFragment()
                if (currentFragment == TestFragment1::class.java.simpleName || currentFragment == TestFragment2::class.java.simpleName){
                    onBackPressed()
                    } else {
                        drawer.openDrawer(GravityCompat.START)
                }
            }
        }



        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navigationView = findViewById(R.id.nav_view)

        val navController = navHostFragment.navController





        navController.addOnDestinationChangedListener { _, destination, _ ->
            val fragmentId = destination.id
            val title = FragmentTitles.getTitleForFragment(fragmentId)
            val toolbarTitle = findViewById<TextView>(R.id.toolbar_title)
            toolbarTitle.text = title

            if (fragmentId == R.id.profile || fragmentId == R.id.settings) {
                val backIcon = ContextCompat.getDrawable(this,R.drawable.back_icon)

                toggle.setHomeAsUpIndicator(backIcon)
            } else {
                val drawerIcon = ContextCompat.getDrawable(this, R.drawable.drawer_icon)
                toggle.setHomeAsUpIndicator(drawerIcon)
            }
        }

        NavigationUI.setupWithNavController(navigationView, navController)


    }

    private fun getCurrentFragment(): String {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
        return currentFragment?.javaClass?.simpleName ?: ""
    }



}