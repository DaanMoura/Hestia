package com.ufscar.mobile.hestiaapp.cenarios.splash_screen

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.ufscar.mobile.hestiaapp.R
import com.ufscar.mobile.hestiaapp.cenarios.main.MainActivity
import org.jetbrains.anko.intentFor

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(intentFor<MainActivity>())
            finish()
        }, 1000)
    }
}
