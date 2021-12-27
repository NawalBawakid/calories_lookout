package com.calories.calorieslookout

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.calories.calorieslookout.viewModel.OverviewViewModel
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    //var firebaseAuth = FirebaseAuth()

    val model: OverviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.overviewFragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // types menu
            R.id.breakfast -> {
                model.getMealsPhotos("breakfast")
//                var action = BreakfastDescriptionFragmentDirections.actionBreakfastDescriptionFragmentToBreakfastFragment()
//                findNavController(R.id.overviewFragment).navigate(action)
                return true
            }

            R.id.lunch -> {
                model.getMealsPhotos("lunch")
//                var action = BreakfastDescriptionFragmentDirections.actionBreakfastDescriptionFragmentToBreakfastFragment()
//                findNavController(R.id.overviewFragment).navigate(action)
                return true
            }

            R.id.dinner -> {
                model.getMealsPhotos("dinner")
//                var action = BreakfastDescriptionFragmentDirections.actionBreakfastDescriptionFragmentToBreakfastFragment()
//                findNavController(R.id.overviewFragment).navigate(action)
                return true
            }

            R.id.calculation -> {
                val queryUrl: Uri = Uri.parse("https://apps.apple.com/sa/app/lifesum-healthy-eating/id286906691")
                val intent = Intent(Intent.ACTION_VIEW, queryUrl)
                this?.startActivity(intent)
                return true
            }

            R.id.signout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(intent)
//                var action = BreakfastDescriptionFragmentDirections.actionBreakfastDescriptionFragmentToBreakfastFragment()
//                findNavController(R.id.overviewFragment).navigate(action)

//                var action = BreakfastFragmentDirections.actionBreakfastFragmentToNavGraph2()
//                findNavController(R.id.overviewFragment).navigate(action)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}