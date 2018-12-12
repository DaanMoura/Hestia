package com.ufscar.mobile.hestiaapp.cenarios.splash_screen

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.ufscar.mobile.hestiaapp.R
import com.ufscar.mobile.hestiaapp.cenarios.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val cm: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        val isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting

        if(!isConnected) {
            Toast.makeText(this, "Verifique sua conexão", Toast.LENGTH_SHORT).show()
            recarregar.visibility = View.VISIBLE
            recarregar.setOnClickListener {
                startActivity(intentFor<SplashScreenActivity>().newTask().clearTask())
            }
        }


        Handler().postDelayed({
            if(isConnected) {
                startActivity(intentFor<MainActivity>())
                finish()
            }
        }, 1000)
    }
}
