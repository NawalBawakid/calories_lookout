package com.calories.calorieslookout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.calories.calorieslookout.viewModel.OverviewViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    val model: OverviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.overviewFragment) as NavHostFragment
        navController = navHostFragment.navController

    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // types menu
            R.id.breakfast -> {
                model.getMealsPhotos("breakfast")
                return true
            }

            R.id.lunch -> {
                model.getMealsPhotos("lunch")
                return true
            }

            R.id.dinner -> {
                model.getMealsPhotos("dinner")
                return true
            }

            R.id.signout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(intent)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}