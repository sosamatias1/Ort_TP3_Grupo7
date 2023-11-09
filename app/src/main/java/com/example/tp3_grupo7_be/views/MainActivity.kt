package com.example.tp3_grupo7_be.views

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.setupWithNavController
import com.example.tp3_grupo7_be.R
import com.google.android.material.navigation.NavigationView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.tp3_grupo7_be.fragments.PerfilFragment
import com.example.tp3_grupo7_be.fragments.ConfiguracionFragment
import com.example.tp3_grupo7_be.fragments.FragmentTitles
import com.example.tp3_grupo7_be.views.viewmodels.SharedViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView


class MainActivity : AppCompatActivity() {
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        val sharedPreferences = application.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        setNavHostFragment()
        setToolbar()
        setBottomNavBar()
        setDrawerMenu()
        onDestinationChangedListener()
        NavigationUI.setupWithNavController(navigationView, navController)


        val headerUsernameTextView: TextView = navigationView.getHeaderView(0).findViewById(R.id.tv_header_username)

        val primeraVez = sharedPreferences.getBoolean("sharedPrefs", true)

        if(primeraVez) {
            val usuario = intent.getStringExtra("username")

            if (!usuario.isNullOrEmpty()) {
                with (sharedPreferences.edit()) {
                    putString("usernameKey", usuario)
                    apply()
            }

                }
            with (sharedPreferences.edit()) {
                putBoolean("is_first_run", false)
                apply()
            }
        }

        val usuarioGuardado = sharedPreferences.getString("usernameKey", "")

        if(!usuarioGuardado.isNullOrEmpty()) {
            sharedViewModel.setUsername(usuarioGuardado)
        }


        sharedViewModel.username.observe(this, Observer { username ->
            headerUsernameTextView.text = username
        })
    }

    private fun setNavHostFragment() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navigationView = findViewById(R.id.nav_view)
        navController = navHostFragment.navController
    }

    private fun setToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun onDestinationChangedListener() {
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
            if (fragmentId == R.id.dogDetailFragment) {
                bottomNavigationView.visibility = View.GONE
            } else{
                bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }

    fun setBottomNavBar() {
        bottomNavigationView = findViewById(R.id.bottom_bar)
        //Desabilitar Shifting para que muestre más de 3 items
        bottomNavigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
        bottomNavigationView.itemBackground = null
        val menu = bottomNavigationView.menu
        menu.clear()
        menuInflater.inflate(R.menu.bottom_menu, menu)
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
                if (currentFragment == PerfilFragment::class.java.simpleName || currentFragment == ConfiguracionFragment::class.java.simpleName) {
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

