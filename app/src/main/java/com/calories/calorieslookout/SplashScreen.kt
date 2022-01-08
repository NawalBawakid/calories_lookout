package com.calories.calorieslookout

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashScreen: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val splashScreenTime = 700
        val homeIntent = Intent(this@SplashScreen, MainActivity::class.java)


        Handler().postDelayed({
            startActivity(homeIntent)
            finish()
        }, splashScreenTime.toLong())

        val animation = AnimationUtils.loadAnimation(this, R.anim.circle_animation)
        val logo = findViewById<ImageView>(R.id.logo)

        logo.startAnimation(animation)

    }

}