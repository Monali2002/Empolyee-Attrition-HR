package com.example.insightcheck_hr.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import com.example.insightcheck_hr.R
import com.example.insightcheck_hr.databinding.ActivitySignUpPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpPage : BaseActivity() {
    private var binding:ActivitySignUpPageBinding? = null
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpPageBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        auth = Firebase.auth

        binding?.tvLoginPage?.setOnClickListener {
            startActivity(Intent(this,LoginPage::class.java))
            finish()
        }

        binding?.btnSignUp?.setOnClickListener { registerUser() }
    }

    private fun registerUser()
    {
        val name = binding?.etSinUpName?.text.toString()
        val email = binding?.etSinUpEmail?.text.toString()
        val password = binding?.etSinUpPassword?.text.toString()
        if (validateForm(name, email, password))
        {
            showProgressBar()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){task ->
                    if (task.isSuccessful)
                    {
                        showToast(this,"User id Created Successful")
                        hideProgressBar()
                        startActivity(Intent(this,NavBar::class.java))
                        finish()
                    }
                    else {
                        showToast(this, "User id not Created")
                        hideProgressBar()
                    }
                }
        }
    }

    private fun validateForm(name:String, email:String,password:String):Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                binding?.tilName?.error = "Enter name"
                false
            }

            TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding?.tilEmail?.error = "Enter valid email address"
                false
            }

            TextUtils.isEmpty(password) -> {
                binding?.tilPassword?.error = "Enter password"
                false
            }

            else -> {
                true
            }
        }
    }
}