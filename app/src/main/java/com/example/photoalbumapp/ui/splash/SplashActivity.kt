package com.example.photoalbumapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.photoalbumapp.databinding.ActivitySplashBinding
import com.example.photoalbumapp.ui.main.MainActivity
import com.example.photoalbumapp.utils.Constants
import com.example.photoalbumapp.utils.SettingManager
import com.example.photoalbumapp.utils.viewbinding.viewBindings

class SplashActivity : AppCompatActivity() {

    private val binding: ActivitySplashBinding by viewBindings()
    val SPLASH_TIME_OUT = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.loading.isEnabled = true
        SettingManager.init(this)
        SettingManager.setValue(Constants().SHAREDID, "djlCbGusTJamg_ca4axEVw")
        val intent = Intent(this, MainActivity::class.java)
        navigateToNextActivity(intent)
    }

    fun navigateToNextActivity(intent: Intent) {
        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }
}