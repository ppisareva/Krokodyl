package com.example.krokodyl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        // set up back button via navigation controller

        val navController = Navigation.findNavController(this, R.id.myNavHostFragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)
        findViewById<Toolbar>(R.id.toolbar).inflateMenu(R.menu.main_menu)


        findViewById<Toolbar>(R.id.toolbar).setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener {

            it.onNavDestinationSelected(navController) || super.onOptionsItemSelected(it)
        })
    }



    override fun onSupportNavigateUp(): Boolean {
         return Navigation.findNavController(this, R.id.myNavHostFragment).navigateUp()
    }
}
