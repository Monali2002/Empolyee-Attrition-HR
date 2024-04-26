package com.example.insightcheck_hr.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.insightcheck_hr.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val logo : ImageView = findViewById(R.id.logo)
        logo.alpha = 0f
        logo.animate().setDuration(1500).alpha(1f).withEndAction{
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()

        }
    }
}