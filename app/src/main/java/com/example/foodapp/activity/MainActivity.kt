package com.example.foodapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.foodapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

   // private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_main)

       val navHostFragment = supportFragmentManager.findFragmentById(R.id.host_fragment) as
             NavHostFragment

       navController = navHostFragment.navController

       appBarConfiguration = AppBarConfiguration(
           setOf(
               R.id.homeFragment,
               R.id.categoriesFragment,
               R.id.favoritesFragment
           )
       )

       toolbar = findViewById(R.id.toolBar)

       NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)

       findViewById<BottomNavigationView>(R.id.btn_nav).setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.host_fragment)
        return navController.navigateUp(appBarConfiguration)
                ||   super.onSupportNavigateUp()
    }
}