package com.calories.calorieslookout

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.calories.calorieslookout.databinding.ActivityLoginBinding
import android.preference.PreferenceManager

import android.content.SharedPreferences
import androidx.core.app.ActivityCompat.startActivityForResult

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class LoginActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityLoginBinding

   // private val favoratFragment = FavoriteFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//      setContentView(R.layout.activity_login)


        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomnav)
        val navController = findNavController(R.id.nav_host_fragment_content_login)

        bottomNavigation.setupWithNavController(navController)


     //   replaceFragment(favoratFragment)

        //binding.content.bottomnav.visibility = View.GONE
//        setSupportActionBar(binding.toolbar)
//
//        val navController = findNavController(R.id.nav_host_fragment_content_login)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//
//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }


        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val Islogin = prefs.getBoolean("Islogin", false)
        prefs.edit().putBoolean("Islogin", Islogin).commit()

        if (Islogin) {

        } else {

        }



    }

//    private fun replaceFragment(fragment: Fragment){
//        if (fragment != null){
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.fragmentContainer, fragment)
//            transaction.commit()
//        }
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_login)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}