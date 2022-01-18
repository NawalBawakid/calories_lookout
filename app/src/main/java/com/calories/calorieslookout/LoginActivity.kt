package com.calories.calorieslookout

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.calories.calorieslookout.databinding.ActivityLoginBinding
import android.preference.PreferenceManager
import android.util.Log
import android.view.View

import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityLoginBinding
    lateinit var bottomNavigation:BottomNavigationView

   // private val favoratFragment = FavoriteFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//      setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // animation
        val animation = AnimationUtils.loadAnimation(this, R.anim.top_to_buttom_nimation)
        val logo = findViewById<ImageView>(R.id.logo)
        val appName = findViewById<TextView>(R.id.calories)
        val registration = findViewById<Button>(R.id.signin)

//        logo.startAnimation(animation)
//        appName.startAnimation(animation)
//        registration.startAnimation(animation)



        // Bottom Navigation
         bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomnav)
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


//        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
//        val Islogin = prefs.getBoolean("Islogin", false)
//        prefs.edit().putBoolean("Islogin", Islogin).commit()
//
//        if (Islogin) {
//
//        } else {
//
//        }


//        var userId= FirebaseAuth.getInstance().currentUser?.uid?:""
//        if(userId == null){
//            val intent = Intent (getActivity(), LoginActivity::class.java)
//                getActivity()?.startActivity(intent)
//            }else{
//                val intent = Intent (getActivity(), BreakfastFragment::class.java)
//                getActivity()?.startActivity(intent)
//            }




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