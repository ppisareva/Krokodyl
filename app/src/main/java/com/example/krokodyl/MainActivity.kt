package com.example.krokodyl

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import java.io.BufferedReader
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)



        // set up back button via navigation controller

        val navController = Navigation.findNavController(this, R.id.myNavHostFragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        val toolbar =  findViewById<Toolbar>(R.id.toolbar)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        toolbar.inflateMenu(R.menu.main_menu)
        toolbar.setOnMenuItemClickListener({

            it.onNavDestinationSelected(navController) || super.onOptionsItemSelected(it)
        })

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
       val fragment = supportFragmentManager.findFragmentById(R.id.myNavHostFragment)!!
           .getChildFragmentManager().getFragments().get(0).id
        Log.e("fragment id", fragment.toString())
        return super.onPrepareOptionsMenu(menu)
    }


    override fun onSupportNavigateUp(): Boolean {
         return Navigation.findNavController(this, R.id.myNavHostFragment).navigateUp()
    }
}
