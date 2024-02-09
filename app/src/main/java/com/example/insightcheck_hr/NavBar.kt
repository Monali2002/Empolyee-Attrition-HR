package com.example.insightcheck_hr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.insightcheck_hr.databinding.ActivityNavBarBinding

class NavBar : AppCompatActivity() {

    private lateinit var binding: ActivityNavBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavBarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId) {

                R.id.home -> replaceFragment(Home())
                R.id.search -> replaceFragment(Search())
                R.id.analysis -> replaceFragment(Analysis())
                R.id.notifications -> replaceFragment(Notifications())
                R.id.profile -> replaceFragment(Profile())

                else ->{
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()

    }

}
