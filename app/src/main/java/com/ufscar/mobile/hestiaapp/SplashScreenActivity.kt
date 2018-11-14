package com.ufscar.mobile.hestiaapp

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.ufscar.mobile.hestiaapp.util.FirestoreUserUtil
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
