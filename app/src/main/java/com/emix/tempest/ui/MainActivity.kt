package com.emix.tempest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.emix.tempest.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        setSupportActionBar(toolbar)

        //set host for navigation
        navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        //setting component with nav controller
        bottomNav.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this,navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        //setting back button
        return NavigationUI.navigateUp(navController,null)
    }
}