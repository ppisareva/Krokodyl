package com.example.krokodyl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set up back button via navigation controller

        val navController = Navigation.findNavController(this, R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)


    }
     // navigate up
    override fun onSupportNavigateUp(): Boolean {
         return Navigation.findNavController(this, R.id.myNavHostFragment).navigateUp()
    }
}
