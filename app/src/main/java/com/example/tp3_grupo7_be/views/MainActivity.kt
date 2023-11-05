package com.example.tp3_grupo7_be.views

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.setupWithNavController
import com.example.tp3_grupo7_be.R
import com.google.android.material.navigation.NavigationView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.tp3_grupo7_be.FragmentTitles
import com.example.tp3_grupo7_be.HomeFragment
import com.example.tp3_grupo7_be.fragments.TestFragment1
import com.example.tp3_grupo7_be.fragments.TestFragment2
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.android.material.navigation.NavigationBarView


class MainActivity : AppCompatActivity() {
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setNavHostFragment()

        setToolbar()

        setBottomNavBar()

        setDrawerMenu()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val fragmentId = destination.id
            val title = FragmentTitles.getTitleForFragment(fragmentId)
            val toolbarTitle = findViewById<TextView>(R.id.toolbar_title)
            toolbarTitle.text = title

            if (fragmentId == R.id.profile || fragmentId == R.id.settings) {
                val backIcon = ContextCompat.getDrawable(this, R.drawable.back_icon)

                toggle.setHomeAsUpIndicator(backIcon)
            } else {
                val drawerIcon = ContextCompat.getDrawable(this, R.drawable.drawer_icon)
                toggle.setHomeAsUpIndicator(drawerIcon)
            }
        }

        NavigationUI.setupWithNavController(navigationView, navController)


    }

    private fun setNavHostFragment() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navigationView = findViewById(R.id.nav_view)

        navController = navHostFragment.navController
    }

    private fun setToolbar() {
        toolbar= findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
    fun setBottomNavBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_bar)


        //Desabilitar Shifting para que muestre más de 3 items
        bottomNavigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
        bottomNavigationView.itemBackground = null
        val menu = bottomNavigationView.menu
        menu.clear()
        menuInflater.inflate(R.menu.bottom_menu, menu)

        //Navegación
        //  val navHostFragment =
        //     supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
    }

    private fun setDrawerMenu() {
        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)

        toggle =
            ActionBarDrawerToggle(this, drawer, R.string.nav_drawer_open, R.string.nav_drawer_close)

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
                if (currentFragment == TestFragment1::class.java.simpleName || currentFragment == TestFragment2::class.java.simpleName) {
                    onBackPressed()
                } else {
                    drawer.openDrawer(GravityCompat.START)
                }
            }
        }
    }

    private fun getCurrentFragment(): String {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
        return currentFragment?.javaClass?.simpleName ?: ""
    }


}

