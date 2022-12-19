package com.example.genshininfos.presentation.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityOptionsCompat
import com.example.genshininfos.R
import com.example.genshininfos.databinding.ActivitySplashBinding
import com.example.genshininfos.presentation.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
        private lateinit var binding: ActivitySplashBinding
        private val auth = FirebaseAuth.getInstance()
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivitySplashBinding.inflate(layoutInflater)
            setContentView(binding.root)
            val handler = Handler()
            handler.postDelayed({
                // do something after 1000ms
                setUpScreen()
            }, 2000)
        }

        fun setUpScreen() {
            if (auth.currentUser != null) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()

            } else {
                val intent = Intent(this, LoginActivity::class.java)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@SplashActivity,
                    binding.imageView5,
                    "profile"
                )
                startActivity(intent, options.toBundle())
                finish()
            }

        }
    }
